package com.sparta.backend.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "alarm")
public class Alarm extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm_id")
    private Long id;

    @Column(name = "is_checked")
    private Boolean isChecked;

    @Column(name = "article_folder_id", nullable = false)
    private Long articleFolderId;

    @Column(name = "from_member_id", nullable = false)
    private Long fromMemberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Builder
    public Alarm (Long articleFolderId, Long fromMemberId, Member member) {
        this.articleFolderId = articleFolderId;
        this.fromMemberId = fromMemberId;
        this.member = member;
    }

}
