package com.emarsys.homework;

import java.time.LocalDateTime;

import static com.emarsys.homework.DueDateCalculatorValidator.*;

/**
 * Implements a due date calculator in an issue tracking system.
 */
public class DueDateCalculator implements IDueDateCalculator {

    private static final int WORK_WINDOW_HOURS = END_WORK_TIME.getHour() - (START_WORK_TIME).getHour(); // 8
    private static final int WORK_WEEK_DAYS = 7 - WEEKEND_DAYS.size(); // 5

    private final DueDateCalculatorValidator validator;

    public DueDateCalculator() {
        this(new DueDateCalculatorValidator());
    }

    public DueDateCalculator(final DueDateCalculatorValidator validator) {
        this.validator = validator;
    }

    @Override
    public LocalDateTime calculateDueDate(final LocalDateTime submission, final int turnaroundHours) {
        validator.validateSubmission(submission);
        validator.validateTurnaroundHours(turnaroundHours);

        return jumpOverWholeWeeksAndCalculateDueDate(submission, turnaroundHours);
    }

    private LocalDateTime jumpOverWholeWeeksAndCalculateDueDate(final LocalDateTime submission, final int turnaroundHours) {
        // optimisation: jump over whole weeks
        int weeks = turnaroundHours / (WORK_WEEK_DAYS * WORK_WINDOW_HOURS);
        int remainingTurnaroundHours = turnaroundHours % (WORK_WEEK_DAYS * WORK_WINDOW_HOURS);
        var timeAfterJumpingOverWeeks = submission.plusWeeks(weeks);

        return calculateDueDateRecursion(timeAfterJumpingOverWeeks, remainingTurnaroundHours);
    }

    private LocalDateTime calculateDueDateRecursion(final LocalDateTime acc, final int turnaroundHours) {
        if (turnaroundHours == 0) {
            return acc;
        }
        var nextHour = acc.plusHours(1);
        if (isWorkingTime(nextHour) || nextHour.toLocalTime() == END_WORK_TIME) {
            return calculateDueDateRecursion(nextHour, turnaroundHours - 1);
        }
        var nextDay = acc.plusDays(1).minusHours(WORK_WINDOW_HOURS).plusHours(1);
        if (isWorkingTime(nextDay) && !isWeekend(nextDay)) {
            return calculateDueDateRecursion(nextDay, turnaroundHours - 1);
        }
        var nextWeek = acc.plusDays(1).plusDays(WEEKEND_DAYS.size()).minusHours(WORK_WINDOW_HOURS).plusHours(1);
        return calculateDueDateRecursion(nextWeek, turnaroundHours - 1);
    }
}
