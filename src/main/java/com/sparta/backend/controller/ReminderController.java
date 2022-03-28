package com.sparta.backend.controller;

import com.sparta.backend.message.RestResponseMessage;
import com.sparta.backend.model.Member;
import com.sparta.backend.requestDto.ReminderRequestDto;
import com.sparta.backend.responseDto.ReminderResponseDto;
import com.sparta.backend.service.ReminderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReminderController {

    private final ReminderService reminderService;

    // 리마인더 가져오기
    @GetMapping("/api/reminders")
    public ResponseEntity<RestResponseMessage> getReminders(@AuthenticationPrincipal Member member) {
        List<ReminderResponseDto> reminders = reminderService.getReminders(member);
        return new ResponseEntity<>(new RestResponseMessage<>(true, "리마인더 가져오기 성공", reminders), HttpStatus.OK);
    }

    // 리마인더 생성하기
    @PostMapping("/api/reminders")
    public ResponseEntity<RestResponseMessage> createReminder(@Valid @RequestBody ReminderRequestDto reminderRequestDto, @AuthenticationPrincipal Member member) {
        reminderService.createReminder(reminderRequestDto, member);
        return new ResponseEntity<>(new RestResponseMessage<>(true, "리마인더 생성 성공", ""), HttpStatus.OK);
    }

    // 리마인더 수정하기
    @PatchMapping("/api/reminders")
    public ResponseEntity<RestResponseMessage> editReminder(@Valid @RequestBody ReminderRequestDto reminderRequestDto, @AuthenticationPrincipal Member member) {
        reminderService.editReminder(reminderRequestDto, member);
        return new ResponseEntity<>(new RestResponseMessage<>(true, "리마인더 수정 성공", ""), HttpStatus.OK);
    }

    // 리마인더 삭제하기
    @DeleteMapping("/api/reminders")
    public ResponseEntity<RestResponseMessage> deleteReminder(@Valid @RequestBody ReminderRequestDto reminderRequestDto, @AuthenticationPrincipal Member member) {
        reminderService.deleteReminder(reminderRequestDto, member);
        return new ResponseEntity<>(new RestResponseMessage<>(true, "리마인더 삭제 성공", ""), HttpStatus.OK);
    }
}
