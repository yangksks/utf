<template>
  <JoinFormComp @getName="getName"></JoinFormComp>
</template>

<script>
import JoinFormComp from "@/components/user/JoinFormComp.vue";
import axios from "axios";
import store from "@/store";
import router from "@/router";

export default {
  name: "LoginView",
  components: {
    JoinFormComp,
  },
  methods: {
    getName(name) {
      axios
        .post(`http://localhost:8080/api/user/join`, {
          userName: name,
          socialLoginType: store.state.tempUserInfo["socialLoginType"],
          socialId: store.state.tempUserInfo["socialId"],
        })
        .then((res) => {
          console.log(res);
          store.dispatch("saveUserInfo", res.data);
          router.push({ path: "/main" });
        })
        .catch((err) => {
          console.log(err);
          router.push({ path: "/login" });
        });
    },
  },
};
</script>
