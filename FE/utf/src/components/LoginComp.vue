<template>
  <div>
    <img
      id="kakaoLoginBtn"
      class="btn"
      src="@/assets/image/kakao_login_medium_wide.png"
      alt="카카오 로그인 버튼"
    />
  </div>
</template>

<script>
// import axios from "axios";

const REST_API_KEY = process.env.VUE_APP_REST_API_KEY;
const REDIRECT_URI = process.env.VUE_APP_REDIRECT_URI;
const getKakao = `https://kauth.kakao.com/oauth/authorize?client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&response_type=code`;

export default {
  name: "LoginComp",
  mounted() {
    const kakaoLoginBtn = document.querySelector("#kakaoLoginBtn");
    function kakaoLogin() {
      window.location.assign(getKakao);
      // axios
      //   .get(getKakao)
      //   .then(function (response) {
      //     console.log(response);
      //   })
      //   .catch(function (error) {
      //     console.log(error);
      //   });
    }
    kakaoLoginBtn.onclick = kakaoLogin;
  },
};
</script>

<style scoped>
.btn {
  cursor: pointer;
}
</style>
