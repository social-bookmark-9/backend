package com.sparta.backend.serviceImpl;

import com.sparta.backend.model.Article;
import com.sparta.backend.model.ArticleFolder;
import com.sparta.backend.model.Hashtag;
import com.sparta.backend.model.Member;
import com.sparta.backend.repository.ArticleFolderRepository;
import com.sparta.backend.repository.ArticleRepository;
import com.sparta.backend.repository.HashtagRepository;
import com.sparta.backend.repository.MemberRepository;
import com.sparta.backend.requestDto.ArticleFolderCreateRequestDto;
import com.sparta.backend.responseDto.ArticleFolderListResponseDto;
import com.sparta.backend.responseDto.MemberInfoResponseDto;
import com.sparta.backend.service.ArticleFolderService;
import com.sparta.backend.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@SpringBootTest
@Transactional
@Rollback(value = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@RequiredArgsConstructor
class MyPageServiceImplTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    ArticleFolderRepository articleFolderRepository;
    @Autowired
    ArticleFolderService articleFolderService;
    @Autowired
    MyPageService myPageService;
    @Autowired
    HashtagRepository hashtagRepository;

    @PersistenceContext
    private EntityManager em;

    @Test
    @DisplayName("필요 정보 저장")
    @Order(1)
    void postConstruct() {
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

        Hashtag articleHashtag3 = Hashtag.builder()
                .hashtag1("SWIM")
                .hashtag2("LOVE")
                .hashtag3("MOVIE")
                .build();

        Hashtag articleHashtag4 = Hashtag.builder()
                .hashtag1("IT")
                .hashtag2("LOVE")
                .hashtag3("MOVIE")
                .build();

        Hashtag articleHashtag5 = Hashtag.builder()
                .hashtag1("SWIM")
                .hashtag2("LOVE")
                .hashtag3("MOVIE")
                .build();

        Hashtag articleHashtag6 = Hashtag.builder()
                .hashtag1("READ")
                .hashtag2("LOVE")
                .hashtag3("MOVIE")
                .build();

        Article article1 = Article.builder()
                .url("https://testArticle1.com")
                .titleOg("https://testTitleOg1.com")
                .imgOg("https://testImgOg1.com")
                .contentOg("https://testContentOg1.com")
                .review("testReview1")
                .reviewHide(false)
                .readCount(1)
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

        Article article3 = Article.builder()
                .url("https://testArticle3.com")
                .titleOg("https://testTitleOg3.com")
                .imgOg("https://testImgOg3.com")
                .contentOg("https://testContentOg3.com")
                .review("testReview3")
                .reviewHide(false)
                .readCount(3)
                .hashtag(articleHashtag3)
                .articleFolder(findFolder.get())
                .member(findMember.get())
                .build();

        articleHashtag3.setArticle(article3);

        Article article4 = Article.builder()
                .url("https://testArticle4.com")
                .titleOg("https://testTitleOg4.com")
                .imgOg("https://testImgOg4.com")
                .contentOg("https://testContentOg4.com")
                .review("testReview4")
                .reviewHide(false)
                .readCount(4)
                .hashtag(articleHashtag4)
                .articleFolder(findFolder.get())
                .member(findMember.get())
                .build();

        articleHashtag4.setArticle(article4);

        Article article5 = Article.builder()
                .url("https://testArticle5.com")
                .titleOg("https://testTitleOg5.com")
                .imgOg("https://testImgOg5.com")
                .contentOg("https://testContentOg5.com")
                .review("testReview5")
                .reviewHide(false)
                .readCount(1)
                .hashtag(articleHashtag5)
                .articleFolder(findFolder.get())
                .member(findMember.get())
                .build();

        articleHashtag5.setArticle(article5);

        Article article6 = Article.builder()
                .url("https://testArticle6.com")
                .titleOg("https://testTitleOg6.com")
                .imgOg("https://testImgOg6.com")
                .contentOg("https://testContentOg6.com")
                .review("testReview6")
                .reviewHide(false)
                .readCount(0)
                .hashtag(articleHashtag6)
                .articleFolder(findFolder.get())
                .member(findMember.get())
                .build();

        articleHashtag6.setArticle(article6);

        articleRepository.save(article1);
        articleRepository.save(article2);
        articleRepository.save(article3);
        articleRepository.save(article4);
        articleRepository.save(article5);
        articleRepository.save(article6);

        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("내 마이페이지용 사용자 정보 조회")
    @Order(2)
    void getMyMemberInfo() {
        Optional<Member> findMember = memberRepository.findAll().stream().findAny();
        MemberInfoResponseDto myMemberInfo = myPageService.getMyMemberInfo(findMember.get());
        System.out.println(myMemberInfo.getMemberName());
    }

    @Test
    @DisplayName("다른 사람의 마이페이지용 사용자 정보 조회")
    @Order(3)
    void getOtherMemberInfo() {
        Optional<Member> findMember = memberRepository.findAll().stream().findAny();
        MemberInfoResponseDto otherMemberInfo = myPageService.getOtherMemberInfo(findMember.get().getId());
        System.out.println(otherMemberInfo.getMemberName());
    }

    @Test
    @DisplayName("내 마이페이지용 아티클 폴더 조회")
    @Order(4)
    void getMyArticleFolderList() {
        Optional<Member> findMember = memberRepository.findAll().stream().findAny();
        List<ArticleFolderListResponseDto> myArticleFolderList = myPageService.getMyArticleFolderList(findMember.get());
        for (ArticleFolderListResponseDto articleFolderListResponseDto : myArticleFolderList) {
            System.out.println(articleFolderListResponseDto.getFolderId());
        }
    }

    @Test
    @DisplayName("다른 사람의 마이페이지용 아티클 폴더 조회")
    @Order(5)
    void getOtherArticleFolderList() {
        Optional<Member> findMember = memberRepository.findAll().stream().findAny();
        List<ArticleFolderListResponseDto> otherArticleFolderList = myPageService.getOtherArticleFolderList(findMember.get().getId());
        for (ArticleFolderListResponseDto articleFolderListResponseDto : otherArticleFolderList) {
            System.out.println(articleFolderListResponseDto.getFolderId());
        }
    }
    @Test
    @DisplayName("모든 데이터 지우기")
    @Order(6)
    public void deleteAll() {
        articleFolderRepository.deleteAll();
        articleRepository.deleteAll();
        memberRepository.deleteAll();
        hashtagRepository.deleteAll();
    }

}