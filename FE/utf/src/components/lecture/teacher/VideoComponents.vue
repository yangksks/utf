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
}

.highlight {
  border: 2px;
  border-style: solid;
  border-color: blue;
}
</style>
