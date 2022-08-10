<template>
  <div class="student-components">
    <waiting-room :stream-manager="publisher" @joinLecture="joinLecture" />
  </div>
</template>

<script>
import WaitingRoom from "@/components/lecture/student/WaitingRoom.vue";
import { OpenVidu } from "openvidu-browser";

export default {
  name: "StudentComponents",
  components: { WaitingRoom },
  props: {},
  data() {
    return {
      OVCamera: undefined,
      sessionCamera: undefined,
      publisher: undefined,
      mySessionId: "SessionA",
      myName: undefined,

      isWait: false,
    };
  },
  methods: {
    joinSession() {
      this.OVCamera = new OpenVidu();
      this.sessionCamera = this.OVCamera.initSession();

      this.publisher = this.OVCamera.initPublisher("video-container", {
        audioSource: undefined, // The source of audio. If undefined default microphone
        videoSource: undefined, // The source of video. If undefined default webcam
        publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
        publishVideo: true, // Whether you want to start publishing with your video enabled or not
        resolution: "640x480", // The resolution of your video
        frameRate: 30, // The frame rate of your video
        insertMode: "REPLACE", // How the video is inserted in the target element 'video-container'
        mirror: false, // Whether to mirror your local video or not
      });
    },

    joinLecture(name) {
      this.myName = name;
      this.isWait = true;
    },
  },
  mounted() {
    this.joinSession();
  },
};
</script>
