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
public class ArticleFolderListDto {

    private long folderId;
    private String folderName;
    private int likeCount;
    private boolean isHide;
    private boolean isdDeleteable;
    private static int completeRate;
    private static String hashTag1;
    private static String hashTag2;
    private static String hashTag3;
    List<ArticleListDto> articleListDtoList = new ArrayList<>();

    private ArticleFolderListDto(ArticleFolder articleFolder) {
        this.folderId = articleFolder.getId();
        this.folderName = articleFolder.getArticleFolderName();
        this.likeCount = articleFolder.getLikeCount();
        this.isHide = articleFolder.isFolderHide();
        this.isdDeleteable = articleFolder.isDeleteable();
    }

    private ArticleFolderListDto(ArticleFolder articleFolder, List<ArticleListDto> articleListDtoList) {
        this.folderId = articleFolder.getId();
        this.folderName = articleFolder.getArticleFolderName();
        this.likeCount = articleFolder.getLikeCount();
        this.isHide = articleFolder.isFolderHide();
        this.isdDeleteable = articleFolder.isDeleteable();
//        this.articleListDtoList = articleListDtoList;
        this.articleListDtoList.addAll(articleListDtoList);
    }

    // 폴더에 아티클이 없을때
    public static ArticleFolderListDto of(ArticleFolder articleFolder) {
        return new ArticleFolderListDto(articleFolder);
    }

    public static ArticleFolderListDto of(ArticleFolder articleFolder, List<Article> articles) {
        return new ArticleFolderListDto(articleFolder, ArticleListDto.of(articles));
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
            // 해시테그
            List<String> articleHasTag1List = articles
                    .stream()
                    .map(article -> article.getHashtag().getHashtag1())
                    .collect(Collectors.toList());

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

//            Iterator<String> iterator = sortedHasTag.iterator();

            hashTag1 = sortedHasTag.get(0);
            if (sortedHasTag.get(1).isEmpty()) {
                hashTag2 = null;
                hashTag3 = null;
            } else {
                hashTag2 = sortedHasTag.get(1);
                if (sortedHasTag.get(2).isEmpty()) {
                    hashTag3 = null;
                } else {
                    hashTag3 = sortedHasTag.get(2);
                }
            }

            // 완독률
            int articlesSize = articles.size();

            List<Article> alreadyReadArticle = articles.stream().filter(article -> article.getReadCount() > 0)
                    .collect(Collectors.toList());

            int alreadyReadArticleSize = alreadyReadArticle.size();

            completeRate = alreadyReadArticleSize / articlesSize;

            return articles.stream()
                    .map(article -> new ArticleListDto(
                            article.getTitleOg(),
                            article.getContentOg())
                        )
                    .collect(Collectors.toList());
        }
    }

}
