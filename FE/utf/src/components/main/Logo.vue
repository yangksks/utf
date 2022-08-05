<template>
  <div @click="goHome()">
    <img src="@/assets/UTFLogo.png" alt="logo" />
    <h3>UTF</h3>
  </div>
</template>

<script>
export default {
  name: "LogoComponent",
  methods: {
    goHome() {
      this.$router.push("/home");
    },
  },
};
</script>

<style></style>
