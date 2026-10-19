package com.fujitsu.app.service;

import com.fujitsu.app.domain.Observation;
import com.fujitsu.app.repository.ObservationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service for retrieving and managing data related to weather observations.
 */
@Service
public class ObservationService {

    private final ObservationRepository observationRepository;

    public ObservationService(ObservationRepository observationRepository) {
        this.observationRepository = observationRepository;
    }

    /**
     * Method for retrieving the latest weather observation in the given city.
     *
     * @param city location of the observation
     * @return latest weather observation in the city
     */
    public Optional<Observation> getLatestObservation(String city) {
        String name = switch (city.toUpperCase()) {
            case "TALLINN" -> "Tallinn-Harku";
            case "TARTU" -> "Tartu-Tõravere";
            case "PÄRNU" -> "Pärnu";
            default -> city;
        };
        return observationRepository.findLatestObservation(name);
    }
}
