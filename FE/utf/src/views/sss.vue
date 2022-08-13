<template>
  <div>
    <div v-for="(item, index) in chartList" :key="index">
      <chart v-if="item.show" :series="item.series" />
    </div>
    <div>
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
import { mapActions, mapGetters } from "vuex";
import Chart from "@/components/lecture/RecordChart.vue";

export default {
  name: "LineChart",
  components: {
    Chart,
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
  methods: {
    ...mapActions("StatisticsStore", ["setRecordStatistics"]),
    ...mapGetters("StatisticsStore", ["getRecordedStatistics"]),
    setChart() {
      const statistics = this.getRecordedStatistics();

      this.chartList[0].series[0].data = statistics[-2];
      this.chartList[1].series[0].data = statistics[-1];
      this.chartList[2].series[0].data = statistics[1];
      this.chartList[3].series[0].data = statistics[2];
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
  mounted() {
    this.setRecordStatistics();
    this.setChart();
  },
};
</script>

<style scoped></style>
