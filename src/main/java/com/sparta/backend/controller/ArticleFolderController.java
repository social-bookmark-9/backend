//package com.sparta.backend.controller;
//
//import com.sparta.backend.message.DataMessage;
//import com.sparta.backend.message.DefaultMessage;
//import com.sparta.backend.model.Member;
//import com.sparta.backend.requestDto.CreateArticleFolderRequestDto;
//import com.sparta.backend.service.ArticleFolderService;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//
//@Slf4j
//@RestController
//@RequestMapping("/api")
//public class ArticleFolderController {
//
//    private ArticleFolderService articleFolderService;
//
//    @Autowired
//    public ArticleFolderController(ArticleFolderService articleFolderService) {
//        this.articleFolderService = articleFolderService;
//    }
//
//    /**
//     * 아티클 폴더 생성
//     * @param articleFolderRequestDto
//     * @return message
//     */
//    @ApiOperation(value = "폴더 생성", notes = "폴더 생성을 위한 API입니다.")
//    @PostMapping("/articleFolder")
//    public ResponseEntity<DefaultMessage> createArticleFolder (
//            final @Valid @RequestBody CreateArticleFolderRequestDto articleFolderRequestDto,
//            @AuthenticationPrincipal Member member) {
//        // 로그인 여부 확인 로직
//        articleFolderService.createArticleFolder(articleFolderRequestDto, member.getId());
//        return new ResponseEntity<>(new DefaultMessage("컬렉션을 생성했습니다."), HttpStatus.OK);
//    }
//
//    /**
//     * 아티클 폴더 안 모든 아티클 조회
//     * @param id
//     * @return
//     */
//    @ApiOperation(value = "폴더 상세보기", notes = "폴더 상세보기를 위한 API입니다.")
//    @GetMapping("/articleFolder/{id}")
//    public ResponseEntity<DataMessage<T>> getAllArticleInFolder(@PathVariable Long id) {
//        articleFolderService.getAllArticleInFolder(id);
//        return null;
//    }
//
//}