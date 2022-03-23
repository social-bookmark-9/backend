package com.sparta.backend.service;

import com.sparta.backend.model.Article;
import com.sparta.backend.model.ArticleFolder;
import com.sparta.backend.model.Member;
import com.sparta.backend.repository.ArticleRepository;
import com.sparta.backend.responseDto.ArticleRandomResponseDto;
import com.sparta.backend.responseDto.RecommendedMemberResponseDto;
import com.sparta.backend.utils.RandomGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MainPageService {

    // 메인페이지 유저 추천
    RandomGenerator randomGenerator = new RandomGenerator();

    private final ArticleRepository articleRepository;

    public List<RecommendedMemberResponseDto> getRecommendedMembers(List<Member> members) {
        List<RecommendedMemberResponseDto> recommendedMembers = new ArrayList<>();
        for(Member member: members) {
            List<ArticleFolder> articleFolders = member.getArticleFolders();
            int likeCount = 0;
            for (ArticleFolder articleFolder:articleFolders) {
                likeCount += articleFolder.getLikeCount();
            }
            // 좋아요 수를 가진 responseDto 생성
            RecommendedMemberResponseDto recommendedMemberResponseDto = RecommendedMemberResponseDto.builder()
                    .memberId(member.getId())
                    .profileImage(member.getProfileImage())
                    .memberComment(member.getMemberComment())
                    .memberName(member.getMemberName())
                    .likeCount(likeCount)
                    .build();
            recommendedMembers.add(recommendedMemberResponseDto);
        }
        // 람다식으로 좋아요 순 내림차순 정렬.
        recommendedMembers.sort((o1, o2) -> Integer.compare(o2.getLikeCount(), o1.getLikeCount()));
        // 좋아요 순 상위 50개 가져오기.
        if (recommendedMembers.size() >= 51) {
            recommendedMembers = recommendedMembers.subList(0, 49);
        }

        // 랜덤으로 가져오기
        List<RecommendedMemberResponseDto> memberList;
        if(recommendedMembers.size() > 8) {
            memberList = randomGenerator.getRecommendedMembers(recommendedMembers, 9);
        } else {
            memberList = recommendedMembers;
        }

        return memberList;
    }

    // 메인페이지 아티클 추천
    public List<ArticleRandomResponseDto> getMonthArticles(String hashtag) {
        LocalDateTime startDatetime = LocalDateTime.of(LocalDate.now().minusDays(30), LocalTime.of(0,0,0));
        LocalDateTime endDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59));

        List<Article> articles = articleRepository.findArticlesByHashtag_Hashtag1AndArticleFolder_FolderHideAndCreatedAtBetween(hashtag, false, startDatetime, endDatetime);
        List<Article> randomArticles;
        if (articles.size() > 8) { randomArticles = randomGenerator.getRandomArticles(articles, 9); }
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
        return articleList;
    }

}
