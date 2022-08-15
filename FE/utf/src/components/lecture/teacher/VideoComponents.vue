<template>
  <div id="video-components">
    <user-video
      :stream-manager="publisher"
      :class="{ highlight: speaker == 'me' }"
    />
    <user-video
      v-for="sub in subscribers"
      :key="sub.stream.streamId"
      :stream-manager="sub"
      :class="{ highlight: sub.stream.streamId == speaker }"
    />
  </div>
</template>

<script>
import UserVideo from "@/components/lecture/UserVideo";

export default {
  name: "VideoComponents",

  components: {
    UserVideo,
  },
  props: {
    publisher: Object,
    subscribers: Object,
    speaker: String,
  },
  data() {
    return {};
  },
};
</script>

<style scoped>
#video-components {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  grid-template-rows: repeat(4, calc(25% - 4px));
  row-gap: 4px;
  overflow: scroll;
  /* overflow-x: hidden; */
}
/* #video-components::-webkit-scrollbar {
  width: 6px;
}
#video-components::-webkit-scrollbar-track {
  background-color: transparent;
}
#video-components::-webkit-scrollbar-thumb {
  border-radius: 3px;
  background-color: gray;
}
#video-components::-webkit-scrollbar-button {
  width: 0;
  height: 0;
} */
.highlight {
  border: 1px;
  border-style: solid;
  border-color: blue;
}
</style>
