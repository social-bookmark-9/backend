package com.sparta.backend.controller;

import com.sparta.backend.message.RestResponseMessage;
import com.sparta.backend.model.Member;
import com.sparta.backend.repository.MemberRepository;
import com.sparta.backend.responseDto.RecommendedMemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MainPageMemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/api/mainpage/members")
    public ResponseEntity<RestResponseMessage> getRecommendedMembers() {

        List<Member> memberList = memberRepository.findAll();
        List<RecommendedMemberResponseDto> recommendedMemberResponseDtos = new ArrayList<>();
        for(Member member:memberList) {
            RecommendedMemberResponseDto recommendedMemberResponseDto = RecommendedMemberResponseDto.builder()
                    .memberId(member.getId())
                    .profileImage(member.getProfileImage())
                    .memberComment(member.getMemberComment())
                    .memberName(member.getMemberName())
                    .build();
            recommendedMemberResponseDtos.add(recommendedMemberResponseDto);
        }

        return new ResponseEntity<>(new RestResponseMessage<>(true,"추천 유저 검색 결과", recommendedMemberResponseDtos), HttpStatus.OK);
    }
}
