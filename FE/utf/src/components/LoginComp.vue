<template>
  <div>
    <img
      id="kakaoLoginBtn"
      class="btn"
      src="@/assets/image/kakao_login_medium_wide.png"
      alt="카카오 로그인 버튼"
    /><br />
    <img
      id="naverLoginBtn"
      class="btn"
      src="@/assets/image/btnG_완성형.png"
      alt="네이버 로그인 버튼"
    />
  </div>
</template>

<script>
// import axios from "axios";

const KAKAO_REST_API_KEY = process.env.VUE_APP_KAKAO_REST_API_KEY;
const KAKAO_REDIRECT_URI = process.env.VUE_APP_KAKAO_REDIRECT_URI;
const getKakao = `https://kauth.kakao.com/oauth/authorize?client_id=${KAKAO_REST_API_KEY}&redirect_uri=${KAKAO_REDIRECT_URI}&response_type=code`;

const NAVER_CLIENT_ID = process.env.VUE_APP_NAVER_CLIENT_ID;
const NAVER_CLIENT_SECRET = process.env.VUE_APP_NAVER_CLIENT_SECRET;
const NAVER_CALLBACK_URL = process.env.VUE_APP_NAVER_CALLBACK_URL;
const getNaver = `https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=${NAVER_CLIENT_ID}&state=${NAVER_CLIENT_SECRET}&redirect_uri=${NAVER_CALLBACK_URL}`;

export default {
  name: "LoginComp",
  mounted() {
    const kakaoLoginBtn = document.querySelector("#kakaoLoginBtn");
    function kakaoLogin() {
      window.location.assign(getKakao);
    }
    kakaoLoginBtn.onclick = kakaoLogin;

    const naverLoginBtn = document.querySelector("#naverLoginBtn");
    function naverLogin() {
      window.location.assign(getNaver);
    }
    naverLoginBtn.onclick = naverLogin;
  },
};
</script>

<style scoped>
.btn {
  width: 300px;
  /* height: 50px; */
  cursor: pointer;
}
</style>
