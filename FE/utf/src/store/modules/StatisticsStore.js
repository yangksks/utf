import { apiInstance } from "@/api/index.js";
const api = apiInstance();

const statisticsStore = {
  namespaced: true,
  state: {
    currentUnderstanding: undefined, //현재 이해도 정보
    recordedStatistics: undefined, //녹화강의 집중도,이해도 통계
    recentLectures: undefined, //최근 강의 목록
    lectureRooms: undefined, //강의실 목록
    lectures: undefined, //강의실 들어왔을 때 강의들
    lecture: undefined, //강의 1개
  },
  getters: {
    getCurrentUnderstanding: (state) => {
      return state.currentUnderstanding;
    },
    getRecordedStatistics: (state) => {
      return state.recordedStatistics;
    },
    getRecentLectures: (state) => {
      return state.recentLectures;
    },
    getLectureRooms: (state) => {
      return state.lectureRooms;
    },
    getLectures: (state) => {
      return state.lectures;
    },
    getLecture: (state) => {
      return state.lecture;
    },
  },
  mutations: {
    SET_UNDERSTANDING: (state, payload) => {
      state.currentUnderstanding = payload;
    },
    SET_RECORD_STATISTICS: (state, payload) => {
      state.recordedStatistics = payload;
    },
    SET_RECENTLY: (state, payload) => {
      state.recentLectures = payload;
    },
    SET_LECTURES: (state, payload) => {
      state.lectures = payload;
    },
    SET_LECTURE_ONE: (state, payload) => {
      state.lecture = payload;
    },
    SET_LECTUREROOMS: (state, payload) => {
      state.lectureRooms = payload;
    },
  },
  actions: {
    setEmotion: (store, item) => {
      //item: [강의실id, 이름,감정,점수]
      api
        .post(
          `/api/statistics/current/${item[0]}`,
          JSON.stringify({
            name: item[1],
            emotion: item[2],
            score: item[3],
          })
        )
        // eslint-disable-next-line
        .then((res) => {})
        .catch((err) => {
          console.log(err);
        });
    },
    setUnderstanding: (store) => {
      api
        .get(`/api/statistics/current/1`)
        .then((res) => {
          try {
            store.commit("SET_UNDERSTANDING", res.data);
          } catch (error) {
            //do-nothing
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    setRecordStatistics: (store, lectureId) => {
      api
        .get(`/api/statistics/record/${lectureId}`)
        .then((res) => {
          store.commit("SET_RECORD_STATISTICS", res.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    setRecently: (store, userId) => {
      api
        .get(`/api/lecture/recent/${userId}`)
        .then((res) => {
          store.commit("SET_RECENTLY", res.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    setLectureRooms: (store, userId) => {
      api
        .get(`/api/lectureRoom/${userId}`)
        .then((res) => {
          store.commit("SET_LECTUREROOMS", res.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    setLectures: (store, lectureRoomId) => {
      api
        .get(`/api/lecture/${lectureRoomId}`)
        .then((res) => {
          store.commit("SET_LECTURES", res.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    setLectureOne: (store, lectureId) => {
      api
        .get(`/api/lecture/one/${lectureId}`)
        .then((res) => {
          store.commit("SET_LECTURE_ONE", res.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};

export default statisticsStore;
