package com.emarsys.homework;

import org.junit.Before;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

public class DueDateCalculatorTest {

    private DueDateCalculator sut;

    @Before
    public void setup() {
        // Given sut (System Under Test) is DueDayCalculator
        sut = new DueDateCalculator();
    }

    @Test(expected = IllegalArgumentException.class)
    public void calculateDueDateShouldReceiveSubmissionDateTime() {
        // When submission is null;
        // Then calculateDueDate should throw IllegalArgumentException
        sut.calculateDueDate(null, 0);
    }

    @Test(expected = RuntimeException.class)
    public void issueCantSubmittedOnWeekendOnSaturday() {
        LocalDateTime saturday = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SATURDAY)).atTime(12, 30);
        sut.calculateDueDate(saturday, 0);
    }

    @Test(expected = RuntimeException.class)
    public void issueCantSubmittedOnWeekendOnSunday() {
        LocalDateTime sunday = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SUNDAY)).atTime(12, 30);
        sut.calculateDueDate(sunday, 0);
    }
}