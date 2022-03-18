package com.sparta.backend.responseDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ArticleListDto extends ArticleFolderListDto{

    private String title;
    private String content;



}
