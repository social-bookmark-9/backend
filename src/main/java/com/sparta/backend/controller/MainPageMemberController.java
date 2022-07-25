/*
package com.sparta.backend.controller;

import com.sparta.backend.message.RestResponseMessage;
import com.sparta.backend.model.Member;
import com.sparta.backend.responseDto.RecommendedMemberResponseDto;
import com.sparta.backend.service.MainPageService;
import com.sparta.backend.utils.RandomGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequiredArgsConstructor
public class MainPageMemberController {

    private final MainPageService mainPageService;

    @GetMapping("/api/mainpage/members")
    public ResponseEntity<RestResponseMessage> getRecommendedMembers(@AuthenticationPrincipal Member getMember) {

        // 랜덤 해시태그 생성
        String randomHashtag = String.valueOf(RandomGenerator.RandomHashtag.getRandomHashtag());

        // 유저
        List<RecommendedMemberResponseDto> memberList = mainPageService.getRecommendedMembersLogin(getMember);

        return new ResponseEntity<>(new RestResponseMessage<>(true,"메인페이지 정보 불러오기", memberList), HttpStatus.OK);
    }
}

*/
