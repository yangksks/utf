<template>
  <div class="control-panel" v-if="publisher">
    <div class="menu-1">
      <!-- <button @click="audioToggle">마이크</button>
      <button @click="videoToggle">비디오</button> -->
      <audio-button
        pill
        :size="buttonSize"
        :stream-manager="publisher"
        class="m-1"
      />
      <cam-button
        pill
        :size="buttonSize"
        :stream-manager="publisher"
        class="m-1"
      />
      <b-button class="screen-share-start m-1" pill @click="screenShare">
        <b-skeleton-icons>
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="buttonSize"
            height="buttonSize"
            fill="currentColor"
            class="bi bi-box-arrow-up"
            viewBox="-2 0 20 20"
          >
            <path
              fill-rule="evenodd"
              d="M3.5 6a.5.5 0 0 0-.5.5v8a.5.5 0 0 0 .5.5h9a.5.5 0 0 0 .5-.5v-8a.5.5 0 0 0-.5-.5h-2a.5.5 0 0 1 0-1h2A1.5 1.5 0 0 1 14 6.5v8a1.5 1.5 0 0 1-1.5 1.5h-9A1.5 1.5 0 0 1 2 14.5v-8A1.5 1.5 0 0 1 3.5 5h2a.5.5 0 0 1 0 1h-2z"
            />
            <path
              fill-rule="evenodd"
              d="M7.646.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1-.708.708L8.5 1.707V10.5a.5.5 0 0 1-1 0V1.707L5.354 3.854a.5.5 0 1 1-.708-.708l3-3z"
            />
          </svg>
        </b-skeleton-icons>
      </b-button>
      <!-- <b-button
        class="screen-share-end m-1"
        v-if="isShared"
        pill
        @click="screenShare"
      >
        <b-skeleton-icons>
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="buttonSize"
            height="buttonSize"
            fill="currentColor"
            class="bi bi-stop"
            viewBox="0 0 16 16"
          >
            <path
              d="M3.5 5A1.5 1.5 0 0 1 5 3.5h6A1.5 1.5 0 0 1 12.5 5v6a1.5 1.5 0 0 1-1.5 1.5H5A1.5 1.5 0 0 1 3.5 11V5zM5 4.5a.5.5 0 0 0-.5.5v6a.5.5 0 0 0 .5.5h6a.5.5 0 0 0 .5-.5V5a.5.5 0 0 0-.5-.5H5z"
            />
          </svg>
        </b-skeleton-icons>
      </b-button> -->
      <b-button
        class="class-start m-1"
        pill
        v-if="!recording"
        @click="recordingStart"
      >
        <b-skeleton-icons>
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="buttonSize"
            height="buttonSize"
            fill="red"
            class="bi bi-circle-fill"
            viewBox="0 0 16 16"
          >
            <circle cx="8" cy="8" r="4" />
          </svg>
        </b-skeleton-icons>
      </b-button>
      <b-button
        class="class-end m-1"
        pill
        v-if="recording"
        @click="recordingEnd"
      >
        <b-skeleton-icons>
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="buttonSize"
            height="buttonSize"
            fill="red"
            class="bi bi-square-fill"
            viewBox="-8 -8 32 32"
          >
            <path
              d="M0 2a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V2z"
            />
          </svg>
        </b-skeleton-icons>
      </b-button>
      <b-button class="exit m-1" pill>
        <b-skeleton-icons>
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="buttonSize"
            height="buttonSize"
            fill="currentColor"
            class="bi bi-box-arrow-right"
            viewBox="-4 -2 20 20"
          >
            <path
              fill-rule="evenodd"
              d="M10 12.5a.5.5 0 0 1-.5.5h-8a.5.5 0 0 1-.5-.5v-9a.5.5 0 0 1 .5-.5h8a.5.5 0 0 1 .5.5v2a.5.5 0 0 0 1 0v-2A1.5 1.5 0 0 0 9.5 2h-8A1.5 1.5 0 0 0 0 3.5v9A1.5 1.5 0 0 0 1.5 14h8a1.5 1.5 0 0 0 1.5-1.5v-2a.5.5 0 0 0-1 0v2z"
            />
            <path
              fill-rule="evenodd"
              d="M15.854 8.354a.5.5 0 0 0 0-.708l-3-3a.5.5 0 0 0-.708.708L14.293 7.5H5.5a.5.5 0 0 0 0 1h8.793l-2.147 2.146a.5.5 0 0 0 .708.708l3-3z"
            />
          </svg>
        </b-skeleton-icons>
      </b-button>
      <b-button class="missing m-1" pill v-if="!isMissed" @click="missed">
        <b-skeleton-icons>
          <svg
            xmlns="http://www.w3.org/2000/svg"
            :width="buttonSize"
            :height="buttonSize"
            fill="currentColor"
            class="bi bi-clock"
            viewBox="0 0 16 16"
          >
            <path
              d="M8 3.5a.5.5 0 0 0-1 0V9a.5.5 0 0 0 .252.434l3.5 2a.5.5 0 0 0 .496-.868L8 8.71V3.5z"
            />
            <path
              d="M8 16A8 8 0 1 0 8 0a8 8 0 0 0 0 16zm7-8A7 7 0 1 1 1 8a7 7 0 0 1 14 0z"
            />
          </svg>
        </b-skeleton-icons>
      </b-button>
      <b-button class="comeback m-1" pill v-if="isMissed" @click="comeback">
        <b-skeleton-icons>
          <svg
            xmlns="http://www.w3.org/2000/svg"
            :width="buttonSize"
            :height="buttonSize"
            fill="currentColor"
            class="bi bi-clock-fill"
            viewBox="0 0 16 16"
          >
            <path
              d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zM8 3.5a.5.5 0 0 0-1 0V9a.5.5 0 0 0 .252.434l3.5 2a.5.5 0 0 0 .496-.868L8 8.71V3.5z"
            />
          </svg>
        </b-skeleton-icons>
      </b-button>
    </div>
    <div class="menu-2">
      <b-button
        class="chatting m-1"
        pill
        :class="[isChatOpened ? '' : 'clicked']"
        @click="chatToggle"
      >
        <b-skeleton-icons>
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="buttonSize"
            height="buttonSize"
            fill="currentColor"
            class="bi bi-chat-left-dots-fill"
            viewBox="0 0 16 16"
          >
            <path
              d="M0 2a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v8a2 2 0 0 1-2 2H4.414a1 1 0 0 0-.707.293L.854 15.146A.5.5 0 0 1 0 14.793V2zm5 4a1 1 0 1 0-2 0 1 1 0 0 0 2 0zm4 0a1 1 0 1 0-2 0 1 1 0 0 0 2 0zm3 1a1 1 0 1 0 0-2 1 1 0 0 0 0 2z"
            />
          </svg>
        </b-skeleton-icons>
      </b-button>
      <b-button
        class="participant m-1"
        pill
        :class="[isParticipantOpened ? '' : 'clicked']"
        @click="participantToggle"
      >
        <b-skeleton-icons>
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="buttonSize"
            height="buttonSize"
            fill="currentColor"
            class="bi bi-people-fill"
            viewBox="0 0 16 16"
          >
            <path
              d="M7 14s-1 0-1-1 1-4 5-4 5 3 5 4-1 1-1 1H7zm4-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"
            />
            <path
              fill-rule="evenodd"
              d="M5.216 14A2.238 2.238 0 0 1 5 13c0-1.355.68-2.75 1.936-3.72A6.325 6.325 0 0 0 5 9c-4 0-5 3-5 4s1 1 1 1h4.216z"
            />
            <path d="M4.5 8a2.5 2.5 0 1 0 0-5 2.5 2.5 0 0 0 0 5z" />
          </svg>
        </b-skeleton-icons>
      </b-button>
    </div>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
import AudioButton from "@/components/common/AudioButton";
import CamButton from "@/components/common/CamButton";
export default {
  name: "ControlPanel",
  props: { recording: Boolean },
  components: {
    AudioButton,
    CamButton,
  },
  data() {
    return {
      video: true,
      audio: true,
      isMissed: false,
      isChatOpened: true,
      isParticipantOpened: true,
      timer: undefined,
      buttonSize: 45,
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
    chatToggle() {
      this.isChatOpened = !this.isChatOpened;
      this.$emit("reSize", this.isChatOpened, this.isParticipantOpened);
    },
    participantToggle() {
      this.isParticipantOpened = !this.isParticipantOpened;
      this.$emit("reSize", this.isChatOpened, this.isParticipantOpened);
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
  display: flex;
}
.menu-1 {
  width: 66.6%;
}
.menu-2 {
  width: 33.3%;
}
.class-end {
  background: #198754;
}
.missing {
  background: #565e64;
}
.clicked {
  background: black;
}
.comeback {
  background-color: #0d6efd;
}
.exit {
  background-color: red;
}
</style>
