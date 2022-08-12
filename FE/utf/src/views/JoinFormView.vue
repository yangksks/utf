<template>
  <JoinFormComp @getName="getName"></JoinFormComp>
</template>

<script>
import JoinFormComp from "@/components/user/JoinFormComp.vue";
import { getName } from "@/api/index.js";

export default {
  name: "LoginView",
  components: {
    JoinFormComp,
  },
  methods: {
    getName,
  },
};
</script>
