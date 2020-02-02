package com.emarsys.homework;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static java.time.DayOfWeek.*;
import static java.time.LocalDate.now;
import static java.time.temporal.TemporalAdjusters.next;

public class DueDateCalculatorValidationTest {

    private IDueDateCalculator sut;

    @Before
    public void setup() {
        // Given sut (System Under Test) is DueDayCalculator
        sut = new GeneralDueDateCalculator();
    }

    @Test(expected = IllegalArgumentException.class)
    public void calculateDueDateShouldReceiveSubmissionDateTime() {
        // When submission is null;
        // Then calculateDueDate should throw IllegalArgumentException
        sut.calculateDueDate(null, 0);
    }

    @Test(expected = RuntimeException.class)
    public void issueCantSubmittedOnWeekendOnSaturday() {
        LocalDateTime saturday = now().with(next(SATURDAY)).atTime(12, 30);
        sut.calculateDueDate(saturday, 0);
    }

    @Test(expected = RuntimeException.class)
    public void issueCantSubmittedOnWeekendOnSunday() {
        LocalDateTime sunday = now().with(next(SUNDAY)).atTime(12, 30);
        sut.calculateDueDate(sunday, 0);
    }

    @Test(expected = RuntimeException.class)
    public void issueCantSubmittedOnNonWorkingHoursEarly() {
        LocalDateTime tooEarly = now().with(next(MONDAY)).atTime(8, 59);
        sut.calculateDueDate(tooEarly, 0);
    }

    @Test(expected = RuntimeException.class)
    public void issueCantSubmittedOnNonWorkingHoursLate() {
        LocalDateTime tooLate = now().with(next(MONDAY)).atTime(17, 00);
        sut.calculateDueDate(tooLate, 0);
    }

    @Test
    public void issueCanSubmittedOnWorkingHoursEarly() {
        LocalDateTime earlyButInWorkWindow = now().with(next(MONDAY)).atTime(9, 00);
        sut.calculateDueDate(earlyButInWorkWindow, 0);
    }

    @Test
    public void issueCanSubmittedOnWorkingHoursMidday() {
        LocalDateTime t = now().with(next(MONDAY)).atTime(12, 25);
        sut.calculateDueDate(t, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void turnaroundHoursCantBeNegative() {
        LocalDateTime t = now().with(next(MONDAY)).atTime(12, 25);
        sut.calculateDueDate(t, -23);
    }
}