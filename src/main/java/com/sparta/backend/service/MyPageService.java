package com.sparta.backend.service;

import com.sparta.backend.model.Member;
import com.sparta.backend.responseDto.ArticleFolderListDto;
import com.sparta.backend.responseDto.MemberInfoDto;

import java.util.List;

public interface MyPageService {

    MemberInfoDto getMyMemberInfo(Member member);

    MemberInfoDto getOtherMemberInfo(long memberId);

    List<ArticleFolderListDto> getMyArticleFolderList(Member member);

    List<ArticleFolderListDto> getOtherArticleFolderList(long memberId);
}
