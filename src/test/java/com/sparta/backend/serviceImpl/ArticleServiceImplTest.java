//package com.sparta.backend.serviceImpl;
//
//import com.sparta.backend.model.Article;
//import com.sparta.backend.model.ArticleFolder;
//import com.sparta.backend.model.Hashtag;
//import com.sparta.backend.model.Member;
//import com.sparta.backend.repository.ArticleFolderRepository;
//import com.sparta.backend.repository.ArticleRepository;
//import com.sparta.backend.repository.HashtagRepository;
//import com.sparta.backend.repository.MemberRepository;
//import com.sparta.backend.requestDto.ArticleCreateRequestDto;
//import com.sparta.backend.requestDto.ArticleReviewRequestDto;
//import com.sparta.backend.requestDto.ArticleUpdateRequestDto;
//import com.sparta.backend.responseDto.ArticleResponseDto;
//import com.sparta.backend.responseDto.ArticleReviewResponseDto;
//import com.sparta.backend.service.ArticleService;
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Collections;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//@SpringBootTest
//@Transactional
//@Rollback(value = false)
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//class ArticleServiceImplTest {
//
//    @Autowired
//    public MemberRepository memberRepository;
//    @Autowired
//    public HashtagRepository hashtagRepository;
//    @Autowired
//    public ArticleRepository articleRepository;
//    @Autowired
//    public ArticleFolderRepository articleFolderRepository;
//    @Autowired
//    public ArticleService articleService;
//
//    // given (멤버)
//    private String memberName;
//    private String email;
//    private String password;
//    private long expiredDate;
//
//    // given (해시태그)
//    private String hashtag1;
//
//    // given (폴더1)
//    private String articleFolderName1;
//    private boolean deleteable;
//
//    // given (폴더2)
//    private String articleFolderName2;
//    private boolean deleteable1;
//
//    // given (아티클1)
//    private String url1;
//    private String titleOg1;
//    private String imgOg1;
//    private String contentOg1;
//
//    // given (아티클2)
//    private String url2;
//    private String titleOg2;
//    private String imgOg2;
//    private String contentOg2;
//
//    @BeforeEach
//    public void setup() {
//
//        // given (멤버)
//        memberName = "현우";
//        password = "1234";
//        expiredDate = 1L;
//        email = "test1@test1.com";
//
//        // given (해시태그)
//        hashtag1 = "IT/개발";
//
//        // given (폴더1)
//        articleFolderName1 = "기본 컬렉션";
//        deleteable = false;
//
//        // given (폴더2)
//        articleFolderName2 = "이직에 필요한 아티클들 모음";
//        deleteable1 = true;
//
//        // given (아티클1)
//        url1 = "www.naver.com";
//        titleOg1 = "네이버";
//        imgOg1 = "www.naver.com";
//        contentOg1 = "네이버 좋아요";
//
//        // given (아티클2)
//        url2 = "www.google.com";
//        titleOg2 = "구글";
//        imgOg2 = "www.google.com";
//        contentOg2 = "구글 좋아요";
//    }
//
//    @Test
//    @Order(1)
//    @DisplayName("아티클 수정")
//    public void updateArticle() {
//
//        Hashtag hashtag = Hashtag.builder()
//                .hashtag1(hashtag1)
//                .build();
//
//        Member member = Member.builder()
//                .memberName(memberName)
//                .email(email)
//                .password(password)
//                .expiredDate(expiredDate)
//                .hashtag(hashtag)
//                .memberRoles(Collections.singletonList("ROLE_USER"))
//                .build();
//
//        // 폴더 1
//        ArticleFolder articleFolder1 = ArticleFolder.builder()
//                .articleFolderName(articleFolderName1)
//                .deleteable(deleteable)
//                .member(member)
//                .build();
//
//        // 폴더 2
//        ArticleFolder articleFolder2 = ArticleFolder.builder()
//                .articleFolderName(articleFolderName2)
//                .deleteable(deleteable1)
//                .member(member)
//                .build();
//
//        // 아티클1
//        ArticleCreateRequestDto articleCreateRequestDto1 = ArticleCreateRequestDto.builder()
//                .url(url1)
//                .titleOg(titleOg1)
//                .imgOg(imgOg1)
//                .contentOg(contentOg1)
//                .hashtag(hashtag)
//                .articleFolder(articleFolder1)
//                .build();
//
//        // 아티클2
//        ArticleCreateRequestDto articleCreateRequestDto2 = ArticleCreateRequestDto.builder()
//                .url(url2)
//                .titleOg(titleOg2)
//                .imgOg(imgOg2)
//                .contentOg(contentOg2)
//                .hashtag(hashtag)
//                .articleFolder(articleFolder1)
//                .build();
//
//        // 아티클 업데이트 Dto
//        ArticleUpdateRequestDto articleUpdateRequestDto = ArticleUpdateRequestDto.builder()
//                .articleFolder(articleFolder2)
//                .review("정말 재밌어용")
//                .reviewHide(false)
//                .hashtag(hashtag)
//                .build();
//
//        // when
//        // 한 멤버에 두 폴더를 생성 (기본 폴더, 커스텀 폴더)
//        memberRepository.save(member);
//        articleFolderRepository.save(articleFolder1);
//        articleService.createArticle(articleCreateRequestDto1, member);
//        long article2Id = articleService.createArticle(articleCreateRequestDto2, member);
//        articleFolderRepository.save(articleFolder2);
//
//        // 한 멤버의 기본 폴더에서 커스텀 폴더로 이동(수정)하는 작업
//        articleService.updateArticle(articleUpdateRequestDto, article2Id, member);
//
//        // 아티클 조회
//        ArticleResponseDto responseDto = articleService.getArticle(article2Id, member);
//
//        // then
//        assertEquals(articleFolder2.getArticleFolderName(), responseDto.getArticleFolder().getArticleFolderName());
//    }
//
//    @Test
//    @Order(2)
//    @DisplayName("아티클 리뷰 수정")
//    public void updateArticleReview() {
//        // given
//        String review = "너무 유익한 아티클입니다.";
//
//        Hashtag hashtag = Hashtag.builder()
//                .hashtag1(hashtag1)
//                .build();
//
//        Member member = Member.builder()
//                .memberName("항해")
//                .email("hanghae@naver.com")
//                .password("123123")
//                .expiredDate(1L)
//                .hashtag(hashtag)
//                .memberRoles(Collections.singletonList("ROLE_USER"))
//                .build();
//
//        ArticleFolder articleFolder1 = ArticleFolder.builder()
//                .articleFolderName(articleFolderName1)
//                .deleteable(deleteable)
//                .member(member)
//                .build();
//
//        ArticleReviewRequestDto requestDto = ArticleReviewRequestDto.builder()
//                .review(review)
//                .build();
//
//        Article article = Article.builder()
//                .url(url1)
//                .titleOg(titleOg1)
//                .imgOg(imgOg1)
//                .contentOg(contentOg1)
//                .review("")
//                .reviewHide(false)
//                .hashtag(hashtag)
//                .articleFolder(articleFolder1)
//                .build();
//
//        memberRepository.save(member);
//        articleFolderRepository.save(articleFolder1);
//        articleRepository.save(article);
//
//        long articleId = article.getId();
//
//        // when (아티클 리뷰 수정하기)
//        ArticleReviewResponseDto responseDto = articleService.updateArticleReview(requestDto, articleId, member);
//
//        // then
//        assertEquals(review, responseDto.getReview());
//    }
//
//    @Test
//    @Order(3)
//    @DisplayName("아티클 리뷰 Hide 수정")
//    public void updateArticleReviewHide() {
//        // given
//        Hashtag hashtag = Hashtag.builder()
//                .hashtag1(hashtag1)
//                .build();
//
//        Member member = Member.builder()
//                .memberName("항해1")
//                .email("hanghae1@naver.com")
//                .password("123123")
//                .expiredDate(1L)
//                .hashtag(hashtag)
//                .memberRoles(Collections.singletonList("ROLE_USER"))
//                .build();
//
//        ArticleFolder articleFolder1 = ArticleFolder.builder()
//                .articleFolderName(articleFolderName1)
//                .deleteable(deleteable)
//                .member(member)
//                .build();
//
//        Article article = Article.builder()
//                .url(url1)
//                .titleOg(titleOg1)
//                .imgOg(imgOg1)
//                .contentOg(contentOg1)
//                .review("")
//                .reviewHide(false)
//                .hashtag(hashtag)
//                .articleFolder(articleFolder1)
//                .build();
//
//        memberRepository.save(member);
//        articleFolderRepository.save(articleFolder1);
//        articleRepository.save(article);
//
//        long articleId = article.getId();
//
//        // when (reviewHide = false -> true, true -> false)
//        boolean reviewHide = articleService.updateArticleReviewHide(articleId);
//
//        // then
//        assertTrue(reviewHide);
//    }
//}