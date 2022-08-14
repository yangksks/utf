<!-- eslint-disable prettier/prettier -->
<template>
  <div :class="{ card: !darkMode, cardDark: darkMode }">
    <div>
      <img src="@/assets/UTFLogo-removebg.png" alt="logo" style="width: 80%" />
      <!-- Button trigger modal -->
      <i class="bi bi-x" data-bs-toggle="modal" data-bs-target="#exampleModal" style="vertical-align: top; margin: 5px; font-size: 30px" @click="emitIndex(index)"></i>
      <!-- <button type="button" class="btn-close" data-bs-toggle="modal" data-bs-target="#exampleModal" aria-label="Close" style="vertical-align: top; margin: 5px" @click="emitIndex(index)"></button> -->
    </div>
    <div>{{ this.lectureRoom.title }}</div>
    <div style="text-align: center"><button type="button" class="btn btn-success" style="width: 45%">통계 보기</button></div>
  </div>
</template>

<script>
export default {
  props: {
    index: Number,
    darkMode: Boolean,
    lectureRoom: Object,
  },
  methods: {
    emitIndex(index) {
      console.log("index : " + index);
      this.$emit("emitIndex", index);
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
