package com.sparta.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.backend.model.Hashtag;
import com.sparta.backend.model.Member;
import com.sparta.backend.oauthDto.KakaoMemberInfoRequestDto;
import com.sparta.backend.oauthDto.KakaoMemberRegisterRequestDto;
import com.sparta.backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class OauthService {

    // 비밀번호 암호화
    private final PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;

    public KakaoMemberInfoRequestDto getKakaoInfo(String code) throws JsonProcessingException {
        // 1. "인가 코드"로 "액세스 토큰" 요청
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "c27b7586bc830a0e582c3ae63780729f");
        body.add("redirect_uri", "http://localhost:8080/user/kakao/callback");
        body.add("code", code);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );
        System.out.println("토큰줘" + response);

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        String accessToken = jsonNode.get("access_token").asText();

        // 2. 토큰으로 카카오 API 호출
        // HTTP Header 생성
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        responseBody = response.getBody();
        jsonNode = objectMapper.readTree(responseBody);
        String id = String.valueOf(jsonNode.get("id").asLong());
        String email = jsonNode.get("kakao_account")
                .get("email").asText();

        return new KakaoMemberInfoRequestDto(id, email);
    }

    // 회원가입 유무 체크
    public boolean checkIfMemberExists(KakaoMemberInfoRequestDto kakaoUserInfo) {
        String kakaoId = kakaoUserInfo.getKakaoId();
        Member checkMember = memberRepository.findMemberByKakaoId(kakaoId).orElse(null);
        if (checkMember == null) {
            return false;
        }
        return true;
    }

    // 회원가입
    public Member createKakaoMember(KakaoMemberRegisterRequestDto kakaoMemberRegisterRequestDto) {

        String password = UUID.randomUUID().toString();
        String encodedPassword = passwordEncoder.encode(password);

        Hashtag hashtag = Hashtag.builder()
                .firstHashtag(kakaoMemberRegisterRequestDto.getHashtag1())
                .secondHashtag(kakaoMemberRegisterRequestDto.getHashtag2())
                .thirdHashtag(kakaoMemberRegisterRequestDto.getHashtag3())
                .build();

        Member kakaoMember = Member.builder()
                .email(kakaoMemberRegisterRequestDto.getEmail())
                .password(encodedPassword)
                .expiredDate(1L)
                .memberName(kakaoMemberRegisterRequestDto.getMemberName())
                .kakaoId(kakaoMemberRegisterRequestDto.getKakaoId())
                .memberRoles(Collections.singletonList("ROLE_USER")) // 최초 가입시 USER 로 설정
                .hashtag(hashtag)
                .build();

        memberRepository.save(kakaoMember);

        return kakaoMember;
    }
}
