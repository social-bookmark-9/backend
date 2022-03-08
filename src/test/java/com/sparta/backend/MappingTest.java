package com.sparta.backend;

import com.sparta.backend.model.*;
import com.sparta.backend.repository.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Rollback(value = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MappingTest {

    private String memberName;
    private String email;
    private String password;
    private Long expiredDate;

    private String firstHashtag;

    private String articleFolderName;

    private String url;
    private String titleOg;
    private String imgOg;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ArticleFolderRepository articleFolderRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private FavoriteRepository favoriteRepository;
    @Autowired
    private AlarmRepository alarmRepository;

    @BeforeEach
    public void setup() {
        memberName = "철수";
        email = "abc@abc.com";
        password = "1234";
        expiredDate = 1L;

        firstHashtag = "역사";

        articleFolderName = "테스트 아티클폴더";

        url = "naver.com";
        titleOg = "naver";
        imgOg = "naver.jpg";
    }

    @Test
    @Order(1)
    @DisplayName("유저 생성 테스트")
    public void createMember() {

        // given
        Hashtag hashtag = Hashtag.builder()
                .firstHashtag(firstHashtag)
                .build();

        Member member = Member.builder()
                .memberName(memberName)
                .email(email)
                .password(password)
                .expiredDate(expiredDate)
                .hashtag(hashtag)
                .build();

        // when
        memberRepository.save(member);

        // then
        Assertions.assertEquals(member, memberRepository.findById(member.getId()).orElseThrow(() -> new IllegalArgumentException("해당 멤버는 존재하지 않습니다")));
    }

    @Test
    @Order(2)
    @DisplayName("아티클폴더 생성 테스트")
    public void createArticleFolder() {

        // given
        Member member = memberRepository.findById(1L).orElseThrow(() -> new IllegalArgumentException("해당 멤버는 존재하지 않습니다"));

        ArticleFolder articleFolder = ArticleFolder.builder()
                .articleFolderName(articleFolderName)
                .member(member)
                .build();
        // when
        articleFolderRepository.save(articleFolder);

        // then
        Assertions.assertEquals(articleFolder, articleFolderRepository.findById(articleFolder.getId()).orElseThrow(() -> new IllegalArgumentException("해당 아티클 폴더는 존재하지 않습니다")));
    }

    @Test
    @Order(3)
    @DisplayName("아티클 폴더에 아티클 추가하기")
    public void createArticle() {

        // given
        ArticleFolder articleFolder = articleFolderRepository.findById(1L).orElseThrow(() -> new IllegalArgumentException("해당 아티클 폴더는 존재하지 않습니다"));

        Hashtag hashtag = Hashtag.builder()
                .firstHashtag(firstHashtag)
                .build();

        Article article = Article.builder()
                .url(url)
                .titleOg(titleOg)
                .imgOg(imgOg)
                .hashtag(hashtag)
                .build();

        article.setArticleFolder(articleFolder);

        // when
        articleRepository.save(article);

        // then
        Assertions.assertEquals(article, articleRepository.findById(article.getId()).orElseThrow(() -> new IllegalArgumentException("해당 아티클이 존재하지 않습니다.")));
    }

    @Test
    @Order(4)
    @DisplayName("유저가 아티클폴더에 좋아요 누르기")
    public void createFavorite() {

        // given
        Member member = memberRepository.findById(1L).orElseThrow(() -> new IllegalArgumentException("해당 멤버는 존재하지 않습니다"));
        ArticleFolder articleFolder = articleFolderRepository.findById(1L).orElseThrow(() -> new IllegalArgumentException("해당 아티클 폴더는 존재하지 않습니다"));
        Favorite favorite = new Favorite(member, articleFolder);

        // when
        favoriteRepository.save(favorite);

        // then
        Assertions.assertEquals(favorite, favoriteRepository.findById(1L).orElseThrow(() -> new IllegalArgumentException("해당 좋아요가 존재하지 않습니다.")));
    }

    @Test
    @Order(5)
    @DisplayName("알람 보내기")
    public void createAlarm() {

        // given
        ArticleFolder articleFolder = articleFolderRepository.findById(1L).orElseThrow(() -> new IllegalArgumentException("해당 아티클 폴더는 존재하지 않습니다"));
        Member member1 = articleFolder.getMember();

        Hashtag hashtag = Hashtag.builder()
                .firstHashtag(firstHashtag)
                .build();
        Member member2 = Member.builder()
                .memberName("영희")
                .email("123@123.com")
                .hashtag(hashtag)
                .expiredDate(expiredDate)
                .password("1234")
                .build();

        memberRepository.save(member2);

        Alarm alarm = Alarm.builder()
                .articleFolderId(articleFolder.getId())
                .fromMemberId(member2.getId())
                .member(member1)
                .build();

        alarmRepository.save(alarm);
        
        Assertions.assertEquals(alarm, alarmRepository.findById(alarm.getId())
                .orElseThrow(() -> new IllegalArgumentException("알람없음")));
    }
}
