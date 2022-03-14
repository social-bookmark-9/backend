package com.sparta.backend.repository;

import com.sparta.backend.model.Reminder;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ReminderRepositoryTest {

    @Autowired
    private ReminderRepository reminderRepository;

    private String email;
    private String titleOg;
    private String memberName;
    private String url;
    private int buttonDate;

    @BeforeEach
    public void setUp() {
        buttonDate = 3;
        email = "leeyuwk54@gmail.com";
        titleOg = "짬뽕 맛있게 먹는법";
        memberName = "wowba";
        url = "naver.com";
    }

    @Test
    @Order(1)
    @DisplayName("리마인더 생성 테스트")
    public void createReminder() {
        // given
        Reminder reminder = Reminder.builder()
                .sendDate(LocalDate.now().plusDays(buttonDate))
                .email(email)
                .titleOg(titleOg)
                .memberName(memberName)
                .url(url)
                .buttonDate(buttonDate)
                .build();
        // when
        reminderRepository.save(reminder);
        // then
        Assertions.assertEquals(reminder, reminderRepository.findById(reminder.getId())
                .orElseThrow(() -> new IllegalArgumentException("리마인더가 존재하지 않아요.")));
    }

    @Test
    @Order(2)
    @DisplayName("오늘 날짜 리마인더 가져오기")
    public void getReminder() {
        // given
        Reminder reminder = Reminder.builder()
                .sendDate(LocalDate.now())
                .email(email)
                .titleOg("귀여운 고양이")
                .memberName(memberName)
                .url(url)
                .buttonDate(buttonDate)
                .build();
        reminderRepository.save(reminder);
        // when
        List<Reminder> reminderList = reminderRepository.findAllBySendDate(LocalDate.now());
        System.out.println(reminderList);
        // then
        Assertions.assertNotNull(reminderList);
    }

    @Test
    @Order(3)
    @DisplayName("리마인더 수정 테스트")
    public void editReminder() {
        // given
        Reminder reminder = reminderRepository.findReminderByMemberNameAndTitleOg(memberName, titleOg);
        // when
        int newButtonDate = 7;
        reminder.editDate(newButtonDate);
        // then
        Assertions.assertEquals(reminder.getSendDate(), LocalDate.now().plusDays(newButtonDate));
    }

    @Test
    @Order(4)
    @DisplayName("리마인더 삭제 테스트")
    public void deleteReminder() {
        // given - when
        reminderRepository.deleteReminderByMemberNameAndTitleOg(memberName, titleOg);
        // then
        Assertions.assertNull(reminderRepository.findReminderByMemberNameAndTitleOg(memberName, titleOg));
    }
}