package com.sparta.backend.serviceImpl;

import com.sparta.backend.model.Article;
import com.sparta.backend.model.ArticleFolder;
import com.sparta.backend.model.Hashtag;
import com.sparta.backend.model.Member;
import com.sparta.backend.repository.ArticleFolderRepository;
import com.sparta.backend.repository.ArticleRepository;
import com.sparta.backend.repository.HashtagRepository;
import com.sparta.backend.repository.MemberRepository;
import com.sparta.backend.requestDto.ArticleCreateRequestDto;
import com.sparta.backend.requestDto.ArticleUpdateRequestDto;
import com.sparta.backend.service.ArticleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class ArticleServiceImplTest {

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

    @Test
    @DisplayName("아티클 수정 (성공케이스")
    void updateArticle() {

        // given (멤버)
        String memberName = "현우";
        String email = "test1@test1.com";
        String password = "1234";
        Long expiredDate = 1L;

        // given (해시태그)
        String firstHashtag = "IT/개발";

        // given (폴더1)
        String articleFolderName1 = "기본 컬렉션";
        boolean deleteable = false;

        // given (폴더2)
        String articleFolderName2 = "이직에 필요한 아티클들 모음";
        boolean deleteable1 = true;

        // given (아티클1)
        String url1 = "www.naver.com";
        String titleOg1 = "네이버";
        String imgOg1 = "www.naver.com";
        String contentOg1 = "네이버 좋아요";
        boolean reviewHide1 = false;
        int readCount1 = 0;

        // given (아티클2)
        String url2 = "www.google.com";
        String titleOg2 = "구글";
        String imgOg2 = "www.google.com";
        String contentOg2 = "구글 좋아요";
        boolean reviewHide2 = false;
        int readCount2 = 0;

        Hashtag hashtag = Hashtag.builder()
                .firstHashtag(firstHashtag)
                .build();

        Member member = Member.builder()
                .memberName(memberName)
                .email(email)
                .password(password)
                .expiredDate(expiredDate)
                .hashtag(hashtag)
                .memberRoles(Collections.singletonList("ROLE_USER"))
                .build();

        // 폴더 1
        ArticleFolder articleFolder1 = ArticleFolder.builder()
                .articleFolderName(articleFolderName1)
                .deleteable(deleteable)
                .member(member)
                .build();

        // 폴더 2
        ArticleFolder articleFolder2 = ArticleFolder.builder()
                .articleFolderName(articleFolderName2)
                .deleteable(deleteable1)
                .member(member)
                .build();

        // 아티클1
        ArticleCreateRequestDto articleCreateRequestDto1 = ArticleCreateRequestDto.builder()
                .url(url1)
                .titleOg(titleOg1)
                .imgOg(imgOg1)
                .contentOg(contentOg1)
                .hashtag(hashtag)
                .articleFolder(articleFolder1)
                .build();

        // 아티클2
        ArticleCreateRequestDto articleCreateRequestDto2 = ArticleCreateRequestDto.builder()
                .url(url2)
                .titleOg(titleOg2)
                .imgOg(imgOg2)
                .contentOg(contentOg2)
                .hashtag(hashtag)
                .articleFolder(articleFolder1)
                .build();

        // 아티클 업데이트 Dto
        ArticleUpdateRequestDto articleUpdateRequestDto = ArticleUpdateRequestDto.builder()
                .articleFolder(articleFolder2)
                .review("정말 재밌어용")
                .reviewHide(false)
                .hashtag(hashtag)
                .build();


        // when
        // 한 멤버에 두 폴더를 생성 (기본 폴더, 커스텀 폴더)
        memberRepository.save(member);
        articleFolderRepository.save(articleFolder1);
        articleService.createArticle(articleCreateRequestDto1, member);
        Long article2Id = articleService.createArticle(articleCreateRequestDto2, member);
        articleFolderRepository.save(articleFolder2);

        System.out.println(articleFolder1.getArticles());
        System.out.println(articleFolder2.getArticles());

        // 아티클 조회
        Article article2 = articleService.getArticle(article2Id);

        // 한 멤버의 기본 폴더에서 커스텀 폴더로 이동(수정)하는 작업
        articleService.updateArticle(articleUpdateRequestDto, member, article2.getId());

        System.out.println(articleFolder1.getArticles());
        System.out.println(articleFolder2.getArticles());

        // then
        assertEquals(articleFolder2.getArticleFolderName(), article2.getArticleFolder().getArticleFolderName());
    }
}