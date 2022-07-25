package com.sparta.backend.serviceImpl;

import com.sparta.backend.exception.EntityNotFoundException;
import com.sparta.backend.exception.ErrorCode;
import com.sparta.backend.exception.InvalidValueException;
import com.sparta.backend.model.Article;
import com.sparta.backend.model.ArticleFolder;
import com.sparta.backend.model.Favorite;
import com.sparta.backend.model.Member;
import com.sparta.backend.repository.ArticleFolderRepository;
import com.sparta.backend.repository.ArticleRepository;
import com.sparta.backend.repository.FavoriteRepository;
import com.sparta.backend.repository.MemberRepository;
import com.sparta.backend.requestDto.ArticleFolderCreateRequestDto;
import com.sparta.backend.requestDto.ArticleFolderNameUpdateRequestDto;
import com.sparta.backend.responseDto.ArticleFolderNameAndIdResponseDto;
import com.sparta.backend.responseDto.ArticlesInFolderResponseDto;
import com.sparta.backend.responseDto.ArticlesInfoInFolderResponseDto;
import com.sparta.backend.responseDto.LikeAddOrRemoveResponseDto;
import com.sparta.backend.service.ArticleFolderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ArticleFolderServiceImpl implements ArticleFolderService {

    private final MemberRepository memberRepository;
    private final ArticleFolderRepository articleFolderRepository;
    private final ArticleRepository articleRepository;
    private final FavoriteRepository favoriteRepository;

    /**
     * 아티클 폴더 생성
     * @param articleFolderRequestDto
     * @param member
     */
    @Override
    public void createArticleFolder(ArticleFolderCreateRequestDto articleFolderRequestDto, Member member) {
        // TODO: 한 유저가 1개 이상의 동일한 아티클 폴더 이름을 생성할 수 없음 (현우)
        Member currentMember = getMember(member.getId());

        ArticleFolder savedArticleFolder = articleFolderRepository
                .findArticleFolderByArticleFolderNameAndMember(articleFolderRequestDto.getArticleFolderName(), currentMember);

        if (savedArticleFolder == null) {
            ArticleFolder articleFolder = ArticleFolder.builder()
                    .articleFolderName(articleFolderRequestDto.getArticleFolderName())
                    .deleteable(true)
                    .folderHide(articleFolderRequestDto.isFolderHide())
                    .likeCount(0)
                    .member(currentMember)
                    .build();
            articleFolderRepository.save(articleFolder);
        } else throw new InvalidValueException(ErrorCode.DUPLICATED_VALUE);
    }

    /**
     * 아티클 폴더 삭제
     * @param folderId
     * @return void
     */
    @Override
    public void deleteArticleFolder(Member member, Long folderId) {
        Member findMember = getMember(member.getId());
        ArticleFolder findFolder = getFolder(folderId);
        if (Objects.equals(findFolder.getMember().getId(), findMember.getId())) {
            int currentLikeCount = findFolder.getLikeCount();
            if (currentLikeCount != 0) {
                findMember.decreaseTotalLikeCount_size(currentLikeCount);
            }
            articleFolderRepository.delete(findFolder);
        } else throw new AccessDeniedException("접근 권한 없음");
    }

    /**
     * 아티클 폴더 제목 수정
     * @param articleFolderNameUpdateRequestDto
     * @param folderId
     * @return void
     */
    @Override
    public void updateArticleFolderName(ArticleFolderNameUpdateRequestDto articleFolderNameUpdateRequestDto, Long folderId) {
        String articleFolderName = articleFolderNameUpdateRequestDto.getArticleFolderName();

        ArticleFolder folder = getFolder(folderId);
        articleFolderRepository.updateArticleFolderName(articleFolderName, folder.getId());
    }

    /**
     * 폴더 안 아티클 조회
     * @param member, id
     * @return List<ArticlesInFolderResponseDto>
     */
    @Override
    public ArticlesInFolderResponseDto findArticlesInFolderLogin(Member member, Long folderId) {
        // 타켓 아티클 폴더 찾기
        Optional<ArticleFolder> findArticleFolder = Optional.of(getFolder(folderId));
        // 타켓 아티클 폴더 안 모든 아티클 articles에 저장
        List<Article> articles = new ArrayList<>();
        findArticleFolder
                .map(ArticleFolder::getArticles)
                .ifPresent(articles::addAll);

        // member의 폴더 리스트에서 아티클 url로 같은 아티클을 가지고 있는지 판별하기 위해 아티클의 url만 리스트로 저장
        Member findMember = getMember(member.getId());

        List<String> myArticlesUrl = new ArrayList<>();
        findMember.getArticleFolders()
                .stream()
                .map(ArticleFolder::getArticles)
                .forEach(myArticleList -> myArticleList
                        .forEach(myArticle -> myArticlesUrl.add(myArticle.getUrl())));

        // 해당 폴더 좋아요 상태
        List<Long> memberFavoriteFolderIdList = favoriteRepository.memberFavoriteFolderList(member.getId());
        Boolean likeStatus = memberFavoriteFolderIdList.contains(folderId);

        // isMe(내가 소유한 폴더인지 아닌지)
        boolean isMe = findArticleFolder.get().getMember().equals(findMember);

        List<ArticlesInfoInFolderResponseDto> articlesInfoInFolderResponseDtoList = new ArrayList<>();

        if (!CollectionUtils.isEmpty(articles)) {
            for (Article article : articles) {
                boolean isRead = article.getReadCount() > 0;
                boolean isSaved = myArticlesUrl.contains(article.getUrl());
                ArticlesInfoInFolderResponseDto articlesInfoInFolderResponseDto = ArticlesInfoInFolderResponseDto.of(article, isRead, isSaved);
                articlesInfoInFolderResponseDtoList.add(articlesInfoInFolderResponseDto);
            }
        } else {
            articlesInfoInFolderResponseDtoList.add(null);
        }

        return ArticlesInFolderResponseDto.of(findArticleFolder.get(), isMe, likeStatus, articlesInfoInFolderResponseDtoList);
    }

    /**
     * 폴더 안 아티클 조회(비로그인)
     * @param folderId
     * @return List<ArticlesInFolderResponseDto>
     */
    @Override
    public ArticlesInFolderResponseDto findArticlesInFolderNonLogin(Long folderId) {
        Optional<ArticleFolder> findArticleFolder = Optional.of(getFolder(folderId));

        List<Article> articles = new ArrayList<>();
        findArticleFolder
                .map(ArticleFolder::getArticles)
                .ifPresent(articles::addAll);

        List<ArticlesInfoInFolderResponseDto> articlesInfoInFolderResponseDtoList = new ArrayList<>();

        if (!CollectionUtils.isEmpty(articles)) {
            for (Article article : articles) {
                ArticlesInfoInFolderResponseDto articlesInfoInFolderResponseDto = ArticlesInfoInFolderResponseDto.of(article);
                articlesInfoInFolderResponseDtoList.add(articlesInfoInFolderResponseDto);
            }
        } else {
            articlesInfoInFolderResponseDtoList.add(null);
        }

        return ArticlesInFolderResponseDto.of(findArticleFolder.get(), articlesInfoInFolderResponseDtoList);
    }

    /**
     * 폴더 안 아티클 삭제
     * @param folderId
     * @param articleId
     */
    @Override
    public void deleteArticleInArticleFolder(Long folderId, Long articleId) {
        ArticleFolder articleFolder = getFolder(folderId);
        if (!articleFolder.getArticles().isEmpty()) {
            articleFolder.getArticles()
                    .stream()
                    .filter(article -> article.getId() == articleId)
                    .forEach(article -> articleFolder.getArticles().remove(article));
        } else throw new EntityNotFoundException("해당 아티클이 폴더에 없습니다.");

    }

    /**
     * 좋아요 추가, 삭제
     * @param member
     * @param folderId
     * @return LikeAddOrRemoveResponseDto
     */
    @Override
    public LikeAddOrRemoveResponseDto likeAddOrRemove(Member member, Long folderId) {
        Member findMember = getMember(member.getId());
        ArticleFolder articleFolder = getFolder(folderId);

        Optional<Favorite> isFavoriteExist = favoriteRepository.findByMemberAndArticleFolder(findMember, articleFolder);
        if (isFavoriteExist.isPresent()) {
            favoriteRepository.delete(isFavoriteExist.get());
            articleFolder.decreaseLikeCount(articleFolder.getLikeCount());
            findMember.decreaseTotalLikeCount(articleFolder.getLikeCount());
            return new LikeAddOrRemoveResponseDto(false);
        } else {
            Favorite favorite = new Favorite(articleFolder, findMember);
            favoriteRepository.save(favorite);
            articleFolder.increaseLikeCount(articleFolder.getLikeCount());
            findMember.increaseTotalLikeCount(articleFolder.getLikeCount());
            return new LikeAddOrRemoveResponseDto(true);
        }
    }

    /**
     * 아티클 폴더 타이틀 목록 조회
     * @param member
     * @return List<ArticleFolderNameAndIdResponseDto>
     */
    @Override
    public List<ArticleFolderNameAndIdResponseDto> getArticleFoldersName(Member member) {
        List<ArticleFolderNameAndIdResponseDto> articleFolderNameAndIdResponseDtoList = new ArrayList<>();

        List<ArticleFolder> findArticleFolders = getMember(member.getId()).getArticleFolders();
        if (!CollectionUtils.isEmpty(findArticleFolders)) {
            for (ArticleFolder articleFolder : findArticleFolders) {
                articleFolderNameAndIdResponseDtoList.add(ArticleFolderNameAndIdResponseDto.of(articleFolder));
            }
        } else {
            return null;
        }

        return articleFolderNameAndIdResponseDtoList;
    }

    /**
     * Member 조회
     * @param id
     */
    private Member getMember(Long id) {
        Optional<Member> member = memberRepository.findById(id);
        if (member.isPresent()) {
            return member.get();
        } else throw new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND.getErrorMessage());
    }

    /**
     * 폴더 ID에 해당되는 폴더 조회 메소드
     * @param id
     * @return Optional<ArticleFolder>
     */
    private ArticleFolder getFolder(Long id) {
        Optional<ArticleFolder> folder = articleFolderRepository.findById(id);
        if (folder.isPresent()) {
            return folder.get();
        } else throw new EntityNotFoundException("존재하지 않는 폴더");

    }


}

