package com.sparta.backend.repositorycustom;

import com.sparta.backend.responseDto.MemberHashtagInfoDto;
import com.sparta.backend.responseDto.RecommendedMemberResponseDto;

import java.util.List;

public interface MemberRepositoryCustom {
    MemberHashtagInfoDto memberHashtagInfo(Long memberId);
    List<RecommendedMemberResponseDto> mainPageMemberLogin(Long memberId, List<String> hashTagList);
    List<RecommendedMemberResponseDto> mainPageMemberNonLogin(String hashtag);
}
