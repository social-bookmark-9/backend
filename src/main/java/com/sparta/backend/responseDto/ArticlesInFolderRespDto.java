package com.sparta.backend.responseDto;

import com.sparta.backend.model.Hashtag;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ArticlesInFolderRespDto {

    private String url;
    private String titleOg;
    private String imgOg;
    private Hashtag hashtag;

    @Builder
    public ArticlesInFolderRespDto(String url, String titleOg, String imgOg, Hashtag hashtag) {
        this.url = url;
        this.titleOg = titleOg;
        this.imgOg = imgOg;
        this.hashtag = hashtag;
    }
}
