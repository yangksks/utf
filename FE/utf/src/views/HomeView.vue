<template>
  <div class="home">
    <img alt="Vue logo" src="../assets/logo.png" />
    <HelloWorld msg="뷰로 도커 jenkins" />
  </div>
</template>
<script>
// @ is an alias to /src
import HelloWorld from "@/components/HelloWorld.vue";
export default {
  name: "HomeView",
  components: {
    HelloWorld,
  },
};
</script>
