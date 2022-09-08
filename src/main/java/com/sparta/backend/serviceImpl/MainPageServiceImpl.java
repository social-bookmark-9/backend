package com.sparta.backend.serviceImpl;

import com.sparta.backend.model.Member;
import com.sparta.backend.responseDto.*;
import com.sparta.backend.repository.ArticleFolderRepository;
import com.sparta.backend.repository.ArticleRepository;
import com.sparta.backend.repository.MemberRepository;
import com.sparta.backend.service.MainPageService;
import com.sparta.backend.utils.RandomGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MainPageServiceImpl implements MainPageService {

    private final ArticleRepository articleRepository;
    private final ArticleFolderRepository articleFolderRepository;
    private final MemberRepository memberRepository;

    private <T> List<T> selectRandom(List<T> dataList) {
        int folderListSize = dataList.size();
        int returnElementSize = 9;

        if (dataList.isEmpty()) {
            return new ArrayList<>();
        }

        if (folderListSize < returnElementSize) {
            return dataList;
        } else {
            Collections.shuffle(dataList);
            return dataList.subList(0, returnElementSize);
        }
    }

    private List<String> transformToHashtagList(MemberHashtagInfoDto memberHashtagInfoDto) {
        List<String> hashtagList = new ArrayList<>();

        if (memberHashtagInfoDto.getMemberHashtag1() != null) {
            hashtagList.add(memberHashtagInfoDto.getMemberHashtag1());
        }
        if (memberHashtagInfoDto.getMemberHashtag2() != null) {
            hashtagList.add(memberHashtagInfoDto.getMemberHashtag2());
        }
        if (memberHashtagInfoDto.getMemberHashtag3() != null) {
            hashtagList.add(memberHashtagInfoDto.getMemberHashtag3());
        }
        if (memberHashtagInfoDto.getFolderHashtag1() != null) {
            hashtagList.add(memberHashtagInfoDto.getFolderHashtag1());
        }
        if (memberHashtagInfoDto.getFolderHashtag2() != null) {
            hashtagList.add(memberHashtagInfoDto.getFolderHashtag2());
        }
        if (memberHashtagInfoDto.getFolderHashtag3() != null) {
            hashtagList.add(memberHashtagInfoDto.getFolderHashtag3());
        }

        return hashtagList;
    }

    @Override
    public MainPageResponseDto mainPageLoginVer(Member member) {
//        LocalDateTime startDatetime = LocalDateTime.of(LocalDate.now().minusDays(30), LocalTime.of(0,0,0));
//        LocalDateTime endDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59));

        // 임시
        String start = "2022-08-01 00:00:00.000";
        String end = "2022-09-01 00:00:00.000";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        LocalDateTime startDateTime = LocalDateTime.parse(start, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(end, formatter);

        List<String> memberHashtagList = new ArrayList<>();
        MemberHashtagInfoDto memberHashtagInfoDto = memberRepository.memberHashtagInfo(member.getId());

        if (memberHashtagInfoDto != null) {
            memberHashtagList = transformToHashtagList(memberHashtagInfoDto);
        }

        List<RecommendedMemberResponseDto> recommendedMemberList = memberRepository.mainPageMemberLogin(member.getId(), memberHashtagList);

        List<MainAndSearchPageArticleFolderResponseDto> recommendedArticleFolderList = articleFolderRepository.mainPageArticleFolderLogin(member.getId(), memberHashtagList);

        List<ArticleRandomResponseDto> recommendedArticleList = articleRepository.mainPageArticleLogin(member.getId(), memberHashtagList, startDateTime, endDateTime);

        List<RecommendedMemberResponseDto> memberResponseDtoList = selectRandom(recommendedMemberList);
        List<MainAndSearchPageArticleFolderResponseDto> articleFolderResponseDtoList = selectRandom(recommendedArticleFolderList);
        List<ArticleRandomResponseDto> articleResponseDtoList = selectRandom(recommendedArticleList);

        return MainPageResponseDto.of(memberResponseDtoList, articleFolderResponseDtoList, articleResponseDtoList, memberHashtagList);
    }

    @Override
    public MainPageResponseDto mainPageNonLoginVer() {
//        LocalDateTime startDatetime = LocalDateTime.of(LocalDate.now().minusDays(30), LocalTime.of(0,0,0));
//        LocalDateTime endDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59));
        String randomHashtag = String.valueOf(RandomGenerator.RandomHashtag.getRandomHashtag());
        // 임시
        String start = "2022-08-01 00:00:00.000";
        String end = "2022-09-01 00:00:00.000";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        LocalDateTime startDateTime = LocalDateTime.parse(start, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(end, formatter);

        List<RecommendedMemberResponseDto> recommendedMemberResponseList = memberRepository.mainPageMemberNonLogin(randomHashtag);

        List<MainAndSearchPageArticleFolderResponseDto> recommendedArticleFolderList = articleFolderRepository.mainPageArticleFolderNonLogin(randomHashtag);

        List<ArticleRandomResponseDto> recommendedArticleList = articleRepository.mainPageArticleNonLogin(startDateTime, endDateTime);

        List<RecommendedMemberResponseDto> memberResponseDtoList = selectRandom(recommendedMemberResponseList);
        List<MainAndSearchPageArticleFolderResponseDto> articleFolderResponseDtoList = selectRandom(recommendedArticleFolderList);
        List<ArticleRandomResponseDto> articleResponseDtoList = selectRandom(recommendedArticleList);

        return MainPageResponseDto.of(memberResponseDtoList, articleFolderResponseDtoList, articleResponseDtoList, Collections.singletonList(randomHashtag));
    }
}
