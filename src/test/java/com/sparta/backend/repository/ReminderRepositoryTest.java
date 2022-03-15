package com.sparta.backend.repository;

import com.sparta.backend.model.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ReminderRepositoryTest {

    @Autowired
    private ReminderRepository reminderRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ArticleFolderRepository articleFolderRepository;
    @Autowired
    private MemberRepository memberRepository;

    private String hashtag1;

    private String password;

    private String articleFolderName;

    private String email;
    private String titleOg;
    private String memberName;
    private String url;
    private int buttonDate;

    private String imgOg;
    private String contentOg;
    private String review;
    private boolean reviewHide;
    private int readCount;

    @BeforeEach
    public void setUp() {
        articleFolderName = "테스트 아티클폴더";
        buttonDate = 3;
        email = "leeyuwk54@gmail.com";
        titleOg = "짬뽕 맛있게 먹는법";
        memberName = "wowba";
        url = "naver.com";
        password = "1234";

        hashtag1 = "역사";

        imgOg = "naver.jpg";
        contentOg = "영욱님 테스트 내용";
        review = "";
        reviewHide = false;
        readCount = 0;
    }

    @Test
    @Order(1)
    @DisplayName("리마인더 생성 테스트")
    public void createReminder() {
        // given
        Hashtag hashtag = Hashtag.builder()
                .hashtag1(hashtag1)
                .hashtag2(null)
                .hashtag3(null)
                .build();

        Member member = Member.builder()
                .memberName(memberName)
                .email(email)
                .password(password)
                .hashtag(hashtag)
                .build();

        ArticleFolder articleFolder = ArticleFolder.builder()
                .articleFolderName(articleFolderName)
                .member(member)
                .deleteable(false)
                .build();

        Article article = Article.builder()
                .url(url)
                .titleOg(titleOg)
                .imgOg(imgOg)
                .contentOg(contentOg)
                .review(review)
                .reviewHide(reviewHide)
                .readCount(readCount)
                .hashtag(hashtag)
                .articleFolder(articleFolder)
                .build();

        Reminder reminder = Reminder.builder()
                .sendDate(LocalDate.now().plusDays(buttonDate))
                .email(email)
                .titleOg(titleOg)
                .memberName(memberName)
                .url(url)
                .buttonDate(buttonDate)
                .article(article)
                .build();

        article.setReminder(reminder);
        // when
        memberRepository.save(member);
        articleFolderRepository.save(articleFolder);
        articleRepository.save(article);
//        reminderRepository.save(reminder);
        // then
        Assertions.assertEquals(reminder, reminderRepository.findById(reminder.getId())
                .orElseThrow(() -> new IllegalArgumentException("리마인더가 존재하지 않아요.")));
    }

    @Test
    @Order(2)
    @DisplayName("리마인더 수정 테스트")
    public void editReminder() {
        // given
        Article article = articleRepository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("아티클 없음"));
        Reminder reminder = article.getReminder();
        // when
        int newButtonDate = 7;
        reminder.editDate(newButtonDate);

        // then
        Assertions.assertEquals(article.getReminder().getSendDate(), LocalDate.now().plusDays(newButtonDate));
    }

    @Test
    @Order(3)
    @DisplayName("리마인더 삭제 테스트")
    public void deleteReminder() {
        // given - when
        reminderRepository.deleteReminderByMemberNameAndTitleOg(memberName, titleOg);
        // then
        Assertions.assertNull(reminderRepository.findReminderByMemberNameAndTitleOg(memberName, titleOg));
    }
}