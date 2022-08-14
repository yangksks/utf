import { createStore } from "vuex";
import lectureStore from "@/store/modules/lectureStore.js";
import createVuexPersistedState from "vuex-persistedstate";
import StatisticsStore from "./modules/StatisticsStore.js";
import chatStore from "./modules/chatStore.js";

export default createStore({
  modules: { lectureStore, StatisticsStore, chatStore },
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
    lectureRoomList: [],
    tempLectureRoom: null,
    lectureList: null,
  },
  getters: {},
  mutations: {
    SAVE_TEMP_USER_INFO(state, payload) {
      state.tempUserInfo = payload;
    },
    SAVE_USER_INFO(state, payload) {
      state.userInfo = payload;
    },
    SAVE_LECTURE_ROOM_LIST(state, payload) {
      state.lectureRoomList = payload;
    },
    SAVE_LECTURE_LIST(state, payload) {
      state.lectureList = payload;
    },
    ADD_LECTURE_ROOM(state, payload) {
      state.tempLectureRoom = payload["lectureRoom"];
    },
  },
  actions: {
    saveTempUserInfo(context, payload) {
      context.commit("SAVE_TEMP_USER_INFO", payload);
    },
    saveUserInfo(context, payload) {
      context.commit("SAVE_USER_INFO", payload);
    },
    saveLectureRoomList(context, payload) {
      context.commit("SAVE_LECTURE_ROOM_LIST", payload);
    },
    saveLectureList(context, payload) {
      context.commit("SAVE_LECTURE_LIST", payload);
    },
    addLectureRoom(context, payload) {
      context.commit("ADD_LECTURE_ROOM", payload);
    },
  },
});
