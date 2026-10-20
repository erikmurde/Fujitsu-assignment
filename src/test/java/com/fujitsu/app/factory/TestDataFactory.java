package com.fujitsu.app.factory;

import com.fujitsu.app.domain.Observation;
import com.fujitsu.app.domain.RegionalBaseFee;

import java.time.Instant;

/**
 * Factory class for creating data used in tests.
 */
public class TestDataFactory {

    /**
     * Create a test Observation object with given values (and some hardcoded ones).
     *
     * @param airTemperature temperature of observation
     * @param windSpeed wind speed of observation
     * @param phenomenon weather phenomenon of observation
     * @return created Observation object
     */
    public static Observation createObservation(double airTemperature, double windSpeed, String phenomenon) {
        Observation observation = new Observation();

        observation.setCityId(1L);
        observation.setWmoCode(0);
        observation.setName("Tartu-Tõravere");
        observation.setAirTemperature(airTemperature);
        observation.setWindSpeed(windSpeed);
        observation.setPhenomenon(phenomenon);
        observation.setTimestamp(Instant.now().getEpochSecond());

        return observation;
    }

    /**
     * Create a test Observation object with a given weather phenomenon.
     *
     * @param phenomenon weather phenomenon of observation
     * @return created Observation object
     */
    public static Observation createObservationPhenomenon(String phenomenon) {
        return createObservation(10, 0, phenomenon);
    }

    /**
     * Create a test Observation object with a given temperature.
     *
     * @param airTemperature temperature of observation
     * @return created Observation object
     */
    public static Observation createObservationTemp(double airTemperature) {
        return createObservation(airTemperature, 0, "");
    }

    /**
     * Create a test Observation object with a given wind speed.
     *
     * @param windSpeed wind speed of observation
     * @return created Observation object
     */
    public static Observation createObservationWind(double windSpeed) {
        return createObservation(10, windSpeed, "");
    }

    /**
     * Create a test RegionalBaseFee object with hardcoded values.
     *
     * @return created RegionalBaseFee object
     */
    public static RegionalBaseFee createRegionalBaseFee() {
        return new RegionalBaseFee(1L, 1L, 1L, 10);
    }
}
