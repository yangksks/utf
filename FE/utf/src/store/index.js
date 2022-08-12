import { createStore } from "vuex";
import lectureStore from "@/store/modules/lectureStore.js";
import createVuexPersistedState from "vuex-persistedstate";
import StatisticsStore from "./modules/StatisticsStore.js";
import chatStore from "./modules/chatStore.js";
import focusStore from "./modules/focusStore.js";

export default createStore({
  modules: { lectureStore, StatisticsStore, chatStore, focusStore },
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
});
