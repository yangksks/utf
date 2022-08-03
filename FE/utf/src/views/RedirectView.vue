<template>redirect</template>

<script>
import axios from "axios";

async function kakaoRedirect() {
  const code = new URL(window.location.href).searchParams.get("code");
  const state = new URL(window.location.href).searchParams.get("state");

  //카카오 로그인
  if (code && !state) {
    await axios
      .post(`http://localhost:8080/api/user/kakaoLogin`, {
        data: {
          code: code,
        },
      })
      .then((res) => {
        console.log(res);
      })
      .catch((err) => {
        console.log(err);
      });
  }
  //네이버 로그인
  else if (code && state) {
    await axios
      .post(`http://localhost:8080/api/user/naverLogin`, {
        data: {
          code: code,
          state: state,
        },
      })
      .then((res) => {
        console.log("성공");
        console.log(res);
      })
      .catch((err) => {
        console.log("에러");
        console.log(err);
      });
  }
}

export default {
  name: "RedirectView",
  mounted() {
    kakaoRedirect();
  },
};
</script>
