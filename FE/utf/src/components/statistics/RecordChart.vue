<template>
  <apexchart
    ref="chart"
    width="100%"
    height="200px"
    type="line"
    :series="series"
    :options="chartOptions"
  />
</template>

<script>
import VueApexCharts from "vue3-apexcharts";

export default {
  name: "LineChart",
  components: {
    apexchart: VueApexCharts,
  },
  props: {
    series: Object,
  },
  data() {
    return {
      chartOptions: {
        chart: {
          type: "line",
          zoom: {
            enabled: false,
          },
          background: "#fff",
        },
        dataLabels: {
          enabled: false,
        },
        title: {
          text: "이해도",
          align: "left",
        },
        grid: {
          row: {
            colors: ["#f3f3f3", "transparent"], // takes an array which will be repeated on columns
            opacity: 0.5,
          },
        },
        yaxis: {
          min: 0,
          max: 100,
          tickAmount: 5,
        },
        tooltip: {
          y: {
            formatter: function (val) {
              return val + "%";
            },
          },
        },
      },
    };
  },
};
</script>

<style scoped></style>
