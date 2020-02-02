package com.emarsys.homework;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Implements a due date calculator in an issue tracking system.
 */
public class GeneralDueDateCalculator implements DueDateCalculator {
    private final WorkCalendar calendar;

    public GeneralDueDateCalculator() {
        this(new SimpleWorkCalendar());
    }

    public GeneralDueDateCalculator(final WorkCalendar calendar) {
        this.calendar = calendar;
    }

    @Override
    public LocalDateTime calculateDueDate(final LocalDateTime submission, final int turnaroundHours) {
        return calculateDueDate(submission, Duration.ofHours(turnaroundHours));
    }


    public LocalDateTime calculateDueDate(final LocalDateTime submission, final Duration turnaround) {
        calendar.validate(submission);
        calendar.validate(turnaround);

        if (turnaround.isZero()) {
            return submission;
        }

        Duration workTimeAvailableToday = calendar.howMuchWorkCanBeDoneToday(submission);
        Duration remainingTurnaround = turnaround.minus(workTimeAvailableToday);
        // We can do more, than the remaining work, so we can finish today:
        if (remainingTurnaround.isNegative() || remainingTurnaround.isZero()) {
            return submission.plus(turnaround);
        } else {
            LocalDateTime finishForToday = submission.plus(workTimeAvailableToday);
            LocalDateTime nextWorkWindow = calendar.nextAvailableWorkingDateTime(finishForToday);
            return calculateDueDate(nextWorkWindow, remainingTurnaround);
        }
    }

}
