package com.sparta.backend.service;

import com.sparta.backend.model.Member;
import com.sparta.backend.responseDto.ArticleFolderListDto;
import com.sparta.backend.responseDto.MemberInfoDto;

public interface MyPageService {

    MemberInfoDto getMyMemberInfo(Member member);

    MemberInfoDto getOtherMemberInfo(long memberId);
    ArticleFolderListDto getMyArticleFolderList(Member member);
    ArticleFolderListDto getOtherArticleFolderList(long memberId);
}
