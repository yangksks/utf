<template>
  <div>
    <ul>
      <li
        v-for="(lecture, index) in this.recentLecture"
        :key="index"
        @click="goLecture(lecture)"
      >
        {{ lecture }}
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: "RecetlyLecture",
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
ul {
  list-style: none;
}
li {
  background-color: aliceblue;
  margin: 10px;
  border-radius: 20px;
}
</style>
