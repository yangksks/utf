import { apiInstance } from "@/api/index.js";
const api = apiInstance();

const statisticsStore = {
  namespaced: true,
  state: {
    currentUnderstanding: undefined, //현재 이해도 정보
    recordedUnderstanding: undefined,
  },
  getters: {
    getCurrentUnderstanding: (state) => {
      return state.currentUnderstanding;
    },
    getRecordedUnderstanding: (state) => {
      return state.recordedUnderstanding;
    },
  },
  mutations: {
    SET_UNDERSTANDING: (state, payload) => {
      state.currentUnderstanding = payload;
    },
    SET_RECORD_UNDERSTANDING: (state, payload) => {
      state.recordedUnderstanding = payload;
    },
  },
  actions: {
    setEmotion: (store, item) => {
      //item: [강의실id, 이름,감정,점수]
      api
        .post(
          `/statistics/current/${item[0]}`,
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
        .get(`/statistics/current/1`)
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
    setRecordUnderstanding: (store) => {
      api
        .get(`/statistics/record/understand/1`)
        .then((res) => {
          store.commit("SET_RECORD_UNDERSTANDING", res.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};

export default statisticsStore;
