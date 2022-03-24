package com.sparta.backend.serviceImpl;

import com.sparta.backend.model.Hashtag;
import com.sparta.backend.model.Member;
import com.sparta.backend.requestDto.MemberInfoEditRequestDto;
import com.sparta.backend.service.MemberInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberInfoServiceImpl implements MemberInfoService {

    // 프로필 닉네임 수정하기
    @Override
    public void editProfileMemberName(String memberName, Member member) {
        member.editMemberName(memberName);
    }

    // 프로필 이미지 수정하기
    @Override
    public void editProfileImageUrl(String imageUrl, Member member) {
        member.editProfileImageUrl(imageUrl);
    }

    // 프로필 자기소개 수정하기
    @Override
    public void editProfileStatusMessage(MemberInfoEditRequestDto memberInfoEditRequestDto, Member member) {
        member.editStatusMessage(memberInfoEditRequestDto.getUserDesc());
    }

    // 프로필 관심분야 수정하기
    @Override
    public void editHashtag(MemberInfoEditRequestDto memberInfoEditRequestDto, Hashtag hashtag) {
        hashtag.setHashtag(memberInfoEditRequestDto.getHashtag1(), memberInfoEditRequestDto.getHashtag2(), memberInfoEditRequestDto.getHashtag3());
    }

    // 프로필 sns 수정하기
    @Override
    public void editProfileSnsUrl(MemberInfoEditRequestDto memberInfoEditRequestDto, Member member) {
        // 인스타그램
        if(memberInfoEditRequestDto.getInstagramUrl() != null) {
            if(Objects.equals(memberInfoEditRequestDto.getProfileImageUrl(), "")) {
                member.setInstagramUrl(null);
            } else {
                member.setInstagramUrl(memberInfoEditRequestDto.getInstagramUrl());
            }
        }
        // 깃헙
        if(memberInfoEditRequestDto.getGithubUrl() != null) {
            if (Objects.equals(memberInfoEditRequestDto.getGithubUrl(), "")) {
                member.setGithubUrl(null);
            } else {
                member.setGithubUrl(memberInfoEditRequestDto.getGithubUrl());
            }
        }
        // 브런치
        if(memberInfoEditRequestDto.getBrunchUrl() != null) {
            if (Objects.equals(memberInfoEditRequestDto.getBrunchUrl(), "")) {
                member.setBrunchUrl(null);
            } else {
                member.setBrunchUrl(memberInfoEditRequestDto.getBrunchUrl());
            }
        }
        // 개인 블로그
        if(memberInfoEditRequestDto.getBlogUrl() != null) {
            if (Objects.equals(memberInfoEditRequestDto.getBlogUrl(), "")) {
                member.setBlogUrl(null);
            } else {
                member.setBlogUrl(memberInfoEditRequestDto.getBlogUrl());
            }
        }
        // 웹사이트
        if(memberInfoEditRequestDto.getWebsiteUrl() != null) {
            if (Objects.equals(memberInfoEditRequestDto.getWebsiteUrl(), "")) {
                member.setWebsiteUrl(null);
            } else {
                member.setWebsiteUrl(memberInfoEditRequestDto.getBlogUrl());
            }
        }
    }
}
