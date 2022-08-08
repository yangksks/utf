<template>
  <video autoplay class="webcam" />
</template>

<script>
import * as faceapi from "face-api.js";
import { mapActions } from "vuex";

export default {
  name: "OvVideo",

  props: {
    streamManager: Object,
    name: String,
  },
  methods: {
    ...mapActions("StatisticsStore", ["setEmotion"]),
  },
  mounted() {
    this.streamManager.addVideoElement(this.$el);
    //감지한 웹캠에서 face-api이용해 표정 감지 함수
    const getFaceEmotion = async () => {
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
        let emotion = Object.keys(expressionFiltered)[0] || "neutral";
        let score = expressionFiltered[emotion] || 0.9;
        const arr = [this.name, emotion, score];
        this.setEmotion(arr);
      }
    };
    //시간당(5초마다) 감정 얻는 함수
    const getFaceEmotionPerTime = () => {
      setInterval(async () => {
        await getFaceEmotion();
      }, 5000);
    };
    getFaceEmotionPerTime();
  },
};
</script>
<style scoped>
video {
  width: 100%;
  height: 100%;
}
</style>
