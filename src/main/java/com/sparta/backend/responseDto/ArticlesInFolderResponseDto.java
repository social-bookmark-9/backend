package com.sparta.backend.responseDto;

import com.sparta.backend.model.Hashtag;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class ArticlesInFolderResponseDto {

    private Long articleId;
    private String url;
    private String titleOg;
    private String imgOg;
    private String contentOg;
    private String hashtag1;
    private String hashtag2;
    private String hashtag3;

    private boolean isMe;
    private boolean isRead;
    private boolean isSaved;

    @Builder
    public ArticlesInFolderResponseDto(Long articleId, String url, String titleOg,
                                       String imgOg, String contentOg, String hashtag1,
                                       String hashtag2, String hashtag3,
                                       Boolean isMe, Boolean isRead, Boolean isSaved) {
        this.articleId = articleId;
        this.url = url;
        this.titleOg = titleOg;
        this.imgOg = imgOg;
        this.contentOg = contentOg;
        this.hashtag1 = hashtag1;
        this.hashtag2 = hashtag2;
        this.hashtag3 = hashtag3;
        this.isMe = isMe;
        this.isRead = isRead;
        this.isSaved = isSaved;
    }
}
