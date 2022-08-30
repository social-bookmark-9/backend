package com.sparta.backend.responseDto;

import com.sparta.backend.model.Article;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
public class ArticlesInfoInFolderResponseDto {

    private Long articleId;
    private String url;
    private String titleOg;
    private String imgOg;
    private String contentOg;
    private LocalDateTime createdAt;
    private String hashtag1;
    private String hashtag2;
    private String hashtag3;
    private boolean isRead;
    private boolean isSaved;

    public ArticlesInfoInFolderResponseDto(Article article) {
        this.articleId = article.getId();
        this.url = article.getUrl();
        this.titleOg = article.getTitleOg();
        this.imgOg = article.getImgOg();
        this.contentOg = article.getContentOg();
        this.createdAt = article.getCreatedAt();
        this.hashtag1 = article.getHashtag().getHashtag1();
        this.hashtag2 = article.getHashtag().getHashtag2();
        this.hashtag3 = article.getHashtag().getHashtag3();
        this.isRead = false;
        this.isSaved = false;
    }

    public ArticlesInfoInFolderResponseDto(Article article, boolean isRead, boolean isSaved) {
        this.articleId = article.getId();
        this.url = article.getUrl();
        this.titleOg = article.getTitleOg();
        this.imgOg = article.getImgOg();
        this.contentOg = article.getContentOg();
        this.createdAt = article.getCreatedAt();
        this.hashtag1 = article.getHashtag().getHashtag1();
        this.hashtag2 = article.getHashtag().getHashtag2();
        this.hashtag3 = article.getHashtag().getHashtag3();
        this.isRead = isRead;
        this.isSaved = isSaved;
    }

    // 비로그인
    public static ArticlesInfoInFolderResponseDto of(Article article) {
        return new ArticlesInfoInFolderResponseDto(article);
    }

    // 로그인
    public static ArticlesInfoInFolderResponseDto of(Article article, boolean isRead, boolean isSaved) {
        return new ArticlesInfoInFolderResponseDto(article, isRead, isSaved);
    }
}
