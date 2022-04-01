package com.sparta.backend.serviceImpl;

import com.sparta.backend.model.*;
import com.sparta.backend.repository.ArticleRepository;
import com.sparta.backend.repository.ReminderRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class ReminderServiceTest {

    @Mock
    ReminderRepository reminderRepository;
    @Mock
    ArticleRepository articleRepository;
    
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
    
    static Member setupMember;

    // articleFolder
    static String articleFolderName = "미분류 컬랙션";

    static ArticleFolder setupArticleFolder;

    // article
    static String url = "testUrl";
    static String titleOg = "testTitleOg";
    static String imgOg = "testImgOg";
    static String contentOg = "testContentOg";

    static Article setupArticle;

    // reminder

    static Reminder setupReminder;

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
        setupMember = Member.builder()
                .email(email)
                .password(password)
                .memberName(memberName)
                .kakaoId(kakaoId)
                .memberRoles(Collections.singletonList("ROLE_USER"))
                .hashtag(memberHashtag)
                .profileImage(profileImage)
                .build();

        memberHashtag.setMember(setupMember);

        // 아티클폴더 생성
        setupArticleFolder = ArticleFolder.builder()
                .articleFolderName(articleFolderName)
                .deleteable(false)
                .folderHide(true)
                .member(setupMember)
                .build();

        // 아티클 생성
        setupArticle = Article.builder()
                .url(url)
                .titleOg(titleOg)
                .imgOg(imgOg)
                .contentOg(contentOg)
                .reviewHide(false)
                .readCount(0)
                .hashtag(articleHashtag)
                .articleFolder(setupArticleFolder)
                .build();

        setupArticle.setArticleFolder(setupArticleFolder);
        articleHashtag.setArticle(setupArticle);

        // 리마인더 생성
        setupReminder = Reminder.builder()
                .sendDate(LocalDate.now().plusDays(3))
                .email(setupMember.getEmail())
                .titleOg(setupArticle.getTitleOg())
                .memberName(setupMember.getMemberName())
                .imgOg(setupArticle.getImgOg())
                .buttonDate(3)
                .article(setupArticle)
                .url(setupArticle.getUrl())
                .build();
    }

    @Test
    @Order(1)
    @DisplayName("리마인더 생성 테스트")
    void createReminder() {
        // given
        Reminder saveReminder = Reminder.builder()
                .sendDate(LocalDate.now().plusDays(3))
                .email(setupMember.getEmail())
                .titleOg(setupArticle.getTitleOg())
                .memberName(setupMember.getMemberName())
                .imgOg(setupArticle.getImgOg())
                .buttonDate(3)
                .article(setupArticle)
                .url(setupArticle.getUrl())
                .build();

        when(reminderRepository.findOne(any())).thenReturn(Optional.ofNullable(saveReminder));

        // when
        Optional<Reminder> reminder = reminderRepository.findOne(any());

        // then
        Assertions.assertEquals(saveReminder, reminder.get());

    }

    @Test
    @Order(2)
    @DisplayName("리마인더 수정 테스트")
    void editReminder() {
        // given
        when(articleRepository.findById(1L)).thenReturn(Optional.ofNullable(setupArticle));
        Optional<Article> article = articleRepository.findById(1L);

        when(reminderRepository.findReminderByMemberNameAndArticle(setupMember.getMemberName(), article.get()))
                .thenReturn(setupReminder);
        Reminder reminder = reminderRepository.findReminderByMemberNameAndArticle(setupMember.getMemberName(), article.get());

        // when
        reminder.editDate(7);

        // then
        Assertions.assertEquals(reminder.getSendDate(), LocalDate.now().plusDays(7));
    }

    @Test
    @Order(3)
    @DisplayName("리마인더 리스트 가져오기 테스트")
    void getReminders() {
        // ReminderRepository 에서 테스트 예정
    }

    @Test
    @Order(4)
    @DisplayName("리마인더 삭제 테스트")
    void deleteReminder() {
        // ReminderRepository 에서 테스트 예정
    }
}