package com.sparta.backend.repository;

import com.sparta.backend.model.*;
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
class ArticleRepositoryTest {

    @Autowired
    public MemberRepository memberRepository;
    @Autowired
    public HashtagRepository hashtagRepository;
    @Autowired
    public ArticleRepository articleRepository;
    @Autowired
    public ArticleFolderRepository articleFolderRepository;

    @Test
    @DisplayName("아티클 생성")
    void createArticle() {

        // 멤버 given
        String memberName = "민아";
        String email = "test@test.com";
        String password = "1234";
        Long expiredDate = 1L;

        // 해시태그 given
        String hashtag1 = "IT/개발";

        // 아티클 폴더 given (Default)
        String articleFolderName = "기본 컬렉션";
        boolean deleteable = false;
        List<Article> articles = new ArrayList<Article>();

        // 아티클 given
        String url = "www.naver.com";
        String titleOg = "네이버";
        String imgOg = "www.naver.com";
        String contentOg = "네이버 좋아요";
        boolean reviewHide = false;
        int readCount = 0;

        Hashtag hashtag = Hashtag.builder()
                .hashtag1(hashtag1)
                .build();

        Member member = Member.builder()
                .memberName(memberName)
                .email(email)
                .password(password)
                .expiredDate(expiredDate)
                .hashtag(hashtag)
                .memberRoles(Collections.singletonList("ROLE_USER"))
                .build();

        ArticleFolder defaultArticleFolder = ArticleFolder.builder()
                .articleFolderName(articleFolderName)
                .deleteable(deleteable)
                .member(member)
                .build();

        Article article = Article.builder()
                .url(url)
                .titleOg(titleOg)
                .imgOg(imgOg)
                .contentOg(contentOg)
                .reviewHide(reviewHide)
                .readCount(readCount)
                .hashtag(hashtag)
                .articleFolder(defaultArticleFolder)
                .build();

        // when
        memberRepository.save(member);
        articleFolderRepository.save(defaultArticleFolder);
        articleRepository.save(article);


        // then
        assertEquals(url, article.getUrl());
        assertEquals(titleOg, article.getTitleOg());
        assertEquals(imgOg, article.getImgOg());
        assertEquals(contentOg, article.getContentOg());
        assertEquals(reviewHide, article.isReviewHide());
        assertEquals(readCount, article.getReadCount());
        assertEquals(hashtag, article.getHashtag());
        assertEquals(articleFolderName, article.getArticleFolder().getArticleFolderName());
    }
}