package com.sparta.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.backend.jwt.JwtTokenProvider;
import com.sparta.backend.message.RestResponseMessage;
import com.sparta.backend.model.Member;
import com.sparta.backend.oauthDto.KakaoMemberInfoRequestDto;
import com.sparta.backend.oauthDto.KakaoMemberRegisterRequestDto;
import com.sparta.backend.oauthDto.TokenDto;
import com.sparta.backend.oauthDto.TokenRequestDto;
import com.sparta.backend.responseDto.MemberLoginResponseDto;
import com.sparta.backend.service.OauthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OauthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final OauthService oauthService;

    // 카카오 로그인
    @GetMapping("/api/users/login")
    public ResponseEntity<RestResponseMessage> kakaoServerLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        // 인가 코드 발행, 토큰 발행 및 API 호출
        KakaoMemberInfoRequestDto kakaoMemberInfoRequestDto = oauthService.getKakaoInfo(code);
        // 호출한 정보로 회원가입 여부 판별
        return oauthService.checkRegister(kakaoMemberInfoRequestDto, response);
    }

    // 카카오 회원가입 실행
    @PostMapping("/api/users/register")
    public ResponseEntity<RestResponseMessage> kakaoRegister(@RequestBody KakaoMemberRegisterRequestDto kakaoMemberRegisterRequestDto, HttpServletResponse response) {
        // 받아온 정보로 회원가입 진행
        Member member = oauthService.createKakaoMember(kakaoMemberRegisterRequestDto);
        // 로그인 ( 토큰 발행 )
        TokenDto token = jwtTokenProvider.createAccessRefreshToken(member.getUsername(), member.getMemberRoles());
        // 리프레시 토큰 저장
        oauthService.saveRefreshToken(member, token);
        // 로그인 한 유저정보 내려주기
        MemberLoginResponseDto myInfo = new MemberLoginResponseDto(member);

        Map<String, Object> map = new HashMap<>();
        map.put("login", true);
        map.put("token", token);
        map.put("myInfo", myInfo);
        
        // 크롬익스텐션 용 엑세스 토큰
        Cookie cookie = new Cookie("accessToken", token.getAccessToken());
        cookie.setMaxAge(1 * 24 * 60 * 60);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        response.addCookie(cookie);

        return new ResponseEntity<>(new RestResponseMessage<>(true,"로그인 성공", map), HttpStatus.OK);
    }

    // 토큰 재발급
    @PostMapping("/api/users/token")
    public ResponseEntity<RestResponseMessage> tokenReissue(@RequestBody TokenRequestDto tokenRequestDto) {
        TokenDto token = oauthService.reissue(tokenRequestDto);
        return new ResponseEntity<>(new RestResponseMessage<>(true,"토큰 재발급", token), HttpStatus.OK);
    }

    // 로그아웃 ( DB에 저장된 리프레시 토큰 삭제 )
    @PostMapping("/api/users/logout")
    public ResponseEntity<RestResponseMessage> kakaoLogout(@RequestBody TokenRequestDto tokenRequestDto) {
        oauthService.deleteRefreshToken(tokenRequestDto.getRefreshToken());
        return new ResponseEntity<>(new RestResponseMessage<>(true,"로그아웃 성공", ""), HttpStatus.OK);
    }

    // 토큰 테스트 (403)
    @Secured("ROLE_USER")
    @PostMapping("/api/users/test")
    public String tokenTest() {
        return "good";
    }

    // 토큰 테스트 2 (403)
    @Secured("ROLE_ADMIN")
    @PostMapping("/api/admins/test")
    public String tokenTest2() {
        return "fail";
    }

}
