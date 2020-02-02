package com.emarsys.homework;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class DueDateCalculatorParametrizedTest {

    @Parameter
    public String submission;

    @Parameter(1)
    public int turnaroundHours;

    @Parameter(2)
    public String expectedDueDateTime;


    private IDueDateCalculator sut;

    /**
     * Able to parse string with format including the day, example: "2020-02-01 Sat 11:25"
     */
    private static LocalDateTime parse(String dateTime) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("y-MM-dd E H:mm", Locale.ENGLISH);
        return LocalDateTime.parse(dateTime, formatter);
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"2020-02-03 Mon 10:22", 0, "2020-02-03 Mon 10:22"},
                {"2020-02-03 Mon 10:22", 1, "2020-02-03 Mon 11:22"},
                {"2020-02-03 Mon 10:22", 4, "2020-02-03 Mon 14:22"},
                {"2020-02-04 Tue 09:00", 8, "2020-02-04 Tue 17:00"},
                {"2020-02-06 Thu 10:22", 5 * 8, "2020-02-13 Thu 10:22"},
                {"2020-02-03 Mon 10:22", 5 * 8 + 5, "2020-02-10 Mon 15:22"},
        });
    }

    @Before
    public void setUp() {
        sut = new GeneralDueDateCalculator();//new DueDateCalculator();
    }

    @Test
    public void calculateDueDate() {
        var result = sut.calculateDueDate(parse(submission), turnaroundHours);
        assertEquals(parse(expectedDueDateTime), result);
    }
}
