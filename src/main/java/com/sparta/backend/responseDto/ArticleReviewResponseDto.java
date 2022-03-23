package com.sparta.backend.responseDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ArticleReviewResponseDto {
    private final String review;
    private final Boolean reviewHide;
}
