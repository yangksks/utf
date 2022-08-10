<template>
  <div class="control-panel">
    <button @click="audioToggle">마이크</button>
    <button @click="videoToggle">비디오</button>
    <button @click="screenShare">화면공유</button>
    <button v-if="!recording" @click="recordingStart">수업시작</button>
    <button v-if="recording" @click="recordingEnd">수업종료</button>
    <button>강의종료</button>
    <button v-if="!isMissed" @click="missed">자리비움</button>
    <button v-if="isMissed" @click="comeback">복귀</button>
    <button @click="comeback">채팅</button>
    <button @click="comeback">참가자</button>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
export default {
  name: "ControlPanel",
  props: { recording: Boolean },
  data() {
    return {
      video: true,
      audio: true,
      isMissed: false,
      timer: undefined,
    };
  },
  computed: {
    ...mapState("lectureStore", ["publisher"]),
  },
  methods: {
    ...mapMutations("chatStore", {
      pushMessage: "PUSH_MSG_DATA",
    }),
    videoToggle() {
      this.video = !this.video;
      this.publisher.publishVideo(this.video);
    },
    audioToggle() {
      this.audio = !this.audio;
      this.publisher.publishAudio(this.audio);
    },
    screenShare() {
      this.$emit("screenShare");
    },

    recordingStart() {
      this.$emit("recordingStart");
    },
    recordingEnd() {
      this.$emit("recordingEnd");
    },
    /* Set timer when user absent */
    missed() {
      console.log("자리비움 시작");
      this.isMissed = true;
      this.timer = setTimeout(() => {
        const msg =
          this.publisher.session.connection.data.slice(15, -2) +
          "님이 자리를 오래 비웠습니다.";
        this.sendMessageTemplate(msg, undefined, "alert");
        console.log("5분 지남");
        // }, 5 * 60 * 1000);
      }, 5 * 1 * 1000);
    },
    /* Clear timer when user comeback */
    comeback() {
      clearTimeout(this.timer);
      this.isMissed = false;
      console.log("복귀했음");
    },
    /* A template for sending a message
     * Input: data(JSON: message), to(Array: target), type(String: message tag)
     */
    sendMessageTemplate(data, to, type) {
      this.publisher.session
        .signal({
          data: data, // Any string (optional)
          to: to, // Array of Connection objects (optional. Broadcast to everyone if empty)
          type: type, // The type of message (optional)
        })
        .then(() => {
          this.pushMessage({
            from: {
              name: this.publisher.session.connection.data.slice(15, -2),
              time: new Date().toLocaleTimeString(),
              type: type,
            },
            msg: data,
          });
          console.log("Message successfully sent");
        })
        .catch((error) => {
          console.error(error);
        });
      setTimeout(() => {
        const element = document.getElementById("chat-body");
        element.scrollTop = element.scrollHeight;
      }, 100);
    },
  },
};
</script>

<style scoped>
.control-panel {
  grid-column: 1/4;
  background: #f0f0ed;
}
</style>
