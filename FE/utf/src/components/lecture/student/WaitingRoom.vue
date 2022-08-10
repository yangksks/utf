<template>
  <div class="waiting-room">
    <b-container fluid style="height: 100px"
      ><logo class="h-100"
    /></b-container>
    <b-container class="waiting-room-container bg-light">
      <div class="position-relative" v-if="streamManager">
        <ov-video :stream-manager="streamManager" class="webcam" />
        <div class="buttons w-100">
          <audio-button
            pill
            :size="45"
            :stream-manager="streamManager"
            class="m-1"
          />
          <cam-button
            pill
            :size="45"
            :stream-manager="streamManager"
            class="m-1"
          />
        </div>
      </div>
      <div class="input-group mt-2 w-25 mx-auto">
        <b-form-input v-model="myName" placeholder="Enter your name" />
        <div class="input-group-append mx-2">
          <b-button :disabled="myName" variant="primary" @click="joinLecture"
            >입장</b-button
          >
        </div>
      </div>
    </b-container>
  </div>
</template>

<script>
import Logo from "@/components/common/Logo.vue";
import OvVideo from "@/components/lecture/OvVideo.vue";
import CamButton from "@/components/common/CamButton.vue";
import AudioButton from "@/components/common/AudioButton.vue";

export default {
  name: "WaitingRoom",
  components: {
    AudioButton,
    CamButton,
    Logo,
    OvVideo,
  },
  props: { streamManager: Object },
  data() {
    return {
      publishAudio: true,
      publishVideo: true,
      myName: "",
    };
  },
  methods: {
    joinLecture() {
      this.$emit("joinLecture", this.myName);
    },
  },
};
</script>

<style>
.waiting-room {
  background: #c1c1c1;
  height: 100vh;
}

.waiting-room-container {
  height: 600px;
  border-radius: 25px;
}

.webcam {
  border-radius: 15px;
}

.buttons {
  position: absolute;
  bottom: 1vh;
}

.btn {
  background-color: #2b80ff;
  width: 70px;
  color: #ffffff;
}
</style>
