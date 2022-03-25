package com.sparta.backend.controller;

import com.sparta.backend.message.RestResponseMessage;
import com.sparta.backend.model.Member;
import com.sparta.backend.responseDto.ArticleFolderListResponseDto;
import com.sparta.backend.responseDto.ArticleRandomResponseDto;
import com.sparta.backend.responseDto.RecommendedMemberResponseDto;
import com.sparta.backend.service.MainPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MainPageController {

    private final MainPageService mainPageService;

    @GetMapping("/api/mainpage")
    public ResponseEntity<RestResponseMessage> getMainPage(@AuthenticationPrincipal Member getMember) {

        List<String> hashtags = new ArrayList<>();
        hashtags.add("커리어"); hashtags.add("업무스킬"); hashtags.add("IT"); hashtags.add("디자인"); hashtags.add("마케팅");hashtags.add("투자");
        hashtags.add("장소"); hashtags.add("동기부여"); hashtags.add("인간관계"); hashtags.add("패션");hashtags.add("예술"); hashtags.add("과학");
        String randomHashtag = hashtags.get((int) (Math.random() * 11));

        // 유저
        List<RecommendedMemberResponseDto> memberList = mainPageService.getRecommendedMembers(getMember, randomHashtag);

        // 아티클 폴더
        List<ArticleFolderListResponseDto> articleFolderList = mainPageService.getRecommendedArticleFolders(getMember);

        // 아티클
        List<ArticleRandomResponseDto> articleList = mainPageService.getMonthArticles(randomHashtag);
        
        // 데이터 리턴
        Map<String, Object> map = new HashMap<>();
        map.put("memberList", memberList);
        map.put("articleFolderList", articleFolderList);
        map.put("articleList", articleList);
        map.put("hashtagButton", randomHashtag);
        
        return new ResponseEntity<>(new RestResponseMessage<>(true,"메인페이지 정보 불러오기", map), HttpStatus.OK);
    }
}
