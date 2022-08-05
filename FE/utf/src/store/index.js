import { createStore } from "vuex";
import createVuexPersistedState from "vuex-persistedstate";
import StatisticsStore from "./modules/StatisticsStore.js";

export default createStore({
  plugins: [
    createVuexPersistedState({
      //동작 안함
      whitelist: ["userInfo"],
      key: "vuexStore",
      storage: window.sessionStorage,
    }),
  ],

  state: {
    tempUserInfo: null,
    userInfo: null,
  },
  getters: {},
  mutations: {
    SAVE_TEMP_USER_INFO(state, payload) {
      state.tempUserInfo = payload;
    },
    SAVE_USER_INFO(state, payload) {
      state.userInfo = payload;
    },
  },
  actions: {
    saveTempUserInfo(context, payload) {
      context.commit("SAVE_TEMP_USER_INFO", payload);
    },
    saveUserInfo(context, payload) {
      context.commit("SAVE_USER_INFO", payload);
    },
  },
  modules: {
    StatisticsStore,
  },
});
