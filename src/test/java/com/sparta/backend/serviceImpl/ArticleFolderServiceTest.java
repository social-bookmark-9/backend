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
import com.sparta.backend.requestDto.ArticleFolderNameUpdateRequestDto;
import com.sparta.backend.responseDto.ArticlesInFolderResponseDto;
import com.sparta.backend.responseDto.ArticlesInfoInFolderResponseDto;
import com.sparta.backend.service.ArticleFolderService;
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

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(properties = "spring.config.location=" + "classpath:/application.yml" )
@Transactional
@Rollback(value = true)
class ArticleFolderServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    ArticleFolderRepository articleFolderRepository;
    @Autowired
    ArticleFolderService articleFolderService;
    @Autowired
    HashtagRepository hashtagRepository;


    @PersistenceContext
    private EntityManager em;

    @AfterEach
    public void deleteAll() {
        articleFolderRepository.deleteAll();
        articleRepository.deleteAll();
        memberRepository.deleteAll();
        hashtagRepository.deleteAll();
    }

    @Test
    @DisplayName("아티클 폴더 생성")
    void create_articleFolder_test() {
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
    }

    @Test
    @DisplayName("아티클 폴더 삭제")
    void delete_articleFolder_test() {
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

        articleRepository.save(article1);
        articleRepository.save(article2);

        // 폴더 삭제

        Optional<ArticleFolder> articleFolder = articleFolderRepository.findAll().stream().findFirst();

        articleFolderService.deleteArticleFolder(articleFolder.get().getMember(), articleFolder.get().getId());

        // 예외
//        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
//                                () -> articleFolderService.deleteArticleFolder(100L));
//
//        assertThat(e.getMessage()).isEqualTo("존재하지 않는 회원");
    }

    @Test
    @DisplayName("아티클 폴더 제목 수정")
    void update_articleFolderName_test() {
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
                .hashtag(Hashtag.builder()
                        .hashtag1("IT")
                        .hashtag2("주식")
                        .hashtag3("스포츠")
                        .build())
                .memberRoles(Arrays.asList("USER", "ADMIN"))
                .build();
        memberHashtag.setMember(testMember);
        memberRepository.save(testMember);

        em.flush();
        em.clear();

        // 폴더 등록
        Optional<Member> findMember = memberRepository.findAll().stream().findAny();

        ArticleFolderCreateRequestDto articleFolderCreateRequestDto =
                new ArticleFolderCreateRequestDto("testFolder", false);

        articleFolderService.createArticleFolder(articleFolderCreateRequestDto, findMember.get());

        em.flush();
        em.clear();

        // 폴더 제목 변경
        ArticleFolderNameUpdateRequestDto articleFolderNameUpdateRequestDto =
                new ArticleFolderNameUpdateRequestDto("modifyTestFolderName");

        Optional<ArticleFolder> findFolder = articleFolderRepository.findAll().stream().findFirst();

        articleFolderService.updateArticleFolderName(articleFolderNameUpdateRequestDto, findFolder.get().getId());

        // 다시 가져옴 (@Modifying(clearAutomatically = true) 로 1차캐시 클리어됨 그래서 다시 조회)
        Optional<ArticleFolder> modifiedFolder = articleFolderRepository.findAll().stream().findFirst();
        assertThat(modifiedFolder.get().getArticleFolderName()).isEqualTo("modifyTestFolderName");
    }

    @Test
    @DisplayName("폴더 안 아티클 삭제")
    void delete_article_in_articleFolder() {
        // 회원 등록
        Hashtag memberHashtag = Hashtag.builder()
                .hashtag1("IT")
                .hashtag2("sport")
                .hashtag3("movie")
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
        Optional<Member> findMember = memberRepository.findAll().stream().findAny();

        ArticleFolderCreateRequestDto articleFolderCreateRequestDto =
                new ArticleFolderCreateRequestDto("testFolder", false);

        articleFolderService.createArticleFolder(articleFolderCreateRequestDto, findMember.get());

        em.flush();
        em.clear();

        // 폴더 안에 아티클 생성 후 등록
        Optional<ArticleFolder> articleFolder = articleFolderRepository.findAll().stream().findAny();

        Hashtag articleHashtag1 = Hashtag.builder()
                .hashtag1("IT")
                .hashtag2("sport")
                .hashtag3("movie")
                .build();

        Hashtag articleHashtag2 = Hashtag.builder()
                .hashtag1("IT")
                .hashtag2("cartoon")
                .hashtag3("movie")
                .build();

        Article article1 = Article.builder()
                .url("https://testArticle.com")
                .titleOg("https://testTitleOg.com")
                .imgOg("https://testImgOg.com")
                .contentOg("https://testContentOg.com")
                .review("testReview")
                .reviewHide(false)
                .readCount(1)
                .hashtag(articleHashtag1)
                .articleFolder(articleFolder.get())
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
                .articleFolder(articleFolder.get())
                .member(findMember.get())
                .build();

        articleHashtag2.setArticle(article2);

        articleRepository.save(article1);
        articleRepository.save(article2);

        em.flush();
        em.clear();

        // 폴더 안 아티클 삭제
        Optional<ArticleFolder> findFolder = articleFolderRepository.findAll().stream().findFirst();
        Optional<Article> findArticle = articleRepository.findAll().stream().findFirst();

        articleFolderService.deleteArticleInArticleFolder(findFolder.get().getId(), findArticle.get().getId());
    }

    @Test
    @DisplayName("좋아요 추가")
    void like_add_or_remove() {
        // 회원 등록
        Hashtag memberHashtag = Hashtag.builder()
                .hashtag1("IT")
                .hashtag2("sport")
                .hashtag3("movie")
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
        Optional<Member> findMember = memberRepository.findAll().stream().findAny();

        ArticleFolderCreateRequestDto articleFolderCreateRequestDto =
                new ArticleFolderCreateRequestDto("testFolder", false);

        articleFolderService.createArticleFolder(articleFolderCreateRequestDto, findMember.get());

        em.flush();
        em.clear();

        // 좋아요 추가, 제거
        Optional<Member> findMember2 = memberRepository.findAll().stream().findFirst();
        Optional<ArticleFolder> findFolder = articleFolderRepository.findAll().stream().findFirst();

        // 추가
        articleFolderService.likeAddOrRemove(findMember2.get(), findFolder.get().getId());

        em.flush();
        em.clear();

        // 제거
        articleFolderService.likeAddOrRemove(findMember2.get(), findFolder.get().getId());

        // 다시 추가
        articleFolderService.likeAddOrRemove(findMember2.get(), findFolder.get().getId());

        em.flush();
        em.clear();

        // 폴더 삭제 시 좋아요도 삭제
        Optional<ArticleFolder> findFolder2 = articleFolderRepository.findAll().stream().findFirst();
        articleFolderService.deleteArticleFolder(findFolder2.get().getMember(), findFolder2.get().getId());
    }

    @Test
    @DisplayName("폴더 안 아티클 조회")
    void find_article_in_folder() {
        Hashtag memberHashtag1 = Hashtag.builder()
                .hashtag1("IT")
                .hashtag2("sport")
                .hashtag3("movie")
                .build();

        Hashtag memberHashtag2 = Hashtag.builder()
                .hashtag1("SPRING")
                .hashtag2("LOVE")
                .hashtag3("JPA")
                .build();

        Member testMember1 = Member.builder()
                .kakaoId("test")
                .memberName("test")
                .email("test@test.com")
                .password("test")
                .profileImage("https://image.com")
                .hashtag(memberHashtag1)
                .memberRoles(Arrays.asList("USER", "ADMIN"))
                .build();

        Member testMember2 = Member.builder()
                .kakaoId("test2")
                .memberName("test2")
                .email("test2@test2.com")
                .password("test2")
                .profileImage("https://image2.com")
                .hashtag(memberHashtag2)
                .memberRoles(Arrays.asList("USER", "ADMIN"))
                .build();

        memberHashtag1.setMember(testMember1);
        memberHashtag2.setMember(testMember2);
        Member saveMember1 = memberRepository.save(testMember1);
        Member saveMember2 = memberRepository.save(testMember2);

        em.flush();

        // 폴더 생성
        ArticleFolderCreateRequestDto articleFolderCreateRequestDto1 =
                new ArticleFolderCreateRequestDto("testFolder1", false);

        ArticleFolderCreateRequestDto articleFolderCreateRequestDto2 =
                new ArticleFolderCreateRequestDto("testFolder2", false);

        articleFolderService.createArticleFolder(articleFolderCreateRequestDto1, saveMember1);
        articleFolderService.createArticleFolder(articleFolderCreateRequestDto2, saveMember2);

        em.flush();
        em.clear();

        // 폴더 안에 아티클 생성 후 등록
        List<ArticleFolder> articleFolders = articleFolderRepository.findAll();

        Hashtag articleHashtag1 = Hashtag.builder()
                .hashtag1("IT")
                .hashtag2("sport")
                .hashtag3("movie")
                .build();

        Hashtag articleHashtag2 = Hashtag.builder()
                .hashtag1("IT")
                .hashtag2("cartoon")
                .hashtag3("movie")
                .build();

        Hashtag articleHashtag3 = Hashtag.builder()
                .hashtag1("SPRING")
                .hashtag2("JPA")
                .hashtag3("movie")
                .build();

        Hashtag articleHashtag4 = Hashtag.builder()
                .hashtag1("STOCK")
                .hashtag2("READ")
                .hashtag3("movie")
                .build();

        Article article1 = Article.builder()
                .url("https://testArticle1.com")
                .titleOg("https://testTitleOg1.com")
                .imgOg("https://testImgOg1.com")
                .contentOg("https://testContentOg1.com")
                .review("testReview1")
                .reviewHide(false)
                .readCount(0)
                .hashtag(articleHashtag1)
                .articleFolder(articleFolders.get(0))
                .member(testMember1)
                .build();

        articleHashtag1.setArticle(article1);

        Article article2 = Article.builder()
                .url("https://testArticle2.com")
                .titleOg("https://testTitleOg2.com")
                .imgOg("https://testImgOg2.com")
                .contentOg("https://testContentOg2.com")
                .review("testReview2")
                .reviewHide(false)
                .readCount(1)
                .hashtag(articleHashtag2)
                .articleFolder(articleFolders.get(0))
                .member(testMember1)
                .build();

        articleHashtag2.setArticle(article2);

        Article article3 = Article.builder()
                .url("https://testArticle3.com")
                .titleOg("https://testTitleOg3.com")
                .imgOg("https://testImgOg3.com")
                .contentOg("https://testContentOg3.com")
                .review("testReview3")
                .reviewHide(false)
                .readCount(0)
                .hashtag(articleHashtag3)
                .articleFolder(articleFolders.get(1))
                .member(testMember1)
                .build();

        articleHashtag3.setArticle(article3);

        Article article4 = Article.builder()
                .url("https://testArticle4.com")
                .titleOg("https://testTitleOg4.com")
                .imgOg("https://testImgOg4.com")
                .contentOg("https://testContentOg4.com")
                .review("testReview4")
                .reviewHide(false)
                .readCount(1)
                .hashtag(articleHashtag4)
                .articleFolder(articleFolders.get(1))
                .member(testMember1)
                .build();

        articleHashtag4.setArticle(article4);

        articleRepository.save(article1);
        articleRepository.save(article2);
        articleRepository.save(article3);
        articleRepository.save(article4);

        em.flush();
        em.clear();

        System.out.println("==============================================================================================");
        List<ArticleFolder> findFolders = articleFolderRepository.findAll();
        List<Member> findMember = memberRepository.findAll();

    }
}


















