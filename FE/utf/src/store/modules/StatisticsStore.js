// import axios from "axios";

const statisticsStore = {
  namespaced: true,
  state: {
    currentEmotions: {},
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
    SETEMOTION: (store, item) => {
      //item [이름,{감정:점수}] 형태의 배열
      store.commit("SETEMOTION", item);
    },
  },
};

export default statisticsStore;
