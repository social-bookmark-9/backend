package com.sparta.backend.controller;

import com.sparta.backend.message.RestResponseMessage;
import com.sparta.backend.model.Member;
import com.sparta.backend.requestDto.ArticleFolderCreateRequestDto;
import com.sparta.backend.requestDto.ArticleFolderNameUpdateRequestDto;
import com.sparta.backend.responseDto.ArticlesInFolderResponseDto;
import com.sparta.backend.responseDto.LikeAddOrRemoveResponseDto;
import com.sparta.backend.service.ArticleFolderService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ArticleFolderController {

    private final ArticleFolderService articleFolderService;

    private void checkAuth(Member member) {
        if (member == null) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * 아티클 폴더 생성
     *
     * @param articleFolderRequestDto
     * @return message
     */
    @ApiOperation(value = "아티클 폴더 생성", notes = "폴더 생성 API")
    @PostMapping("/articleFolder")
    public ResponseEntity<RestResponseMessage<?>> createArticleFolder(
            final @Valid @RequestBody ArticleFolderCreateRequestDto articleFolderRequestDto,
            @AuthenticationPrincipal Member member) {
//        checkAuth(member);
        articleFolderService.createArticleFolder(articleFolderRequestDto, member);
        return new ResponseEntity<>(new RestResponseMessage<>(true,"컬렉션을 생성 완료.", null), HttpStatus.OK);
    }

    /**
     * 아티클 폴더 삭제
     *
     * @param member
     * @param id
     * @return message
     */
    @ApiOperation(value = "아티클 폴더 삭제", notes = "아티클 폴더 삭제 API")
    @DeleteMapping("/articleFolder/{id}")
    public ResponseEntity<RestResponseMessage<?>> deleteArticleFolder(
            @AuthenticationPrincipal Member member,
            @PathVariable long id) {
//        checkAuth(member);
        articleFolderService.deleteArticleFolder(id);
        return new ResponseEntity<>(new RestResponseMessage<>(true,"아티클 폴더 삭제 완료", ""), HttpStatus.OK);
    }

    /**
     * 아티클 폴더 제목 수정
     *
     * @param member
     * @param id
     * @param articleFolderNameUpdateRequestDto
     * @return
     */
    @ApiOperation(value = "아티클 폴더 제목 수정", notes = "아티클 폴더 제목 수정 API")
    @PatchMapping("/articleFolder/{id}")
    public ResponseEntity<RestResponseMessage<?>> updateArticleFolderName(
            @AuthenticationPrincipal Member member,
            @PathVariable long id,
            @RequestBody ArticleFolderNameUpdateRequestDto articleFolderNameUpdateRequestDto) {
//        checkAuth(member);
        articleFolderService.updateArticleFolderName(articleFolderNameUpdateRequestDto, id);
        return new ResponseEntity<>(new RestResponseMessage<>(true, "아티클 제목 수정 완료", ""), HttpStatus.OK);
    }

    /**
     * 아티클 폴더 안 모든 아티클 조회
     *
     * @param id
     * @return List<ArticlesInFolderRespDto>
     */
    @ApiOperation(value = "아티클 폴더 안 모든 아티클 조회", notes = "아티클 폴더 안 모든 아티클 조회 API")
    @GetMapping("/articleFolders/{id}")
    public ResponseEntity<RestResponseMessage<Map<String, List<ArticlesInFolderResponseDto>>>> findArticlesInFolder(
            @AuthenticationPrincipal Member member,
            @PathVariable long id) {
        List<ArticlesInFolderResponseDto> articlesInFolderResponseDtoList
                = articleFolderService.findArticlesInFolder(member, id);

        Map<String, List<ArticlesInFolderResponseDto>> dataMap = new HashMap<>();
        dataMap.put("articles", articlesInFolderResponseDtoList);

        return new ResponseEntity<>(new RestResponseMessage<>(true,"폴더 안 아티클 조회", dataMap), HttpStatus.OK);
    }

    /**
     * 폴더 안 아티클 삭제
     *
     * @param member
     * @param folderId
     * @param articleId
     * @return message
     */
    @ApiOperation(value = "폴더 안 아티클 삭제", notes = "폴더 안 아티클 삭제 API")
    @DeleteMapping("/articleFolder/{folderId}/articles{articleId}")
    public ResponseEntity<RestResponseMessage<?>> deleteArticleInArticleFolder(
            @AuthenticationPrincipal Member member,
            @PathVariable Long folderId,
            @PathVariable long articleId) {
//        checkAuth(member);
        articleFolderService.deleteArticleInArticleFolder(folderId, articleId);
        return new ResponseEntity<>(new RestResponseMessage<>(true, "북마크를 삭제했습니다.", ""), HttpStatus.OK);
    }

    @ApiOperation(value = "좋아요 추가, 삭제", notes = "좋아요 추가, 삭제 API")
    @PatchMapping("/articleFolders/{folderId}/likes")
    public ResponseEntity<RestResponseMessage<LikeAddOrRemoveResponseDto>> likeAddOrRemove(
            @AuthenticationPrincipal Member member,
            @PathVariable long folderId) {

        LikeAddOrRemoveResponseDto likeAddOrRemoveResponseDto = articleFolderService.likeAddOrRemove(member, folderId);
        return new ResponseEntity<>(new RestResponseMessage<>(true, "좋아요 추가 또는 삭제", likeAddOrRemoveResponseDto), HttpStatus.OK);
    }

}