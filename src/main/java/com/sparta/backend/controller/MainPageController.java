package com.sparta.backend.controller;

import com.sparta.backend.message.RestResponseMessage;
import com.sparta.backend.model.Article;
import com.sparta.backend.model.ArticleFolder;
import com.sparta.backend.model.Member;
import com.sparta.backend.repository.ArticleFolderRepository;
import com.sparta.backend.repository.ArticleRepository;
import com.sparta.backend.repository.MemberRepository;
import com.sparta.backend.responseDto.ArticleFolderListResponseDto;
import com.sparta.backend.responseDto.ArticleRandomResponseDto;
import com.sparta.backend.responseDto.RecommendedMemberResponseDto;
import com.sparta.backend.service.MainPageService;
import com.sparta.backend.utils.RandomGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MainPageController {

    private final MemberRepository memberRepository;
    private final ArticleFolderRepository articleFolderRepository;
    private final MainPageService mainPageService;

    @GetMapping("/api/mainpage")
    public ResponseEntity<RestResponseMessage> getMainPage(@AuthenticationPrincipal Member getMember) {

        List<String> hashtags = new ArrayList<>();
        hashtags.add("커리어"); hashtags.add("업무스킬"); hashtags.add("IT"); hashtags.add("디자인"); hashtags.add("마케팅");hashtags.add("투자");
        hashtags.add("장소"); hashtags.add("동기부여"); hashtags.add("인간관계"); hashtags.add("패션");hashtags.add("예술"); hashtags.add("과학");
        String randomHashtag = hashtags.get((int) (Math.random() * 11));

        // 유저
        List<RecommendedMemberResponseDto> memberList;
        // 비로그인 일 경우
        if(getMember == null) {
            // 랜덤으로 해쉬태그중 하나로 검색하기
            List<Member> members = memberRepository.findMembersByHashtag_Hashtag1(randomHashtag);
            // 멤버를 아티클 폴더의 좋아요 수의 총합으로 내림차순 나열하기
            memberList = mainPageService.getRecommendedMembers(members);
        }
        // 로그인일 경우
        else {
            // 로그인한 유저의 해쉬태그로 유저 검색
            String recommendedHashtag = getMember.getHashtag().getHashtag1();
            List<Member> members = memberRepository.findMembersByHashtag_Hashtag1(recommendedHashtag);
            // 멤버를 아티클 폴더의 좋아요 수의 총합으로 내림차순 나열하기
            memberList = mainPageService.getRecommendedMembers(members);
        }

        // 아티클 폴더
        List<ArticleFolder> articleFolders = articleFolderRepository.findAll();
        List<ArticleFolderListResponseDto> articleFolderList = new ArrayList<>();
        for (ArticleFolder articleFolder : articleFolders) {
            ArticleFolderListResponseDto articleFolderListResponseDto;
            if (articleFolder.getArticles().isEmpty()) {
                articleFolderListResponseDto = ArticleFolderListResponseDto.of(articleFolder);
            } else {
                articleFolderListResponseDto = ArticleFolderListResponseDto.of(articleFolder, articleFolder.getArticles());
            }
            articleFolderList.add(articleFolderListResponseDto);
        }

        // 아티클
        List<ArticleRandomResponseDto> articleList = mainPageService.getMonthArticles(randomHashtag);
        
        // 데이터 리턴
        Map<String, Object> map = new HashMap<>();
        map.put("memberList", memberList);
        map.put("articleFolderList", articleFolderList);
        map.put("articleList", articleList);
        map.put("hashtagButton", randomHashtag);
        
        return new ResponseEntity<>(new RestResponseMessage<>(true,"메인페이지 정보 불러오기", map), HttpStatus.OK);
    }
}
