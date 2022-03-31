package com.sparta.backend.serviceImpl;

import com.sparta.backend.exception.BusinessException;
import com.sparta.backend.exception.ErrorCode;
import com.sparta.backend.jwt.JwtTokenProvider;
import com.sparta.backend.model.ArticleFolder;
import com.sparta.backend.model.Hashtag;
import com.sparta.backend.model.Member;
import com.sparta.backend.model.RefreshToken;
import com.sparta.backend.oauthDto.TokenDto;
import com.sparta.backend.oauthDto.TokenRequestDto;
import com.sparta.backend.repository.MemberRepository;
import com.sparta.backend.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
class OauthServiceTest {

    @Mock
    MemberRepository memberRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    JwtTokenProvider jwtTokenProvider;
    @Mock
    RefreshTokenRepository refreshTokenRepository;

    // hashtag
    static String hashtag1 = "IT";
    static String hashtag2 = "업무스킬";
    static String hashtag3 = "패션";

    static Hashtag memberHashtag;

    // member
    static String email = "test@email.com";
    static String memberName = "testMemberName";
    static String kakaoId = "testKakaoId";
    static String profileImage = "testProfileImage";

    static Member setupMember;

    @BeforeAll
    static void setup() {

        // 해쉬태그 생성
        memberHashtag = Hashtag.builder()
                .hashtag1(hashtag1)
                .build();

        // 멤버 생성
        setupMember = Member.builder()
                .email(email)
                .password("testPassword")
                .memberName(memberName)
                .kakaoId(kakaoId)
                .memberRoles(Collections.singletonList("ROLE_USER"))
                .hashtag(memberHashtag)
                .profileImage(profileImage)
                .build();
    }

    @Test
    @Order(1)
    @DisplayName("회원가입 테스트")
    void createKakaoMember() {

        // given
        String password = UUID.randomUUID().toString();
        when(passwordEncoder.encode(password)).thenReturn("12345");
        String encodedPassword = passwordEncoder.encode(password);

        Hashtag hashtag = Hashtag.builder()
                .hashtag1(hashtag1)
                .hashtag2(hashtag2)
                .hashtag3(hashtag3)
                .build();

        Member kakaoMember = Member.builder()
                .email(email)
                .password(encodedPassword)
                .memberName(memberName)
                .kakaoId(kakaoId)
                .memberRoles(Collections.singletonList("ROLE_USER")) // 최초 가입시 USER 로 설정
                .hashtag(hashtag)
                .profileImage(profileImage)
                .build();

        hashtag.setMember(kakaoMember);

        when(memberRepository.findMemberByKakaoId(kakaoId)).thenReturn(Optional.ofNullable(kakaoMember));

        // when
        Optional<Member> findmember = memberRepository.findMemberByKakaoId(kakaoId);

        // then
        Assertions.assertEquals(kakaoMember, findmember.get());
    }

    @Test
    @Order(2)
    @DisplayName("토큰 재발행 테스트")
    void reissue() {

        // given
        RefreshToken refreshToken = new RefreshToken("key", "token");
        TokenDto tokenDto = new TokenDto("bearer", "accessToken", "refreshToken", 1L);

        // AccessToken ,Refresh Token 재발급 및 리프레시 토큰 저장
        when(jwtTokenProvider.createAccessRefreshToken(setupMember.getUsername(), setupMember.getMemberRoles())).thenReturn(tokenDto);
        TokenDto newToken = jwtTokenProvider.createAccessRefreshToken(setupMember.getUsername(), setupMember.getMemberRoles());

        // when
        RefreshToken updateRefreshToken = refreshToken.updateToken(newToken.getRefreshToken());

        // then
        Assertions.assertEquals(refreshToken, updateRefreshToken);

    }
}