package com.sparta.backend.controller;

import com.sparta.backend.message.RestResponseMessage;
import com.sparta.backend.model.Member;
import com.sparta.backend.responseDto.ArticleFolderListResponseDto;
import com.sparta.backend.responseDto.ArticleRandomResponseDto;
import com.sparta.backend.responseDto.MainPageArticleFolderResponseDto;
import com.sparta.backend.responseDto.RecommendedMemberResponseDto;
import com.sparta.backend.service.MainPageService;
import com.sparta.backend.utils.RandomGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MainPageController {

    private final MainPageService mainPageService;

    @GetMapping("/api/mainpage")
    public ResponseEntity<RestResponseMessage> getMainPage(@AuthenticationPrincipal Member getMember) {
        
        // 랜덤 해시태그 생성
        String randomHashtag = String.valueOf(RandomGenerator.RandomHashtag.getRandomHashtag());

        // 유저
        List<RecommendedMemberResponseDto> memberList = mainPageService.getRecommendedMembers(getMember, randomHashtag);

        // 아티클 폴더
        List<MainPageArticleFolderResponseDto> articleFolderList = mainPageService.getRecommendedArticleFolders(getMember);

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
