package com.sparta.backend.utils;

import com.sparta.backend.lib.opengraph.OpenGraph;
import com.sparta.backend.requestDto.OGTagRequestDto;

public class OpenGraphScrapper {
    public OGTagRequestDto openGraphScrapper(String url) {

        String titleOg = null;
        String imgOg = null;
        String contentOg = null;

        try {
            OpenGraph ogTag = new OpenGraph(url, true);
            titleOg = ogTag.getContent("title") == null ? null : ogTag.getContent("title");
            imgOg = ogTag.getContent("image") == null ? null : ogTag.getContent("image");
            contentOg = ogTag.getContent("description") == null ? null : ogTag.getContent("description");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return OGTagRequestDto.builder()
                .titleOg(titleOg)
                .imgOg(imgOg)
                .contentOg(contentOg)
                .build();
    }
}
