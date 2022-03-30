package com.sparta.backend.service;

import com.sparta.backend.model.Member;
import com.sparta.backend.responseDto.ArticleFolderListResponseDto;
import com.sparta.backend.responseDto.MemberInfoResponseDto;

import java.util.List;

public interface MyPageService {

    MemberInfoResponseDto getMyMemberInfo(Member member);

    MemberInfoResponseDto getOtherMemberInfo(Long memberId);

    List<ArticleFolderListResponseDto> getMyArticleFolderList(Member member);

    List<ArticleFolderListResponseDto> getOtherArticleFolderList(Long memberId);
}
