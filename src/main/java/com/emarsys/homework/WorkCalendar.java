package com.emarsys.homework;

import java.time.Duration;
import java.time.LocalDateTime;

public interface WorkCalendar {
    Duration howMuchWorkCanBeDoneToday(LocalDateTime submission);

    LocalDateTime nextAvailableWorkingDateTime(LocalDateTime submission);

    void validate(LocalDateTime submission);

    void validate(Duration turnaround);
}
