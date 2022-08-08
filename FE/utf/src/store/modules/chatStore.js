const chatStore = {
  namespaced: true,
  state: {
    msgList: [],
  },
  mutations: {
    PUSH_MSG_DATA: (state, payload) => {
      state.msgList.push(payload);
    },
  },
  actions: {},
};
export default chatStore;
