package com.sparta.backend.service;

import com.sparta.backend.model.Article;
import com.sparta.backend.model.ArticleFolder;
import com.sparta.backend.model.Hashtag;
import com.sparta.backend.model.Member;
import com.sparta.backend.requestDto.ArticleFolderCreateRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ArticleFolderServiceTest {
    private ArticleFolder articleFolder;
    private Member member;
    private Article article;

    @BeforeEach
    public void setMember() {
        member = Member.builder()
                .memberName("test")
                .email("test@test.com")
                .password("test")
                .expiredDate(1L)
                .hashtag(new Hashtag("test", null, null))
                .kakaoId("test")
                .memberRoles(Arrays.asList("USER", "ADMIN"))
                .build();
    }

    @BeforeEach
    public void setArticleFolder() {
        articleFolder = ArticleFolder.builder()
                .articleFolderName("testFolder")
                .deleteable(false)
                .member(member)
                .article(article)
                .build();
    }

    @BeforeEach
    public void setArticle() {
        article = Article.builder()
                .url("https://url.com")
                .titleOg("https://title.com")
                .imgOg("https://image.com")
                .hashtag(new Hashtag("test", null, null))
                .articleFolder(articleFolder)
                .build();
    }

    public ArticleFolderCreateRequestDto ArticleFolderCreateRequestDto() {
        return new ArticleFolderCreateRequestDto("testFolder");
    }

    @Test
    @DisplayName("아티클 폴더 생성")
    void create_articleFolder_test() {

    }

}