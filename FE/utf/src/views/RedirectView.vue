<template>redirect</template>

<script>
import axios from "axios";

async function kakaoRedirect() {
  const code = new URL(window.location.href).searchParams.get("code");
  console.log(code);
  if (code) {
    await axios
      .get(`http://localhost:8080/api/user/kakaoLogin?code=${code}`)
      .then((res) => {
        console.log("성공");
        console.log(res);
      })
      .catch((err) => {
        console.log("에러");
        console.log(err);
      });
    console.log("되나");
  }
}

export default {
  name: "RedirectView",
  mounted() {
    kakaoRedirect();
  },
};
</script>
