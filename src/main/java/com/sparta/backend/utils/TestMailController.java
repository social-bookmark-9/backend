package com.sparta.backend.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TestMailController {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @PostMapping("/mail")
    public String sendMail() {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

            mimeMessageHelper.setTo("ilovechicken8989@gmail.com"); // 메일 수신자
            mimeMessageHelper.setSubject("메일 테스트"); // 메일 제목

            Context context = new Context(); // 메일 본문 내용 작성
            context.setVariable("month", "1");
            context.setVariable("days", "1");
            context.setVariable("title", "시연용 제목");
            context.setVariable("url", "https://naver.com");
            String message = templateEngine.process("email", context);
            mimeMessageHelper.setText(message, true);

            javaMailSender.send(mimeMessage);

            log.info("Mail send Success!!");
        } catch (MessagingException e) {
            log.info("Mail send fail!!");
            throw new RuntimeException(e);
        }
        return "good";
    }
}