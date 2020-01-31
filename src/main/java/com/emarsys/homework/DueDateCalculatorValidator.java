package com.emarsys.homework;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.Stream;

import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;

public class DueDateCalculatorValidator {
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

    private boolean isWeekend(LocalDateTime submission) {
        return Stream.of(SATURDAY, SUNDAY).anyMatch(day -> day == submission.getDayOfWeek());
    }

    private boolean isWorkingTime(LocalDateTime submission) {
        LocalTime start = LocalTime.of(9, 0);
        LocalTime end = LocalTime.of(17, 0);
        LocalTime submissionTime = submission.toLocalTime();
        return submissionTime.equals(start) || submissionTime.isAfter(start) && submissionTime.isBefore(end);
    }
}
