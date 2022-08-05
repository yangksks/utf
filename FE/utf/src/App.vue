<template>
  <nav>
    <router-link to="/login">Login</router-link> |
    <router-link to="/">Home</router-link> |
    <router-link to="/about">About</router-link>
    <div v-if="isLogin">로그인 상태</div>
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

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}
</style>
