package com.sparta.backend.requestDto;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class ArticleReviewRequestDto {
    @NotNull
    private final String review;
}
