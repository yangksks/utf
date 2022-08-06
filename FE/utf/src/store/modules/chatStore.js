const chatStore = {
  namespaced: true,
  state: {
    msgList: [],
  },
  mutations: {
    PUSH_MSG: (state, msg) => {
      state.msgList.push(msg);
    },
  },
  actions: {
    pushMessage: ({ commit }, msg) => {
      commit("PUSH_MSG", msg);
    },
  },
};
export default chatStore;
