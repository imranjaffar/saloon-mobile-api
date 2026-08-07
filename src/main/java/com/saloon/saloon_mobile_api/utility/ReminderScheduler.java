package com.saloon.saloon_mobile_api.utility;

import com.saloon.saloon_mobile_api.entity.Reminder;
import com.saloon.saloon_mobile_api.entity.ReminderStatus;
import com.saloon.saloon_mobile_api.repository.ReminderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ReminderScheduler {

    private final ReminderRepository reminderRepository;

    @Scheduled(cron = "0 0 8 * * *") // Every day at 8 AM
    public void generateTodayReminders() {

        LocalDate today = LocalDate.now();

        List<Reminder> reminders =
                reminderRepository.findByReminderDateAndStatus(
                        today,
                        ReminderStatus.PENDING
                );

        reminders.forEach(reminder ->
                reminder.setStatus(ReminderStatus.DUE));

        reminderRepository.saveAll(reminders);
    }
}