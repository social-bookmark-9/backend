package com.sparta.backend.serviceImpl;

import com.sparta.backend.repositorycustom.SearchQueryRepository;
import com.sparta.backend.responseDto.MainAndSearchPageArticleFolderResponseDto;
import com.sparta.backend.responseDto.SearchPageArticleResponseDto;
import com.sparta.backend.service.SearchPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SearchPageServiceImpl implements SearchPageService {

    private final SearchQueryRepository searchQueryRepository;

    // convert String
    private static String convertString(String str, int n) {
        if (str == null || str.isEmpty()) return null;

        StringTokenizer stringTokenizer = new StringTokenizer(str.trim(), " ");
        List<String> strArr = new ArrayList<>();

        while (stringTokenizer.hasMoreTokens()) {
            String token = stringTokenizer.nextToken();
            for (int i = 0; i < token.length(); i++) {
                if (i + n > token.length()) {
                    break;
                }
                strArr.add(token.substring(i, i + n));
            }
        }

        String strVal = strArr.get(0);
        String newStrVal = "+" + strVal;
        strArr.set(0, newStrVal);

        return String.join(" +", strArr);
    }


    @Override
    public Slice<MainAndSearchPageArticleFolderResponseDto> searchArticleFolders(String hashtag, String keyword, Pageable pageable) {
        String convertedKeyword = convertString(keyword, 2);
        Slice<MainAndSearchPageArticleFolderResponseDto> articleFolders = searchQueryRepository.searchOnlyArticleFolders(hashtag, convertedKeyword, pageable);

        return articleFolders;
    }

    @Override
    public Slice<SearchPageArticleResponseDto> searchArticles(String hashtag, String keyword, Pageable pageable) {
        String convertedKeyword = convertString(keyword, 2);
        Slice<SearchPageArticleResponseDto> articles = searchQueryRepository.searchOnlyArticles(hashtag, convertedKeyword, pageable);

        return articles;
    }
}
