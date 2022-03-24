package com.sparta.backend.responseDto;

import com.sparta.backend.model.ArticleFolder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ArticleFolderNameAndIdResponseDto {

    private long articleFolderId;
    private String articleFolderName;

    private ArticleFolderNameAndIdResponseDto(ArticleFolder articleFolder) {
        this.articleFolderId = articleFolder.getId();
        this.articleFolderName = articleFolder.getArticleFolderName();
    }

    public static ArticleFolderNameAndIdResponseDto of(ArticleFolder articleFolder) {
        return new ArticleFolderNameAndIdResponseDto(articleFolder);
    }
}
