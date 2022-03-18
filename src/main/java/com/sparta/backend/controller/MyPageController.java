package com.sparta.backend.controller;

import com.sparta.backend.model.Member;
import com.sparta.backend.responseDto.ArticleFolderListDto;
import com.sparta.backend.responseDto.MemberInfoDto;
import com.sparta.backend.responseDto.MyPageResponseDto;
import com.sparta.backend.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/mypage/{memberId}")
    public ResponseEntity<ResponseEntity<MyPageResponseDto>> myPage(
            @AuthenticationPrincipal Member member,
            @PathVariable long memberId) {

        MemberInfoDto memberInfoDto = member.getId() == memberId ?
                myPageService.getMyMemberInfo(member) : myPageService.getOtherMemberInfo(memberId);

        ArticleFolderListDto articleFolderListDto = member.getId() == memberId ?
                myPageService.getMyArticleFolderList(member) : myPageService.getOtherArticleFolderList(memberId);

        return null;
    }

}
