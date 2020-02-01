package com.emarsys.homework;

import java.time.LocalDateTime;

import static com.emarsys.homework.DueDateCalculatorValidator.isWeekend;
import static com.emarsys.homework.DueDateCalculatorValidator.isWorkingTime;

/**
 * Implements a due date calculator in an issue tracking system
 */
public class DueDateCalculator {

    private final DueDateCalculatorValidator validator;

    public DueDateCalculator() {
        this(new DueDateCalculatorValidator());
    }

    public DueDateCalculator(DueDateCalculatorValidator validator) {
        this.validator = validator;
    }

    /**
     * Working hours are from 9AM to 5PM on every working day, Monday to Friday.
     * A problem can only be reported during working hours.
     * If a problem was reported at 2:12PM on Tuesday and the turnaround time is 16 hours, then it is due by 2:12PM on Thursday.
     *
     * @param submission      Issue submission date/time
     * @param turnaroundHours Issue turnaround time in hours
     * @return the date/time when the issue is resolved
     */
    public LocalDateTime calculateDueDate(LocalDateTime submission, int turnaroundHours) {
        validator.validateSubmission(submission);
        validator.validateTurnaroundHours(turnaroundHours);

        // optimisation: jump over whole weeks
        int weeks = turnaroundHours / (5 * 8);
        int remainingTurnaroundHours = turnaroundHours % (5 * 8);

        var timeAfterJumpingOverWeeks = submission.plusWeeks(weeks);

        return calculateDueDateRecursion(timeAfterJumpingOverWeeks, remainingTurnaroundHours);
    }

    private LocalDateTime calculateDueDateRecursion(LocalDateTime acc, int turnaroundHours) {
        if (turnaroundHours == 0) {
            return acc;
        }
        var nextHour = acc.plusHours(1);
        if (isWorkingTime(nextHour)) {
            return calculateDueDateRecursion(nextHour, turnaroundHours - 1);
        }
        var nextDay = acc.plusDays(1).minusHours(8).plusHours(1);
        if (isWorkingTime(nextDay) && !isWeekend(nextDay)) {
            return calculateDueDateRecursion(nextDay, turnaroundHours - 1);
        }
        var nextWeek = acc.plusDays(3).minusHours(8).plusHours(1);
        return calculateDueDateRecursion(nextWeek, turnaroundHours - 1);
    }
}