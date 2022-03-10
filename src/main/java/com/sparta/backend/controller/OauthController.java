package com.sparta.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.backend.jwt.JwtTokenProvider;
import com.sparta.backend.message.DataMessage;
import com.sparta.backend.model.Member;
import com.sparta.backend.oauthDto.KakaoMemberInfoRequestDto;
import com.sparta.backend.oauthDto.KakaoMemberInfoResponseDto;
import com.sparta.backend.oauthDto.KakaoMemberRegisterRequestDto;
import com.sparta.backend.service.OauthService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class OauthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final OauthService oauthService;

    // 카카오 로그인 실행
    @GetMapping("/user/kakao/callback")
    public ResponseEntity<DataMessage> kakaoLogin(@RequestParam String code) throws JsonProcessingException {
        // 인가 코드 발행, 토큰 발행 및 API 호출
        KakaoMemberInfoRequestDto kakaoUserInfoRequestDto = oauthService.getKakaoInfo(code);
        // 호출한 정보로 회원가입 여부 판별
        boolean isExist = oauthService.checkIfMemberExists(kakaoUserInfoRequestDto);

        if (isExist) {
            KakaoMemberInfoResponseDto kakaoUserInfoResponseDto = oauthService.ifNeededCreateKakaoMemberAndLogin(kakaoUserInfoRequestDto);
            String token = jwtTokenProvider.createToken(kakaoUserInfoResponseDto.getMember().getUsername(), kakaoUserInfoResponseDto.getMember().getMemberRoles());

            Map<String, Object> map = new HashMap<>();
            map.put("login", true);
            map.put("token", token);

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
        String token = jwtTokenProvider.createToken(member.getUsername(), member.getMemberRoles());

        Map<String, Object> map = new HashMap<>();
        map.put("login", true);
        map.put("token", token);

        return new ResponseEntity<>(new DataMessage<>("로그인 성공", map), HttpStatus.OK);
    }
}
