<template>
  <video autoplay class="webcam" />
</template>

<script>
import * as faceapi from "face-api.js";
import webgazer from "webgazer";
import { mapActions, mapGetters } from "vuex";

export default {
  name: "OvVideo",

  props: {
    streamManager: Object,
    name: String,
  },
  data() {
    return {
      x: 0,
      y: 0,
      isFocus: true,
    };
  },
  computed: {
    lectureRoomId() {
      return this.getLectureRoomId();
    },
  },
  methods: {
    ...mapActions("focusStore", ["sendMyFocus"]),
    ...mapActions("StatisticsStore", ["setEmotion", "setLectureRoomId"]),
    ...mapGetters("StatisticsStore", ["getLectureRoomId"]),
    eyeTrackingBegin() {
      webgazer
        // eslint-disable-next-line
        .setGazeListener(function (data, elapsedTime) {
          if (data == null) {
            return;
          }
        })
        .begin();
    },
    sendFocus() {
      webgazer.getCurrentPrediction().then((res) => {
        if (res.x < 200 || 900 < res.x || res.y < 200 || 900 < res.y) {
          this.isFocus = false;
          this.x = res.x;
          this.y = res.y;
        } else {
          this.isFocus = true;
          this.x = res.x;
          this.y = res.y;
        }
      });
      this.sendMyFocus([this.name, this.isFocus, this.lectureRoomId]);
    },
    setId() {
      let link = document.location.href.split("/");
      let code = link[4];
      this.setLectureRoomId(code);
    },
  },
  mounted() {
    this.setId();
    this.streamManager.addVideoElement(this.$el);
    //eyetracking
    this.eyeTrackingBegin();
    const setFocusFunc = async () => {
      setInterval(() => {
        this.sendFocus();
      }, 5000);
    };
    //faceapi 모델 로드
    const webcamElement = document.querySelectorAll(".webcam")[0];
    const modelLoad = async () => {
      await faceapi.loadSsdMobilenetv1Model("/models");
      await faceapi.loadFaceExpressionModel("/models");
      await faceapi.loadFaceLandmarkModel("/models");
    };
    modelLoad();
    //감지한 웹캠에서 face-api이용해 표정 감지 함수
    const getFaceEmotion = async () => {
      const minConfidenceFace = 0.5;

      const expressionResult = await faceapi
        .detectSingleFace(
          webcamElement,
          new faceapi.SsdMobilenetv1Options({ minConfidenceFace })
        )
        .withFaceExpressions();
      const expressionFiltered = Object.fromEntries(
        Object.entries(expressionResult.expressions)
          // eslint-disable-next-line
          .filter(([key, score]) => score > minConfidenceFace) // key : 감정 , score : 점수
          .sort((a, b) => b[1] - a[1]) //score 높은 순으로 정렬
      );
      // this.emotion.push(Object.keys(expressionFiltered)[0]); //가장 높은 점수의 감정 저장
      let emotion = Object.keys(expressionFiltered)[0] || "neutral";
      let score = expressionFiltered[emotion] || 0.9;
      const arr = [this.lectureRoomId, this.name, emotion, score];
      this.setEmotion(arr);
    };
    //시간당(5초마다) 이해도/집중도 얻는 함수
    const getStatisticsPerTime = () => {
      setInterval(async () => {
        await getFaceEmotion();
        setFocusFunc();
      }, 5000);
    };
    getStatisticsPerTime();

    // const el = document.querySelector("#webgazerVideoContainer");
    // console.log(el);
    // el.style.display = "none";
  },
};
</script>
<style scoped>
video {
  width: 100%;
  height: 100%;
}
</style>
