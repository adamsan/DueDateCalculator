package com.emarsys.homework;

import java.time.LocalDateTime;

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
        // Duration turnaroundD = Duration.of(turnaroundHours, ChronoUnit.HOURS);
        return null;
    }
}