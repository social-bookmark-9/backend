package com.sparta.backend.repository;

import com.sparta.backend.model.Reminder;
import jdk.jfr.Name;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReminderRepository extends JpaRepository<Reminder, Long> {
    void deleteReminderByMemberNameAndTitleOg(String memberName, String titleOg);

    Reminder findReminderByMemberNameAndTitleOg(String memberName, String titleOg);

    List<Reminder> findAllBySendDate(LocalDate localDate);

    List<Reminder> findAllByMemberName(String memberName);

    boolean existsReminderByMemberNameAndTitleOg(String memberName, String titleOg);
}
