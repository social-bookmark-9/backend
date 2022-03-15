package com.sparta.backend.service;

import com.sparta.backend.model.Article;
import com.sparta.backend.model.ArticleFolder;
import com.sparta.backend.model.Hashtag;
import com.sparta.backend.model.Member;
import com.sparta.backend.repository.ArticleFolderRepository;
import com.sparta.backend.repository.ArticleRepository;
import com.sparta.backend.repository.FavoriteRepository;
import com.sparta.backend.repository.MemberRepository;
import com.sparta.backend.requestDto.ArticleFolderCreateRequestDto;
import com.sparta.backend.requestDto.ArticleFolderNameUpdateRequestDto;
import jdk.jfr.Percentage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class ArticleFolderServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ArticleFolderRepository articleFolderRepository;
    @Autowired
    ArticleFolderService articleFolderService;

    @PersistenceContext
    private EntityManager em;

//    @AfterEach
//    public void clear() {
//        memberRepository.deleteAll();
//    }

    @Test
    @DisplayName("아티클 폴더 생성")
    void create_articleFolder_test() {
        Member testMember = Member.builder()
                .kakaoId("test")
                .memberName("test")
                .email("test@test.com")
                .password("test")
                .profileImage("https://image.com")
                .hashtag(new Hashtag("IT", "SPRING", null))
                .memberRoles(Arrays.asList("USER", "ADMIN"))
                .build();

        memberRepository.save(testMember);

        em.flush();
        em.clear();

        Optional<Member> findMember = memberRepository.findAll().stream().findAny();

        ArticleFolderCreateRequestDto articleFolderCreateRequestDto =
                new ArticleFolderCreateRequestDto("testFolder", false);

        articleFolderService.createArticleFolder(articleFolderCreateRequestDto, findMember.get());
    }

    @Test
    @DisplayName("아티클 폴더 삭제")
    void delete_articleFolder_test() {
        Member testMember = Member.builder()
                .kakaoId("test")
                .memberName("test")
                .email("test@test.com")
                .password("test")
                .profileImage("https://image.com")
                .hashtag(new Hashtag("IT", "SPRING", null))
                .memberRoles(Arrays.asList("USER", "ADMIN"))
                .build();

        memberRepository.save(testMember);

        em.flush();
        em.clear();

        Optional<Member> findMember = memberRepository.findAll().stream().findAny();

        ArticleFolderCreateRequestDto articleFolderCreateRequestDto =
                new ArticleFolderCreateRequestDto("testFolder", false);

        articleFolderService.createArticleFolder(articleFolderCreateRequestDto, findMember.get());

        em.flush();
        em.clear();

        Optional<ArticleFolder> articleFolder = articleFolderRepository.findAll().stream().findAny();

        articleFolderService.deleteArticleFolder(articleFolder.get().getId());
    }

    @Test
    @DisplayName("아티클 폴더 제목 수정")
    void update_articleFolderName_test() {
        Member testMember = Member.builder()
                .kakaoId("test")
                .memberName("test")
                .email("test@test.com")
                .password("test")
                .profileImage("https://image.com")
                .hashtag(new Hashtag("IT", "SPRING", null))
                .memberRoles(Arrays.asList("USER", "ADMIN"))
                .build();

        memberRepository.save(testMember);

        em.flush();
        em.clear();

        Optional<Member> findMember = memberRepository.findAll().stream().findAny();

        ArticleFolderCreateRequestDto articleFolderCreateRequestDto =
                new ArticleFolderCreateRequestDto("testFolder", false);

        articleFolderService.createArticleFolder(articleFolderCreateRequestDto, findMember.get());

        em.flush();
        em.clear();

        ArticleFolderNameUpdateRequestDto articleFolderNameUpdateRequestDto =
                new ArticleFolderNameUpdateRequestDto("modifyTestFolderName");

        Optional<ArticleFolder> articleFolder = articleFolderRepository.findAll().stream().findAny();

        articleFolderService.updateArticleFolderName(articleFolderNameUpdateRequestDto, articleFolder.get().getId());
    }

    @Test
    @DisplayName("폴더 안 아티클 삭제")
    void delete_article_in_articleFolder() {

    }
}