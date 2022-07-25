package com.sparta.backend.controller;

import com.sparta.backend.message.RestResponseMessage;
import com.sparta.backend.model.Member;
import com.sparta.backend.repository.MemberRepository;
import com.sparta.backend.responseDto.ArticleRandomResponseDto;
import com.sparta.backend.responseDto.MainPageArticleFolderResponseDto;
import com.sparta.backend.responseDto.MemberHashtagInfoDto;
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
    private final MemberRepository memberRepository;

    @GetMapping("/api/mainpage")
    public ResponseEntity<RestResponseMessage> getMainPage(@AuthenticationPrincipal Member getMember) {
        // 데이터 리턴
        Map<String, Object> returnData = new HashMap<>();

        if (getMember == null) {
            // 랜덤 해시태그 생성
            String randomHashtag = String.valueOf(RandomGenerator.RandomHashtag.getRandomHashtag());

            List<RecommendedMemberResponseDto> memberList = mainPageService.getRecommendedMembersNonLogin(randomHashtag);

            List<MainPageArticleFolderResponseDto> articleFolderList = mainPageService.getRecommendedArticleFoldersNonLogin(randomHashtag);

            List<ArticleRandomResponseDto> articleList = mainPageService.getMonthArticlesNonLogin();

            returnData.put("memberList", memberList);
            returnData.put("articleFolderList", articleFolderList);
            returnData.put("articleList", articleList);
            returnData.put("hashtagButton", randomHashtag);
        } else {
            // 유저 해시테그 정보
            MemberHashtagInfoDto memberHashtagInfoDto = memberRepository.memberHashtagInfo(getMember.getId());
            // 유저
            List<RecommendedMemberResponseDto> memberList = mainPageService.getRecommendedMembersLogin(getMember, memberHashtagInfoDto);
            // 아티클 폴더
            List<MainPageArticleFolderResponseDto> articleFolderList = mainPageService.getRecommendedArticleFoldersLogin(getMember, memberHashtagInfoDto);
            // 아티클
            List<ArticleRandomResponseDto> articleList = mainPageService.getMonthArticlesLogin(getMember, memberHashtagInfoDto);

            returnData.put("memberList", memberList);
            returnData.put("articleFolderList", articleFolderList);
            returnData.put("articleList", articleList);
            returnData.put("hashtagButton", memberHashtagInfoDto);
        }

        return new ResponseEntity<>(new RestResponseMessage<>(true,"메인페이지 정보 불러오기", returnData), HttpStatus.OK);
    }
}
