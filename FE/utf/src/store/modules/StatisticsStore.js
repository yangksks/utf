import axios from "axios";

const statisticsStore = {
  namespaced: true,
  state: {
    emotion: undefined,
    score: undefined,
  },
  getters: {},
  mutations: {
    SET_EMOTION: (state, payload) => {
      console.log("mutation", payload);
      state.emotion = payload[0];
      state.score = payload[1];
      console.log("dfscdb", state.emotion, state.score);
    },
  },
  actions: {
    setEmotion: (store, item) => {
      //item: [이름,감정,점수]
      axios
        .post(
          `http://localhost:8081/api/statistics/commit`,
          JSON.stringify({
            name: item[0],
            emotion: item[1],
            score: item[2],
          })
        )
        .then((res) => {
          console.log(res);
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};

export default statisticsStore;
