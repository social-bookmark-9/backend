package com.sparta.backend.service;

import com.sparta.backend.model.Member;
import com.sparta.backend.requestDto.ReminderRequestDto;
import com.sparta.backend.responseDto.ReminderResponseDto;

import java.util.List;

public interface ReminderService {

    List<ReminderResponseDto> getReminders(Member member);

    void createReminder(ReminderRequestDto reminderRequestDto, Member member);

    void editReminder(ReminderRequestDto reminderRequestDto, Member member);

    void deleteReminder(ReminderRequestDto reminderRequestDto, Member member);
}
