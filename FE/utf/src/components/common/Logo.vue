<template>
  <img src="@/assets/UTFLogo.png" alt="logo" @click="goHome()" />
</template>

<script>
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
