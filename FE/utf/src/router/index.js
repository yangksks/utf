import { createRouter, createWebHistory } from "vue-router";

const routes = [
  {
    path: "/",
    name: "lecture",
    component: () =>
      import(/* webpackChunkName: "about" */ "@/views/lecture/LectureRoom.vue"),
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
