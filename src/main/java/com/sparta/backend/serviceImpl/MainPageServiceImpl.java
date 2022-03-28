package com.sparta.backend.serviceImpl;

import com.sparta.backend.model.Article;
import com.sparta.backend.model.ArticleFolder;
import com.sparta.backend.model.Member;
import com.sparta.backend.repository.ArticleFolderRepository;
import com.sparta.backend.repository.ArticleRepository;
import com.sparta.backend.repository.MemberRepository;
import com.sparta.backend.responseDto.ArticleFolderListResponseDto;
import com.sparta.backend.responseDto.ArticleRandomResponseDto;
import com.sparta.backend.responseDto.RecommendedMemberResponseDto;
import com.sparta.backend.service.MainPageService;
import com.sparta.backend.utils.RandomGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class MainPageServiceImpl implements MainPageService {

    // 메인페이지 유저 추천
    RandomGenerator randomGenerator = new RandomGenerator();

    private final ArticleRepository articleRepository;
    private final ArticleFolderRepository articleFolderRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<RecommendedMemberResponseDto> getRecommendedMembers(Member getMember, String randomHashtag) {

        // 유저
        List<RecommendedMemberResponseDto> memberList;
        // 비로그인 일 경우
        if(getMember == null) {
            // 랜덤으로 해쉬태그중 하나로 검색하기
            List<Member> members = memberRepository.findMembersByHashtag_Hashtag1(randomHashtag);
            // 멤버를 아티클 폴더의 좋아요 수의 총합으로 내림차순 나열하기
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
            // 좋아요 순 상위 50개중 9개 랜덤으로 가져오기
            if (recommendedMembers.size() >= 51) {
                recommendedMembers = recommendedMembers.subList(0, 49);
            }
            if(recommendedMembers.size() > 8) {
                memberList = randomGenerator.getRecommendedMembers(recommendedMembers, 9);
            } else {
                memberList = recommendedMembers;
            }

            return memberList;
        }
        // 로그인일 경우
        else {
            // 로그인한 유저의 해쉬태그로 유저 검색
            String recommendedHashtag = getMember.getHashtag().getHashtag1();
            List<Member> members = memberRepository.findMembersByHashtag_Hashtag1(recommendedHashtag);
            // 멤버를 아티클 폴더의 좋아요 수의 총합으로 내림차순 나열하기
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
            // 좋아요 순 상위 50개중 9개 랜덤으로 가져오기
            if (recommendedMembers.size() >= 51) {
                recommendedMembers = recommendedMembers.subList(0, 49);
            }
            // 랜덤으로 가져오기
            if(recommendedMembers.size() > 8) {
                memberList = randomGenerator.getRecommendedMembers(recommendedMembers, 9);
            } else {
                memberList = recommendedMembers;
            }

            return memberList;
        }
    }

    // 메인페이지 아티클 추천
    @Override
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

    // 메인페이지 아티클 폴더 추천
    @Override
    public List<ArticleFolderListResponseDto> getRecommendedArticleFolders(Member getMember) {

        List<ArticleFolderListResponseDto> articleFolderList = new ArrayList<>();
        List<ArticleFolderListResponseDto> recommendedArticleFolderList = new ArrayList<>();

        // 비로그인 일 경우
        if(getMember == null) {
            List<ArticleFolder> articleFolders = articleFolderRepository.findTop50ByOrderByLikeCountDesc();

            for (ArticleFolder articleFolder : articleFolders) {
                ArticleFolderListResponseDto articleFolderListResponseDto;
                if (articleFolder.getArticles().isEmpty()) {
                    articleFolderListResponseDto = ArticleFolderListResponseDto.of(articleFolder);
                } else {
                    articleFolderListResponseDto = ArticleFolderListResponseDto.of(articleFolder, articleFolder.getArticles());
                }
                articleFolderList.add(articleFolderListResponseDto);
            }
            if(articleFolderList.size() > 8) {
                articleFolderList = randomGenerator.getRecommendedAtricleFolders(articleFolderList, 9);
            }
        }
        // 로그인일 경우
        else {
            String recommendHashtag = getMember.getHashtag().getHashtag1();

            // 전체 아티클 가져오기
            List<ArticleFolder> articleFolders = articleFolderRepository.findArticleFoldersByFolderHide(false);
            for (ArticleFolder articleFolder : articleFolders) {
                ArticleFolderListResponseDto articleFolderListResponseDto;
                if (articleFolder.getArticles().isEmpty()) {
                    articleFolderListResponseDto = ArticleFolderListResponseDto.of(articleFolder);
                } else {
                    articleFolderListResponseDto = ArticleFolderListResponseDto.of(articleFolder, articleFolder.getArticles());
                }
                recommendedArticleFolderList.add(articleFolderListResponseDto);
            }

            // 해쉬태그 같은것 분류하기
            for(ArticleFolderListResponseDto articleFolderListResponseDto : recommendedArticleFolderList) {
                if (Objects.equals(articleFolderListResponseDto.getHashTag1(), recommendHashtag)) {
                    articleFolderList.add(articleFolderListResponseDto);
                }
            }

            // 좋아요 높은 순으로 정렬 및 50개 추출
            articleFolderList.sort((o1, o2) -> Integer.compare(o2.getLikeCount(), o1.getLikeCount()));
            if (articleFolderList.size() >= 51) {
                articleFolderList = articleFolderList.subList(0, 49);
            }
            // 랜덤으로 9개 가져오기
            if(articleFolderList.size() > 8) {
                articleFolderList = randomGenerator.getRecommendedAtricleFolders(articleFolderList, 9);
            }
        }
        return articleFolderList;
    }

}
