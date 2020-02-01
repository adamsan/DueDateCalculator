package com.emarsys.homework;

import org.junit.Before;
import org.junit.Test;

import static java.time.DayOfWeek.*;
import static java.time.LocalDate.now;
import static java.time.temporal.ChronoUnit.*;
import static java.time.temporal.TemporalAdjusters.next;
import static org.junit.Assert.assertEquals;

public class DueDateCalculatorTest {

    private DueDateCalculator sut;

    @Before
    public void setUp() throws Exception {
        // Given sut (System Under Test) is DueDayCalculator
        sut = new DueDateCalculator();
    }

    @Test
    public void issueWithZeroTurnaroundHoursFinishesFast() {
        var submissionTime = now().with(next(MONDAY)).atTime(12, 25);
        var result = sut.calculateDueDate(submissionTime, 0);
        assertEquals(submissionTime, result);
    }

    @Test
    public void issueWithTwoTurnaroundHoursFinishesOnTheSameDay() {
        var submissionTime = now().with(next(MONDAY)).atTime(12, 25);
        var turnaroundHours = 2;
        var expected = submissionTime.plus(turnaroundHours, HOURS);
        var result = sut.calculateDueDate(submissionTime, turnaroundHours);
        assertEquals(expected, result);
    }

    @Test
    public void issueWithEightTurnaroundHoursFinishesOnTheNextWorkDayMonday() {
        var submissionTime = now().with(next(MONDAY)).atTime(12, 25);
        var turnaroundHours = 8;
        var expected = submissionTime.plus(24, HOURS);
        var result = sut.calculateDueDate(submissionTime, turnaroundHours);
        assertEquals(expected, result);
    }

    @Test
    public void issueWithEightTurnaroundHoursFinishesOnTheNextWorkDayFriday() {
        var submissionTime = now().with(next(FRIDAY)).atTime(12, 25);
        var turnaroundHours = 8;
        var expected = submissionTime.plus(24 * 3, HOURS);
        var result = sut.calculateDueDate(submissionTime, turnaroundHours);
        assertEquals(expected, result);
    }

    @Test
    public void issueWithSubmissionTimeAndTurnaroundAsSpecified() {
        var submissionTime = now().with(next(TUESDAY)).atTime(14, 12);
        var turnaroundHours = 2 * 8;
        var expected = now().with(next(THURSDAY)).atTime(14, 12);
        var result = sut.calculateDueDate(submissionTime, turnaroundHours);
        assertEquals(expected, result);
    }

    @Test
    public void issueWithTwoWeeksTwoDaysAndTwoHours() {
        var submissionTime = now().with(next(TUESDAY)).atTime(14, 12);
        var turnaroundHours = 2 * (5 * 8) + 1 * 8 + 2;
        var expected = submissionTime.plus(2, WEEKS).plus(1, DAYS).plus(2, HOURS);
        var result = sut.calculateDueDate(submissionTime, turnaroundHours);
        assertEquals(expected, result);
    }

    @Test
    public void issueWithTwoWeeksThreeDaysAndOneHours() {
        var submissionTime = now().with(next(TUESDAY)).atTime(14, 12);
        var turnaroundHours = 2 * (5 * 8) + 3 * 8 + 1;
        var expected = submissionTime.plus(2, WEEKS).plus(3, DAYS).plus(1, HOURS);
        var result = sut.calculateDueDate(submissionTime, turnaroundHours);
        assertEquals(expected, result);
    }

    @Test
    public void issueWithTwoWeeksThreeDaysAndFiveHours() {
        var submissionTime = now().with(next(TUESDAY)).atTime(14, 12);
        var turnaroundHours = 2 * (5 * 8) + 3 * 8 + 5;
        var expected = submissionTime.plus(2, WEEKS).plus(3, DAYS)
                .plus(3,DAYS).minus(8,HOURS) // weekend
                .plus(5, HOURS);
        var result = sut.calculateDueDate(submissionTime, turnaroundHours);
        assertEquals(expected, result);
    }
}