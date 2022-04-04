package com.sparta.backend.responseDto;

import com.sparta.backend.model.Article;
import com.sparta.backend.model.ArticleFolder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;


@Getter
@NoArgsConstructor
public class ArticleFolderListResponseDto {

    private Long folderId;
    private String folderName;
    private Integer likeCount;
    private Boolean isHide;
    private Boolean isdDeleteable;
    private Boolean likeStatus;
    private Integer completeRate;
    private String hashTag1;
    private String hashTag2;
    private String hashTag3;
    private List<ArticleListDto> articleListDtoList = new ArrayList<>();

    public ArticleFolderListResponseDto(ArticleFolder articleFolder) {
        this.folderId = articleFolder.getId();
        this.folderName = articleFolder.getArticleFolderName();
        this.likeCount = articleFolder.getLikeCount();
        this.isHide = articleFolder.getFolderHide();
        this.isdDeleteable = articleFolder.getDeleteable();
        this.likeStatus = null;
        this.completeRate = 0;
        this.hashTag1 = null;
        this.hashTag2 = null;
        this.hashTag3 = null;
        this.articleListDtoList = new ArrayList<>();
    }

    public ArticleFolderListResponseDto(ArticleFolder articleFolder, List<ArticleListDto> articleListDtoList, DecideFolderInfo decideFolderInfo) {
        this.folderId = articleFolder.getId();
        this.folderName = articleFolder.getArticleFolderName();
        this.likeCount = articleFolder.getLikeCount();
        this.isHide = articleFolder.getFolderHide();
        this.isdDeleteable = articleFolder.getDeleteable();
        this.likeStatus = null;
        this.completeRate = decideFolderInfo.getCompleteRate();
        this.hashTag1 = articleFolder.getFolderHashtag1();
        this.hashTag2 = articleFolder.getFolderHashtag2();
        this.hashTag3 = articleFolder.getFolderHashtag3();
        this.articleListDtoList.addAll(articleListDtoList);
    }

    public ArticleFolderListResponseDto(ArticleFolder articleFolder, Boolean likeStatus) {
        this.folderId = articleFolder.getId();
        this.folderName = articleFolder.getArticleFolderName();
        this.likeCount = articleFolder.getLikeCount();
        this.isHide = articleFolder.getFolderHide();
        this.isdDeleteable = articleFolder.getDeleteable();
        this.likeStatus = likeStatus;
        this.completeRate = 0;
        this.hashTag1 = null;
        this.hashTag2 = null;
        this.hashTag3 = null;
        this.articleListDtoList = new ArrayList<>();
    }

    public ArticleFolderListResponseDto(ArticleFolder articleFolder, List<ArticleListDto> articleListDtoList, DecideFolderInfo decideFolderInfo, Boolean likeStatus) {
        this.folderId = articleFolder.getId();
        this.folderName = articleFolder.getArticleFolderName();
        this.likeCount = articleFolder.getLikeCount();
        this.isHide = articleFolder.getFolderHide();
        this.isdDeleteable = articleFolder.getDeleteable();
        this.likeStatus = likeStatus;
        this.completeRate = decideFolderInfo.getCompleteRate();
        this.hashTag1 = articleFolder.getFolderHashtag1();
        this.hashTag2 = articleFolder.getFolderHashtag2();
        this.hashTag3 = articleFolder.getFolderHashtag3();
        this.articleListDtoList.addAll(articleListDtoList);
    }

    // 폴더에 아티클이 없을때(내꺼)
    public static ArticleFolderListResponseDto of(ArticleFolder articleFolder) {
        return new ArticleFolderListResponseDto(articleFolder);
    }

    // 폴더에 아티클이 있을때(내꺼)
    public static ArticleFolderListResponseDto of(ArticleFolder articleFolder, List<Article> articles) {
        return new ArticleFolderListResponseDto(articleFolder, ArticleListDto.of(articles), DecideFolderInfo.of(articles));
    }

    // 폴더에 아티클이 없을때(남의것)
    public static ArticleFolderListResponseDto of(ArticleFolder articleFolder, Boolean likeStatus) {
        return new ArticleFolderListResponseDto(articleFolder, likeStatus);
    }

    // 폴더에 아티클이 있을때(남의것)
    public static ArticleFolderListResponseDto of(ArticleFolder articleFolder, List<Article> articles, Boolean likeStatus) {
        return new ArticleFolderListResponseDto(articleFolder, ArticleListDto.of(articles), DecideFolderInfo.of(articles), likeStatus);
    }

    @Getter
    @NoArgsConstructor
    private static class ArticleListDto {
        private String title;
        private String content;

        public ArticleListDto(String title, String content) {
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
    @NoArgsConstructor
    private static class DecideFolderInfo {
        private int completeRate;

        public DecideFolderInfo(int completeRate) {
            this.completeRate = completeRate;
        }

        public static DecideFolderInfo of(List<Article> articles) {
            int completeRate;
            int articlesSize = articles.size();
            int alreadyReadArticleSize = (int) articles
                    .stream()
                    .filter(article -> article.getReadCount() > 0)
                    .count();

            completeRate = alreadyReadArticleSize / articlesSize;

            return new DecideFolderInfo(completeRate);
        }
    }

}
