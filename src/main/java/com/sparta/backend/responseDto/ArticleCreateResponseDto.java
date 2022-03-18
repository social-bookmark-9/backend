package com.sparta.backend.responseDto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ArticleCreateResponseDto {
    private final Long articleId;
    private final LocalDateTime createdAt;
    private final String url;
    private final String titleOg;
    private final String imgOg;
    private final String contentOg;
    private final String hashtag1;
    private final String hashtag2;
    private final String hashtag3;
    private final Boolean isMe;
    private final Boolean isRead;
    private final Boolean isSaved;
}
