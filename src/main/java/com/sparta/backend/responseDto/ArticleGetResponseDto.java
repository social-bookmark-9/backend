package com.sparta.backend.responseDto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ArticleGetResponseDto {
    private final Long articleId;
    private final Long writerMemberId;
    private final String writerMemberName;
    private final String url;
    private final String titleOg;
    private final String imgOg;
    private final String contentOg;
    private final String hashtag1;
    private final String hashtag2;
    private final String hashtag3;
    private final String review;
    private final Boolean reviewHide;
    private final Integer reminderDate;
    private final String articleFolderName;
    private final List<ArticleRandomResponseDto> recommendArticles;
}
