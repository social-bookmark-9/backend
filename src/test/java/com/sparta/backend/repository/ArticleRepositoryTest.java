package com.sparta.backend.repository;

import com.sparta.backend.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
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
    @DisplayName("아티클 생성 (성공케이스)")
    void createArticle() {

        // 멤버 given
        String memberName = "철수";
        String email = "abc@abc.com";
        String password = "1234";
        Long expiredDate = 1L;

        // 해시태그 given
        String firstHashtag = "IT/개발";

        // 아티클 폴더 given (Default)
        String articleFolderName = "기본 컬렉션";
        Boolean deleteable = false;
        List<Article> articles = new ArrayList<Article>();

        // 아티클 given
        String url = "www.naver.com";
        String titleOg = "네이버";
        String imgOg = "www.naver.com";
        Boolean reviewHide = false;
        int readCount = 0;

        Hashtag hashtag = Hashtag.builder()
                .firstHashtag(firstHashtag)
                .build();

        Member member = Member.builder()
                .memberName(memberName)
                .email(email)
                .password(password)
                .expiredDate(expiredDate)
                .hashtag(hashtag)
                .memberRole(MemberRoleEnum.USER)
                .build();

        ArticleFolder defaultArticleFolder = ArticleFolder.builder()
                .articleFolderName(articleFolderName)
                .deleteable(deleteable)
                .member(member)
                .build();

        Article article = Article.createArticleDtoBuilder()
                .url(url)
                .titleOg(titleOg)
                .imgOg(imgOg)
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
        assertEquals(reviewHide, article.isReviewHide());
        assertEquals(readCount, article.getReadCount());
        assertEquals(hashtag, article.getHashtag());
        assertEquals(articleFolderName, article.getArticleFolder().getArticleFolderName());
    }
}