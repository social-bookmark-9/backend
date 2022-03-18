package com.sparta.backend.responseDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor
public class ArticleFolderListDto {

    private long folderId;
    private String folderName;
    private String hashTag1;
    private String hashTag2;
    private String hashTag3;
    private int likeCount;
    private int completeRate;
    private boolean isHide;
    List<ArticleListDto> articleListDtoList = new ArrayList<>();

    @Builder
    public ArticleFolderListDto(long folderId, String folderName, String hashTag1, String hashTag2, String hashTag3,
                                int likeCount, int completeRate, boolean isHide, List<ArticleListDto> articleListDtoList) {
        this.folderId = folderId;
        this.folderName = folderName;
        this.hashTag1 = hashTag1;
        this.hashTag2 = hashTag2;
        this.hashTag3 = hashTag3;
        this.likeCount = likeCount;
        this.completeRate = completeRate;
        this.isHide = isHide;
        this.articleListDtoList.addAll(articleListDtoList);
    }
}
