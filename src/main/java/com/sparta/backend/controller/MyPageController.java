package com.sparta.backend.controller;

import com.sparta.backend.message.RestResponseMessage;
import com.sparta.backend.model.Member;
import com.sparta.backend.responseDto.ArticleFolderListResponseDto;
import com.sparta.backend.responseDto.MemberInfoResponseDto;
import com.sparta.backend.responseDto.MyPageResponseDto;
import com.sparta.backend.service.MyPageService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    /**
     * 마이페이지 데이터
     * @param member
     * @param memberId
     * @return MyPageResponseDto
     */
    @ApiOperation(value = "마이 페이지 데이터", notes = "마이 페이지 데이터 API")
    @GetMapping("/mypage/{memberId}")
    public ResponseEntity<RestResponseMessage<MyPageResponseDto>> myPage(
            @AuthenticationPrincipal Member member,
            @PathVariable long memberId) {

        boolean loginStatus = member != null;
        System.out.println(member);
        MemberInfoResponseDto memberInfoResponseDto = loginStatus && member.getId() == memberId ?
                myPageService.getMyMemberInfo(member) : myPageService.getOtherMemberInfo(memberId);

        List<ArticleFolderListResponseDto> articleFolderListResponseDto = loginStatus && member.getId() == memberId ?
                myPageService.getMyArticleFolderList(member) : myPageService.getOtherArticleFolderList(memberId);

        MyPageResponseDto myPageResponseDto = MyPageResponseDto.of(memberInfoResponseDto, articleFolderListResponseDto);

        return new ResponseEntity<>(new RestResponseMessage<>(true, "success", myPageResponseDto), HttpStatus.OK);
    }

}
