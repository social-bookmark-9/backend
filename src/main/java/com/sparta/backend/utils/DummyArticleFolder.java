package com.sparta.backend.utils;

import com.sparta.backend.exception.InvalidValueException;
import com.sparta.backend.model.Article;
import com.sparta.backend.model.ArticleFolder;
import com.sparta.backend.model.Hashtag;
import com.sparta.backend.model.Member;
import com.sparta.backend.repository.ArticleFolderRepository;
import com.sparta.backend.repository.ArticleRepository;
import com.sparta.backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Transactional
public class DummyArticleFolder {

    private final MemberRepository memberRepository;
    private final ArticleFolderRepository articleFolderRepository;
    private final ArticleRepository articleRepository;

    // 소요시간 10초
    @PostMapping("/dummy/articlefolders/10")
    public String createDummyArticles1000(@AuthenticationPrincipal Member member) {

        List<String> hashtags = new ArrayList<>();
        hashtags.add("커리어"); hashtags.add("업무스킬"); hashtags.add("IT"); hashtags.add("디자인"); hashtags.add("마케팅");hashtags.add("투자");
        hashtags.add("장소"); hashtags.add("동기부여"); hashtags.add("인간관계"); hashtags.add("패션");hashtags.add("예술"); hashtags.add("과학");

        Member currentMember = memberRepository.findById(member.getId()).orElseThrow(
                () -> new InvalidValueException("사용자가 존재하지 않습니다."));

        for(int i = 0; i < 10; i++) {
            ArticleFolder articleFolder = ArticleFolder.builder()
                    .articleFolderName("더미 아티클 폴더 " + i)
                    .deleteable(true)
                    .folderHide(false)
                    .likeCount((int) (Math.random() * 10))
                    .member(currentMember)
                    .build();

            articleFolderRepository.save(articleFolder);

            for(int j = 0; j < 100; j++) {
                Hashtag hashtag = Hashtag.builder()
                        .hashtag1(hashtags.get((int) (Math.random() * 11)))
                        .hashtag2(hashtags.get((int) (Math.random() * 11)))
                        .hashtag3(hashtags.get((int) (Math.random() * 11)))
                        .build();

                Article article = Article.builder()
                        .url(RandomString.make(10))
                        .titleOg(RandomString.make(10))
                        .imgOg(RandomString.make(10))
                        .contentOg(RandomString.make(10))
                        .reviewHide(false)
                        .readCount(0)
                        .hashtag(hashtag)
                        .articleFolder(articleFolder)
                        .member(currentMember)
                        .build();

                article.setArticleFolder(articleFolder);
                article.setHashtag(hashtag);
                hashtag.setArticle(article);
                articleRepository.save(article);
            }
        }
        return "더미 아티클폴더 생성 완료!";
    }

    // 소요시간 1분 40초
    @PostMapping("/dummy/articlefolders/100")
    public String createDummyArticles10000(@AuthenticationPrincipal Member member) {

        List<String> hashtags = new ArrayList<>();
        hashtags.add("커리어"); hashtags.add("업무스킬"); hashtags.add("IT"); hashtags.add("디자인"); hashtags.add("마케팅");hashtags.add("투자");
        hashtags.add("장소"); hashtags.add("동기부여"); hashtags.add("인간관계"); hashtags.add("패션");hashtags.add("예술"); hashtags.add("과학");

        Member currentMember = memberRepository.findById(member.getId()).orElseThrow(
                () -> new InvalidValueException("사용자가 존재하지 않습니다."));

        for(int i = 0; i < 100; i++) {
            ArticleFolder articleFolder = ArticleFolder.builder()
                    .articleFolderName("더미 아티클 폴더 " + i)
                    .deleteable(true)
                    .folderHide(false)
                    .likeCount((int) (Math.random() * 10))
                    .member(currentMember)
                    .build();

            articleFolderRepository.save(articleFolder);

            for(int j = 0; j < 100; j++) {
                Hashtag hashtag = Hashtag.builder()
                        .hashtag1(hashtags.get((int) (Math.random() * 11)))
                        .hashtag2(hashtags.get((int) (Math.random() * 11)))
                        .hashtag3(hashtags.get((int) (Math.random() * 11)))
                        .build();

                Article article = Article.builder()
                        .url(RandomString.make(10))
                        .titleOg(RandomString.make(10))
                        .imgOg(RandomString.make(10))
                        .contentOg(RandomString.make(10))
                        .reviewHide(false)
                        .readCount(0)
                        .hashtag(hashtag)
                        .articleFolder(articleFolder)
                        .member(currentMember)
                        .build();

                article.setArticleFolder(articleFolder);
                article.setHashtag(hashtag);
                hashtag.setArticle(article);
                articleRepository.save(article);
            }
        }
        return "더미 아티클폴더 생성 완료!";
    }

    // 소요시간 16분 
    @PostMapping("/dummy/articlefolders/1000")
    public String createDummyArticles100000(@AuthenticationPrincipal Member member) {

        List<String> hashtags = new ArrayList<>();
        hashtags.add("커리어"); hashtags.add("업무스킬"); hashtags.add("IT"); hashtags.add("디자인"); hashtags.add("마케팅");hashtags.add("투자");
        hashtags.add("장소"); hashtags.add("동기부여"); hashtags.add("인간관계"); hashtags.add("패션");hashtags.add("예술"); hashtags.add("과학");

        Member currentMember = memberRepository.findById(member.getId()).orElseThrow(
                () -> new InvalidValueException("사용자가 존재하지 않습니다."));

        for(int i = 0; i < 1000; i++) {
            ArticleFolder articleFolder = ArticleFolder.builder()
                    .articleFolderName("더미 아티클 폴더 " + i)
                    .deleteable(true)
                    .folderHide(false)
                    .likeCount((int) (Math.random() * 10))
                    .member(currentMember)
                    .build();

            articleFolderRepository.save(articleFolder);

            for(int j = 0; j < 100; j++) {
                Hashtag hashtag = Hashtag.builder()
                        .hashtag1(hashtags.get((int) (Math.random() * 11)))
                        .hashtag2(hashtags.get((int) (Math.random() * 11)))
                        .hashtag3(hashtags.get((int) (Math.random() * 11)))
                        .build();

                Article article = Article.builder()
                        .url(RandomString.make(10))
                        .titleOg(RandomString.make(10))
                        .imgOg(RandomString.make(10))
                        .contentOg(RandomString.make(10))
                        .reviewHide(false)
                        .readCount(0)
                        .hashtag(hashtag)
                        .articleFolder(articleFolder)
                        .member(currentMember)
                        .build();

                article.setArticleFolder(articleFolder);
                article.setHashtag(hashtag);
                hashtag.setArticle(article);
                articleRepository.save(article);
            }
        }
        return "더미 아티클폴더 생성 완료!";
    }
    
    // 소요시간 ??? 2시간 이상 예상
    @PostMapping("/dummy/articlefolders/10000")
    public String createDummyArticles1000000(@AuthenticationPrincipal Member member) {

        List<String> hashtags = new ArrayList<>();
        hashtags.add("커리어"); hashtags.add("업무스킬"); hashtags.add("IT"); hashtags.add("디자인"); hashtags.add("마케팅");hashtags.add("투자");
        hashtags.add("장소"); hashtags.add("동기부여"); hashtags.add("인간관계"); hashtags.add("패션");hashtags.add("예술"); hashtags.add("과학");

        Member currentMember = memberRepository.findById(member.getId()).orElseThrow(
                () -> new InvalidValueException("사용자가 존재하지 않습니다."));

        for(int i = 0; i < 10000; i++) {
            ArticleFolder articleFolder = ArticleFolder.builder()
                    .articleFolderName("더미 아티클 폴더 " + i)
                    .deleteable(true)
                    .folderHide(false)
                    .likeCount((int) (Math.random() * 10))
                    .member(currentMember)
                    .build();

            articleFolderRepository.save(articleFolder);

            for(int j = 0; j < 100; j++) {
                Hashtag hashtag = Hashtag.builder()
                        .hashtag1(hashtags.get((int) (Math.random() * 11)))
                        .hashtag2(hashtags.get((int) (Math.random() * 11)))
                        .hashtag3(hashtags.get((int) (Math.random() * 11)))
                        .build();

                Article article = Article.builder()
                        .url(RandomString.make(10))
                        .titleOg(RandomString.make(10))
                        .imgOg(RandomString.make(10))
                        .contentOg(RandomString.make(10))
                        .reviewHide(false)
                        .readCount(0)
                        .hashtag(hashtag)
                        .articleFolder(articleFolder)
                        .member(currentMember)
                        .build();

                article.setArticleFolder(articleFolder);
                article.setHashtag(hashtag);
                hashtag.setArticle(article);
                articleRepository.save(article);
            }
        }
        return "더미 아티클폴더 생성 완료!";
    }
}
