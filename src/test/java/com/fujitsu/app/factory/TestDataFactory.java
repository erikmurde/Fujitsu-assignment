package com.fujitsu.app.factory;

import com.fujitsu.app.domain.ExtraFee;
import com.fujitsu.app.domain.Observation;
import com.fujitsu.app.domain.RegionalBaseFee;

import java.time.Instant;

/**
 * Factory class for creating data used in tests.
 */
public class TestDataFactory {

    /**
     * Creates a test Observation object with given values (and some hardcoded ones).
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
     * Creates a test Observation object with a given weather phenomenon.
     *
     * @param phenomenon weather phenomenon of observation
     * @return created Observation object
     */
    public static Observation createObservationPhenomenon(String phenomenon) {
        return createObservation(10, 0, phenomenon);
    }

    /**
     * Creates a test Observation object with a given temperature.
     *
     * @param airTemperature temperature of observation
     * @return created Observation object
     */
    public static Observation createObservationTemp(double airTemperature) {
        return createObservation(airTemperature, 0, "");
    }

    /**
     * Creates a test Observation object with a given wind speed.
     *
     * @param windSpeed wind speed of observation
     * @return created Observation object
     */
    public static Observation createObservationWind(double windSpeed) {
        return createObservation(10, windSpeed, "");
    }

    /**
     * Creates a test RegionalBaseFee object with hardcoded values.
     *
     * @return created RegionalBaseFee object
     */
    public static RegionalBaseFee createRegionalBaseFee() {
        return new RegionalBaseFee(1L, 1L, 1L, 10);
    }

    /**
     * Creates a test ExtraFee object with given values.
     *
     * @param condition business rule to evaluate weather observations against
     * @param amount extra fee to add to delivery fee total
     * @param errorMessage message to show in case matching the condition requires an error to be thrown
     * @return created ExtraFee object
     */
    public static ExtraFee createExtraFee(String condition, double amount, String errorMessage) {
        return new ExtraFee(null, null, condition, amount, errorMessage);
    }

    /**
     * Creates a test ExtraFee object with hardcoded values.
     *
     * @return created ExtraFee object
     */
    public static ExtraFee createExtraFee() {
        ExtraFee fee = createExtraFee("windSpeed > 5", 10, "");
        fee.setId(1L);
        fee.setVehicleTypeId(1L);

        return fee;
    }
}
