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
        // yaxis: {
        //   categories: [0, 20, 40, 60, 80, 100],
        // },
        xaxis: {
          categories: [
            "Jan",
            "Feb",
            "Mar",
            "Apr",
            "May",
            "Jun",
            "Jul",
            "Aug",
            "Sep",
          ],
        },
      },
    };
  },
};
</script>

<style scoped></style>
