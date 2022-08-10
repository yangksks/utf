import { createRouter, createWebHistory } from "vue-router";
import LoginView from "@/views/LoginView.vue";
import RedirectView from "@/views/RedirectView.vue";
import JoinFormView from "@/views/JoinFormView.vue";

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
    name: "lecture",
    component: () => import("@/views/LectureRoom.vue"),
  },
  {
    path: "/main",
    name: "main",
    component: () => import("@/views/Main.vue"),
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
