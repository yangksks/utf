<template>
  <div class="lecture-video-container">
    <div class="video">
      <video :src="lecture.videoUrl" style="width: 100%" controls></video>
    </div>
    <div v-for="(item, index) in chartList" :key="index">
      <chart v-if="item.show" :series="item.series" class="mt-1" />
    </div>
    <div class="radioGroup mt-2">
      <div v-for="(item, index) in chartList" :key="index">
        <input
          type="radio"
          :id="item.key"
          v-model="selected"
          :value="item.value"
          @change="showChart(index)"
        />
        <label :for="item.key" class="text">{{ item.value }}</label>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import Chart from "@/components/statistics/RecordChart.vue";

export default {
  name: "LineChart",
  components: {
    Chart,
  },
  props: {
    lecture: Object,
  },
  data() {
    return {
      chartList: [
        {
          value: "집중x",
          show: false,
          series: [{ data: [] }],
        },
        {
          value: "이해x",
          show: false,
          series: [{ data: [] }],
        },
        {
          value: "이해o",
          show: false,
          series: [{ data: [] }],
        },
        {
          value: "집중o",
          show: false,
          series: [{ data: [] }],
        },
      ],
      selected: "",
    };
  },
  computed: {
    statistics() {
      return this.getRecordedStatistics();
    },
  },
  watch: {
    statistics() {
      this.setChart();
    },
  },
  methods: {
    ...mapGetters("StatisticsStore", ["getRecordedStatistics"]),

    setChart() {
      // let ss = JSON.parse(this.statistics);
      // console.log(ss);
      console.log(this.statistics);
      this.chartList[0].series[0].data = this.statistics["-2"];
      this.chartList[1].series[0].data = this.statistics["-1"];
      this.chartList[2].series[0].data = this.statistics["1"];
      this.chartList[3].series[0].data = this.statistics["2"];

      //비율로 바꿔주기
      let maxSize = 0; //수업길이
      Object.keys(this.statistics).forEach((key) => {
        maxSize =
          maxSize > this.statistics[key].length
            ? maxSize
            : this.statistics[key].length;
      });
      //수업길이보다 작다면 부족한 시간만큼 0추가해주기
      Object.keys(this.statistics).forEach((key) => {
        if (this.statistics[key].length < maxSize) {
          let diff = maxSize - this.statistics[key].length;
          for (let i = 0; i < diff; i++) {
            this.statistics[key].unshift(0);
          }
        }
      });
      //각 시간에서의 값들을 비율로 바꿔주기
      for (let i = 0; i < maxSize; i++) {
        let num = 0; // i번째 시간에서 총 학생 수
        num += this.statistics[-2][i];
        num += this.statistics[2][i];

        if (num == 0) {
          this.statistics[-2][i] = 0;
          this.statistics[-1][i] = 0;
          this.statistics[0][i] = 0;
          this.statistics[1][i] = 0;
          this.statistics[2][i] = 0;
        } else {
          this.statistics[-2][i] = parseInt(
            (this.statistics[-2][i] * 100) / num
          );
          this.statistics[-1][i] = parseInt(
            (this.statistics[-1][i] * 100) / num
          );
          this.statistics[0][i] = parseInt((this.statistics[0][i] * 100) / num);
          this.statistics[1][i] = parseInt((this.statistics[1][i] * 100) / num);
          this.statistics[2][i] = parseInt((this.statistics[2][i] * 100) / num);
        }
      }
    },
    showChart(index) {
      for (let i = 0; i < this.chartList.length; i++) {
        this.chartList[i].show = false;
      }
      for (let i = 0; i < this.chartList.length; i++) {
        if (i === index) {
          this.chartList[i].show = true;
        }
      }
    },
  },
};
</script>

<style scoped>
.radioGroup {
  display: flex;
  justify-content: space-evenly;
}
.lecture-video-container {
  height: 90vh;
}
.video {
  height: 50%;
  width: 100%;
  background-color: white;
}
</style>
