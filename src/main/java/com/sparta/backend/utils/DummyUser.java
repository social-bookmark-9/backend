package com.sparta.backend.utils;

import com.sparta.backend.model.ArticleFolder;
import com.sparta.backend.model.Hashtag;
import com.sparta.backend.model.Member;
import com.sparta.backend.repository.ArticleFolderRepository;
import com.sparta.backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Transactional
public class DummyUser {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final ArticleFolderRepository articleFolderRepository;

    @PostMapping("/dummy/users/100")
    public String createDummyUsers100() {

        List<String> hashtags = new ArrayList<>();
        hashtags.add("커리어"); hashtags.add("업무스킬"); hashtags.add("IT"); hashtags.add("디자인"); hashtags.add("마케팅");hashtags.add("투자");
        hashtags.add("장소"); hashtags.add("동기부여"); hashtags.add("인간관계"); hashtags.add("패션");hashtags.add("예술"); hashtags.add("과학");

        for(int i = 0; i < 100; i++) {
            String password = UUID.randomUUID().toString();
            String encodedPassword = passwordEncoder.encode(password);

            Hashtag hashtag = Hashtag.builder()
                    .hashtag1(hashtags.get((int) (Math.random() * 11)))
                    .hashtag2(hashtags.get((int) (Math.random() * 11)))
                    .hashtag3(hashtags.get((int) (Math.random() * 11)))
                    .build();

            Member kakaoMember = Member.builder()
                    .email(RandomString.make(10))
                    .password(encodedPassword)
                    .memberName(RandomString.make(10))
                    .kakaoId(RandomString.make(10))
                    .memberRoles(Collections.singletonList("ROLE_USER")) // 최초 가입시 USER 로 설정
                    .hashtag(hashtag)
                    .profileImage(RandomString.make(10))
                    .build();

            hashtag.setMember(kakaoMember);

            memberRepository.save(kakaoMember);

            ArticleFolder articleFolder = ArticleFolder.builder()
                    .articleFolderName("미분류 컬렉션")
                    .deleteable(false)
                    .folderHide(true)
                    .member(kakaoMember)
                    .build();

            articleFolderRepository.save(articleFolder);
        }

        return "더미 유저 생성 완료";
    }

    @PostMapping("/dummy/users/1000")
    public String createDummyUsers1000() {

        List<String> hashtags = new ArrayList<>();
        hashtags.add("커리어"); hashtags.add("업무스킬"); hashtags.add("IT"); hashtags.add("디자인"); hashtags.add("마케팅");hashtags.add("투자");
        hashtags.add("장소"); hashtags.add("동기부여"); hashtags.add("인간관계"); hashtags.add("패션");hashtags.add("예술"); hashtags.add("과학");

        for(int i = 0; i < 1000; i++) {
            String password = UUID.randomUUID().toString();
            String encodedPassword = passwordEncoder.encode(password);

            Hashtag hashtag = Hashtag.builder()
                    .hashtag1(hashtags.get((int) (Math.random() * 11)))
                    .hashtag2(hashtags.get((int) (Math.random() * 11)))
                    .hashtag3(hashtags.get((int) (Math.random() * 11)))
                    .build();

            Member kakaoMember = Member.builder()
                    .email(RandomString.make(10))
                    .password(encodedPassword)
                    .memberName(RandomString.make(10))
                    .kakaoId(RandomString.make(10))
                    .memberRoles(Collections.singletonList("ROLE_USER")) // 최초 가입시 USER 로 설정
                    .hashtag(hashtag)
                    .profileImage(RandomString.make(10))
                    .build();

            hashtag.setMember(kakaoMember);

            memberRepository.save(kakaoMember);

            ArticleFolder articleFolder = ArticleFolder.builder()
                    .articleFolderName("미분류 컬렉션")
                    .deleteable(false)
                    .folderHide(true)
                    .member(kakaoMember)
                    .build();

            articleFolderRepository.save(articleFolder);
        }

        return "더미 유저 생성 완료";
    }
}
