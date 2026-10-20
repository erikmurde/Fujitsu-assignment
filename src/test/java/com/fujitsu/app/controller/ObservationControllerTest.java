package com.fujitsu.app.controller;

import com.fujitsu.app.BaseTest;
import com.fujitsu.app.domain.Observation;
import com.fujitsu.app.repository.ObservationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;

import static com.fujitsu.app.factory.TestDataFactory.createObservationTemp;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Class for testing weather observation controller.
 */
public class ObservationControllerTest extends BaseTest {

    private static final String PATH = "observations/latest?city={city}";

    @Autowired
    private ObservationRepository repository;

    @Test void testNoObservationsFound() throws Exception {
        performGet(PATH, "TARTU")
                .andExpect(status().isNotFound())
                .andExpect(content().string("No observations found"));
    }

    @Test
    void testGetLatestObservation() throws Exception {
        Observation oldObservation = createObservationTemp(10);
        Observation newObservation = createObservationTemp(20);

        oldObservation.setTimestamp(10L);
        newObservation.setTimestamp(20L);

        repository.save(oldObservation);
        repository.save(newObservation);

        performGet(PATH, "TARTU")
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.airTemperature").value(20));
    }

    @Test
    void testGetLatestObservationWithCustomDatetime() throws Exception {
        String oldDatetime = "2026-10-05T00:00:00Z";
        String newDatetime = "2026-10-10T00:00:00Z";

        Observation oldObservation = createObservationTemp(10);
        Observation newObservation = createObservationTemp(20);

        oldObservation.setTimestamp(Instant.parse(oldDatetime).getEpochSecond());
        newObservation.setTimestamp(Instant.parse(newDatetime).getEpochSecond());

        repository.save(oldObservation);
        repository.save(newObservation);

        performGet(PATH + "&datetime={datetime}", "TARTU", oldDatetime)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.airTemperature").value(10));
    }
}
