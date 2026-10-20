package com.fujitsu.app.domain;

import com.fujitsu.app.domain.exception.AppException;
import org.junit.jupiter.api.Test;
import org.springframework.expression.ExpressionException;

import static com.fujitsu.app.factory.TestDataFactory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Class for testing evaluateCondition method in ExtraFee class.
 */
public class ExtraFeeTest {

    @Test
    void testExtraFeeAirTemperature() {
        Observation observation = createObservationTemp(-20);
        ExtraFee fee = createExtraFee("airTemperature < -10", 10, "");

        assertEquals(10, fee.evaluateCondition(observation));

        observation.setAirTemperature(0d);

        assertEquals(0, fee.evaluateCondition(observation));
    }

    @Test
    void testExtraFeeWindSpeed() {
        Observation observation = createObservationWind(10);
        ExtraFee fee = createExtraFee("windSpeed > 5", 10, "");

        assertEquals(10, fee.evaluateCondition(observation));

        observation.setWindSpeed(0d);

        assertEquals(0, fee.evaluateCondition(observation));
    }

    @Test
    void testExtraFeeWeatherPhenomenon() {
        Observation observation = createObservationPhenomenon("Moderate snowfall");
        ExtraFee fee = createExtraFee("phenomenon.contains('snow')", 10, "");

        assertEquals(10, fee.evaluateCondition(observation));

        observation.setPhenomenon("Clear");

        assertEquals(0, fee.evaluateCondition(observation));
    }

    @Test
    void testExtraFeeWithErrorMessage() {
        Observation observation = createObservationWind(30);
        ExtraFee fee = createExtraFee("windSpeed > 20", 0, "Too much wind");

        assertThrows(
                AppException.class,
                () -> fee.evaluateCondition(observation));
    }

    @Test
    void testExtraFeeInvalidCondition() {
        Observation observation = createObservationTemp(0);
        ExtraFee fee = createExtraFee("airTemperature < 'a'", 10, "");

        assertThrows(
                ExpressionException.class,
                () -> fee.evaluateCondition(observation));
    }
}
