<template>
  <div class="lecture-room">
    <video-components
      :publisher="publisher"
      :subscribers="subscribers"
      :speaker="speaker"
    />
    <chat-components
      :publisher="publisher"
      :subscribers="subscribers"
    ></chat-components>
    <user-list-components></user-list-components>
    <control-panel @screenShare="screenShare"></control-panel>
  </div>
</template>

<script>
import ChatComponents from "@/components/chat/ChatComponents.vue";
import ControlPanel from "@/components/lecture/ControlPanel.vue";
import UserListComponents from "@/components/lecture/UserListComponents.vue";
import VideoComponents from "@/components/lecture/VideoComponents.vue";
import axios from "axios";
import { OpenVidu } from "openvidu-browser";
import { mapActions } from "vuex";

axios.defaults.headers.post["Content-Type"] = "application/json";
const OPENVIDU_SERVER_URL = "https://" + location.hostname + ":4443";
const OPENVIDU_SERVER_SECRET = "MY_SECRET";

export default {
  name: "lecture_room",
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
      sessionCamera: undefined,
      sessionScreen: undefined,
      publisher: undefined,
      subscribers: [],
      speaker: undefined,

      mySessionId: "SessionA",
      myUserName: "학생" + Math.floor(Math.random() * 100),
    };
  },
  methods: {
    ...mapActions("lectureStore", ["setPublisher"]),

    joinSession() {
      this.OVCamera = new OpenVidu();
      this.OVScreen = new OpenVidu();
      this.sessionCamera = this.OVCamera.initSession();
      this.sessionScreen = this.OVScreen.initSession();

      this.sessionCamera.on("streamCreated", ({ stream }) => {
        if (stream.typeOfVideo == "CAMERA") {
          const subscriber = this.sessionCamera.subscribe(stream);
          subscriber.on("publisherStartSpeaking", ({ streamId }) => {
            this.speaker = streamId;
          });
          subscriber.on("publisherStopSpeaking", () => {
            this.speaker = null;
          });

          this.subscribers.push(subscriber);
        }
      });

      this.sessionScreen.on("streamCreated", ({ stream }) => {
        if (stream.typeOfVideo == "SCREEN") {
          // screen-share ID의 엘리먼트에 공유된 화면을 뿌립니다
          this.sessionScreen.subscribe(stream, "screen-share");
        }
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
          .connect(token, { clientData: this.myUserName })
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
            this.setPublisher(this.publisher);
          })
          .catch((error) => {
            console.log(
              "There was an error connecting to the session:",
              error.code,
              error.message
            );
          });
      });

      this.getToken(this.mySessionId).then((tokenScreen) => {
        this.sessionScreen
          .connect(tokenScreen)
          .then(() => {
            console.log("Session screen connected");
          })
          .catch((error) => {
            console.warn(
              "There was an error connecting to the session:",
              error.code,
              error.message
            );
          });
      });

      window.addEventListener("beforeunload", this.leaveSession);
    },

    screenShare() {
      let publisherScreen = this.OVScreen.initPublisher("container-screens", {
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
    },

    leaveSession() {
      // --- Leave the session by calling 'disconnect' method over the Session object ---
      if (this.sessionCamera) this.sessionCamera.disconnect();

      this.sessionCamera = undefined;
      this.sessionScreen = undefined;
      this.publisher = undefined;
      this.subscribers = [];
      this.OVCamera = undefined;
      this.speaker = undefined;

      window.removeEventListener("beforeunload", this.leaveSession);
    },

    getToken(mySessionId) {
      return this.createSession(mySessionId).then((sessionId) =>
        this.createToken(sessionId)
      );
    },

    createSession(sessionId) {
      return new Promise((resolve, reject) => {
        axios
          .post(
            `${OPENVIDU_SERVER_URL}/openvidu/api/sessions`,
            JSON.stringify({
              customSessionId: sessionId,
            }),
            {
              auth: {
                username: "OPENVIDUAPP",
                password: OPENVIDU_SERVER_SECRET,
              },
            }
          )
          .then((response) => response.data)
          .then((data) => resolve(data.id))
          .catch((error) => {
            if (error.response.status === 409) {
              resolve(sessionId);
            } else {
              console.warn(
                `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`
              );
              if (
                window.confirm(
                  `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`
                )
              ) {
                location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
              }
              reject(error.response);
            }
          });
      });
    },

    createToken(sessionId) {
      return new Promise((resolve, reject) => {
        axios
          .post(
            `${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`,
            {},
            {
              auth: {
                username: "OPENVIDUAPP",
                password: OPENVIDU_SERVER_SECRET,
              },
            }
          )
          .then((response) => response.data)
          .then((data) => resolve(data.token))
          .catch((error) => reject(error.response));
      });
    },
  },

  mounted() {
    this.joinSession();
  },
};
</script>

<style>
.lecture-room {
  display: grid;
  grid-template-columns: 3fr 1fr 1fr;
  grid-template-rows: 1fr 70px;
  background: #f7f7f4;
  height: 100vh;
}
</style>
