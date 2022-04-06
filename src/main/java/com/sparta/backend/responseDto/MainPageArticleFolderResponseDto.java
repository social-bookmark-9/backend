package com.sparta.backend.responseDto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class MainPageArticleFolderResponseDto {

    private Long folderId;
    private String folderName;
    private Integer likeCount;
    private String hashTag1;
    private String hashTag2;
    private String hashTag3;
    private List<ArticleTitleContentDto> articleTitleContentDto = new ArrayList<>();

    @QueryProjection
    public MainPageArticleFolderResponseDto(Long folderId, String folderName, Integer likeCount,
                                            String hashTag1, String hashTag2, String hashTag3,
                                            List<ArticleTitleContentDto> articleTitleContentDto) {
        this.folderId = folderId;
        this.folderName = folderName;
        this.likeCount = likeCount;

        if (hashTag1 != null && !hashTag1.isEmpty()) {
            this.hashTag1 = hashTag1;
        } else {
            this.hashTag1 = null;
        }
        if (hashTag2 != null && !hashTag2.isEmpty()) {
            this.hashTag2 = hashTag2;
        } else {
            this.hashTag2 = null;
        }
        if (hashTag3 != null && !hashTag3.isEmpty()) {
            this.hashTag3 = hashTag3;
        } else {
            this.hashTag3 = null;
        }

        this.articleTitleContentDto = articleTitleContentDto;
    }

    @Getter
    @NoArgsConstructor
    public static class ArticleTitleContentDto {
        private String titleOg;
        private String contentOg;

        @QueryProjection
        public ArticleTitleContentDto(String titleOg, String contentOg) {
            this.titleOg = titleOg;
            this.contentOg = contentOg;
        }
    }

}
