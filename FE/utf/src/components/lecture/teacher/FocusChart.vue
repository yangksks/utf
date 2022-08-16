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
import VueApexCharts from "vue3-apexcharts";
import { mapActions, mapGetters } from "vuex";

export default {
  components: {
    apexchart: VueApexCharts,
  },
  props: {
    lectureRoomId: Number,
  },
  data() {
    return {
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
    ...mapActions("focusStore", ["setFocusing"]),
    ...mapGetters("focusStore", ["getFocusRatio"]),
    setChart() {
      setInterval(() => {
        this.setFocusing(this.lectureRoomId);
        let obj = this.getFocusRatio();
        let newChart = [obj[2], obj[-2]];
        this.series = newChart;
      }, 5000);
    },
  },
  mounted() {
    this.setChart();
  },
};
</script>

<style></style>
