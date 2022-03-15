package com.sparta.backend.responseDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ArticleReviewHideResponseDto {
    private final boolean reviewHide;
}
