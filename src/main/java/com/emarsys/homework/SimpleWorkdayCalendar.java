package com.emarsys.homework;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.List;

import static java.time.DayOfWeek.*;

class SimpleWorkCalendar {
    private static final List<DayOfWeek> WEEKEND_DAYS = Arrays.asList(SATURDAY, SUNDAY);
    private static final LocalTime START_WORK_TIME = LocalTime.of(9, 0);
    private static final LocalTime END_WORK_TIME = LocalTime.of(17, 0);

    private static boolean isWeekend(final LocalDateTime submission) {
        return WEEKEND_DAYS.stream().anyMatch(day -> day == submission.getDayOfWeek());
    }

    private static boolean isWorkingTime(final LocalDateTime submission) {
        LocalTime submissionTime = submission.toLocalTime();
        return submissionTime.equals(START_WORK_TIME) || submissionTime.isAfter(START_WORK_TIME) && submissionTime.isBefore(END_WORK_TIME);
    }

    public Duration howMuchWorkCanBeDoneToday(LocalDateTime submission) {
        return Duration.between(submission.toLocalTime(), END_WORK_TIME);
    }

    public LocalDateTime nextAvailableWorkingDateTime(LocalDateTime submission) {
        if (isWeekend(submission)) {
            return getNextMondayStart(submission);
        }
        if (submission.toLocalTime().equals(END_WORK_TIME) || submission.toLocalTime().isAfter(END_WORK_TIME)) {
            if (submission.getDayOfWeek() == FRIDAY) {
                return getNextMondayStart(submission);
            }
            return getNextDayStart(submission);
        }
        return submission;
    }

    private LocalDateTime getNextDayStart(LocalDateTime submission) {
        return submission.plusDays(1).withHour(START_WORK_TIME.getHour()).withMinute(START_WORK_TIME.getMinute());
    }

    private LocalDateTime getNextMondayStart(LocalDateTime submission) {
        return submission.with(TemporalAdjusters.next(MONDAY)).withHour(START_WORK_TIME.getHour()).withMinute(START_WORK_TIME.getMinute());
    }

    public void validate(LocalDateTime submission) {
        if (submission == null) {
            throw new IllegalArgumentException("Submission datetime can't be null!");
        }
        if (isWeekend(submission)) {
            throw new RuntimeException("Issue can't be submitted on weekends.");
        }
        if (!isWorkingTime(submission)) {
            throw new RuntimeException("Issue can't be submitted outside of workwindow. (9AM to 5PM).");
        }
    }

    public void validate(Duration turnaround) {
        if (turnaround.isNegative()) {
            throw new IllegalArgumentException("Turnaround hours can't be negative!");
        }
    }


}