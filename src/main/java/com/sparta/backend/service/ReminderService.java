package com.sparta.backend.service;

import com.sparta.backend.exception.BusinessException;
import com.sparta.backend.exception.ErrorCode;
import com.sparta.backend.model.Article;
import com.sparta.backend.model.Member;
import com.sparta.backend.model.Reminder;
import com.sparta.backend.repository.ArticleRepository;
import com.sparta.backend.repository.ReminderRepository;
import com.sparta.backend.requestDto.ReminderRequestDto;
import com.sparta.backend.responseDto.ReminderResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReminderService {

    private final ReminderRepository reminderRepository;
    private final ArticleRepository articleRepository;

    // 리마인더 가져오기
    public List<ReminderResponseDto> getReminders(Member member) {
        List<Reminder> reminders = reminderRepository.findAllByMemberName(member.getMemberName());
        List<ReminderResponseDto> reminderResponseDtos = new ArrayList<>();

        for(Reminder reminder : reminders) {
            ReminderResponseDto reminderResponseDto = ReminderResponseDto.builder()
                    .articleId(reminder.getArticle().getId())
                    .imgOg(reminder.getImgOg())
                    .titleOg(reminder.getTitleOg())
                    .hashtag1(reminder.getArticle().getHashtag().getHashtag1())
                    .hashtag2(reminder.getArticle().getHashtag().getHashtag2())
                    .hashtag3(reminder.getArticle().getHashtag().getHashtag3())
                    .build();
            reminderResponseDtos.add(reminderResponseDto);
        }
        return reminderResponseDtos;
    }

    // 리마인더 생성하기
    public void createReminder(ReminderRequestDto reminderRequestDto, Member member) {
        Article article = articleRepository.findById(reminderRequestDto.getArticleId())
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));

        // 리마인더가 존재하는지 확인
        if(reminderRepository.existsReminderByMemberNameAndTitleOg(member.getMemberName(), reminderRequestDto.getTitleOg())) {
            throw new BusinessException(ErrorCode.REMINDER_DUPLICATED);
        }

        Reminder reminder = Reminder.builder()
                .sendDate(LocalDate.now().plusDays(reminderRequestDto.getButtonDate()))
                .email(member.getEmail())
                .titleOg(reminderRequestDto.getTitleOg())
                .memberName(member.getMemberName())
                .imgOg(reminderRequestDto.getImgOg())
                .buttonDate(reminderRequestDto.getButtonDate())
                .article(article)
                .url(article.getUrl())
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
