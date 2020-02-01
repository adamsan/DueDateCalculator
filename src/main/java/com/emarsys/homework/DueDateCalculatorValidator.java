package com.emarsys.homework;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;

public class DueDateCalculatorValidator {
    public static final LocalTime START_WORK_TIME = LocalTime.of(9, 0);
    public static final LocalTime END_WORK_TIME = LocalTime.of(17, 0);
    public static final List<DayOfWeek> WEEKEND_DAYS = Arrays.asList(SATURDAY, SUNDAY);

    public static boolean isWeekend(LocalDateTime submission) {
        return WEEKEND_DAYS.stream().anyMatch(day -> day == submission.getDayOfWeek());
    }

    public static boolean isWorkingTime(LocalDateTime submission) {
        LocalTime submissionTime = submission.toLocalTime();
        return submissionTime.equals(START_WORK_TIME) || submissionTime.isAfter(START_WORK_TIME) && submissionTime.isBefore(END_WORK_TIME);
    }

    public void validateTurnaroundHours(int turnaroundHours) {
        if (turnaroundHours < 0) {
            throw new IllegalArgumentException("Turnaround hours can't be negative!");
        }
    }

    public void validateSubmission(LocalDateTime submission) {
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
}
