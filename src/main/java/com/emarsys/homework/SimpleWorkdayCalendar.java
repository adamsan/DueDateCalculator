package com.emarsys.homework;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

import static java.time.DayOfWeek.*;

class SimpleWorkCalendar {
    private LocalTime start = LocalTime.of(9, 0);
    private LocalTime end = LocalTime.of(17, 0);

    public Duration howMuchWorkCanBeDoneToday(LocalDateTime submission) {

        return Duration.between(submission.toLocalTime(), end);
    }

    public LocalDateTime nextAvailableWorkingDateTime(LocalDateTime submission) {
        var day = submission.getDayOfWeek();
        if (day == SATURDAY || day == SUNDAY) {
            return submission.with(TemporalAdjusters.next(MONDAY)).withHour(start.getHour()).withMinute(start.getMinute());
        }
        if (submission.toLocalTime().equals(end) || submission.toLocalTime().isAfter(end)) {
            if (day == FRIDAY) {
                return submission.with(TemporalAdjusters.next(MONDAY)).withHour(start.getHour()).withMinute(start.getMinute());
            }
            return submission.plusDays(1).withHour(start.getHour()).withMinute(start.getMinute());
        }
        return submission;
    }
}