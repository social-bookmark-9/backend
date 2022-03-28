package com.sparta.backend.controller;

import com.sparta.backend.message.RestResponseMessage;
import com.sparta.backend.model.Member;
import com.sparta.backend.requestDto.ArticleFolderCreateRequestDto;
import com.sparta.backend.requestDto.ArticleFolderNameUpdateRequestDto;
import com.sparta.backend.responseDto.ArticleFolderNameAndIdResponseDto;
import com.sparta.backend.responseDto.ArticlesInFolderResponseDto;
import com.sparta.backend.responseDto.LikeAddOrRemoveResponseDto;
import com.sparta.backend.service.ArticleFolderService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
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

    private void checkAuth(Member member) {
        if (member == null)  throw new AccessDeniedException("로그인이 필요합니다.");
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

        checkAuth(member);

        articleFolderService.createArticleFolder(articleFolderRequestDto, member);
        return new ResponseEntity<>(new RestResponseMessage<>(true, "컬렉션을 생성 완료.", ""), HttpStatus.OK);
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
            @PathVariable Long id) {

        checkAuth(member);

        articleFolderService.deleteArticleFolder(member, id);
        return new ResponseEntity<>(new RestResponseMessage<>(true, "아티클 폴더 삭제 완료", ""), HttpStatus.OK);
    }

    /**
     * 아티클 폴더 타이틀 목록 조회
     * @param member
     * @return List<ArticleFolderNameAndIdResponseDto>
     */
    @ApiOperation(value = "아티클 폴더 타이틀 목록 조회", notes = "아티클 폴더 타이틀 목록 조회 API")
    @GetMapping("/articleFolders/folderName")
    public ResponseEntity<RestResponseMessage<List<ArticleFolderNameAndIdResponseDto>>> getArticleFoldersName(
            @AuthenticationPrincipal Member member) {

        checkAuth(member);

        List<ArticleFolderNameAndIdResponseDto> articleFolderNameAndIdResponseDtoList = articleFolderService.getArticleFoldersName(member);
        return new ResponseEntity<>(new RestResponseMessage<>(true, "아티클 폴더 타이틀 목록 조회", articleFolderNameAndIdResponseDtoList), HttpStatus.OK);
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
            @PathVariable Long id,
            @RequestBody ArticleFolderNameUpdateRequestDto articleFolderNameUpdateRequestDto) {

        checkAuth(member);

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
    public ResponseEntity<RestResponseMessage<ArticlesInFolderResponseDto>> findArticlesInFolder(
            @AuthenticationPrincipal Member member,
            @PathVariable Long id) {

        boolean loginStatus = member != null;

        ArticlesInFolderResponseDto articlesInFolder =
                loginStatus ? articleFolderService.findArticlesInFolderLoginTrue(member, id) : articleFolderService.findArticlesInFolderLoginFalse(id);

        return new ResponseEntity<>(new RestResponseMessage<>(true, "폴더 안 아티클 조회", articlesInFolder), HttpStatus.OK);
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
            @PathVariable Long articleId) {

        checkAuth(member);

        articleFolderService.deleteArticleInArticleFolder(folderId, articleId);
        return new ResponseEntity<>(new RestResponseMessage<>(true, "북마크를 삭제했습니다.", ""), HttpStatus.OK);
    }

    /**
     * 좋아요 추가, 삭제
     * @param member
     * @param folderId
     * @return LikeAddOrRemoveResponseDto
     */
    @ApiOperation(value = "좋아요 추가, 삭제", notes = "좋아요 추가, 삭제 API")
    @PatchMapping("/articleFolders/{folderId}/likes")
    public ResponseEntity<RestResponseMessage<LikeAddOrRemoveResponseDto>> likeAddOrRemove(
            @AuthenticationPrincipal Member member,
            @PathVariable Long folderId) {

        checkAuth(member);

        LikeAddOrRemoveResponseDto likeAddOrRemoveResponseDto = articleFolderService.likeAddOrRemove(member, folderId);
        return new ResponseEntity<>(new RestResponseMessage<>(true, "좋아요 추가 또는 삭제", likeAddOrRemoveResponseDto), HttpStatus.OK);
    }


}