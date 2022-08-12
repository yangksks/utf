<template>
  <div class="waiting-room">
    <b-container fluid style="height: 100px"
      ><logo class="h-100"
    /></b-container>
    <b-container class="waiting-room-container bg-light">
      <div class="position-relative" v-if="streamManager">
        <ov-video :stream-manager="streamManager" class="webcam" />
        <div class="buttons w-100">
          <audio-button
            pill
            :size="45"
            :stream-manager="streamManager"
            class="m-1"
          />
          <cam-button
            pill
            :size="45"
            :stream-manager="streamManager"
            class="m-1"
          />
        </div>
      </div>
      <div class="input-group mt-2 w-25 mx-auto">
        <b-form-input v-model="myName" placeholder="Enter your name" />
        <div class="input-group-append mx-2">
          <b-button :disabled="myName" variant="primary" @click="joinLecture"
            >입장</b-button
          >
        </div>
      </div>
    </b-container>

    <b-modal id="wait" v-model="isWait" hide-footer>
      <div class="wait-modal">
        <h2>입장 대기중</h2>
        <b-button
          class="mt-3 cancel w-100"
          variant="danger"
          block
          @click="isWait = false"
          >취소하기</b-button
        >
      </div>
    </b-modal>
    <div>
      <apexchart
        ref="chart"
        width="150"
        height="150"
        type="pie"
        :series="series"
        :options="chartOptions"
      />
    </div>
  </div>
</template>

<script>
import Logo from "@/components/common/Logo.vue";
import OvVideo from "@/components/lecture/OvVideo.vue";
import CamButton from "@/components/common/CamButton.vue";
import AudioButton from "@/components/common/AudioButton.vue";
import webgazer from "webgazer";
import { mapActions, mapGetters } from "vuex";
import VueApexCharts from "vue3-apexcharts";

export default {
  name: "WaitingRoom",
  components: {
    AudioButton,
    CamButton,
    Logo,
    OvVideo,
    apexchart: VueApexCharts,
  },
  props: { streamManager: Object },
  data() {
    return {
      publishAudio: true,
      publishVideo: true,
      myName: "",
      isWait: false,
      x: 0,
      y: 0,
      isFocus: true,
      series: [0, 0],
      chartOptions: {
        chart: {
          type: "pie",
        },
        labels: ["집중o", "집중x"],
        legend: {
          show: false,
        },
        responsive: [
          {
            breakpoint: 480,
          },
        ],
      },
    };
  },
  methods: {
    ...mapActions("focusStore", ["sendMyFocus", "setFocusing"]),
    ...mapGetters("focusStore", ["getFocusRatio"]),
    joinLecture() {
      this.$emit("joinLecture", this.myName);
      // this.isWait = true; 접속대기기능
    },
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
      this.sendMyFocus(["sfd", this.isFocus]);
    },
    setChart() {
      setInterval(() => {
        this.setFocusing();
        let obj = this.getFocusRatio();
        let newChart = [obj[2], obj[-2]];
        this.series = newChart;
      }, 5000);
    },
  },
  mounted() {
    this.eyeTrackingBegin();
    const setFocusFunc = () => {
      setInterval(() => {
        this.sendFocus();
      }, 5000);
    };
    setFocusFunc();
    this.setChart();
  },
};
</script>

<style>
.waiting-room {
  background: #c1c1c1;
  height: 100vh;
}

.waiting-room-container {
  height: 600px;
  border-radius: 25px;
}

.webcam {
  border-radius: 15px;
}

.buttons {
  position: absolute;
  bottom: 1vh;
}

.btn {
  background-color: #2b80ff;
  width: 70px;
  color: #ffffff;
}

.cancel {
  background-color: #ff4f5a;
  color: #ffffff;
  margin-top: 16rem;
}

.wait-modal {
  height: 350px;
  text-align: center;
}
</style>
