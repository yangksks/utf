<template>
  <div class="message-box">
    <div v-if="msg.from.type === 'general'" class="chat-message-box-outer">
      <div class="chat-message-box-inner">
        <p class="chat-message-sender">{{ msg.from.name + ": " }}</p>
        <p class="chat-message-content">{{ msg.msg }}</p>
      </div>
      <p class="chat-message-time">{{ msg.from.time }}</p>
    </div>
    <div v-if="msg.from.type === 'alert'" class="alert-message-box-outer">
      <div class="chat-message-box-inner">
        <p class="alert-message-content">{{ msg.msg }}</p>
      </div>
      <p class="chat-message-time">{{ msg.from.time }}</p>
    </div>
  </div>
</template>

<script>
export default {
  name: "ChatMessage",
  props: ["msg", "prev"],
  data() {
    return {
      isSame: false,
    };
  },
  methods: {
    isSamePerson(msg, prev) {
      if (prev === null) {
        return false;
      } else if (prev[0]?.from.name == msg?.from.name) {
        return true;
      } else {
        return false;
      }
    },
    created() {
      this.isSame = this.isSamePerson(this.msg, this.prev);
    },
  },
};
</script>
<style>
.chat-message-sender {
  font-weight: bold;
  text-align: justify;
  vertical-align: top;
  min-width: 50px;
  margin-bottom: 0.5rem;
}
.chat-message-content {
  font-weight: lighter;
  text-align: justify;
  margin-bottom: 0.5rem;
  line-break: anywhere;
}
.chat-message-time {
  flex-direction: column;
  text-align: left;
  margin: 0;
  font-size: 12px;
  color: #9c9c9c;
}
.chat-message-box-inner {
  display: flex;
  align-items: flex-start;
  white-space: pre-wrap;
}
.chat-message-box-outer {
  border-radius: 15px 15px 15px 15px;
  background-color: #f3f3f3;
  /* max-width: 180px; */
  color: #414141;
  padding: 0.8rem;
  font-size: 14px;
  width: 100%;
}
.alert-message-content {
  font-weight: bold;
  text-align: justify;
  margin-bottom: 0.5rem;
  line-break: anywhere;
}
.alert-message-box-outer {
  border-radius: 15px 15px 15px 15px;
  background-color: rgba(0, 0, 0, 0.75);
  /* max-width: 180px; */
  color: white;
  padding: 0.8rem;
  font-size: 14px;
  width: 100%;
}
.chat__mymessage {
  display: flex;
  justify-content: right;
  align-items: flex-end;
  margin: 0;
  min-height: 40px;
  line-break: anywhere;
}
.chat__mymessage__paragraph {
  margin: 0.4rem 0 0 1rem;
  border-radius: 20px 20px 0px 20px;
  max-width: 180px;
  background-color: #bbc4ef;
  color: #ffffff;
  padding: 0.8rem;
  font-size: 14px;
}
.message-box {
  margin-top: 1rem;
}
/* .chat__yourmessage__user {
  text-align: left;
  font-size: 14px;
  font-weight: 700;
  color: #292929;
  margin-top: 0;
  margin-block-end: 0rem;
} */
</style>
