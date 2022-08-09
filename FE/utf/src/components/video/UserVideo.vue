<template>
  <div class="user-video" v-if="streamManager">
    <p class="client-name">{{ clientData }}</p>
    <ov-video :stream-manager="streamManager" class="webcam" />
  </div>
</template>

<script>
import OvVideo from "./OvVideo";
import * as faceapi from "face-api.js";
import { mapActions } from "vuex";

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
    ...mapActions("StatisticsStore", ["SETEMOTION"]),
    getConnectionData() {
      const { connection } = this.streamManager.stream;
      return JSON.parse(connection.data);
    },
    //감지한 웹캠에서 face-api이용해 표정 감지 함수
    async getFaceEmotion() {
      const webcamElement = document.querySelectorAll(".webcam")[0];

      const minConfidenceFace = 0.5;
      await faceapi.loadSsdMobilenetv1Model("/models");
      await faceapi.loadFaceExpressionModel("/models");
      await faceapi.loadFaceLandmarkModel("/models");

      const expressionResult = await faceapi
        .detectSingleFace(
          webcamElement,
          new faceapi.SsdMobilenetv1Options({ minConfidenceFace })
        )
        .withFaceExpressions();

      if (expressionResult === undefined) {
        // 감정 detect 실패시 neutral로 취급
      } else {
        const expressionFiltered = Object.fromEntries(
          Object.entries(expressionResult.expressions)
            // eslint-disable-next-line
            .filter(([key, score]) => score > minConfidenceFace) // key : 감정 , score : 점수
            .sort((a, b) => b[1] - a[1]) //score 높은 순으로 정렬
        );
        // this.emotion.push(Object.keys(expressionFiltered)[0]); //가장 높은 점수의 감정 저장
        let data = [this.clientData, expressionFiltered];
        this.SETEMOTION(data);
      }
    },
    //시간별로 감정 얻는 함수
    getFaceEmotionPerTime() {
      const time = 5000;
      setInterval(async () => {
        await this.getFaceEmotion();
        //시간별로 잘찍히는지 확인
        let time = new Date();
        console.log("time", time.getSeconds());
        console.log(document.querySelectorAll(".webcam")[0]);
      }, time);
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
