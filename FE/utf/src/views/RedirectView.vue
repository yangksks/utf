<template>redirect</template>

<script>
import axios from "axios";

async function redirect() {
  const code = new URL(window.location.href).searchParams.get("code");
  const state = new URL(window.location.href).searchParams.get("state");
  const scope = new URL(window.location.href).searchParams.get("scope");
  //카카오 로그인
  if (code && !state && !scope) {
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
  else if (code && state && !scope) {
    await axios
      .post(`http://localhost:8080/api/user/naverLogin`, {
        data: {
          code: code,
          state: state,
        },
      })
      .then((res) => {
        console.log(res);
      })
      .catch((err) => {
        console.log(err);
      });
  }
  //구글 로그인
  else if (code && state && scope) {
    await axios
      .post(`http://localhost:8080/api/user/googleLogin`, {
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
}

export default {
  name: "RedirectView",
  mounted() {
    redirect();
  },
};
</script>
