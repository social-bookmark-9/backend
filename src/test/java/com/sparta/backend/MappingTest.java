package com.sparta.backend;

import com.sparta.backend.model.Article;
import com.sparta.backend.model.ArticleFolder;
import com.sparta.backend.model.Hashtag;
import com.sparta.backend.model.Member;
import com.sparta.backend.repository.ArticleFolderRepository;
import com.sparta.backend.repository.ArticleRepository;
import com.sparta.backend.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
//@Rollback(value = false)
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
    @DisplayName("유저 및 해쉬태그 테스트")
    public void createUser() {

        // given - when
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

        // then
        memberRepository.save(member);

        memberRepository.delete(member);
    }

    @Test
    @DisplayName("유저 및 아티클폴더 생성 및 삭제 테스트")
    public void createArticleFolder() {

        // given - when
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

        memberRepository.save(member);

        ArticleFolder articleFolder = ArticleFolder.builder()
                .articleFolderName(articleFolderName)
                .member(member)
                .build();

        articleFolderRepository.save(articleFolder);

        // then
        articleFolderRepository.delete(articleFolder);
        memberRepository.delete(member);
    }

    @Test
    @DisplayName("유저 및 아티클폴더 및 아티클 테스트")
    public void createArticle() {
        // given - when
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

        memberRepository.save(member);

        ArticleFolder articleFolder = ArticleFolder.builder()
                .articleFolderName(articleFolderName)
                .member(member)
                .build();

        articleFolderRepository.save(articleFolder);

        Article article = Article.builder()
                .url(url)
                .titleOg(titleOg)
                .imgOg(imgOg)
                .hashtag(hashtag)
                .articleFolder(articleFolder)
                .build();

        article.SetArticleFolder(articleFolder);

        articleRepository.save(article);
        System.out.println("아티클 삭제 쿼리");
//        articleRepository.delete(article);
        articleRepository.deleteAllInBatch();
//        articleFolder.getArticles().remove(article);
    }
}
