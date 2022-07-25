package com.sparta.backend.responseDto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class ArticleRandomResponseDto {
    private Long articleId;
    private String titleOg;
    private String imgOg;
    private String contentOg;
    private String hashtag1;
    private String hashtag2;
    private String hashtag3;

    @QueryProjection
    public ArticleRandomResponseDto(Long articleId, String titleOg, String imgOg, String contentOg, String hashtag1, String hashtag2, String hashtag3) {
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


}
