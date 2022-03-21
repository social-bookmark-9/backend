package com.sparta.backend.controller;

import com.sparta.backend.amazons3.AmazonS3Uploader;
import com.sparta.backend.exception.BusinessException;
import com.sparta.backend.exception.ErrorCode;
import com.sparta.backend.message.RestResponseMessage;
import com.sparta.backend.model.Hashtag;
import com.sparta.backend.model.Member;
import com.sparta.backend.repository.HashtagRepository;
import com.sparta.backend.repository.MemberRepository;
import com.sparta.backend.requestDto.MemberInfoEditRequestDto;
import com.sparta.backend.responseDto.MemberLoginResponseDto;
import com.sparta.backend.service.MemberInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MemberInfoController {

    private final MemberInfoService memberInfoService;
    private final AmazonS3Uploader amazonS3Uploader;
    private final MemberRepository memberRepository;
    private final HashtagRepository hashtagRepository;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;
    
    // 유저 정보 전달하기
    @GetMapping("/api/users/check")
    public ResponseEntity<RestResponseMessage> getMyInfo(@AuthenticationPrincipal Member member) {
        MemberLoginResponseDto myInfo = MemberLoginResponseDto.builder()
                .memberId(member.getId())
                .nickName(member.getMemberName())
                .email(member.getEmail())
                .profileImageUrl(member.getProfileImage())
                .userDesc(member.getMemberComment())
                .instagramUrl(member.getInstagramUrl())
                .githubUrl(member.getGithubUrl())
                .brunchUrl(member.getBrunchUrl())
                .blogUrl(member.getBlogUrl())
                .websiteUrl(member.getWebsiteUrl())
                .build();
        Map<String, Object> map = new HashMap<>();
        map.put("myInfo", myInfo);
        return new ResponseEntity<>(new RestResponseMessage<>(true,"유저 정보", map), HttpStatus.OK);
    }

    // 유저 프로필 이미지 수정하기
    @PostMapping("/api/mypage/profileimage")
    public ResponseEntity<RestResponseMessage> editProfileImage(@RequestParam("image") MultipartFile multipartFile, @AuthenticationPrincipal Member member) throws IOException {

        Member editMember = memberRepository.findById(member.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));

        // 만약 S3에 올려져 있는 파일이라면 삭제 후 수정.
        if(editMember.getProfileImage().contains("bubbled-profile.s3.ap-northeast-2.amazonaws.com/bubbled-profile-image/")) {
            amazonS3Uploader.deleteFile(editMember.getProfileImage());
        }
        // 이미지 업로드 하기.
        String imageUrl = amazonS3Uploader.upload(multipartFile, "bubbled-profile-image");
        memberInfoService.editProfileImageUrl(imageUrl, editMember);

        return new ResponseEntity<>(new RestResponseMessage<>(true, "프로필 이미지가 업데이트 되었습니다.", imageUrl), HttpStatus.OK);
    }

    // 유저 프로필 URL 수정하기
    @PatchMapping("/api/mypage/snsurl")
    public ResponseEntity<RestResponseMessage> editProfileSnsUrl(@RequestBody MemberInfoEditRequestDto memberInfoEditRequestDto, @AuthenticationPrincipal Member member) {

        Member editMember = memberRepository.findById(member.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));

        memberInfoService.editProfileSnsUrl(memberInfoEditRequestDto, editMember);

        return new ResponseEntity<>(new RestResponseMessage<>(true, "프로필 URL이 업데이트 되었습니다.", memberInfoEditRequestDto), HttpStatus.OK);
    }

    // 유저 프로필 자기소개 수정하기
    @PatchMapping("/api/mypage/statusmessage")
    public ResponseEntity<RestResponseMessage> editStatusMessage(@RequestBody MemberInfoEditRequestDto memberInfoEditRequestDto, @AuthenticationPrincipal Member member) {

        Member editMember = memberRepository.findById(member.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));

        memberInfoService.editProfileStatusMessage(memberInfoEditRequestDto, editMember);

        return new ResponseEntity<>(new RestResponseMessage<>(true, "프로필 자기소개가 업데이트 되었습니다.", ""), HttpStatus.OK);
    }

    // 유저 프로필 닉네임 수정하기
    @PatchMapping("/api/mypage/nickname")
    public ResponseEntity<RestResponseMessage> editMemberName(@RequestBody MemberInfoEditRequestDto memberInfoEditRequestDto, @AuthenticationPrincipal Member member) {

        Member editMember = memberRepository.findById(member.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));

        memberInfoService.editProfileMemberName(memberInfoEditRequestDto.getNickname(), editMember);

        return new ResponseEntity<>(new RestResponseMessage<>(true, "프로필 닉네임이 업데이트 되었습니다.", ""), HttpStatus.OK);
    }

    // 유저 프로필 관심분야 수정하기
    @PatchMapping("/api/mypage/hashtag")
    public ResponseEntity<RestResponseMessage> editHashtag(@RequestBody MemberInfoEditRequestDto memberInfoEditRequestDto, @AuthenticationPrincipal Member member) {

        Hashtag editHashtag = hashtagRepository.findById(member.getHashtag().getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));

        memberInfoService.editHashtag(memberInfoEditRequestDto, editHashtag);

        return new ResponseEntity<>(new RestResponseMessage<>(true, "프로필 관심분야가 업데이트 되었습니다.", memberInfoEditRequestDto), HttpStatus.OK);
    }
}
