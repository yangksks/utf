<template>
  <div class="background">
    <b-container>
      <logo class="logo" :size="40" />
      <b-row>
        <!-- 강의리스트 -->
        <b-col sm="2" class="list">
          <div class="listBackground pt-2">
            <div
              class="lectures"
              :class="{ selectedLecture: lecture.lectureId === this.lectureId }"
              v-for="(lecture, index) in lectureList"
              :key="index"
              @click="lectureOne(lecture.lectureId)"
            >
              {{ lecture.startTime }}
              <br />
              ~
              <br />
              {{ lecture.endTime }}
            </div>
          </div>
        </b-col>
        <!-- 강의 동영상/통계 -->
        <b-col sm="6">
          <div v-if="selected">
            <lecture-video :lecture="lecture" />
          </div>
          <div v-else class="selectMsg">
            <h3>강의를 선택하세요</h3>
          </div>
        </b-col>
        <!-- 채팅목록 -->
        <b-col sm="2"><lecture-chat /></b-col>
        <!-- 참가자목록 -->
        <b-col sm="2"><user-list /></b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import { mapActions, mapGetters } from "vuex";
import LectureVideo from "@/components/statistics/LectureVideo.vue";
import LectureChat from "@/components/statistics/LectureChat.vue";
import UserList from "@/components/statistics/UserList.vue";
import Logo from "@/components/common/Logo.vue";

export default {
  components: {
    LectureVideo,
    LectureChat,
    UserList,
    Logo,
  },
  props: {},
  data() {
    return {
      lectureRoomId: 0,
      lectureId: 0,
      selected: false,
    };
  },
  computed: {
    lectureList() {
      return this.getLectures();
    },
    lecture() {
      return this.getLecture();
    },
  },
  watch: {
    lecture() {
      this.lectureId = this.lecture.lectureId;
      this.selected = true;
      this.setRecordStatistics(this.lectureId);
    },
  },
  methods: {
    ...mapActions("StatisticsStore", [
      "setLectures",
      "setLectureOne",
      "setRecordStatistics",
    ]),
    ...mapGetters("StatisticsStore", ["getLectures", "getLecture"]),
    lectureOne(lectureId) {
      this.lectureId = lectureId;
      this.selected = true;
      this.setLectureOne(lectureId);
    },
  },
  mounted() {
    this.lectureRoomId = this.$route.params.lectureRoomId;
    this.setLectures(this.lectureRoomId);
  },
};
</script>

<style scoped>
.background {
  background-color: #f7f7f4;
  height: 100vh;
  padding-top: 50px;
}
.listBackground {
  background-color: white;
  border-radius: 15px;
  height: 80vh;
}
.list {
  border-right: #c1c1c1 solid 1px;
}
.lectures {
  cursor: pointer;
  background-color: #f7f7f4;
  border-radius: 10px;
  margin: 10px;
}
.selectedLecture {
  background-color: #5196fe;
  color: white;
}
.logo {
  width: 20%;
  position: relative;
  right: 5%;
  bottom: 10px;
}
</style>
