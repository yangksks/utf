package com.ssafy.utf.api.controller;

import com.ssafy.utf.api.service.KakaoUserService;
import com.ssafy.utf.db.entity.user.KakaoTokenInfo;
import com.ssafy.utf.db.entity.user.KakaoUserInfo;
import com.ssafy.utf.db.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    @Autowired
    KakaoUserService kakaoUserService;

    @GetMapping("/kakaoLogin")
    public ResponseEntity<Object> kakaoLogin(String code) {
        System.out.println("code : " + code);

        HashMap<String, Object> result = new HashMap<>();
        HttpStatus status = null;
        try {
            KakaoTokenInfo kakaoTokenInfo = kakaoUserService.sendCode(code);
            KakaoUserInfo kakaoUserInfo = kakaoUserService.sendToken(kakaoTokenInfo.getAccessToken());
            User user = kakaoUserService.socialIdCheck(kakaoUserInfo.getId());

            //가입 안했으면
            if (user == null) {
                System.out.println("가입된 회원이 존재하지 않습니다.");
                result.put("existingUser", false);
                result.put("socialLoginType", 1);
                result.put("socialId", kakaoUserInfo.getId());
            }
            //가입 했으면
            else {
                System.out.println("가입된 회원이 존재합니다.");
                result.put("existingUser", true);
                result.put("userId", user.getUserId());
                result.put("userName", user.getUserName());
                result.put("email", user.getEmail());
                result.put("phoneNumber", user.getPhoneNumber());
            }
            status = HttpStatus.OK;
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            e.printStackTrace();
        }

        return ResponseEntity.status(status).body(result);
    }

//    @PostMapping("/naverLogin")
//    public ResponseEntity<Object> naverLogin() {
//
//    }
//
//    @PostMapping("/googleLogin")
//    public ResponseEntity<Object> googleLogin() {
//
//    }
//
//    @PostMapping("/userInfo")
//    public ResponseEntity<Object> uesrInfo() {
//
//    }
//
//    @PutMapping("/{userId}")
//    public ResponseEntity<Object> updateUser(UserReq userReq) {
//
//    }
//
//    @DeleteMapping("/{userId}")
//    public ResponseEntity<Object> deleteUser() {
//
//    }

}
