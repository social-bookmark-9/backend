package com.sparta.backend.controller;

import com.sparta.backend.message.RestResponseMessage;
import com.sparta.backend.model.Article;
import com.sparta.backend.model.ArticleFolder;
import com.sparta.backend.model.Member;
import com.sparta.backend.repository.ArticleFolderRepository;
import com.sparta.backend.repository.ArticleRepository;
import com.sparta.backend.repository.MemberRepository;
import com.sparta.backend.responseDto.ArticleFolderListResponseDto;
import com.sparta.backend.responseDto.ArticleRandomResponseDto;
import com.sparta.backend.responseDto.RecommendedMemberResponseDto;
import com.sparta.backend.utils.RandomGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MainPageController {

    private final MemberRepository memberRepository;
    private final ArticleFolderRepository articleFolderRepository;
    private final ArticleRepository articleRepository;

    @GetMapping("/api/mainpage")
    public ResponseEntity<RestResponseMessage> getMainPage() {

        // 유저
        List<Member> members = memberRepository.findAll();
        List<RecommendedMemberResponseDto> memberList = new ArrayList<>();
        for(Member member:members) {
            RecommendedMemberResponseDto recommendedMemberResponseDto = RecommendedMemberResponseDto.builder()
                    .memberId(member.getId())
                    .profileImage(member.getProfileImage())
                    .memberComment(member.getMemberComment())
                    .memberName(member.getMemberName())
                    .build();
            memberList.add(recommendedMemberResponseDto);
        }
        
        // 아티클 폴더
        List<ArticleFolder> articleFolders = articleFolderRepository.findAll();
        List<ArticleFolderListResponseDto> articleFolderList = new ArrayList<>();
        for (ArticleFolder articleFolder : articleFolders) {
            ArticleFolderListResponseDto articleFolderListResponseDto;
            if (articleFolder.getArticles().isEmpty()) {
                articleFolderListResponseDto = ArticleFolderListResponseDto.of(articleFolder);
            } else {
                articleFolderListResponseDto = ArticleFolderListResponseDto.of(articleFolder, articleFolder.getArticles());
            }
            articleFolderList.add(articleFolderListResponseDto);
        }

        // 아티클
        RandomGenerator randomGenerator = new RandomGenerator();
        List<Article> articles = articleRepository.findAll();
        List<Article> randomArticles;
        if (articles.size() > 5) { randomArticles = randomGenerator.getRandomArticles(articles, 6); }
        else { randomArticles = articles; }
        List<ArticleRandomResponseDto> articleList = new ArrayList<>();
        for (Article randomArticle : randomArticles) {
            ArticleRandomResponseDto responseDto = ArticleRandomResponseDto.builder()
                    .articleId(randomArticle.getId())
                    .titleOg(randomArticle.getTitleOg())
                    .imgOg(randomArticle.getImgOg())
                    .contentOg(randomArticle.getContentOg())
                    .hashtag1(randomArticle.getHashtag().getHashtag1())
                    .hashtag2(randomArticle.getHashtag().getHashtag2())
                    .hashtag3(randomArticle.getHashtag().getHashtag3())
                    .build();
            articleList.add(responseDto);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("memberList", memberList);
        map.put("articleFolderList", articleFolderList);
        map.put("articleList", articleList);
        
        return new ResponseEntity<>(new RestResponseMessage<>(true,"메인페이지 정보 불러오기", map), HttpStatus.OK);
    }
}
