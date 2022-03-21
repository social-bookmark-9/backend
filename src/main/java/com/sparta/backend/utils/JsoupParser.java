package com.sparta.backend.utils;

import com.sparta.backend.requestDto.OGTagRequestDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;

public class JsoupParser {

    public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
    public static final String WEB_DRIVER_PATH = "chromedriver";

    // TODO: 셀레니움 테스트
//    public String seleniumParser(String url) {
//        try {
//            System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        ChromeOptions options = new ChromeOptions();
//        WebDriver driver = new ChromeDriver(options);
//        driver.get(url);
//        WebElement titleEl = driver.findElement(By.xpath("//meta[@property='og:title']"));
//        WebElement imgEl = driver.findElement(By.xpath("//meta[@property='og:image']"));
//        WebElement descriptionEl = driver.findElement(By.xpath("//meta[@property='og:description']"));
//        String titleContent = titleEl.getAttribute("content");
//        String imgContent = descriptionEl.getAttribute("content");
//        String descriptionContent = imgEl.getAttribute("content");
//        System.out.println("titleContent = " + titleContent);
//        System.out.println("imgContent = " + imgContent);
//        System.out.println("descriptionContent = " + descriptionContent);
//        return titleContent;
//    }

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
