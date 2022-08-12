<!-- eslint-disable prettier/prettier -->
<template>
  <div></div>
</template>

<script>
/* eslint-disable prettier/prettier */
import { loginRequest } from "@/api/index.js";

function redirect() {
  const code = new URL(window.location.href).searchParams.get("code");
  const state = new URL(window.location.href).searchParams.get("state");
  const scope = new URL(window.location.href).searchParams.get("scope");
  let socialLoginType = 0;
  //카카오 로그인
  if (code && !state && !scope) {
    socialLoginType = 1;
  }
  //네이버 로그인
  else if (code && state && !scope) {
    socialLoginType = 2;
  }
  //구글 로그인
  else if (code && state && scope) {
    socialLoginType = 3;
  }
  loginRequest(`http://localhost:8080/api/user/socialLogin`, socialLoginType, code, state);
}

export default {
  name: "RedirectView",
  mounted() {
    redirect();
  },
};
</script>
