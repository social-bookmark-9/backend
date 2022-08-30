package com.sparta.backend.serviceImpl;

import com.sparta.backend.model.Member;
import com.sparta.backend.responseDto.ArticleRandomResponseDto;
import com.sparta.backend.responseDto.MainAndSearchPageArticleFolderResponseDto;
import com.sparta.backend.responseDto.MemberHashtagInfoDto;
import com.sparta.backend.responseDto.RecommendedMemberResponseDto;
import com.sparta.backend.repository.ArticleFolderRepository;
import com.sparta.backend.repository.ArticleRepository;
import com.sparta.backend.repository.MemberRepository;
import com.sparta.backend.service.MainPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MainPageServiceImpl implements MainPageService {

    private final ArticleRepository articleRepository;
    private final ArticleFolderRepository articleFolderRepository;
    private final MemberRepository memberRepository;

    private <T> List<T> selectRandom(List<T> folderList) {
        int folderListSize = folderList.size();
        int returnElementSize = 9;

        if (folderListSize < returnElementSize) {
            return folderList;
        } else {
            Collections.shuffle(folderList);
            return folderList.subList(0, returnElementSize);
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
    public List<RecommendedMemberResponseDto> getRecommendedMembersLogin(Member member, MemberHashtagInfoDto memberHashtagInfoDto) {
        List<String> hashtagList = transformToHashtagList(memberHashtagInfoDto);
        List<RecommendedMemberResponseDto> recommendedMemberResponseDtoList = memberRepository.mainPageMemberLogin(member.getId(), hashtagList);
        return selectRandom(recommendedMemberResponseDtoList);
    }

    @Override
    public List<MainAndSearchPageArticleFolderResponseDto> getRecommendedArticleFoldersLogin(Member member, MemberHashtagInfoDto memberHashtagInfoDto) {
        List<String> hashtagList = transformToHashtagList(memberHashtagInfoDto);
        List<MainAndSearchPageArticleFolderResponseDto> mainAndSearchPageArticleFolderResponseDtoList = articleFolderRepository.mainPageArticleFolderLogin(member.getId(), hashtagList);
        return selectRandom(mainAndSearchPageArticleFolderResponseDtoList);
    }

    @Override
    public List<ArticleRandomResponseDto> getMonthArticlesLogin(Member member, MemberHashtagInfoDto memberHashtagInfoDto) {
        LocalDateTime startDatetime = LocalDateTime.of(LocalDate.now().minusDays(30), LocalTime.of(0,0,0));
        LocalDateTime endDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59));

        List<String> hashtagList = transformToHashtagList(memberHashtagInfoDto);
        List<ArticleRandomResponseDto> articleRandomResponseDtoList = articleRepository.mainPageArticleLogin(member.getId(), hashtagList, startDatetime, endDatetime);
        return selectRandom(articleRandomResponseDtoList);
    }

    @Override
    public List<RecommendedMemberResponseDto> getRecommendedMembersNonLogin(String hashtag) {
        List<RecommendedMemberResponseDto> recommendedMemberResponseDtoList = memberRepository.mainPageMemberNonLogin(hashtag);
        return selectRandom(recommendedMemberResponseDtoList);
    }

    @Override
    public List<MainAndSearchPageArticleFolderResponseDto> getRecommendedArticleFoldersNonLogin(String hashtag) {
        List<MainAndSearchPageArticleFolderResponseDto> folderResponseList = articleFolderRepository.mainPageArticleFolderNonLogin(hashtag);
        return selectRandom(folderResponseList);
    }

    @Override
    public List<ArticleRandomResponseDto> getMonthArticlesNonLogin() {
        LocalDateTime startDatetime = LocalDateTime.of(LocalDate.now().minusDays(30), LocalTime.of(0,0,0));
        LocalDateTime endDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59));

        List<ArticleRandomResponseDto> articleRandomResponseDtoList = articleRepository.mainPageArticleNonLogin(startDatetime, endDatetime);
        return selectRandom(articleRandomResponseDtoList);
    }


}
