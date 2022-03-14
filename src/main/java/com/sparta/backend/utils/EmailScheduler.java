package com.sparta.backend.utils;

import com.sparta.backend.model.Reminder;
import com.sparta.backend.repository.ReminderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailScheduler {

    private final ReminderRepository reminderRepository;
    private final JavaMailSender javaMailSender;

    // 초, 분, 시, 일, 월, 주
    @Scheduled(cron = "0 0 5 * * *") // 매일 오전 5시에 메일 보내기.
    public void sendEmail() throws InterruptedException {
        List<Reminder> reminderList = reminderRepository.findAllBySendDate(LocalDate.now());

        for (Reminder reminder : reminderList) {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            try {
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
                mimeMessageHelper.setTo(reminder.getEmail()); // 메일 수신자
                mimeMessageHelper.setSubject("오늘 읽을 아티클이 왔어요!"); // 메일 제목
                mimeMessageHelper.setText(
                        reminder.getMemberName() + " 님, 오늘 " + reminder.getTitleOg() + "를 읽어야 해요! " + reminder.getUrl() , false); // 메일 본문 내용, HTML 여부
                javaMailSender.send(mimeMessage);
                reminderRepository.delete(reminder);
                log.info("Mail send Success!!");
            } catch (MessagingException e) {
                log.info("Mail send fail!!");
                throw new RuntimeException(e);
            }
        }
    }
}
