package com.sparta.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.backend.jwt.JwtTokenProvider;
import com.sparta.backend.message.DataMessage;
import com.sparta.backend.model.Member;
import com.sparta.backend.model.RefreshToken;
import com.sparta.backend.oauthDto.*;
import com.sparta.backend.repository.MemberRepository;
import com.sparta.backend.repository.RefreshTokenRepository;
import com.sparta.backend.service.OauthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class OauthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final OauthService oauthService;
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    // 카카오 로그인 실행 ( 로컬 테스트용 )
    @GetMapping("/user/kakao/callback")
    public ResponseEntity<DataMessage> kakaoLocalLogin(@RequestParam String code) throws JsonProcessingException {
        // 인가 코드 발행, 토큰 발행 및 API 호출
        KakaoMemberInfoRequestDto kakaoUserInfoRequestDto = oauthService.getKakaoInfo(code);
        // 호출한 정보로 회원가입 여부 판별
        boolean isExist = oauthService.checkIfMemberExists(kakaoUserInfoRequestDto);

        if (isExist) {
            Member member = memberRepository.findMemberByKakaoId(kakaoUserInfoRequestDto.getKakaoId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다."));
            TokenDto token = jwtTokenProvider.createAccessRefreshToken(member.getUsername(), member.getMemberRoles());

            Map<String, Object> map = new HashMap<>();
            map.put("login", true);
            map.put("token", token);

            RefreshToken refreshToken = RefreshToken.builder()
                    .key(member.getKakaoId())
                    .token(token.getRefreshToken())
                    .build();
            refreshTokenRepository.save(refreshToken);

            return new ResponseEntity<>(new DataMessage<>("로그인 성공", map), HttpStatus.OK);
        } else {

            Map<String, Object> map = new HashMap<>();
            map.put("login", false);
            map.put("kakaoMemberInfo", kakaoUserInfoRequestDto);

            return new ResponseEntity<>(new DataMessage<>("아직 회원가입을 하지 않았습니다.", map), HttpStatus.OK);
        }
    }

    // 카카오 로그인 ( 프론트 연결 용 )
    @GetMapping("/api/user/login")
    public ResponseEntity<DataMessage> kakaoServerLogin(@RequestBody String code) throws JsonProcessingException {
        // 인가 코드 발행, 토큰 발행 및 API 호출
        KakaoMemberInfoRequestDto kakaoUserInfoRequestDto = oauthService.getKakaoInfo(code);
        // 호출한 정보로 회원가입 여부 판별
        boolean isExist = oauthService.checkIfMemberExists(kakaoUserInfoRequestDto);

        if (isExist) {
            Member member = memberRepository.findMemberByKakaoId(kakaoUserInfoRequestDto.getKakaoId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다."));
            TokenDto token = jwtTokenProvider.createAccessRefreshToken(member.getUsername(), member.getMemberRoles());

            Map<String, Object> map = new HashMap<>();
            map.put("login", true);
            map.put("token", token);

            RefreshToken refreshToken = RefreshToken.builder()
                    .key(member.getKakaoId())
                    .token(token.getRefreshToken())
                    .build();
            refreshTokenRepository.save(refreshToken);

            return new ResponseEntity<>(new DataMessage<>("로그인 성공", map), HttpStatus.OK);
        } else {

            Map<String, Object> map = new HashMap<>();
            map.put("login", false);
            map.put("kakaoMemberInfo", kakaoUserInfoRequestDto);

            return new ResponseEntity<>(new DataMessage<>("아직 회원가입을 하지 않았습니다.", map), HttpStatus.OK);
        }
    }

    // 카카오 회원가입 실행
    @PostMapping("/user/api/register")
    public ResponseEntity<DataMessage> kakaoRegister(@RequestBody KakaoMemberRegisterRequestDto kakaoMemberRegisterRequestDto) {
        // 받아온 정보로 회원가입 진행
        Member member = oauthService.createKakaoMember(kakaoMemberRegisterRequestDto);
        // 로그인 ( 토큰 발행 )
        TokenDto token = jwtTokenProvider.createAccessRefreshToken(member.getUsername(), member.getMemberRoles());

        Map<String, Object> map = new HashMap<>();
        map.put("login", true);
        map.put("token", token);

        RefreshToken refreshToken = RefreshToken.builder()
                .key(member.getKakaoId())
                .token(token.getRefreshToken())
                .build();
        refreshTokenRepository.save(refreshToken);

        return new ResponseEntity<>(new DataMessage<>("로그인 성공", map), HttpStatus.OK);
    }

    // 토큰 재발급
    @PostMapping("/user/token")
    public ResponseEntity<DataMessage> tokenReissue(@RequestBody TokenRequestDto tokenRequestDto) {
        TokenDto token = oauthService.reissue(tokenRequestDto);
        return new ResponseEntity<>(new DataMessage<>("토큰 재발급", token), HttpStatus.OK);
    }

    // 로그아웃 (토큰 삭제)


    // 토큰 테스트 (401)
    @PostMapping("/user/test")
    public String tokenTest() {
        return "good";
    }

    // 토큰 테스트 2 (403)
    @PostMapping("/admin/test")
    public String tokenTest2() {
        return "fail";
    }
}
