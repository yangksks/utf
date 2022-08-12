<!-- eslint-disable prettier/prettier -->
<template>
  <div class="container">
    <div :class="{ profileBox: !darkMode, profileBoxDark: darkMode }">
      <div class="row">
        <div class="col-10">
          <div>{{ userName }} 강사님 환영합니다</div>
        </div>
        <div class="col-2">
          <i class="bi bi-gear buttons" id="settingBtn"></i>
          <i class="bi bi-box-arrow-right buttons" id="logoutBtn"></i>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
/* eslint-disable prettier/prettier */
import store from "@/store";

export default {
  name: "ProfileComponent",
  props: {
    darkMode: Boolean,
  },
  data() {
    return {
      userName: store.state.userInfo["userName"],
    };
  },
  mounted() {
    const settingBtn = document.querySelector("#settingBtn");
    settingBtn.onclick = () => {
      this.$emit("setting");
    };

    const logoutBtn = document.querySelector("#logoutBtn");
    logoutBtn.onclick = () => {
      this.$emit("logout");
    };
  },
};
</script>

<style scoped>
</style>
