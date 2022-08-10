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
          <b-col sm="3" id="add-lecture-card">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="#000000"
              viewBox="0 0 50 50"
              width="80px"
              height="80px"
            >
              <path
                fill="none"
                stroke="#000000"
                stroke-miterlimit="10"
                stroke-width="2"
                d="M13 25L37 25M25 13L25 37M25 3A22 22 0 1 0 25 47 22 22 0 1 0 25 3z"
              />
            </svg>
          </b-col>
        </b-row>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import Logo from "@/components/common/Logo.vue";
import Profile from "@/components/main/Profile.vue";
import RecetlyLecture from "@/components/main/RecentlyLecture.vue";
import LectureItem from "@/components/main/LectureItem.vue";

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
      lectures: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14],
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
