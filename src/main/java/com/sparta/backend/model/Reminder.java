package com.sparta.backend.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "reminder")
public class Reminder{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reminder_id")
    private Long id;

    @Column(name = "send_date", nullable = false)
    private LocalDate sendDate;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "member_name", nullable = false)
    private String memberName;

    @Column(name = "button_date", nullable = false)
    private int buttonDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @Builder
    public Reminder(LocalDate sendDate, String email, String titleOg,
                    Article article, String memberName, String imgOg, int buttonDate, String url) {
        this.sendDate = sendDate;
        this.email = email;
        this.memberName = memberName;
        this.buttonDate = buttonDate;
        this.article = article;
    }
    
    // 리마인더 수정하기
    public void editDate(int buttonDate) {
        this.buttonDate = buttonDate;
        this.sendDate = LocalDate.now().plusDays(buttonDate);
    }
}
