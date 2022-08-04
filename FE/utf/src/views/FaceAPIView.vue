<template>
  <div>
    <video id="webcam" autoplay playsinline></video>
    <canvas id="canvas" class="d-none"></canvas>
  </div>
</template>

<script>
import { onMounted, reactive } from "vue";
import Webcam from "webcam-easy";
import * as faceapi from "face-api.js";

export default {
  setup() {
    const state = reactive({
      emotion: [],
    });
    onMounted(() => {
      const webcamElement = document.getElementById("webcam");
      const webcam = new Webcam(webcamElement, "user");
      //웹캠 감지 함수...openvidu 적용시 바뀔 부분
      const webcamDetect = () => {
        webcam
          .start()
          .then(() => {
            console.log("webcam started");
          })
          .catch((err) => {
            console.log(err);
          });
      };
      webcamDetect();

      //감지한 웹캠에서 face-api이용해 표정 감지 함수
      const faceExpressionDetect = async () => {
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
          state.emotion.push("neutral");
        } else {
          const expressionFiltered = Object.fromEntries(
            Object.entries(expressionResult.expressions)
              // eslint-disable-next-line
              .filter(([key, score]) => score > minConfidenceFace) // key : 감정 , score : 점수
              .sort((a, b) => b[1] - a[1]) //score 높은 순으로 정렬
          );
          state.emotion.push(Object.keys(expressionFiltered)[0]); //가장 높은 점수의 감정 저장
        }
      };
      const loop = () => {
        setInterval(async () => {
          await faceExpressionDetect();
          //시간별로 잘찍히는지 확인
          let time = new Date();
          console.log("time", time.getSeconds());
        }, 1000);
        setInterval(async () => {
          console.log(state.emotion);
        }, 5000);
      };
      loop();
    });
  },
};
</script>

<style></style>
