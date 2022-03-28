package com.sparta.backend.amazons3;

import com.sparta.backend.requestDto.MemberInfoEditRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class AmazonS3Controller {

    private final AmazonS3Uploader amazonS3Uploader;

    @PostMapping("/uploadtest")
    public String upload(@RequestParam("image")MultipartFile multipartFile)throws IOException{
        String imageUrl = amazonS3Uploader.upload(multipartFile, "bubbled-profile-image");
        return "업로드 성공" + " / 이미지 주소 : " + imageUrl;
    }

    @DeleteMapping("/deletetest")
    public String delete(@RequestBody MemberInfoEditRequestDto memberInfoEditRequestDto) {
        amazonS3Uploader.deleteFile(memberInfoEditRequestDto.getProfileImageUrl());
        return "삭제 성공";
    }
}
