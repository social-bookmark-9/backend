package com.sparta.backend.responseDto;

import com.querydsl.core.annotations.QueryProjection;
import com.sparta.backend.model.Article;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class SearchPageArticleResponseDto {

    private Long articleId;
    private String titleOg;
    private String imgOg;
    private String contentOg;
    private String hashtag1;
    private String hashtag2;
    private String hashtag3;

    @QueryProjection
    public SearchPageArticleResponseDto(Long articleId, String titleOg, String imgOg, String contentOg, String hashtag1, String hashtag2, String hashtag3) {
        this.articleId = articleId;
        this.titleOg = titleOg;
        this.imgOg = imgOg;
        this.contentOg = contentOg;

        if (hashtag1 != null && !hashtag1.isEmpty()) {
            this.hashtag1 = hashtag1;
        } else {
            this.hashtag1 = null;
        }
        if (hashtag2 != null && !hashtag2.isEmpty()) {
            this.hashtag2 = hashtag2;
        } else {
            this.hashtag2 = null;
        }
        if (hashtag3 != null && !hashtag3.isEmpty()) {
            this.hashtag3 = hashtag3;
        } else {
            this.hashtag3 = null;
        }
    }

    // 삭제
    public SearchPageArticleResponseDto(Article article) {
        this.articleId = article.getId();
        this.titleOg = article.getTitleOg();
        this.imgOg = article.getImgOg();
        this.contentOg = article.getContentOg();
        this.hashtag1 = article.getHashtag().getHashtag1();
        this.hashtag2 = article.getHashtag().getHashtag2();
        this.hashtag3 = article.getHashtag().getHashtag3();
    }

}
