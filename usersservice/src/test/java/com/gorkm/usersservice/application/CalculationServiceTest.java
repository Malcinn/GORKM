package com.gorkm.usersservice.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CalculationServiceTest {

    public final CalculationService underTests = new CalculationServiceImpl();

    @Test
    public void shouldReturnCalculationResult() {
        UserResponse userResponse = new UserResponse("007", "test",
                "The Test", "User", "https://some.url",
                "2015-02-23T16:11:01Z", 6, 6);
        Double result = underTests.calculate(userResponse);
        Assertions.assertNotNull(result);
        Assertions.assertEquals("8.0", String.format("%.1f", result));
    }

    @Test
    public void shouldReturnCalculationDoubleResult() {
        UserResponse userResponse = new UserResponse("007", "test",
                "The Test", "User", "https://some.url",
                "2015-02-23T16:11:01Z", 458698, 6);
        Double result = underTests.calculate(userResponse);
        Assertions.assertNotNull(result);
        Assertions.assertEquals("0.000104644", String.format("%.9f", result));
    }

    @Test
    public void shouldReturnNullIfFollowersCountIsZero() {
        UserResponse userResponse = new UserResponse("007", "test",
                "The Test", "User", "https://some.url",
                "2015-02-23T16:11:01Z", 0, 6);
        Double result = underTests.calculate(userResponse);
        Assertions.assertNull(result);
    }
}
