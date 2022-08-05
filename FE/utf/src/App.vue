<template>
  <router-view />
</template>
<script>
import store from "./store";
export default {
  data() {
    return {
      isLogin: store.state.userInfo == null ? false : true,
    };
  },
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
body {
  margin: 0;
}
</style>
