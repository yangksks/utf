<template>
  <apexchart
    ref="chart"
    width="150"
    height="150"
    type="pie"
    :series="series"
    :options="chartOptions"
  />
</template>

<script>
import { mapActions, mapGetters } from "vuex";
import VueApexCharts from "vue3-apexcharts";

export default {
  components: {
    apexchart: VueApexCharts,
  },
  props: {
    lectureRoomId: Number,
  },
  data() {
    return {
      series: [0, 0, 0],
      chartOptions: {
        chart: {
          type: "pie",
        },
        labels: ["이해함", "중립", "이해못함"],
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
    ...mapActions("StatisticsStore", ["setUnderstanding"]),
    ...mapGetters("StatisticsStore", ["getCurrentUnderstanding"]),
    setChart() {
      setInterval(() => {
        this.setUnderstanding(this.lectureRoomId);
        let obj = this.getCurrentUnderstanding();
        let newChart = [obj[1], obj[0], obj[-1]];
        this.series = newChart;
      }, 5000);
    },
  },
  mounted() {
    this.setChart();
  },
};
</script>

<style scoped></style>
