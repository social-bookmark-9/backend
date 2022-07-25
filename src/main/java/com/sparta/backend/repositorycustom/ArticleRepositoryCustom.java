package com.sparta.backend.repositorycustom;

import com.sparta.backend.responseDto.ArticleRandomResponseDto;

import java.time.LocalDateTime;
import java.util.List;

public interface ArticleRepositoryCustom {
    List<ArticleRandomResponseDto> mainPageArticleLogin(Long memberId, List<String> hashTagList, LocalDateTime startDateTime, LocalDateTime endDateTime);
    List<ArticleRandomResponseDto> mainPageArticleNonLogin(LocalDateTime startDateTime, LocalDateTime endDateTime);
}
