/* eslint-disable prettier/prettier */
import axios from "axios";
import store from "@/store/index.js";
import router from "@/router/index.js";

async function loginRequest(requestURL, socialLoginType, code, state) {
    await axios
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
            }
        })
        .catch((err) => {
            console.log(err);
            router.push({ path: "/" });
        });
}

async function sendName(name) {
    await axios
        .post(`http://localhost:8080/api/user/join`, {
            userName: name,
            socialLoginType: store.state.tempUserInfo["socialLoginType"],
            socialId: store.state.tempUserInfo["socialId"],
            email: store.state.tempUserInfo["email"],
        })
        .then((res) => {
            console.log(res);
            store.dispatch("saveUserInfo", res.data);
        })
        .catch((err) => {
            console.log(err);
            router.push({ path: "/" });
        });
}
function deleteUserRequest(userId) {
    axios
        .delete(`http://localhost:8080/api/user/${userId}`)
        .then((res) => {
            console.log(res);
            router.push({ path: "/" });
        })
        .catch((err) => {
            console.log(err);
            router.push({ path: "/" });
        });
}
async function updateUserRequest(userId, userName, email) {
    await axios
        .put(`http://localhost:8080/api/user/${userId}`, {
            userName: userName,
            email: email
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
async function registLectureRoomRequest(lectureName) {
    await axios
        .post(`http://localhost:8080/api/lectureRoom`, {
            userId: store.state.userInfo["userId"],
            title: lectureName,
        })
        .then((res) => {
            console.log(res);
            store.dispatch('addLectureRoom', res.data);
            router.push({ path: "/main" });
            // router.push({ path: "/main", params: { lectureRoom: res.data["lectureRoom"] } });
        })
        .catch((err) => {
            console.log(err);
            router.push({ path: "/main" });
        });
}
function getLectureRoomList(userId) {
    axios
        .get(`http://localhost:8080/api/lectureRoom/${userId}`)
        .then((res) => {
            console.log(res);
            store.dispatch("saveLectureRoomList", res.data["lectureRoomList"]);
            router.push({ path: "/main" });
        })
        .catch((err) => {
            console.log(err);
            router.push({ path: "/main" });
        });
}
function deleteLectureRoom(lectureRoomId) {
    axios
        .delete(`http://localhost:8080/api/lectureRoom/${lectureRoomId}`)
        .then((res) => {
            console.log(res);
            router.push({ path: "/main" });
        })
        .catch((err) => {
            console.log(err);
            router.push({ path: "/main" });
        });
}
export {
    loginRequest,
    sendName,
    deleteUserRequest,
    updateUserRequest,
    registLectureRoomRequest,
    getLectureRoomList,
    deleteLectureRoom,
};
