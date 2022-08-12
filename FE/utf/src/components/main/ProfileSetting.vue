<!-- eslint-disable prettier/prettier -->
<template>
  <div class="container">
    <div :class="{ profileBox: !darkMode, profileBoxDark: darkMode }">
      Edit profile<br />
      name<br /><input v-model="userName" /><br />
      email<br /><input v-model="email" /><br />
      <button type="button" class="btn btn-danger" style="margin-right: 50px" @click="deleteUser()">탈퇴</button>
      <button type="button" class="btn btn-primary" @click="updateUser()">수정</button>
      <button type="button" id="cancleBtn" class="btn btn-secondary">취소</button>
    </div>
  </div>
</template>

<script>
/* eslint-disable prettier/prettier */
import store from "@/store";
import { deleteUserRequest } from "@/api/index.js";
import { updateUserRequest } from "@/api/index.js";
export default {
  props: {
    darkMode: Boolean,
  },
  data() {
    return {
      userName: store.state.userInfo["userName"],
      email: store.state.userInfo["email"],
    };
  },
  mounted() {
    const cancleBtn = document.querySelector("#cancleBtn");
    cancleBtn.onclick = () => {
      this.userName = store.state.userInfo["userName"];
      this.email = store.state.userInfo["email"];
      this.$emit("cancle");
    };
  },
  methods: {
    deleteUser() {
      deleteUserRequest(store.state.userInfo["userId"]);
    },
    async updateUser() {
      await updateUserRequest(store.state.userInfo["userId"], this.userName, this.email);
      // this.$emit("toggle");
      window.location.reload();
    },
  },
};
</script>

<style></style>
