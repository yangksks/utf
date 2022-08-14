<!-- eslint-disable prettier/prettier -->
<template>
  <div :class="{ card: !darkMode, cardDark: darkMode }">
    <div>
      <img src="@/assets/UTFLogo-removebg.png" alt="logo" style="width: 80%" />
      <!-- Button trigger modal -->
      <i class="bi bi-x" data-bs-toggle="modal" data-bs-target="#deleteLectureModal" style="vertical-align: top; margin: 5px; font-size: 30px" @click="emitIndex(index)"></i>
      <!-- <button type="button" class="btn-close" data-bs-toggle="modal" data-bs-target="#exampleModal" aria-label="Close" style="vertical-align: top; margin: 5px" @click="emitIndex(index)"></button> -->
    </div>
    <div>{{ lecture["title"] }}</div>
    <div id="lectureCode">강의 코드 : {{ lecture["lectureRoomCode"] }}</div>
    <div><button type="button" class="btn btn-info" @click="copyCode">코드 복사</button></div>
    <div style="text-align: center"><button type="button" class="btn btn-success" style="width: 45%">통계 보기</button></div>
  </div>
</template>

<script>
export default {
  props: {
    lecture: Object,
    index: Number,
    darkMode: Boolean,
  },
  methods: {
    emitIndex(index) {
      console.log("index : " + index);
      this.$emit("emitIndex", index);
    },
    copyCode() {
      var text = this.lecture["lectureRoomCode"];

      navigator.clipboard
        .writeText(text)
        .then(() => {
          console.log("Text copied to clipboard");
        })
        .catch((err) => {
          console.error("Error in copying text: ", err);
        });
    },
  },
};
</script>

<style scoped>
.card {
  width: 100%;
  height: 100%;
  background-color: #ffffff;
  border: 1px solid #c1c1c1;
  border-radius: 20px;
}
.cardDark {
  width: 100%;
  height: 100%;
  background-color: #202026;
  border: 1px solid #3e3e3e;
  border-radius: 20px;
}
</style>
