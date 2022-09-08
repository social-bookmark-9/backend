package com.sparta.backend.responseDto;

import com.sparta.backend.model.ArticleFolder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ArticlesInFolderResponseDto {

    private Long folderId;
    private String folderName;
    private Integer likeCount;
    private Boolean isMe;
    private Boolean likeStatus;
    private List<ArticlesInfoInFolderResponseDto> articlesInfoInFolderResponseDtoList = new ArrayList<>();

    public ArticlesInFolderResponseDto(ArticleFolder articleFolder, List<ArticlesInfoInFolderResponseDto> articlesInfoInFolderResponseDtoList) {
        this.folderId = articleFolder.getId();
        this.folderName = articleFolder.getArticleFolderName();
        this.likeCount = articleFolder.getLikeCount();
        this.isMe = false;
        this.likeStatus = false;
        this.articlesInfoInFolderResponseDtoList = articlesInfoInFolderResponseDtoList;
    }

    public ArticlesInFolderResponseDto(ArticleFolder articleFolder, Boolean isMe, Boolean likeStatus, List<ArticlesInfoInFolderResponseDto> articlesInfoInFolderResponseDtoList) {
        this.folderId = articleFolder.getId();
        this.folderName = articleFolder.getArticleFolderName();
        this.likeCount = articleFolder.getLikeCount();
        this.isMe = isMe;
        this.likeStatus = likeStatus;
        this.articlesInfoInFolderResponseDtoList = articlesInfoInFolderResponseDtoList;
    }

    //비로그인
    public static ArticlesInFolderResponseDto of(ArticleFolder articleFolder, List<ArticlesInfoInFolderResponseDto> articlesInfoInFolderResponseDtoList) {
        return new ArticlesInFolderResponseDto(articleFolder, articlesInfoInFolderResponseDtoList);
    }

    // 로그인
    public static ArticlesInFolderResponseDto of(ArticleFolder articleFolder, Boolean isMe, Boolean likeStatus, List<ArticlesInfoInFolderResponseDto> articlesInfoInFolderResponseDtoList) {
        return new ArticlesInFolderResponseDto(articleFolder, isMe, likeStatus, articlesInfoInFolderResponseDtoList);
    }
}
