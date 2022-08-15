<!-- eslint-disable prettier/prettier -->
<template>
  <div :class="{ card: !darkMode, cardDark: darkMode }">
    <div style="display: flex">
      <img :src="getImgSrc()" alt="logo" style="width: 70%; margin: 15px" />
      <!-- Button trigger modal -->
      <i
        class="bi bi-x"
        data-bs-toggle="modal"
        data-bs-target="#deleteLectureModal"
        style="vertical-align: top; margin: 5px; font-size: 30px"
        @click="emitIndex(index)"
      ></i>
      <!-- <button type="button" class="btn-close" data-bs-toggle="modal" data-bs-target="#exampleModal" aria-label="Close" style="vertical-align: top; margin: 5px" @click="emitIndex(index)"></button> -->
    </div>
    <div>
      <h5>{{ lecture["title"] }}</h5>
    </div>
    <div>
      <button
        type="button"
        class="btn btn-info"
        style="margin: 7px"
        @click="copyCode"
      >
        URL 복사
      </button>
      <button
        type="button"
        class="btn btn-success"
        style="margin: 7px"
        @click="goLecture(this.lecture.lectureRoomId)"
      >
        통계 보기
      </button>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    lecture: Object,
    index: Number,
    darkMode: Boolean,
    lectureRoom: Object,
  },
  methods: {
    emitIndex(index) {
      console.log("index : " + index);
      this.$emit("emitIndex", index);
    },
    copyCode() {
      var text =
        "http://localhost:3000/lecture/" + this.lecture["lectureRoomCode"];
      console.log(text);
      navigator.clipboard
        .writeText(text)
        .then(() => {
          console.log("Text copied to clipboard");
          alert("복사되었습니다");
        })
        .catch((err) => {
          console.error("Error in copying text: ", err);
        });
    },
    getImgSrc() {
      return require("../../assets/image/subjectIcon/subject" +
        this.lecture["subject"] +
        ".png");
    },
    goLecture(lectureRoomId) {
      this.$router.push(`/lectureRoom/${lectureRoomId}`);
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
