package com.fujitsu.app.controller;

import com.fujitsu.app.domain.Observation;
import com.fujitsu.app.domain.exception.AppException;
import com.fujitsu.app.service.ObservationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for managing weather observations.
 */
@RestController
public class ObservationController {

    private final ObservationService service;

    public ObservationController(ObservationService service) {
        this.service = service;
    }

    /**
     * Retrieves the latest weather observation in the given city.
     *
     * @param city location of the observation
     * @return latest weather observation in the given city
     */
    @GetMapping("api/observations/latest")
    public Observation getLatestObservation(@RequestParam String city) {
        return service
                .getLatestObservation(city)
                .orElseThrow(() -> new AppException("No observations found", HttpStatus.NOT_FOUND));
    }
}
