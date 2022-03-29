package com.sparta.backend.service;

import com.sparta.backend.model.Hashtag;
import com.sparta.backend.model.Member;
import com.sparta.backend.requestDto.MemberInfoEditRequestDto;

public interface MemberInfoService {

    void editProfileMemberName(String memberName, Member member);

    void editProfileImageUrl(String imageUrl, Member member);

    void editProfileStatusMessage(MemberInfoEditRequestDto memberInfoEditRequestDto, Member member);

    void editHashtag(MemberInfoEditRequestDto memberInfoEditRequestDto, Hashtag hashtag);

    void editProfileSnsUrl(MemberInfoEditRequestDto memberInfoEditRequestDto, Member member);

    void editEmail(MemberInfoEditRequestDto memberInfoEditRequestDto, Member member);

    boolean checkDuplicateMemberName(String memberName);
}
