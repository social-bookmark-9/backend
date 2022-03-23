package com.sparta.backend.responseDto;

import com.sparta.backend.model.Article;
import lombok.*;


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

    private ArticlesInFolderResponseDto(Article article, boolean isMe, boolean isRead, boolean isSaved) {
        this.articleId = article.getId();
        this.url = article.getUrl();
        this.titleOg = article.getTitleOg();
        this.imgOg = article.getImgOg();
        this.contentOg = article.getContentOg();
        this.hashtag1 = article.getHashtag().getHashtag1();
        this.hashtag2 = article.getHashtag().getHashtag2();
        this.hashtag3 = article.getHashtag().getHashtag3();
        this.isMe = isMe;
        this.isRead = isRead;
        this.isSaved = isSaved;
    }

    public static ArticlesInFolderResponseDto of(Article article, boolean isMe, boolean isRead, boolean isSaved) {
        return new ArticlesInFolderResponseDto(article, isMe, isRead, isSaved);
    }
}
