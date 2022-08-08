import axios from "axios";

const statisticsStore = {
  namespaced: true,
  state: {
    emotion: undefined,
    score: undefined,
  },
  getters: {},
  mutations: {
    SETEMOTION: (state, payload) => {
      let name = payload[0];
      let data = payload[1];
      state.currentEmotions[name] = data;

      if (Object.keys(state.currentEmotions).length == 3) {
        //각 방의 참가자수를 알아내서 3에 집어넣기
        console.log(state.currentEmotions);
        state.currentEmotions = {};
      }
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
