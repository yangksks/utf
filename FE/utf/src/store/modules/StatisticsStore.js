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
      axios
        .post(`http://localhost:8081/api/statistics/commit`, state.emotion)
        .then(console.log("성공 ㅅㅅㅅ"))
        .catch((err) => {
          console.log(err);
        });
    },
  },
  actions: {
    setEmotion: ({ commit }, item) => {
      //item: [감정,점수]
      commit("SET_EMOTION", item);
      // 이 문장 넣으면 다음 에러발생
      // Uncaught (in promise) TypeError: Converting circular structure to JSON
    },
  },
};

export default statisticsStore;
