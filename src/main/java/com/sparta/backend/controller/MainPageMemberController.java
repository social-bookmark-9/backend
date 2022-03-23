package com.sparta.backend.controller;

import com.sparta.backend.message.RestResponseMessage;
import com.sparta.backend.model.Member;
import com.sparta.backend.repository.MemberRepository;
import com.sparta.backend.responseDto.RecommendedMemberResponseDto;
import com.sparta.backend.service.MainPageService;
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

    private final MemberRepository memberRepository;
    private final MainPageService mainPageService;

    @GetMapping("/api/mainpage/members")
    public ResponseEntity<RestResponseMessage> getRecommendedMembers(@AuthenticationPrincipal Member getMember) {

        // 비로그인 일 경우
        List<RecommendedMemberResponseDto> memberList;
        if(getMember == null) {
            System.out.println("비로그인 메인페이지 유저 추천");

            // 랜덤으로 해쉬태그중 하나로 검색하기
            List<String> hashtags = new ArrayList<>();
            hashtags.add("커리어"); hashtags.add("업무스킬"); hashtags.add("IT"); hashtags.add("디자인"); hashtags.add("마케팅");hashtags.add("투자");
            hashtags.add("장소"); hashtags.add("동기부여"); hashtags.add("인간관계"); hashtags.add("패션");hashtags.add("예술"); hashtags.add("과학");
            List<Member> members = memberRepository.findMembersByHashtag_Hashtag1(hashtags.get((int) (Math.random() * 11)));

            // 멤버를 아티클 폴더의 좋아요 수의 총합으로 내림차순 나열하기
            memberList = mainPageService.getRecommendedMembers(members);
        }
        // 로그인일 경우
        else {
            System.out.println("로그인 메인페이지 유저 추천");

            // 로그인한 유저의 해쉬태그로 유저 검색
            String recommendedHashtag = getMember.getHashtag().getHashtag1();
            List<Member> members = memberRepository.findMembersByHashtag_Hashtag1(recommendedHashtag);

            // 멤버를 아티클 폴더의 좋아요 수의 총합으로 내림차순 나열하기
            memberList = mainPageService.getRecommendedMembers(members);
        }
        return new ResponseEntity<>(new RestResponseMessage<>(true,"메인페이지 정보 불러오기", memberList), HttpStatus.OK);
    }
}

