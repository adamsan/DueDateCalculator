package com.emarsys.homework;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GreeterTest {
    private Greeter sut;

    @Before
    public void setup() {
        sut = new Greeter();
    }

    @Test
    public void greet() {
        var expected = "Hello Adam";
        var result = sut.greet("Adam");
        assertEquals(expected, result);
    }
}