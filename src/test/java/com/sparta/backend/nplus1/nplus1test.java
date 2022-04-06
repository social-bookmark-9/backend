package com.sparta.backend.nplus1;

import com.sparta.backend.model.Article;
import com.sparta.backend.model.ArticleFolder;
import com.sparta.backend.model.Hashtag;
import com.sparta.backend.model.Member;
import com.sparta.backend.repository.ArticleFolderRepository;
import com.sparta.backend.repository.ArticleRepository;
import com.sparta.backend.repository.HashtagRepository;
import com.sparta.backend.repository.MemberRepository;
import com.sparta.backend.requestDto.ArticleFolderCreateRequestDto;
import com.sparta.backend.service.ArticleFolderService;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
@RequiredArgsConstructor
public class nplus1test {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ArticleFolderService articleFolderService;
    @Autowired
    private ArticleFolderRepository articleFolderRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    HashtagRepository hashtagRepository;

    @AfterEach
    public void deleteAll() {
        articleFolderRepository.deleteAll();
        articleRepository.deleteAll();
        memberRepository.deleteAll();
        hashtagRepository.deleteAll();
    }

    @Test
    public void test1() {
        // 회원 등록
        Hashtag memberHashtag = Hashtag.builder()
                .hashtag1("IT")
                .hashtag2("주식")
                .hashtag3("스포츠")
                .build();

        Member testMember = Member.builder()
                .kakaoId("test")
                .memberName("test")
                .email("test@test.com")
                .password("test")
                .profileImage("https://image.com")
                .hashtag(memberHashtag)
                .memberRoles(Arrays.asList("USER", "ADMIN"))
                .build();

        memberHashtag.setMember(testMember);
        memberRepository.save(testMember);

        em.flush();
        em.clear();

        // 폴더 생성
        Optional<Member> findMember = memberRepository.findAll().stream().findFirst();

        ArticleFolderCreateRequestDto articleFolderCreateRequestDto =
                new ArticleFolderCreateRequestDto("testFolder", false);

        articleFolderService.createArticleFolder(articleFolderCreateRequestDto, findMember.get());

        em.flush();
        em.clear();

        // 폴더에 아티클 저장
        Optional<ArticleFolder> findFolder = articleFolderRepository.findAll().stream().findAny();

        Hashtag articleHashtag1 = Hashtag.builder()
                .hashtag1("IT")
                .hashtag2("FASHION")
                .hashtag3("MOVIE")
                .build();

        Hashtag articleHashtag2 = Hashtag.builder()
                .hashtag1("IT")
                .hashtag2("LOVE")
                .hashtag3("MOVIE")
                .build();

        Article article1 = Article.builder()
                .url("https://testArticle.com")
                .titleOg("https://testTitleOg.com")
                .imgOg("https://testImgOg.com")
                .contentOg("https://testContentOg.com")
                .review("testReview")
                .reviewHide(false)
                .readCount(0)
                .hashtag(articleHashtag1)
                .articleFolder(findFolder.get())
                .member(findMember.get())
                .build();

        articleHashtag1.setArticle(article1);

        Article article2 = Article.builder()
                .url("https://testArticle2.com")
                .titleOg("https://testTitleOg2.com")
                .imgOg("https://testImgOg2.com")
                .contentOg("https://testContentOg2.com")
                .review("testReview2")
                .reviewHide(false)
                .readCount(0)
                .hashtag(articleHashtag2)
                .articleFolder(findFolder.get())
                .member(findMember.get())
                .build();

        articleHashtag2.setArticle(article2);

        articleRepository.save(article1);
        articleRepository.save(article2);

        em.flush();
        em.clear();

        System.out.println("############### 아티클 쿼리 시작 #################");
        List<Article> articles = articleRepository.findAll();

        for(Article article : articles) {
            // one to one 양방향은 lazy를 해도 대상 테이블에 외래키가 있으면 (hashtag) 프록시 문제로 인해 항상 eager 로 변경된다. ( 가짜 프록시 객체로 가져오지 않는다.)
            // @EntityGraph를 활용해 한방쿼리로 가져오는지 확인해보자. -> 정상적으로 한번에 가져온다!
            System.out.println("article.hashtah = " + article.getHashtag());
            System.out.println("article.reminder = " + article.getReminder());
        }

        System.out.println("############### 종료 #################");

//        System.out.println("############### 아티클 폴더 쿼리 시작 #################");
//        articleFolderRepository.findAll();
//        System.out.println("############### 종료 #################");

        System.out.println("############### 멤버 쿼리 시작 #################");
        List<Member> members = memberRepository.findAll();

        for(Member member : members) {
            // one to one 양방향은 lazy를 해도 대상 테이블에 외래키가 있으면 (hashtag) 프록시 문제로 인해 항상 eager 로 변경된다. ( 가짜 프록시 객체로 가져오지 않는다.)
            // @EntityGraph를 활용해 한방쿼리로 가져오는지 확인해보자. -> 정상적으로 한번에 가져온다!
            System.out.println("member.hashtag = " + member.getHashtag());
        }

        System.out.println("############### 종료 #################");

    }
}
