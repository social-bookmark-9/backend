package com.sparta.backend.responseDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ArticleListDto {

    private String title;
    private String content;

    @Builder
    public ArticleListDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
