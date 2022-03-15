package com.sparta.backend.controller;

import com.sparta.backend.message.RestResponseMessage;
import com.sparta.backend.model.ArticleFolder;
import com.sparta.backend.model.Hashtag;
import com.sparta.backend.model.Member;
import com.sparta.backend.repository.ArticleFolderRepository;
import com.sparta.backend.repository.ArticleRepository;
import com.sparta.backend.repository.HashtagRepository;
import com.sparta.backend.repository.MemberRepository;
import com.sparta.backend.requestDto.ArticleCreateRequestDto;
import com.sparta.backend.requestDto.ReminderRequestDto;
import com.sparta.backend.service.ArticleService;
import com.sparta.backend.service.ReminderService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ArticleControllerTest {

    @Autowired
    public MemberRepository memberRepository;
    @Autowired
    public HashtagRepository hashtagRepository;
    @Autowired
    public ArticleRepository articleRepository;
    @Autowired
    public ArticleFolderRepository articleFolderRepository;
    @Autowired
    public ArticleService articleService;
    @Autowired
    public ReminderService reminderService;

    // given (멤버)
    private String memberName;
    private String email;
    private String password;
    private long expiredDate;

    // given (해시태그)
    private String hashtag1;

    // given (폴더1)
    private String articleFolderName1;
    private boolean deleteable;

    // given (아티클1)
    private String url1;
    private String titleOg1;
    private String imgOg1;
    private String contentOg1;

    private Member member;
    private Hashtag hashtag;
    private ArticleFolder articleFolder1;
    private ArticleCreateRequestDto requestDto;

    @BeforeEach
    public void setup() {
        memberName = "현우1";
        password = "12341";
        expiredDate = 1L;
        email = "test12@test12.com";


        // given (해시태그)
        hashtag1 = "IT/개발";

        // given (폴더1)
        articleFolderName1 = "기본 컬렉션";
        deleteable = false;

        // given (아티클1)
        url1 = "www.naver.com";
        titleOg1 = "네이버";
        imgOg1 = "www.naver.com";
        contentOg1 = "네이버 좋아요";

        hashtag = Hashtag.builder()
                .hashtag1(hashtag1)
                .build();

        member = Member.builder()
                .memberName(memberName)
                .email(email)
                .password(password)
                .expiredDate(expiredDate)
                .hashtag(hashtag)
                .memberRoles(Collections.singletonList("ROLE_USER"))
                .build();

        // 폴더 1
        articleFolder1 = ArticleFolder.builder()
                .articleFolderName(articleFolderName1)
                .deleteable(deleteable)
                .member(member)
                .build();

        requestDto = ArticleCreateRequestDto.builder()
                .url(url1)
                .titleOg(titleOg1)
                .imgOg(imgOg1)
                .contentOg(contentOg1)
                .hashtag(hashtag)
                .articleFolder(articleFolder1)
                .buttonDate(7)
                .build();

        memberRepository.save(member);
        articleFolderRepository.save(articleFolder1);
        articleService.createArticle(requestDto);
    }

    @Test()
    @DisplayName("아티클 생성 (리마인더)")
    public void createArticles() {
        articleService.createArticle(requestDto);
        if (requestDto.getButtonDate() != 0) {
            ReminderRequestDto requestDto1 = ReminderRequestDto.builder()
                    .titleOg(requestDto.getTitleOg())
                    .buttonDate(requestDto.getButtonDate())
                    .url(requestDto.getUrl())
                    .build();
            reminderService.createReminder(requestDto1, member);
        }
    }
}