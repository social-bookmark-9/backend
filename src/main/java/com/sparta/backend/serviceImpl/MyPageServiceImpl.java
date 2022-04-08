package com.sparta.backend.serviceImpl;

import com.sparta.backend.exception.EntityNotFoundException;
import com.sparta.backend.model.ArticleFolder;
import com.sparta.backend.model.Member;
import com.sparta.backend.repository.FavoriteRepository;
import com.sparta.backend.repository.MemberRepository;
import com.sparta.backend.responseDto.ArticleFolderListResponseDto;
import com.sparta.backend.responseDto.MemberInfoResponseDto;
import com.sparta.backend.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService {

    private final MemberRepository memberRepository;
    private final FavoriteRepository favoriteRepository;

    /**
     * 내 마이페이지용 사용자 정보 조회
     * @param member
     * @return MemberInfoDto
     */
    @Override
    public MemberInfoResponseDto getMyMemberInfo(Member member) {
        return MemberInfoResponseDto.of(member);
    }

    /**
     * 다른 사람의 마이페이지용 사용자 정보 조회
     * @param otherMemberId
     * @return MemberInfoDto
     */
    @Override
    public MemberInfoResponseDto getOtherMemberInfo(Long otherMemberId) {
        Member findMember = getMember(otherMemberId);

        return MemberInfoResponseDto.of(findMember);
    }

    /**
     * 내 마이페이지용 아티클 폴더 조회
     * @param member
     * @return List<ArticleFolderListDto>
     */
    @Override
    public List<ArticleFolderListResponseDto> getMyArticleFolderList(Member member) {
        Member findMember = getMember(member.getId());
        List<ArticleFolder> myArticleFolders = findMember.getArticleFolders();

        return getMyArticleFolderListDtoList(myArticleFolders);
    }

    /**
     * 다른 사람의 마이페이지용 아티클 폴더 조회
     * @param loginMember, otherMemberId
     * @return List<ArticleFolderListDto>
     */
    @Override
    public List<ArticleFolderListResponseDto> getOtherArticleFolderList(Member loginMember, Long otherMemberId) {
        Member otherMember = getMember(otherMemberId);
        List<ArticleFolder> otherArticleFolders = otherMember.getArticleFolders();

        return getOtherArticleFolderListDtoList(loginMember, otherArticleFolders);
    }

    private List<ArticleFolderListResponseDto> getMyArticleFolderListDtoList(List<ArticleFolder> myArticleFolders) {
        List<ArticleFolderListResponseDto> articleFolderListDtoListResponse = new ArrayList<>();

        for (ArticleFolder otherArticleFolder : myArticleFolders) {
            if (CollectionUtils.isEmpty(otherArticleFolder.getArticles())) {
                ArticleFolderListResponseDto noArticlesInFolder = ArticleFolderListResponseDto.of(otherArticleFolder);
                articleFolderListDtoListResponse.add(noArticlesInFolder);
            } else {
                ArticleFolderListResponseDto articlesInFolder = ArticleFolderListResponseDto.of(otherArticleFolder, otherArticleFolder.getArticles());
                articleFolderListDtoListResponse.add(articlesInFolder);
            }
        }

        return articleFolderListDtoListResponse;
    }

    private List<ArticleFolderListResponseDto> getOtherArticleFolderListDtoList(Member loginMember, List<ArticleFolder> otherArticleFolders) {
        List<ArticleFolderListResponseDto> articleFolderListDtoListResponse = new ArrayList<>();
        List<Long> memberFavoriteFolderIdList = favoriteRepository.memberFavoriteFolderList(loginMember.getId());

        for (ArticleFolder otherArticleFolder : otherArticleFolders) {
            Boolean likeStatus = memberFavoriteFolderIdList.contains(otherArticleFolder.getId());
            if (CollectionUtils.isEmpty(otherArticleFolder.getArticles())) {
                ArticleFolderListResponseDto noArticlesInFolder = ArticleFolderListResponseDto.of(otherArticleFolder, likeStatus);
                articleFolderListDtoListResponse.add(noArticlesInFolder);
            } else {
                ArticleFolderListResponseDto articlesInFolder = ArticleFolderListResponseDto.of(otherArticleFolder, otherArticleFolder.getArticles(), likeStatus);
                articleFolderListDtoListResponse.add(articlesInFolder);
            }
        }

        return articleFolderListDtoListResponse;
    }

    /**
     * Member 조회
     * @param id
     */
    private Member getMember(Long id) {
        Optional<Member> member = memberRepository.findById(id);
        if (member.isPresent()) {
            return member.get();
        } else throw new EntityNotFoundException("존재하지 않는 회원");
    }
}
