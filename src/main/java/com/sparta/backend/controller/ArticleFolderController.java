package com.sparta.backend.controller;

import com.sparta.backend.message.DataListMessage;
import com.sparta.backend.message.DefaultMessage;
import com.sparta.backend.model.Member;
import com.sparta.backend.requestDto.ArticleFolderCreateRequestDto;
import com.sparta.backend.requestDto.ArticleFolderNameUpdateRequestDto;
import com.sparta.backend.responseDto.ArticlesInFolderResponseDto;
import com.sparta.backend.service.ArticleFolderService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ArticleFolderController {

    private final ArticleFolderService articleFolderService;

    /**
     * 아티클 폴더 생성
     * @param articleFolderRequestDto
     * @return message
     */
    @ApiOperation(value = "아티클 폴더 생성", notes = "폴더 생성 API")
    @PostMapping("/articleFolder")
    public ResponseEntity<DefaultMessage> createArticleFolder (
            final @Valid @RequestBody ArticleFolderCreateRequestDto articleFolderRequestDto,
            @AuthenticationPrincipal Member member) {
        // 로그인 여부 확인 로직
        articleFolderService.createArticleFolder(articleFolderRequestDto, member);
        return new ResponseEntity<>(new DefaultMessage("컬렉션을 생성 완료."), HttpStatus.OK);
    }

    /**
     * 아티클 폴더 삭제
     * @param member
     * @param id
     * @return
     */
    @ApiOperation(value = "아티클 폴더 삭제", notes = "아티클 폴더 삭제 API")
    @DeleteMapping("/articleFolder/{id}")
    public ResponseEntity<DefaultMessage> deleteArticleFolder (
            @AuthenticationPrincipal Member member,
            @PathVariable Long id) {
        // 로그인 여부 확인 로직
        articleFolderService.deleteArticleFolder(id);
        return new ResponseEntity<>(new DefaultMessage("아티클 폴더 삭제 완료"), HttpStatus.OK);
    }

    /**
     * 아티클 폴더 제목 수정
     * @param member
     * @param id
     * @param articleFolderNameUpdateRequestDto
     * @return
     */
    @ApiOperation(value = "아티클 폴더 제목 수정", notes = "아티클 폴더 제목 수정 API")
    @PatchMapping("/articleFolder/{id}")
    public ResponseEntity<DefaultMessage> updateArticleFolderName(
            @AuthenticationPrincipal Member member,
            @PathVariable Long id,
            @RequestBody ArticleFolderNameUpdateRequestDto articleFolderNameUpdateRequestDto) {
        //로그인 여부 확인 로직
        articleFolderService.updateArticleFolderName(articleFolderNameUpdateRequestDto, id);
        return new ResponseEntity<>(new DefaultMessage("아티클 폴더 수정 완료"), HttpStatus.OK);
    }

    /**
     * 아티클 폴더 안 모든 아티클 조회
     * @param id
     * @return List<ArticlesInFolderRespDto>
     */
    @ApiOperation(value = "아티클 폴더 안 모든 아티클 조회", notes = "아티클 폴더 안 모든 아티클 조회 API")
    @GetMapping("/articleFolder/{id}")
    public ResponseEntity<DataListMessage<ArticlesInFolderResponseDto>> findArticlesInFolder(@PathVariable Long id) {
        List<ArticlesInFolderResponseDto> articlesInFolderResponseDtoList = articleFolderService.findArticlesInFolder(id);

        return new ResponseEntity<>(new DataListMessage<>("폴더 안 아티클 조회", articlesInFolderResponseDtoList), HttpStatus.OK);

    }

}
