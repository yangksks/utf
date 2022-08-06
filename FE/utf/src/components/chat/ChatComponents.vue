<template>
  <div class="chat">
    <div class="chat-header">
      <span class="chat-header-greetings">
        <!-- 안녕하세요. {{ userData.userName }}님! -->
        <!-- 안녕하세요. {{clientData}}님! -->
        안녕하세요. 헤더입니다.
      </span>
    </div>
    <chat-list :msgs="msgList"></chat-list>
    <chat-form @submitMessage="sendMessage"></chat-form>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
import ChatList from "@/components/chat/ChatList";
import ChatForm from "@/components/chat/ChatForm";
export default {
  name: "ChatComponents",
  data() {
    return {};
  },
  components: {
    ChatList,
    ChatForm,
  },
  props: {
    publisher: Object,
    subscribers: Array,
  },
  computed: {
    ...mapState("chatStore", ["msgList"]),
  },
  created() {},
  mounted() {
    this.receiveMessage();
  },
  methods: {
    ...mapMutations("chatStore", {
      pushMessage: "PUSH_MSG_DATA",
    }),
    sendMessage(msg) {
      // submitMessage in ChatForm
      // console.log(this.publisher.session);
      console.log("msg: ", msg);
      console.log(this.msgList);
      console.log(this.publisher);
      console.log(this.subscribers);
      this.pushMessage({
        from: {
          name: "test",
          time: "00:00:00",
        },
        msg,
      });
      this.publisher.session
        .signal({
          data: msg, // Any string (optional)
          to: this.subscribers, // Array of Connection objects (optional. Broadcast to everyone if empty)
          // type: 'my-chat'             // The type of message (optional)
        })
        .then(() => {
          console.log("Message successfully sent");
        })
        .catch((error) => {
          console.error(error);
        });
      setTimeout(() => {
        // scroll 변경
        const element = document.getElementById("chat-body");
        element.scrollTop = element.scrollHeight;
      }, 0);
    },
    receiveMessage() {
      this.publisher.session.on("signal", (event) => {
        console.log(event.data); // Message
        console.log(event.from); // Connection object of the sender
        console.log(event.type); // The type of message ("my-chat")
        const msg = event.data;
        this.pushMessage({
          from: {
            name: event.from, //username,
            time: "00:00:00",
          },
          msg,
        }); // save to vuex
        setTimeout(() => {
          const element = document.getElementById("chat-body");
          element.scrollTop = element.scrollHeight;
        }, 0);
      });
    },
  },
};
</script>

<style scoped>
.chat {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  background-color: #cccccc;
}
.chat-header {
  background: #ffffff;
  box-shadow: 0px 3px 10px rgba(0, 0, 0, 0.05);
  border-radius: 24px 24px 0px 0px;
  padding: 1.8rem;
  font-size: 16px;
  font-weight: 700;
}
.chat-header-greetings {
  color: #292929;
}
</style>
