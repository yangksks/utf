<template>
  <div
    :class="{ recentList: !darkMode, recentListDark: darkMode }"
    v-for="(lecture, index) in this.recentLectures"
    :key="index"
    class="buttons"
    @click="goLecture(lecture)"
  >
    {{ lecture.title }}<br />
    {{ lecture.startTime }}
  </div>
</template>

<script>
import { mapActions, mapGetters } from "vuex";
export default {
  name: "RecetlyLecture",
  props: {
    darkMode: Boolean,
  },
  data() {
    return {
      recentLectures: [],
    };
  },
  methods: {
    ...mapActions("StatisticsStore", ["setRecently"]),
    ...mapGetters("StatisticsStore", ["getRecentLectures"]),
    goLecture(lecture) {
      console.log(lecture);
    },
  },
  mounted() {
    this.setRecently(4);
    this.recentLectures = this.getRecentLectures();
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
