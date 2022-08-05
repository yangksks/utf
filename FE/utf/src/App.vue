<template>
  <nav>
    <router-link to="/login">Login</router-link> |
    <router-link to="/">Home</router-link> |
    <router-link to="/about">About</router-link> |
    <router-link to="/face">Face</router-link> |
    <router-link to="/main">Main</router-link> |
    <router-link to="/lecture">Lecture</router-link> |
  </nav>
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
