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
            router.push({ path: "/" });
        });
}

function getName(name) {
    axios
        .post(`http://localhost:8080/api/user/join`, {
            userName: name,
            socialLoginType: store.state.tempUserInfo["socialLoginType"],
            socialId: store.state.tempUserInfo["socialId"],
            email: store.state.tempUserInfo["email"],
        })
        .then((res) => {
            console.log(res);
            store.dispatch("saveUserInfo", res.data);
            router.push({ path: "/main" });
        })
        .catch((err) => {
            console.log(err);
            router.push({ path: "/" });
        });
}

export {
    loginRequest,
    getName
};
