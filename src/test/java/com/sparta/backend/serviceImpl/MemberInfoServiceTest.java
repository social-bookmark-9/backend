package com.sparta.backend.serviceImpl;

import com.sparta.backend.model.Hashtag;
import com.sparta.backend.model.Member;
import com.sparta.backend.repository.MemberRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class MemberInfoServiceTest {

    @Mock
    MemberRepository memberRepository;

    // hashtag
    static String hashtag1 = "IT";

    static Hashtag memberHashtag;

    // member
    static String email = "test@email.com";
    static String password = "testPassword";
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
                .password(password)
                .memberName(memberName)
                .kakaoId(kakaoId)
                .memberRoles(Collections.singletonList("ROLE_USER"))
                .hashtag(memberHashtag)
                .profileImage(profileImage)
                .build();
    }

    @Test
    @Order(1)
    @DisplayName("프로필 닉네임 수정 테스트")
    void editProfileMemberName() {

        // given
        String changeMemberName = "newName";

        // when
        setupMember.editMemberName(changeMemberName);

        // then
        Assertions.assertEquals(setupMember.getMemberName(), changeMemberName);
    }

    @Test
    @Order(2)
    @DisplayName("프로필 이미지 수정 테스트")
    void editProfileImageUrl() {

        // given
        String changeProfileImageUrl = "newImageUrl";

        // when
        setupMember.editProfileImageUrl(changeProfileImageUrl);

        // then
        Assertions.assertEquals(setupMember.getProfileImage(), changeProfileImageUrl);
    }

    @Test
    @Order(3)
    @DisplayName("프로필 자기소개 수정 테스트")
    void editProfileStatusMessage() {

        // given
        String changeUserDesc = "newUserDesc";

        // when
        setupMember.editStatusMessage(changeUserDesc);

        // then
        Assertions.assertEquals(setupMember.getMemberComment(), changeUserDesc);
    }

    @Test
    @Order(4)
    @DisplayName("프로필 관심분야 수정하기")
    void editHashtag() {

        // given
        String newHashtag1 = "IT";
        String newHashtag2 = "마케팅";
        String newHashtag3 = "인간관계";

        // when
        setupMember.getHashtag().setHashtag(newHashtag1, newHashtag2, newHashtag3);

        //then
        Assertions.assertEquals(setupMember.getHashtag().getHashtag1(), newHashtag1);
        Assertions.assertEquals(setupMember.getHashtag().getHashtag2(), newHashtag2);
        Assertions.assertEquals(setupMember.getHashtag().getHashtag3(), newHashtag3);
    }

    @Test
    @Order(5)
    @DisplayName("프로필 sns 수정하기")
    void editProfileSnsUrl() {

        // given
        String instagramUrl = "instagram";
        String githubUrl = "github";
        String brunchUrl = "brunch";
        String blogUrl = "blog";
        String websiteUrl = "website";

        // when
        // 인스타그램
        if(instagramUrl != null) {
            if(Objects.equals(instagramUrl, "")) {
                setupMember.setInstagramUrl(null);
            } else {
                setupMember.setInstagramUrl(instagramUrl);
            }
        }
        // 깃헙
        if(githubUrl != null) {
            if (Objects.equals(githubUrl, "")) {
                setupMember.setGithubUrl(null);
            } else {
                setupMember.setGithubUrl(githubUrl);
            }
        }
        // 브런치
        if(brunchUrl != null) {
            if (Objects.equals(brunchUrl, "")) {
                setupMember.setBrunchUrl(null);
            } else {
                setupMember.setBrunchUrl(brunchUrl);
            }
        }
        // 개인 블로그
        if(blogUrl != null) {
            if (Objects.equals(blogUrl, "")) {
                setupMember.setBlogUrl(null);
            } else {
                setupMember.setBlogUrl(blogUrl);
            }
        }
        // 웹사이트
        if(websiteUrl != null) {
            if (Objects.equals(websiteUrl, "")) {
                setupMember.setWebsiteUrl(null);
            } else {
                setupMember.setWebsiteUrl(websiteUrl);
            }
        }

        // then
        Assertions.assertEquals(setupMember.getInstagramUrl(), instagramUrl);
        Assertions.assertEquals(setupMember.getGithubUrl(), githubUrl);
        Assertions.assertEquals(setupMember.getBrunchUrl(), brunchUrl);
        Assertions.assertEquals(setupMember.getBlogUrl(), blogUrl);
        Assertions.assertEquals(setupMember.getWebsiteUrl(), websiteUrl);
    }

    @Test
    @Order(6)
    @DisplayName("프로필 이메일 수정하기")
    void editEmail() {

        // given
        String newEmail = "newEmail";

        // when
        setupMember.editEmail(newEmail);

        // then
        Assertions.assertEquals(newEmail, setupMember.getEmail());
    }
}