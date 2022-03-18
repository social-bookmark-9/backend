package com.sparta.backend.utils;

import com.sparta.backend.lib.opengraph.OpenGraph;
import com.sparta.backend.requestDto.OpenGraphRequestDto;

public class OpenGraphScrapper {
    public OpenGraphRequestDto openGraphScrapper(String url) {

        try {
            OpenGraph ogTag = new OpenGraph(url, true);
            return OpenGraphRequestDto.builder()
                    .titleOg(ogTag.getContent("title"))
                    .imgOg(ogTag.getContent("image"))
                    .contentOg(ogTag.getContent("description"))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return OpenGraphRequestDto.builder()
                .titleOg("제목을 찾을 수 없습니다.")
                .imgOg("이미지를 찾을 수 없습니다.")
                .contentOg("내용을 찾을 수 없습니다.")
                .build();
    }
}
