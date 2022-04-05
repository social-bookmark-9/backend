package com.sparta.backend.responseDto;

import com.sparta.backend.model.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchPageArticleResponseDto {

    private Long articleId;
    private String titleOg;
    private String imgOg;
    private String contentOg;
    private String hashtag1;
    private String hashtag2;
    private String hashtag3;

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
