<template>
  <div class="chat-body" id="chat-body">
    <chat-message
      v-for="(msg, index) in msgs"
      :key="index"
      :msg="msg"
      :prev="[index == 0 ? null : msgs[index - 1]]"
    >
    </chat-message>
  </div>
</template>

<script>
import ChatMessage from "@/components/chat/ChatMessage";
export default {
  name: "ChatList",
  props: ["msgs"],
  components: {
    ChatMessage,
  },
};
</script>

<style>
.chat-body {
  padding: 1rem;
  overflow: scroll;
  scroll-behavior: smooth;
}
.chat-body::-webkit-scrollbar {
  display: none;
}
</style>
