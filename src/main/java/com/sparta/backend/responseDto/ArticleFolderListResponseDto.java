package com.sparta.backend.responseDto;

import com.sparta.backend.model.Article;
import com.sparta.backend.model.ArticleFolder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
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
    private List<ArticleTitleContentDto> articleTitleContentDto = new ArrayList<>();

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
        this.articleTitleContentDto = new ArrayList<>();
    }

    public ArticleFolderListResponseDto(ArticleFolder articleFolder, List<ArticleTitleContentDto> articleTitleContentDto, DecideFolderInfo decideFolderInfo) {
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
        this.articleTitleContentDto.addAll(articleTitleContentDto);
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
        this.articleTitleContentDto = new ArrayList<>();
    }

    public ArticleFolderListResponseDto(ArticleFolder articleFolder, List<ArticleTitleContentDto> articleTitleContentDto, DecideFolderInfo decideFolderInfo, Boolean likeStatus) {
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
        this.articleTitleContentDto.addAll(articleTitleContentDto);
    }

    // 폴더에 아티클이 없을때(내꺼)
    public static ArticleFolderListResponseDto of(ArticleFolder articleFolder) {
        return new ArticleFolderListResponseDto(articleFolder);
    }

    // 폴더에 아티클이 있을때(내꺼)
    public static ArticleFolderListResponseDto of(ArticleFolder articleFolder, List<Article> articles) {
        return new ArticleFolderListResponseDto(articleFolder, ArticleTitleContentDto.of(articles), DecideFolderInfo.of(articles));
    }

    // 폴더에 아티클이 없을때(남의것)
    public static ArticleFolderListResponseDto of(ArticleFolder articleFolder, Boolean likeStatus) {
        return new ArticleFolderListResponseDto(articleFolder, likeStatus);
    }

    // 폴더에 아티클이 있을때(남의것)
    public static ArticleFolderListResponseDto of(ArticleFolder articleFolder, List<Article> articles, Boolean likeStatus) {
        return new ArticleFolderListResponseDto(articleFolder, ArticleTitleContentDto.of(articles), DecideFolderInfo.of(articles), likeStatus);
    }

    @Getter
    @NoArgsConstructor
    private static class ArticleTitleContentDto {
        private String titleOg;
        private String contentOg;

        public ArticleTitleContentDto(String titleOg, String contentOg) {
            this.titleOg = titleOg;
            this.contentOg = contentOg;
        }

        public static List<ArticleTitleContentDto> of(List<Article> articles) {
            return articles.stream()
                    .map(article -> new ArticleTitleContentDto(
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
