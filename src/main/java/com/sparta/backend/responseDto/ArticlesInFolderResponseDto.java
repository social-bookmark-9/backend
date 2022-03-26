package com.sparta.backend.responseDto;

import com.sparta.backend.model.ArticleFolder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ArticlesInFolderResponseDto {

    private long folderId;
    private String folderName;
    private int likeCount;
    private boolean isMe;
    private List<ArticlesInfoInFolderResponseDto> articlesInfoInFolderResponseDtoList = new ArrayList<>();

    public ArticlesInFolderResponseDto(ArticleFolder articleFolder, List<ArticlesInfoInFolderResponseDto> articlesInfoInFolderResponseDtoList) {
        this.folderId = articleFolder.getId();
        this.folderName = articleFolder.getArticleFolderName();
        this.likeCount = articleFolder.getLikeCount();
        this.isMe = false;
        this.articlesInfoInFolderResponseDtoList = articlesInfoInFolderResponseDtoList;
    }

    public ArticlesInFolderResponseDto(ArticleFolder articleFolder, boolean isMe, List<ArticlesInfoInFolderResponseDto> articlesInfoInFolderResponseDtoList) {
        this.folderId = articleFolder.getId();
        this.folderName = articleFolder.getArticleFolderName();
        this.likeCount = articleFolder.getLikeCount();
        this.isMe = isMe;
        this.articlesInfoInFolderResponseDtoList.addAll(articlesInfoInFolderResponseDtoList);
    }

    //비로그인
    public static ArticlesInFolderResponseDto of(ArticleFolder articleFolder, List<ArticlesInfoInFolderResponseDto> articlesInfoInFolderResponseDtoList) {
        return new ArticlesInFolderResponseDto(articleFolder, articlesInfoInFolderResponseDtoList);
    }

    // 로그인
    public static ArticlesInFolderResponseDto of(ArticleFolder articleFolder, boolean isMe, List<ArticlesInfoInFolderResponseDto> articlesInfoInFolderResponseDtoList) {
        return new ArticlesInFolderResponseDto(articleFolder, isMe, articlesInfoInFolderResponseDtoList);
    }
}
