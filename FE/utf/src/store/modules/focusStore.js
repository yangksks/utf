import { apiInstance } from "@/api/index.js";
const api = apiInstance();

const statisticsStore = {
  namespaced: true,
  state: {
    focusRatio: undefined,
  },
  getters: {
    getFocusRatio: (state) => {
      return state.focusRatio;
    },
  },
  mutations: {
    SET_FOCUSING: (state, payload) => {
      state.focusRatio = payload;
    },
  },
  actions: {
    sendMyFocus: (store, item) => {
      api
        .post(`/statistics/focus/current/1`, {
          name: item[0],
          isFocus: item[1],
        })
        .then((res) => {
          console.log(res.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    setFocusing: (store) => {
      api
        .get(`statistics/focus/current/1`)
        .then((res) => {
          try {
            console.log(res.data);
            store.commit("SET_FOCUSING", res.data);
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
