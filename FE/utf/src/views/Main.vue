<template>
  <b-container>
    <b-row>
      <b-col sm="3" class="left">
        <logo />
        <profile />

        <h2>Recently</h2>
        <recetly-lecture />
      </b-col>
      <b-col sm="9" class="right">
        <h2>Class</h2>
        <b-row class="cards">
          <b-col sm="3" v-for="(lecture, index) in lectures" :key="index">
            <lecture-item></lecture-item>
            <div class="w-100" v-if="index % 4 == 0"></div>
          </b-col>
          <div id="add-lecture-card">
            <button>+</button>
          </div>
        </b-row>
      </b-col>
    </b-row>
    <!-- <div class="left">
    </div>

    <div class="right">
    </div> -->
  </b-container>
</template>

<script>
import Logo from "../components/Logo.vue";
import Profile from "../components/Profile.vue";
import RecetlyLecture from "../components/RecentlyLecture.vue";
import LectureItem from "../components/LectureItem.vue";

export default {
  name: "MainVue",
  components: {
    Logo,
    Profile,
    RecetlyLecture,
    LectureItem,
  },
  data() {
    return {
      lectures: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15],
    };
  },
  methods: {
    getLectruesByUserId() {},
  },
};
</script>

<style>
.left {
  background-color: aqua;
}
.right {
  background-color: bisque;
}
#add-lecture-card {
  border: 1px solid gray;
  border-radius: 15px;
}
</style>
