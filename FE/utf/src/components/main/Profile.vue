<!-- eslint-disable prettier/prettier -->
<template>
  <b-container>
    <div :class="{ profileBox: !darkMode, profileBoxDark: darkMode }">
      <!-- <b-row class="row">
        <b-col sm="10" class="welcomeMsg">
          <div>{{ userName }} 강사님 환영합니다</div>
        </b-col>
        <b-col sm="2" class="btnGroup">
          <div class="settingBtnWrap">
            <i class="bi bi-gear buttons" id="settingBtn" style="font-size: 30px" @click="updateProfile()"></i>
          </div>
          <div class="logoutBtnWrap">
            <i class="bi bi-box-arrow-right buttons" id="logoutBtn" style="font-size: 30px" @click="logout()"></i>
          </div>
        </b-col>
      </b-row> -->
      <div style="margin-top: 20px">{{ userName }} 강사님 환영합니다</div>
      <div>
        <i class="bi bi-gear buttons" id="settingBtn" style="font-size: 20px; margin-left: 30px; margin-right: 170px" @click="updateProfile()"></i>
        <i class="bi bi-box-arrow-right buttons" id="logoutBtn" style="font-size: 20px" @click="logout()"></i>
      </div>
    </div>
  </b-container>
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
  methods: {
    updateProfile() {
      this.$emit("setting");
    },
    logout() {
      let flag = confirm("로그아웃 하시겠습니까??");
      if (flag) {
        this.$emit("logout");
      }
    },
  },
};
</script>

<style scoped>
.row {
  width: 100%;
}
.welcomeMsg {
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
