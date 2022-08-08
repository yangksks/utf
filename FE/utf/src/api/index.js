/* eslint-disable prettier/prettier */
import axios from "axios";
import store from "@/store/index.js";
import router from "@/router/index.js";

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

export { loginRequest };
