<!-- eslint-disable prettier/prettier -->
<template>
  <div class="flip-card">
    <div class="flip-card-inner" :class="{ flip: isFlip }">
      <div class="buttons" :class="{ 'flip-card-front': !darkMode, 'flip-card-frontDark': darkMode }" @click="flip()">
        <i class="bi bi-plus" style="font-size: 130px"></i>
      </div>
      <div :class="{ 'flip-card-back': !darkMode, 'flip-card-backDark': darkMode }">
        강의명
        <input type="text" id="lectureName" />
        <button type="button" class="btn btn-primary" @click="addLecture()">개설</button>
        <button type="button" class="btn btn-secondary" @click="flip()">취소</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    darkMode: Boolean,
  },
  data() {
    return {
      isFlip: false,
    };
  },
  methods: {
    addLecture() {
      this.$emit("registLecture", document.querySelector("#lectureName").value);
      this.flip();
    },
    flip() {
      this.isFlip = !this.isFlip;
      document.querySelector("#lectureName").value = "";
      this.$emit("addBtnFlip");
    },
  },
};
</script>

<style scoped>
/* The flip card container - set the width and height to whatever you want. We have added the border property to demonstrate that the flip itself goes out of the box on hover (remove perspective if you don't want the 3D effect */
.flip-card {
  background-color: transparent;
  width: 100%;
  perspective: 1000px; /* Remove this if you don't want the 3D effect */
}

/* This container is needed to position the front and back side */
.flip-card-inner {
  position: relative;
  width: 100%;
  text-align: center;
  transition: transform 0.8s;
  transform-style: preserve-3d;
}

/* Do an horizontal flip when you move the mouse over the flip box container */
/* .flip-card:hover .flip-card-inner {
  transform: rotateY(180deg);
} */
.flip {
  transform: rotateY(180deg);
}

/* Position the front and back side */
.flip-card-front,
.flip-card-back {
  position: absolute;
  width: 100%;
  height: 196px;
  border: 1px solid #c1c1c1;
  border-radius: 20px;
  -webkit-backface-visibility: hidden; /* Safari */
  backface-visibility: hidden;
}
.flip-card-frontDark,
.flip-card-backDark {
  position: absolute;
  width: 100%;
  height: 196px;
  border: 1px solid #3e3e3e;
  border-radius: 20px;
  -webkit-backface-visibility: hidden; /* Safari */
  backface-visibility: hidden;
}

/* Style the front side (fallback if image is missing) */
.flip-card-front {
  background-color: #ffffff;
}
.flip-card-frontDark {
  background-color: #202026;
}

/* Style the back side */
.flip-card-back {
  background-color: #ffffff;
  transform: rotateY(180deg);
}
.flip-card-backDark {
  background-color: #202026;
  transform: rotateY(180deg);
}
</style>
