<template>
  <div class="control-panel">
    <button @click="audioToggle">마이크</button>
    <button @click="videoToggle">비디오</button>
    <button @click="screenShare">화면공유</button>
    <button>수업시작</button>
    <button>강의종료</button>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "control-panel",
  props: [],
  data() {
    return {
      video: true,
      audio: true,
    };
  },
  computed: {
    ...mapState("lectureStore", ["publisher"]),
  },
  methods: {
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
  },
};
</script>

<style scoped>
.control-panel {
  grid-column: 1/4;
  background: #f0f0ed;
}
</style>
