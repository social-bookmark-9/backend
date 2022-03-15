package com.sparta.backend.service;

import com.sparta.backend.model.Article;
import com.sparta.backend.model.Member;
import com.sparta.backend.model.Reminder;
import com.sparta.backend.repository.ArticleRepository;
import com.sparta.backend.repository.ReminderRepository;
import com.sparta.backend.requestDto.ReminderRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class ReminderService {

    private final ReminderRepository reminderRepository;
    private final ArticleRepository articleRepository;

    // 리마인더 생성하기
    public void createReminder(ReminderRequestDto reminderRequestDto, Member member) {
        Article article = articleRepository.findById(reminderRequestDto.getArticleId())
                .orElseThrow(() -> new IllegalArgumentException("해당 아티클이 존재하지 않습니다."));

        Reminder reminder = Reminder.builder()
                .sendDate(LocalDate.now().plusDays(reminderRequestDto.getButtonDate()))
                .email(member.getEmail())
                .titleOg(reminderRequestDto.getTitleOg())
                .memberName(member.getMemberName())
                .url(reminderRequestDto.getUrl())
                .buttonDate(reminderRequestDto.getButtonDate())
                .article(article)
                .build();

        article.setReminder(reminder);

        reminderRepository.save(reminder);
    }

    // 리마인더 수정하기
    public void editReminder(ReminderRequestDto reminderRequestDto, Member member) {
        Reminder reminder = reminderRepository.findReminderByMemberNameAndTitleOg(member.getMemberName(), reminderRequestDto.getTitleOg());
        reminder.editDate(reminderRequestDto.getButtonDate());
    }

    // 리마인더 삭제하기
    public void deleteReminder(ReminderRequestDto reminderRequestDto, Member member) {
        reminderRepository.deleteReminderByMemberNameAndTitleOg(member.getMemberName(), reminderRequestDto.getTitleOg());
    }
}
