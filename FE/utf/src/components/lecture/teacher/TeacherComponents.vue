<template>
  <div
    class="teacher-components"
    :class="{
      'both-item': thema == 'both',
      'no-chat': thema == 'noChat',
      'no-participant': thema == 'noParticipant',
      'neither-item': thema == 'neither',
    }"
  >
    <video-components
      :publisher="publisher"
      :subscribers="subscribers"
      :speaker="speaker"
    />
    <chat-components
      v-if="publisher"
      :publisher="publisher"
      :subscribers="subscribers"
    ></chat-components>
    <user-list-components :subscribers="subscribers"></user-list-components>
    <control-panel
      :publisher="publisher"
      @screenShare="screenShare"
      @recordingStart="recordingStart"
      @recordingEnd="recordingEnd"
      @reSize="reSize"
      :recording="recording"
    ></control-panel>
  </div>
</template>
<script>
import ChatComponents from "@/components/chat/ChatComponents.vue";
import ControlPanel from "@/components/lecture/teacher/ControlPanel.vue";
import UserListComponents from "@/components/lecture/UserListComponents.vue";
import VideoComponents from "@/components/lecture/teacher/VideoComponents.vue";
import { OpenVidu } from "openvidu-browser";
import {
  createTokenApi,
  createSessionApi,
  closeSessionApi,
  recordingStartApi,
  recordingStopApi,
} from "@/api/openvidu.js";
import store from "@/store";

export default {
  name: "TeacherComponents",
  components: {
    ChatComponents,
    ControlPanel,
    UserListComponents,
    VideoComponents,
  },
  data() {
    return {
      OVCamera: undefined,
      OVScreen: undefined,
      publisher: undefined,
      recording: false,
      recordingId: undefined,
      sessionCamera: undefined,
      sessionScreen: undefined,
      subscribers: [],
      speaker: undefined,

      thema: "both",
      mySessionId: "Session_A",
      myUserName: store.state.userInfo["userName"],
    };
  },
  methods: {
    joinSession() {
      this.OVCamera = new OpenVidu();
      this.OVScreen = new OpenVidu();
      this.sessionCamera = this.OVCamera.initSession();
      this.sessionScreen = this.OVScreen.initSession();

      this.sessionCamera.on("streamCreated", ({ stream }) => {
        const subscriber = this.sessionCamera.subscribe(stream);

        subscriber.on("publisherStartSpeaking", ({ streamId }) => {
          this.speaker = streamId;
        });
        subscriber.on("publisherStopSpeaking", () => {
          this.speaker = null;
        });
        this.subscribers.push(subscriber);
      });

      this.sessionCamera.on("streamDestroyed", ({ stream }) => {
        const index = this.subscribers.indexOf(stream.streamManager, 0);
        if (index >= 0) {
          this.subscribers.splice(index, 1);
        }
      });
      this.sessionCamera.on("exception", ({ exception }) => {
        console.warn(exception);
      });

      this.getToken(this.mySessionId).then((token) => {
        this.sessionCamera
          .connect(token, { clientData: this.myUserName, role: "teacher" })
          .then(() => {
            // --- Get your own camera stream with the desired properties ---
            let publisher = this.OVCamera.initPublisher(undefined, {
              audioSource: undefined, // The source of audio. If undefined default microphone
              videoSource: undefined, // The source of video. If undefined default webcam
              publishAudio: false, // Whether you want to start publishing with your audio unmuted or not
              publishVideo: true, // Whether you want to start publishing with your video enabled or not
              resolution: "640x480", // The resolution of your video
              frameRate: 30, // The frame rate of your video
              insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
              mirror: false, // Whether to mirror your local video or not
            });

            publisher.on("publisherStartSpeaking", () => {
              this.speaker = "me";
            });
            publisher.on("publisherStopSpeaking", () => {
              this.speaker = null;
            });

            this.publisher = publisher;
            this.sessionCamera.publish(this.publisher);
          })
          .catch((error) => {
            console.log(
              "There was an error connecting to the session:",
              error.code,
              error.message
            );
          });
      });

      window.addEventListener("beforeunload", this.leaveSession);
    },

    screenShare() {
      this.getToken(this.mySessionId).then((token) => {
        this.sessionScreen
          .connect(token, { clientData: this.myUserName })
          .then(() => {
            let publisherScreen = this.OVScreen.initPublisher(undefined, {
              videoSource: "screen",
              publishAudio: false,
            });

            publisherScreen.once("accessAllowed", () => {
              publisherScreen.stream
                .getMediaStream()
                .getVideoTracks()[0]
                .addEventListener("ended", () => {
                  this.sessionScreen.unpublish(publisherScreen);
                  console.log('User pressed the "Stop sharing" button');
                });

              this.sessionScreen.publish(publisherScreen);
            });
            publisherScreen.on("videoElementCreated", function (event) {
              event.element["muted"] = true;
            });
            publisherScreen.once("accessDenied", () => {
              console.warn("ScreenShare: Access Denied");
            });
          })
          .catch((error) => {
            console.log(
              "There was an error connecting to the session:",
              error.code,
              error.message
            );
          });
      });
    },

    recordingStart() {
      this.recording = true;
      recordingStartApi(
        this.mySessionId,
        ({ data }) => {
          this.RECORDING_ID = data.id;
          console.log("레코딩 id", this.RECORDING_ID);
        },
        (error) => console.log(error)
      );
    },

    recordingEnd() {
      this.recording = false;
      recordingStopApi(
        this.RECORDING_ID,
        (response) => {
          console.log(response);
        },
        (error) => console.log(error)
      );
    },

    reSize(chat, participant) {
      if (chat && participant) this.thema = "both";
      else if (!chat && participant) this.thema = "noChat";
      else if (chat && !participant) this.thema = "noParticipant";
      else this.thema = "neither";
    },

    leaveSession() {
      // --- Leave the session by calling 'disconnect' method over the Session object ---
      if (this.sessionCamera) this.sessionCamera.disconnect();
      if (this.sessionScreen) this.sessionScreen.disconnect();
      this.sessionCamera = undefined;
      this.sessionScreen = undefined;
      this.publisher = undefined;
      this.subscribers = [];
      this.OVCamera = undefined;
      this.speaker = undefined;

      closeSessionApi(this.sessionId);

      window.removeEventListener("beforeunload", this.leaveSession);
    },

    getToken(mySessionId) {
      return this.createSession(mySessionId).then((sessionId) =>
        this.createToken(sessionId)
      );
    },

    createSession(sessionId) {
      return new Promise((resolve, reject) => {
        createSessionApi(
          sessionId,
          ({ data }) => resolve(data.id),
          (error) => {
            if (error.response.status === 409) {
              resolve(sessionId);
            } else {
              reject(error.response);
            }
          }
        );
      });
    },

    createToken(sessionId) {
      return new Promise((resolve, reject) => {
        createTokenApi(
          sessionId,
          "PUBLISHER",
          ({ data }) => resolve(data.token),
          (error) => reject(error.response)
        );
      });
    },
  },

  mounted() {
    this.joinSession();
  },
};
</script>
<style>
.teacher-components {
  display: grid;
  /* grid-template-columns: 3fr 1fr 1fr; */
  grid-template-rows: 1fr 70px;
  background: #c1c1c1;
  height: 100vh;
}
.both-item {
  grid-template-columns: 3fr 1fr 1fr;
}
.no-chat {
  grid-template-columns: 4fr 0fr 1fr;
}
.no-participant {
  grid-template-columns: 4fr 1fr 0fr;
}
.neither-item {
  grid-template-columns: 5fr 0fr 0fr;
}
</style>
