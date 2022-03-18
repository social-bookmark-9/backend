package com.sparta.backend.serviceImpl;

import com.sparta.backend.model.Member;
import com.sparta.backend.repository.ArticleFolderRepository;
import com.sparta.backend.repository.MemberRepository;
import com.sparta.backend.responseDto.ArticleFolderListDto;
import com.sparta.backend.responseDto.MemberInfoDto;
import com.sparta.backend.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static springfox.documentation.schema.Annotations.memberName;

@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService {

    private final MemberRepository memberRepository;
    private final ArticleFolderRepository articleFolderRepository;

    @Override
    public MemberInfoDto getMyMemberInfo(Member member) {
        return MemberInfoDto.builder()
                .memberId(member.getId())
                .memberName(member.getMemberName())
                .email(member.getEmail())
                .profileImage(member.getProfileImage())
                .memberComment(member.getMemberComment())
                .instagramUrl(member.getInstagramUrl())
                .githubUrl(member.getGithubUrl())
                .brunchUrl(member.getBrunchUrl())
                .blogUrl(member.getBlogUrl())
                .websiteUrl(member.getWebsiteUrl())
                .build();
    }

    @Override
    public MemberInfoDto getOtherMemberInfo(long memberId) {
        Optional<Member> member= Optional.ofNullable(memberRepository.findById(memberId)
                .orElseThrow(IllegalArgumentException::new));

        return MemberInfoDto.builder()
                .memberId(member.get().getId())
                .memberName(member.get().getMemberName())
                .email(member.get().getEmail())
                .profileImage(member.get().getProfileImage())
                .memberComment(member.get().getMemberComment())
                .instagramUrl(member.get().getInstagramUrl())
                .githubUrl(member.get().getGithubUrl())
                .brunchUrl(member.get().getBrunchUrl())
                .blogUrl(member.get().getBlogUrl())
                .websiteUrl(member.get().getWebsiteUrl())
                .build();
    }

    @Override
    public ArticleFolderListDto getMyArticleFolderList(Member member) {

        

        return null;
    }

    @Override
    public ArticleFolderListDto getOtherArticleFolderList(long memberId) {
        return null;
    }
}
