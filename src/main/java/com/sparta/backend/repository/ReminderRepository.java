package com.sparta.backend.repository;

import com.sparta.backend.model.Article;
import com.sparta.backend.model.Reminder;
import jdk.jfr.Name;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReminderRepository extends JpaRepository<Reminder, Long> {

    void deleteReminderByMemberNameAndArticle_Id(String memberName, long id);

    Reminder findReminderByMemberNameAndArticle_Id(String memberName, long id);

    List<Reminder> findAllBySendDate(LocalDate localDate);

    List<Reminder> findAllByMemberName(String memberName);

    boolean existsReminderByMemberNameAndArticle(String memberName, Article article);
}
