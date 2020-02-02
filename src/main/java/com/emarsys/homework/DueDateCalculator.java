package com.emarsys.homework;

import java.time.LocalDateTime;

public interface DueDateCalculator {
    /**
     * @param submission      Issue submission date/time
     * @param turnaroundHours Issue turnaround time in hours
     * @return the date/time when the issue is resolved
     */
    LocalDateTime calculateDueDate(LocalDateTime submission, int turnaroundHours);
}
