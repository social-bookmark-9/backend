package com.sparta.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.backend.exception.BusinessException;
import com.sparta.backend.exception.ErrorCode;
import com.sparta.backend.jwt.JwtTokenProvider;
import com.sparta.backend.model.ArticleFolder;
import com.sparta.backend.model.Hashtag;
import com.sparta.backend.model.Member;
import com.sparta.backend.model.RefreshToken;
import com.sparta.backend.oauthDto.KakaoMemberInfoRequestDto;
import com.sparta.backend.oauthDto.KakaoMemberRegisterRequestDto;
import com.sparta.backend.oauthDto.TokenDto;
import com.sparta.backend.oauthDto.TokenRequestDto;
import com.sparta.backend.repository.ArticleFolderRepository;
import com.sparta.backend.repository.MemberRepository;
import com.sparta.backend.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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

    @Value("${client-id}")
    private String clientId;

    // 비밀번호 암호화
    private final PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final ArticleFolderRepository articleFolderRepository;

    public KakaoMemberInfoRequestDto getKakaoInfo(String code) throws JsonProcessingException {
        // 1. "인가 코드"로 "액세스 토큰" 요청
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("redirect_uri", "http://localhost:3000/api/users/login"); // 프론트 연결 테스트용
//        body.add("redirect_uri", "http://3.34.99.169/api/users/login"); // 서버 연결 테스트용
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
        String profileImage = jsonNode.get("kakao_account")
                .get("profile").get("profile_image_url").asText();

        return new KakaoMemberInfoRequestDto(id, email, profileImage);
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
                .hashtag1(kakaoMemberRegisterRequestDto.getHashtag1())
                .hashtag2(kakaoMemberRegisterRequestDto.getHashtag2())
                .hashtag3(kakaoMemberRegisterRequestDto.getHashtag3())
                .build();

        Member kakaoMember = Member.builder()
                .email(kakaoMemberRegisterRequestDto.getEmail())
                .password(encodedPassword)
                .memberName(kakaoMemberRegisterRequestDto.getMemberName())
                .kakaoId(kakaoMemberRegisterRequestDto.getKakaoId())
                .memberRoles(Collections.singletonList("ROLE_USER")) // 최초 가입시 USER 로 설정
                .hashtag(hashtag)
                .profileImage(kakaoMemberRegisterRequestDto.getProfileImage())
                .build();

        hashtag.setMember(kakaoMember);

        memberRepository.save(kakaoMember);

        ArticleFolder articleFolder = ArticleFolder.builder()
                .articleFolderName("미분류 컬렉션")
                .deleteable(false)
                .folderHide(false)
                .member(kakaoMember)
                .build();

        articleFolderRepository.save(articleFolder);

        return kakaoMember;
    }

    // 토큰 재발급
    public TokenDto reissue(TokenRequestDto tokenRequestDto) {
        // 리프레시 토큰도 만료되었을 경우 에러
        if (!jwtTokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new BusinessException(ErrorCode.REFRESH_TOKEN_EXPIRED);
        }

        // AccessToken 에서 userPk 가져오기
        String accessToken = tokenRequestDto.getAccessToken();
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);

        // userPk로 유저 검색 혹은 토큰 DB에 리프레시 토큰이 없을시 에러
        Member member = memberRepository.findMemberByKakaoId(authentication.getName())
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));
        RefreshToken refreshToken = refreshTokenRepository.findByKey(member.getKakaoId())
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));

        // 리프레시 토큰 불일치시 에러
        if (!refreshToken.getToken().equals(tokenRequestDto.getRefreshToken()))
            throw new BusinessException(ErrorCode.REFRESH_TOKEN_NOTMATCH);

        // AccessToken ,Refresh Token 재발급 및 리프레시 토큰 저장
        TokenDto newToken = jwtTokenProvider.createAccessRefreshToken(member.getUsername(), member.getMemberRoles());
        RefreshToken updateRefreshToken = refreshToken.updateToken(newToken.getRefreshToken());
        refreshTokenRepository.save(updateRefreshToken);

        return newToken;
    }

    // 로그아웃 ( 리프레시 토큰 삭제 )
    public void deleteRefreshToken(String refreshToken){
        // Refresh Token 삭제
        refreshTokenRepository.deleteRefreshTokenByToken(refreshToken);
    }
}
