<template>
  <div>
    <div
      v-if="msg.from.name === 'DevplaCalledMe'"
      class="chat__mymessage"
      :class="[isSame ? '' : 'chat__first']"
    >
      <!-- <p class="chat__yourmessage__time">23:38</p> -->
      <p class="chat__mymessage__paragraph">{{ msg.msg }}</p>
    </div>
    <div
      v-else
      class="chat__yourmessage"
      :class="[isSame ? '' : 'chat__first']"
    >
      <!-- <div class="chat__yourmessage__avartar">
        <img
          :src="avatar"
          alt=""
          v-if="!isSame"
          class="chat__yourmessage__img"
        />
      </div> -->
      <div class="chat-message-box">
        <p class="chat-message-sender">{{ msg.from.name + " : " }}</p>
        <p class="chat-message-content">{{ msg.msg }}</p>
      </div>
    </div>
    <p class="chat-message-time">{{ msg.from.time }}</p>
  </div>
</template>

<script>
export default {
  name: "ChatMessage",
  props: ["msg", "prev"],
  data() {
    return {
      isSame: false,
      //   avatar: require("../assets/avatar.svg"),
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
    // created() {
    //   this.isSame = this.isSamePerson(this.msg, this.prev);
    //   // if (this.msg?.from.avatar) {
    //   //   this.avatar = this.msg?.from.avatar;
    //   // }
  },
};
</script>
<style>
.chat-message-sender {
  font-weight: bold;
  vertical-align: top;
  min-width: 50px;
}
.chat-message-content {
  font-weight: lighter;
  text-align: justify;
}
.chat-message-time {
  text-align: left;
  margin: 0;
  margin-left: 1rem;
  font-size: 12px;
  color: #9c9c9c;
}
.chat-message-box {
  display: flex;
  align-items: flex-start;
  line-break: anywhere;
  margin: 0.4rem 1rem 0 0;
  border-radius: 10px 10px 10px 10px;
  background-color: #f3f3f3;
  /* max-width: 180px; */
  color: #414141;
  padding: 0.8rem;
  font-size: 14px;
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
.chat__first {
  margin-top: 2rem;
}
.chat__yourmessage {
  display: flex;
}
.chat__yourmessage__avartar {
  width: 40px;
  margin-right: 1rem;
}
.chat__yourmessage__img {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  object-fit: cover;
}
.chat__yourmessage__user {
  text-align: left;
  font-size: 14px;
  font-weight: 700;
  color: #292929;
  margin-top: 0;
  margin-block-end: 0rem;
}
</style>
