package com.emarsys.homework;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Defines the Calendar for DueDayCalculator. It's implementation defines, which days are workdays,
 * holidays, when does the work start and end.
 */
public interface WorkCalendar {

    /**
     * Example: if the submission day is '2020-jan-4 Monday 13:00' and the workday lasts until 17:00,
     * the method will return with a duration of 4 hours.
     *
     * @param submission LocalDateTime, when a task is submitted/started
     * @return Duration of how much work can be done at the same day
     */
    Duration howMuchWorkCanBeDoneToday(LocalDateTime submission);

    /**
     * Example: if the submission day is '2020-jan-4 Monday 17:00' and the workday lasts until 17:00,
     * and the next workday starts 9:00,
     * the method will return with the value '2020-jan-4 Tuesday 9:00'
     *
     * @param submission LocalDateTime, when the task is submitted/started
     * @return LocalDateTime start of the next available workwindow
     */
    LocalDateTime nextAvailableWorkingDateTime(LocalDateTime submission);

    /**
     * @param submission can the work start at this time? If not, throws exceptions.
     */
    void validate(LocalDateTime submission);

    /**
     * @param turnaround If it's not valid, throws exceptions.
     */
    void validate(Duration turnaround);
}
