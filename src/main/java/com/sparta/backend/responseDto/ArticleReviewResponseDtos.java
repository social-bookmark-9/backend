package com.sparta.backend.responseDto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ArticleReviewResponseDtos {
    private final List<ArticleReviewResponseDto> reviewList;
}
