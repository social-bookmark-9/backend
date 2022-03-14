package com.sparta.backend.controller;

import com.sparta.backend.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send-mail")
    public ResponseEntity sendMail() {
        EmailMessage emailMessage = EmailMessage.builder()
                .to("leeyuwk54@gmail.com")
                .subject("테스트 메일 제목")
                .message("테스트 메일 본문")
                .build();
        emailService.sendMail(emailMessage);
        return new ResponseEntity(HttpStatus.OK);
    }
}
