<template>
  <apexchart ref="chart" type="pie" :series="series" :options="chartOptions" />
</template>

<script>
import { mapActions, mapGetters } from "vuex";
import VueApexCharts from "vue3-apexcharts";

export default {
  components: {
    apexchart: VueApexCharts,
  },
  data() {
    return {
      series: [0, 0, 0],
      chartOptions: {
        chart: {
          width: "100%",
          type: "pie",
        },
        labels: ["이해함", "중립", "이해못함"],
        responsive: [
          {
            breakpoint: 480,
            options: {
              chart: {
                width: "100%",
              },
            },
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
        this.setUnderstanding();
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
