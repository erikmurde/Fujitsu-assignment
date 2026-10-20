package com.fujitsu.app.controller;

import com.fujitsu.app.BaseTest;
import com.fujitsu.app.domain.Observation;
import com.fujitsu.app.repository.ObservationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Instant;

import static com.fujitsu.app.factory.TestDataFactory.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Class for testing delivery fee controller.
 */
class DeliveryFeeControllerTest extends BaseTest {

	private static final String TARTU = "TARTU";

	private static final String CAR = "CAR";
	private static final String SCOOTER = "SCOOTER";
	private static final String BIKE = "BIKE";

	private static final String INVALID_CITY_MSG = "Invalid city name";
	private static final String INVALID_VEHICLE_MSG = "Invalid vehicle type";
	private static final String FORBIDDEN_VEHICLE_MSG = "Usage of selected vehicle type is forbidden";

	private static final String PATH = "delivery-fee?city={city}&vehicle={vehicle}";

	@Autowired
	private ObservationRepository repository;

	@Test
	void testInvalidCity() throws Exception {
		performBadRequest("INVALID", BIKE)
				.andExpect(content().string(INVALID_CITY_MSG));

		performBadRequest("", BIKE)
				.andExpect(content().string(INVALID_CITY_MSG));
	}

	@Test
	void testInvalidVehicle() throws Exception {
		performBadRequest(TARTU, "INVALID")
				.andExpect(content().string(INVALID_VEHICLE_MSG));

		performBadRequest(TARTU, "")
				.andExpect(content().string(INVALID_VEHICLE_MSG));
	}

	@Test
	void testNoObservations() throws Exception {
		performOk("TALLINN", CAR)
				.andExpect(content().string("4.0"));
		performOk(TARTU, SCOOTER)
				.andExpect(content().string("3.0"));
		performOk("PÄRNU", BIKE)
				.andExpect(content().string("2.0"));
	}

	@Test
	void testAirTemperature() throws Exception {
		Observation observation = createObservationTemp(-20);
		repository.save(observation);

		performOk(TARTU, SCOOTER)
				.andExpect(content().string("4.0"));
		performOk(TARTU, BIKE)
				.andExpect(content().string("3.5"));

		observation.setAirTemperature(-5d);
		repository.save(observation);

		performOk(TARTU, SCOOTER)
				.andExpect(content().string("3.5"));
		performOk(TARTU, BIKE)
				.andExpect(content().string("3.0"));
	}

	@Test
	void testWindSpeed() throws Exception {
		Observation observation = createObservationWind(12);
		repository.save(observation);

		performOk(TARTU, BIKE)
				.andExpect(content().string("3.0"));

		observation.setWindSpeed(30d);
		repository.save(observation);

		performBadRequest(TARTU, BIKE)
				.andExpect(content().string(FORBIDDEN_VEHICLE_MSG));
	}

	@Test
	void testWeatherPhenomenonSnow() throws Exception {
		repository.save(createObservationPhenomenon("Moderate snowfall"));

		performOk(TARTU, CAR)
				.andExpect(content().string("3.5"));
		performOk(TARTU, SCOOTER)
				.andExpect(content().string("4.0"));
		performOk(TARTU, BIKE)
				.andExpect(content().string("3.5"));
	}

	@Test
	void testWeatherPhenomenonSleet() throws Exception {
		repository.save(createObservationPhenomenon("Moderate sleet"));

		performOk(TARTU, CAR)
				.andExpect(content().string("3.5"));
		performOk(TARTU, SCOOTER)
				.andExpect(content().string("4.0"));
		performOk(TARTU, BIKE)
				.andExpect(content().string("3.5"));
	}

	@Test
	void testWeatherPhenomenonRain() throws Exception {
		Observation observation = createObservationPhenomenon("Moderate rain");
		repository.save(observation);

		performOk(TARTU, CAR)
				.andExpect(content().string("3.5"));
		performOk(TARTU, SCOOTER)
				.andExpect(content().string("3.5"));
		performOk(TARTU, BIKE)
				.andExpect(content().string("3.0"));
	}

	@Test
	void testWeatherPhenomenonRainShower() throws Exception {
		Observation observation = createObservationPhenomenon("Moderate shower");
		repository.save(observation);

		performOk(TARTU, CAR)
				.andExpect(content().string("3.5"));
		performOk(TARTU, SCOOTER)
				.andExpect(content().string("3.5"));
		performOk(TARTU, BIKE)
				.andExpect(content().string("3.0"));
	}

	@Test
	void testWeatherPhenomenonGlaze() throws Exception {
		repository.save(createObservationPhenomenon("Glaze"));

		performOk(TARTU, CAR)
				.andExpect(content().string("3.5"));

		performBadRequest(TARTU, SCOOTER)
				.andExpect(content().string(FORBIDDEN_VEHICLE_MSG));

		performBadRequest(TARTU, BIKE)
				.andExpect(content().string(FORBIDDEN_VEHICLE_MSG));
	}

	@Test
	void testWeatherPhenomenonHail() throws Exception {
		repository.save(createObservationPhenomenon("Hail"));

		performOk(TARTU, CAR)
				.andExpect(content().string("3.5"));

		performBadRequest(TARTU, SCOOTER)
				.andExpect(content().string(FORBIDDEN_VEHICLE_MSG));

		performBadRequest(TARTU, BIKE)
				.andExpect(content().string(FORBIDDEN_VEHICLE_MSG));
	}

	@Test
	void testExtraFeeAllTypes() throws Exception {
		repository.save(
				createObservation(-2.1, 4.7, "Light snow shower")
		);
		performOk(TARTU, BIKE)
				.andExpect(content().string("4.0"));
	}

	@Test
	void testOptionalDatetime() throws Exception {
		String oldTime = "2026-10-05T00:00:00Z";
		String newTime = "2026-10-10T00:00:00Z";

		Observation oldObservation = createObservationPhenomenon("Clear");
		Observation newObservation = createObservationPhenomenon("Moderate snowfall");

		oldObservation.setTimestamp(Instant.parse(oldTime).getEpochSecond());
		newObservation.setTimestamp(Instant.parse(newTime).getEpochSecond());

		repository.save(oldObservation);
		repository.save(newObservation);

		performGet(PATH + "&datetime={datetime}", TARTU, BIKE, oldTime)
				.andExpect(status().isOk())
				.andExpect(content().string("2.5"));

		performOk(TARTU, BIKE)
				.andExpect(content().string("3.5"));
	}

	private ResultActions performOk(String city, String vehicle) throws Exception {
		return performGet(PATH, city, vehicle)
				.andExpect(status().isOk());
	}

	private ResultActions performBadRequest(String city, String vehicle) throws Exception {
		return performGet(PATH, city, vehicle)
				.andExpect(status().isBadRequest());
	}
}
