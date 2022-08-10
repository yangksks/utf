<template>
  <div class="user-video" v-if="streamManager">
    <p class="client-name">{{ clientData }}</p>
    <ov-video :stream-manager="streamManager" />
  </div>
</template>

<script>
import OvVideo from "./OvVideo";

export default {
  name: "UserVideo",

  components: {
    OvVideo,
  },

  props: {
    streamManager: Object,
  },

  computed: {
    clientData() {
      const { clientData } = this.getConnectionData();
      // this.getFaceEmotionPerTime(); // 얘를 여기 넣는게 맞나...
      return clientData;
    },
  },

  methods: {
    getConnectionData() {
      const { connection } = this.streamManager.stream;
      return JSON.parse(connection.data);
    },
  },
};
</script>

<style>
.user-video {
  position: relative;
  border-radius: 20px;
  overflow: hidden;
}
.client-name {
  position: absolute;
  color: white;
  z-index: 1000;
  left: 12px;
  margin: 0;
}
</style>
