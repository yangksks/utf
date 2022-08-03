package com.ssafy.utf.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.utf.db.entity.user.*;
import com.ssafy.utf.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@PropertySource("classpath:social.properties")
public class NaverUserServiceImpl implements NaverUserService {
    @Autowired
    private UserRepository userRepository;

    @Value("${NAVER_CLIENT_ID}")
    private String naverClientId;

    @Value("${NAVER_CLIENT_SECRET}")
    private String naverClientSecret;

    @Value("${NAVER_CALLBACK_URL}")
    private String naverCallbackUrl;

    //네이버 서버로 인증 코드를 보내서 액세스/리프레쉬 토큰을 받아오는 메서드
    @Override
    public NaverTokenInfo sendCode(String code, String state) throws Exception {
        System.out.println("sendCode");

        String reqUrl = "https://nid.naver.com/oauth2.0/token";

        URL url = new URL(reqUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        StringBuilder sb = new StringBuilder();
        sb.append("grant_type=authorization_code");
        sb.append("&client_id=" + naverClientId);
        sb.append("&client_secret=" + naverClientSecret);
        sb.append("&code=" + code);
        sb.append("&state=" + state);
        bw.write(sb.toString());
        bw.flush();

        int responseCode = conn.getResponseCode();
        System.out.println("responseCode : " + responseCode);

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line = "";
        String result = "";

        while ((line = br.readLine()) != null) {
            result += line;
        }
        System.out.println("response body : " + result);

        ObjectMapper om = new ObjectMapper();

        NaverTokenInfo naverTokenInfo = om.readValue(result, NaverTokenInfo.class);

        System.out.println(naverTokenInfo.toString());

        return naverTokenInfo;
    }

    //네이버 서버에 액세스 토큰을 보내서 사용자 정보를 받아오는 메서드
    @Override
    public NaverUserInfo sendToken(String accessToken) throws Exception {
        System.out.println("sendToken");
        String reqUrl = "https://openapi.naver.com/v1/nid/me";

        URL url = new URL(reqUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Authorization", "Bearer " + accessToken);

        int responseCode = conn.getResponseCode();
        System.out.println("responseCode : " + responseCode);

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line = "";
        String result = "";

        while ((line = br.readLine()) != null) {
            result += line;
        }
        System.out.println("response body : " + result);

        ObjectMapper om = new ObjectMapper();

        NaverUserInfo naverUserInfo = om.readValue(result, NaverUserInfo.class);

        System.out.println(naverUserInfo.toString());

        return naverUserInfo;
    }

    //사용자 소셜id가 DB에 존재하는지 조회해서 회원가입 여부 판단. 존재하면 유저 정보, 존재하지 않으면 null 반환
    @Override
    public User socialIdCheck(String socialId) {
        User user = userRepository.findByNaverSocialId(socialId);
        return user;
    }
//
//    //유저 아이디를 받아 토큰을 갱신하고 액세스 토큰 반환
//    @Override
//    public String refreshToken(Long userId) throws Exception {
////        System.out.println("refreshToken");
////
////        String reqUrl = "https://kauth.kakao.com/oauth/token";
////
////        String refreshToken = userRepository.findById(userId).get().getRefreshToken();
////        System.out.println(refreshToken);
////
////        URL url = new URL(reqUrl);
////        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
////
////        conn.setRequestMethod("POST");
////        conn.setDoOutput(true);
////
////        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
////        StringBuilder sb = new StringBuilder();
////        sb.append("grant_type=refresh_token");
////        sb.append("&client_id=644f22ddf456df96ce3ac3e3b870991a");
////        sb.append("&refresh_token=" + refreshToken);
////        bw.write(sb.toString());
////        bw.flush();
////
////        int responseCode = conn.getResponseCode();
////        System.out.println("responseCode : " + responseCode);
////
////        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
////        String line = "";
////        String result = "";
////
////        while ((line = br.readLine()) != null) {
////            result += line;
////        }
////        System.out.println("response body : " + result);
////
////        ObjectMapper om = new ObjectMapper();
////
////        RefreshedTokenInfo refreshedTokenInfo = om.readValue(result, RefreshedTokenInfo.class);
////
////        System.out.println(refreshedTokenInfo.toString());
////
////        return refreshedTokenInfo.getAccessToken();
//        return null;
//    }
//
//    //액세스 토큰으로 카카오 로그아웃
//    @Override
//    public void kakaoUnlink(String accessToken) throws Exception {
//        System.out.println("kakaoUnlink");
//
//        String reqUrl = "https://kapi.kakao.com/v1/user/unlink";
//
//        URL url = new URL(reqUrl);
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//
//        conn.setRequestMethod("POST");
//        conn.setDoOutput(true);
//        conn.setRequestProperty("Authorization", "Bearer " + accessToken);
//
//        int responseCode = conn.getResponseCode();
//        System.out.println("responseCode : " + responseCode);
//
//        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//        String line = "";
//        String result = "";
//
//        while ((line = br.readLine()) != null) {
//            result += line;
//        }
//        System.out.println("response body : " + result);
//    }
}
