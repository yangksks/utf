package com.ssafy.utf.api.service;

import com.ssafy.utf.db.entity.user.*;

public interface NaverUserService {
    //네이버 서버로 인증 코드를 보내서 액세스/리프레쉬 토큰을 받아오는 메서드
    NaverTokenInfo sendCode(String code, String state) throws Exception;

    //네이버 서버에 액세스 토큰을 보내서 사용자 정보를 받아오는 메서드
    NaverUserInfo sendToken(String accessToken) throws Exception;

    //사용자 소셜id가 DB에 존재하는지 조회해서 회원가입 여부 판단. 존재하면 유저 정보, 존재하지 않으면 null 반환
    User socialIdCheck(String socialId);
//
//    //유저 아이디를 받아 토큰을 갱신하고 액세스 토큰 반환
//    String refreshToken(Long userId) throws Exception;
//
//    //액세스 토큰으로 카카오 로그아웃
//    void kakaoUnlink(String accessToken) throws Exception;
}
