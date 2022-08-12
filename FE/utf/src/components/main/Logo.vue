<!-- eslint-disable prettier/prettier -->
<template>
  <div @click="goHome()">
    <img src="@/assets/UTFLogo-removebg.png" alt="logo" class="buttons" />
  </div>
</template>

<script>
/* eslint-disable prettier/prettier */
export default {
  name: "LogoComponent",
  methods: {
    goHome() {
      this.$router.push("/main");
    },
  },
};
</script>

<style></style>
