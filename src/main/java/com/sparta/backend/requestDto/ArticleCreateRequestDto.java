package com.sparta.backend.requestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleCreateRequestDto {
    @URL(message = "올바른 URL형식이 아닙니다.")
    private String url;
    @NotNull(message = "readCount가 없습니다.")
    private Integer readCount;
    @NotNull(message = "buttonDate가 없습니다.")
    private Integer reminderDate;
    private Boolean reviewHide;
    @NotBlank(message = "한 개 이상의 태그를 선택해야 합니다.")
    private String hashtag1;
    private String hashtag2;
    private String hashtag3;
    @NotBlank(message = "아티클 폴더를 반드시 선택해야 합니다.")
    private String articleFolderName;
}