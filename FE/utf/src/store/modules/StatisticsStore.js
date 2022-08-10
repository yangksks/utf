import { apiInstance } from "@/api/index.js";
const api = apiInstance();

const statisticsStore = {
  namespaced: true,
  state: {
    currentUnderstanding: undefined, //현재 이해도 정보
  },
  getters: {
    getCurrentUnderstanding: (state) => {
      return state.currentUnderstanding;
    },
  },
  mutations: {
    SET_UNDERSTANDING: (state, payload) => {
      state.currentUnderstanding = payload;
    },
  },
  actions: {
    setEmotion: (store, item) => {
      //item: [이름,감정,점수]
      api
        .post(
          `/statistics/current`,
          JSON.stringify({
            name: item[0],
            emotion: item[1],
            score: item[2],
          })
        )
        // eslint-disable-next-line
        .then((res) => {})
        .catch((err) => {
          console.log(err);
        });
    },
    setUnderstanding: (context) => {
      api
        .get(`/statistics/current`)
        .then((res) => {
          try {
            context.commit("SET_UNDERSTANDING", res.data);
          } catch (error) {
            //do-nothing
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};

export default statisticsStore;
