package com.sparta.backend.utils;

import com.sparta.backend.requestDto.OGTagRequestDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class JsoupParser {

    public OGTagRequestDto ogTagScraper(String url) {

        String titleOg = null;
        String imgOg = null;
        String contentOg = null;

        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.87 Safari/537.36")
                    .get();

            Elements ogTags = doc.select("meta[property^=og:]");

            if (ogTags.size() == 0) {
                return OGTagRequestDto.builder()
                        .titleOg(null)
                        .imgOg(null)
                        .contentOg(null)
                        .build();
            }

            for (Element el : ogTags) {
                String text = el.attr("property");

                switch (text) {
                    case "og:title":
                        titleOg = el.attr("content");
                        break;
                    case "og:image":
                        imgOg = el.attr("content");
                        break;
                    case "og:description":
                        contentOg = el.attr("content");
                        break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return OGTagRequestDto.builder()
                .titleOg(titleOg)
                .imgOg(imgOg)
                .contentOg(contentOg)
                .build();
    }
}
