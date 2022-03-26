package com.sparta.backend.utils;

import com.sparta.backend.model.Article;
import com.sparta.backend.responseDto.ArticleFolderListResponseDto;
import com.sparta.backend.responseDto.RecommendedMemberResponseDto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class RandomGenerator {
    public List<Article> getRandomArticles(List<Article> articles, int cnt) {
        HashSet<Article> relatedArticles = new HashSet<>();
        Random random = new Random();
        int bound = articles.size();
        while (relatedArticles.size() < cnt) {
            int randomNum = random.nextInt(bound);
            relatedArticles.add(articles.get(randomNum));
        }
        return new ArrayList<>(relatedArticles);
    }
    
    // 메인페이지 유저 추천용
    public List<RecommendedMemberResponseDto> getRecommendedMembers(List<RecommendedMemberResponseDto> memberResponseDtos, int cnt) {
        HashSet<RecommendedMemberResponseDto> randomMembers = new HashSet<>();
        Random random = new Random();
        int bound = memberResponseDtos.size();
        while (randomMembers.size() < cnt) {
            int randomNum = random.nextInt(bound);
            randomMembers.add(memberResponseDtos.get(randomNum));
        }
        return new ArrayList<>(randomMembers);
    }

    // 메인페이지 아티클 폴더 추천용
    public List<ArticleFolderListResponseDto> getRecommendedAtricleFolders(List<ArticleFolderListResponseDto> articleFolderResponseDtos, int cnt) {
        HashSet<ArticleFolderListResponseDto> randomArticleFolders = new HashSet<>();
        Random random = new Random();
        int bound = articleFolderResponseDtos.size();
        while (randomArticleFolders.size() < cnt) {
            int randomNum = random.nextInt(bound);
            randomArticleFolders.add(articleFolderResponseDtos.get(randomNum));
        }
        return new ArrayList<>(randomArticleFolders);
    }
    
    // 메인페이지 랜덤 해시태그 생성용
    public enum RandomHashtag {

        커리어, 업무스킬, IT, 디자인, 마케팅, 투자,
        장소, 동기부여, 인간관계, 패션, 예술, 과학;

        public static RandomHashtag getRandomHashtag() {
            Random random = new Random();
            return values()[random.nextInt((values().length))];
        }
    }
}
