/* eslint-disable prettier/prettier */
import axios from "axios";
import store from "@/store/index.js";
import router from "@/router/index.js";

var API_BASE_URL ="http://localhost:8081"
var OPENVIDU_BASE_URL ="http://i7a701.p.ssafy.io:5443"

// axios 객체 생성
function apiInstance() {
    const instance = axios.create({
      baseURL: API_BASE_URL,
      headers: {
        "Content-type": "application/json",
      },
    });
    return instance;
  }

  function openviduInstance() {
    const instance = axios.create({
      baseURL: OPENVIDU_BASE_URL,
      headers: {
        "Content-type": "application/json",
      },
    });
    return instance;
  }

function loginRequest(requestURL, socialLoginType, code, state) {
    axios
        .post(requestURL, {
            data: {
                socialLoginType: socialLoginType,
                code: code,
                state: state,
            },
        })
        .then((res) => {
            console.log(res);
            if (!res.data["userExist"]) {
                store.dispatch("saveTempUserInfo", res.data);
                router.push({ path: "/joinForm" });
            } else {
                store.dispatch("saveUserInfo", res.data);
                router.push({ path: "/main" });
            }
        })
        .catch((err) => {
            console.log(err);
            router.push({ path: "/login" });
        });
}

export { loginRequest,apiInstance,openviduInstance };
