package com.sparta.backend.utils;

import com.sparta.backend.model.Article;
import com.sparta.backend.model.ArticleFolder;
import com.sparta.backend.model.Hashtag;
import com.sparta.backend.model.Member;
import com.sparta.backend.repository.ArticleFolderRepository;
import com.sparta.backend.repository.ArticleRepository;
import com.sparta.backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Transactional
public class DummyData {

    private final MemberRepository memberRepository;
    private final ArticleFolderRepository articleFolderRepository;
    private final ArticleRepository articleRepository;
    private final PasswordEncoder passwordEncoder;

    // 영욱 로컬호스트 더미데이터 생성 테스트
    
    // 유저 1 아티클폴더 1 아티클 100 = 3초 ( 아티클 총 100개 )
    // 유저 1 아티클폴더 1 아티클 1,000 = 37초 ( 아티클 총 1,000개 )
    // 유저 1 아티클폴더 1 아티클 10,000 = 16분 ( 아티클 총 10,000개 )

    // 유저 1 아티클폴더 10 아티클 100 = 10초 ( 아티클 총 1,000개 )
    // 유저 1 아티클폴더 100 아티클 100 = 1분 40초 ( 아티클 총 10,000개 )
    // 유저 1 아티클폴더 1,000 아티클 100 = 16분 ( 아티클 총 100,000개 )

    // 유저 10 아티클폴더 10 아티클 10 = 12초 ( 아티클 총 1,000개 )
    // 유저 100 아티클폴더 100 아티클 10 = 2분 ( 아티클 총 10,000개 )

    @PostMapping("/dummy")
    public String createDummyArticles1000(@RequestBody DummyRequestDto dummyRequestDto) {

        for(int i = 0; i < dummyRequestDto.getUsers(); i++) {

            List<String> hashtags = new ArrayList<>();
            hashtags.add("커리어");
            hashtags.add("업무스킬");
            hashtags.add("IT");
            hashtags.add("디자인");
            hashtags.add("마케팅");
            hashtags.add("투자");
            hashtags.add("장소");
            hashtags.add("동기부여");
            hashtags.add("인간관계");
            hashtags.add("패션");
            hashtags.add("예술");
            hashtags.add("과학");

            String password = UUID.randomUUID().toString();
            String encodedPassword = passwordEncoder.encode(password);

            Hashtag hashtag = Hashtag.builder()
                    .hashtag1(hashtags.get((int) (Math.random() * 11)))
                    .hashtag2(hashtags.get((int) (Math.random() * 11)))
                    .hashtag3(hashtags.get((int) (Math.random() * 11)))
                    .build();

            Member kakaoMember = Member.builder()
                    .email(RandomString.make(10))
                    .password(encodedPassword)
                    .memberName(RandomString.make(10))
                    .kakaoId(RandomString.make(10))
                    .memberRoles(Collections.singletonList("ROLE_USER")) // 최초 가입시 USER 로 설정
                    .hashtag(hashtag)
                    .profileImage(RandomString.make(10))
                    .build();

            hashtag.setMember(kakaoMember);

            memberRepository.save(kakaoMember);

            ArticleFolder memberArticleFolder = ArticleFolder.builder()
                    .articleFolderName("미분류 컬렉션")
                    .deleteable(false)
                    .folderHide(true)
                    .member(kakaoMember)
                    .build();

            articleFolderRepository.save(memberArticleFolder);

            for (int k = 0; k < dummyRequestDto.getArticleFolders(); k++) {
                ArticleFolder articleFolder = ArticleFolder.builder()
                        .articleFolderName("더미 아티클 폴더 " + k)
                        .deleteable(true)
                        .folderHide(false)
                        .likeCount((int) (Math.random() * 10))
                        .member(kakaoMember)
                        .build();

                articleFolderRepository.save(articleFolder);

                for (int j = 0; j < dummyRequestDto.getArticles(); j++) {
                    Hashtag articleHashtag = Hashtag.builder()
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
                            .hashtag(articleHashtag)
                            .articleFolder(articleFolder)
                            .member(kakaoMember)
                            .build();

                    article.setArticleFolder(articleFolder);
                    article.setHashtag(articleHashtag);
                    articleHashtag.setArticle(article);
                    articleRepository.save(article);
                }
            }
        }
        return "더미 데이터 생성 완료!";
    }
}
