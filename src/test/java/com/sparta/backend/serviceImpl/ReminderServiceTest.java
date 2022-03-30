package com.sparta.backend.serviceImpl;

import com.sparta.backend.model.*;
import com.sparta.backend.repository.ReminderRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class ReminderServiceTest {

    @Mock
    ReminderRepository reminderRepository;
    
    // hashtag
    static String hashtag1 = "IT";
    
    static Hashtag memberHashtag;
    static Hashtag articleHashtag;
    
    // member
    static String email = "test@email.com";
    static String password = "testPassword";
    static String memberName = "testMemberName";
    static String kakaoId = "testKakaoId";
    static String profileImage = "testProfileImage";
    
    static Member member;

    // articleFolder
    static String articleFolderName = "미분류 컬랙션";

    static ArticleFolder articleFolder;

    // article
    static String url = "testUrl";
    static String titleOg = "testTitleOg";
    static String imgOg = "testImgOg";
    static String contentOg = "testContentOg";

    static Article article;

    @BeforeAll
    static void setup() {
        
        // 해쉬태그 생성
        memberHashtag = Hashtag.builder()
                .hashtag1(hashtag1)
                .build();

        articleHashtag = Hashtag.builder()
                .hashtag1(hashtag1)
                .build();
        
        // 멤버 생성
        member = Member.builder()
                .email(email)
                .password(password)
                .memberName(memberName)
                .kakaoId(kakaoId)
                .memberRoles(Collections.singletonList("ROLE_USER"))
                .hashtag(memberHashtag)
                .profileImage(profileImage)
                .build();

        memberHashtag.setMember(member);

        // 아티클폴더 생성
        articleFolder = ArticleFolder.builder()
                .articleFolderName(articleFolderName)
                .deleteable(false)
                .folderHide(true)
                .member(member)
                .build();

        // 아티클 생성
        article = Article.builder()
                .url(url)
                .titleOg(titleOg)
                .imgOg(imgOg)
                .contentOg(contentOg)
                .reviewHide(false)
                .readCount(0)
                .hashtag(articleHashtag)
                .articleFolder(articleFolder)
                .build();

        article.setArticleFolder(articleFolder);
        article.setHashtag(articleHashtag);
        articleHashtag.setArticle(article);
    }

    @Test
    @Order(1)
    @DisplayName("리마인더 생성 테스트")
    void createReminder() {
        // given
        System.out.println(article);
        System.out.println();
    }

    @Test
    @Order(2)
    @DisplayName("리마인더 수정 테스트")
    void editReminder() {
    }

    @Test
    @Order(3)
    @DisplayName("리마인더 확인 테스트")
    void getReminders() {
    }

    @Test
    @Order(4)
    @DisplayName("리마인더 삭제 테스트")
    void deleteReminder() {
    }
}