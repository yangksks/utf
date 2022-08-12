<!-- eslint-disable prettier/prettier -->
<template>
  <div :class="{ recentList: !darkMode, recentListDark: darkMode }" v-for="(lecture, index) in this.recentLecture" :key="index" class="buttons" @click="goLecture(lecture)">
    {{ lecture }}
  </div>
</template>

<script>
export default {
  name: "RecetlyLecture",
  props: {
    darkMode: Boolean,
  },
  computed: {
    recentLecture() {
      return [1, 2, 3, 4, 5];
    },
  },
  methods: {
    goLecture(lecture) {
      console.log(lecture);
    },
  },
};
</script>

<style scoped>
.recentList {
  background-color: #ffffff;
  margin: 10px;
  border-radius: 10px;
  border: #c1c1c1 solid 1px;
}
.recentListDark {
  background-color: #202026;
  margin: 10px;
  border-radius: 10px;
  border: #3e3e3e solid 1px;
}
</style>
