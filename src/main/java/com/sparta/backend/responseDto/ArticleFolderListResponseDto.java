package com.sparta.backend.responseDto;

import com.sparta.backend.model.Article;
import com.sparta.backend.model.ArticleFolder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleFolderListResponseDto {

    private long folderId;
    private String folderName;
    private int likeCount;
    private boolean isHide;
    private boolean isdDeleteable;
    private int completeRate;
    private String hashTag1;
    private String hashTag2;
    private String hashTag3;
    List<ArticleListDto> articleListDtoList = new ArrayList<>();

    private ArticleFolderListResponseDto(ArticleFolder articleFolder) {
        this.folderId = articleFolder.getId();
        this.folderName = articleFolder.getArticleFolderName();
        this.likeCount = articleFolder.getLikeCount();
        this.isHide = articleFolder.isFolderHide();
        this.isdDeleteable = articleFolder.isDeleteable();
    }

    private ArticleFolderListResponseDto(ArticleFolder articleFolder, List<ArticleListDto> articleListDtoList, DecideFolderInfo decideFolderInfo) {
        this.folderId = articleFolder.getId();
        this.folderName = articleFolder.getArticleFolderName();
        this.likeCount = articleFolder.getLikeCount();
        this.isHide = articleFolder.isFolderHide();
        this.isdDeleteable = articleFolder.isDeleteable();
        this.completeRate = decideFolderInfo.getCompleteRate();
        this.hashTag1 = decideFolderInfo.getHashTag1();
        this.hashTag2 = decideFolderInfo.getHashTag2();
        this.hashTag3 = decideFolderInfo.getHashTag3();
        this.articleListDtoList.addAll(articleListDtoList);
    }

    // 폴더에 아티클이 없을때
    public static ArticleFolderListResponseDto of(ArticleFolder articleFolder) {
        return new ArticleFolderListResponseDto(articleFolder);
    }

    // 폴더에 아티클이 있을때
    public static ArticleFolderListResponseDto of(ArticleFolder articleFolder, List<Article> articles) {
        return new ArticleFolderListResponseDto(articleFolder, ArticleListDto.of(articles), DecideFolderInfo.of(articles));
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    private static class ArticleListDto {
        private String title;
        private String content;

        private ArticleListDto(String title, String content) {
            this.title = title;
            this.content = content;
        }

        public static List<ArticleListDto> of(List<Article> articles) {
            return articles.stream()
                    .map(article -> new ArticleListDto(
                            article.getTitleOg(),
                            article.getContentOg())
                    )
                    .collect(Collectors.toList());
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    private static class DecideFolderInfo {
        private int completeRate;
        private String hashTag1;
        private String hashTag2;
        private String hashTag3;

        private DecideFolderInfo(int completeRate, String hashTag1, String hashTag2, String hashTag3) {
            this.completeRate = completeRate;
            this.hashTag1 = hashTag1;
            this.hashTag2 = hashTag2;
            this.hashTag3 = hashTag3;
        }

        public static DecideFolderInfo of(List<Article> articles) {
            int completeRate;
            String hashTag1;
            String hashTag2 = null;
            String hashTag3 = null;

            // 폴더 안 아티클 hastag1 리스트
            List<String> articleHasTag1List = articles
                    .stream()
                    .map(article -> article.getHashtag().getHashtag1())
                    .collect(Collectors.toList());

            // hastag1 정렬
            Map<String, Integer> map = new HashMap<>();

            for (String articleHasTag1 : articleHasTag1List) {
                if (map.containsKey(articleHasTag1)) {
                    int cnt = map.get(articleHasTag1);
                    cnt++;
                    map.put(articleHasTag1, cnt);
                } else {
                    map.put(articleHasTag1, 1);
                }
            }

            final List<String> sortedHasTag = map.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

            if (sortedHasTag.size() == 1) {
                hashTag1 = sortedHasTag.get(0);
            } else if (sortedHasTag.size() == 2) {
                hashTag1 = sortedHasTag.get(0);
                hashTag2 = sortedHasTag.get(1);
            } else {
                hashTag1 = sortedHasTag.get(0);
                hashTag2 = sortedHasTag.get(1);
                hashTag3 = sortedHasTag.get(2);
            }

            // 완독률
            int articlesSize = articles.size();

            List<Article> alreadyReadArticle = articles.stream().filter(article -> article.getReadCount() > 0)
                    .collect(Collectors.toList());

            int alreadyReadArticleSize = alreadyReadArticle.size();

            completeRate = alreadyReadArticleSize / articlesSize;

            return new DecideFolderInfo(completeRate, hashTag1, hashTag2, hashTag3);
        }
    }

}
