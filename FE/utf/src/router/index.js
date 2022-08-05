import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import LoginView from "../views/LoginView.vue";
import RedirectView from "../views/RedirectView.vue";
import JoinFormView from "../views/JoinFormView.vue";

const routes = [
  {
    path: "/login",
    name: "login",
    component: LoginView,
  },
  {
    path: "/joinForm",
    name: "joinForm",
    component: JoinFormView,
  },
  {
    path: "/redirect",
    name: "redirect",
    component: RedirectView,
  },
  {
    path: "/",
    name: "home",
    component: HomeView,
  },
  {
    path: "/lecture",
    name: "lecture",
    component: () => import("@/views/lecture/LectureRoom.vue"),
  },
  {
    path: "/face",
    name: "face",
    component: () => import("../views/FaceAPIView.vue"),
  },
  {
    path: "/main",
    name: "main",
    component: () => import("../views/Main.vue"),
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
