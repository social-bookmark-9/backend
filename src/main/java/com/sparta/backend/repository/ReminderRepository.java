package com.sparta.backend.repository;

import com.sparta.backend.model.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReminderRepository extends JpaRepository<Reminder, Long> {
    Long deleteReminderByMemberNameAndTitleOg(String memberName, String titleOg);

    Reminder findReminderByMemberNameAndTitleOg(String memberName, String titleOg);

    List<Reminder> findAllBySendDate(LocalDate localDate);
}
