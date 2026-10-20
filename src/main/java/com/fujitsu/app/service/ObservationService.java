package com.fujitsu.app.service;

import com.fujitsu.app.domain.Observation;
import com.fujitsu.app.domain.exception.AppException;
import com.fujitsu.app.dto.ObservationDto;
import com.fujitsu.app.dto.WeatherDataDto;
import com.fujitsu.app.repository.CityRepository;
import com.fujitsu.app.repository.ObservationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tools.jackson.dataformat.xml.XmlMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

/**
 * Service for managing data related to weather observations.
 */
@Service
public class ObservationService {

    private static final Map<String, String > OBSERVATION_TO_CITY_MAP = Map.of(
            "Tallinn-Harku", "TALLINN",
            "Tartu-Tõravere", "TARTU",
            "Pärnu", "PÄRNU"
    );

    private final ObservationRepository observationRepository;

    private final CityRepository cityRepository;

    public ObservationService(ObservationRepository observationRepository, CityRepository cityRepository) {
        this.observationRepository = observationRepository;
        this.cityRepository = cityRepository;
    }

    /**
     * Method for retrieving the latest weather observation in the given city.
     *
     * @param city location of the observation
     * @param datetime specifies the latest possible time of the returned observation
     * @return latest weather observation in the city
     */
    public Optional<Observation> getLatestObservation(String city, Optional<Date> datetime) {
        String name = city;

        for (String nameFromMap: OBSERVATION_TO_CITY_MAP.keySet()) {
            if (OBSERVATION_TO_CITY_MAP.get(nameFromMap).equals(city.toUpperCase())) {
                name = nameFromMap;
            }
        }
        if (datetime.isEmpty()) {
            return observationRepository.findLatest(name);
        }
        long timestamp = datetime.get().getTime() / 1000;
        return observationRepository.findLatestBeforeTimestamp(name, timestamp);
    }

    /**
     * Method for fetching latest weather data from the external API and saving it to database.
     * This method is run periodically based on the cronjob schedule (defined in application.properties).
     */
    @Scheduled(cron = "${cronjob.schedule}")
    public void fetchAndSaveObservationData() {
        try (HttpClient client = HttpClient.newHttpClient()) {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php"))
                    .header("Accept", "application/xml")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(
                    request, HttpResponse.BodyHandlers.ofString()
            );
            WeatherDataDto data = deserializeXml(response.body());
            saveObservations(data);

        } catch (Exception ex) {
            throw new AppException("Error fetching data: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void saveObservations(WeatherDataDto data) {
        for (ObservationDto dto: data.getObservations()) {
            String name = dto.getName();

            if (OBSERVATION_TO_CITY_MAP.containsKey(name)) {
                saveObservation(dto, OBSERVATION_TO_CITY_MAP.get(name), data.getTimestamp());
            }
        }
    }

    private void saveObservation(ObservationDto dto, String cityName, Long timestamp) {
        Long cityId = cityRepository
                .findByName(cityName)
                .orElseThrow(() -> new AppException("Missing city: " + cityName, HttpStatus.INTERNAL_SERVER_ERROR))
                .getId();

        Observation observation = new Observation();

        observation.setName(dto.getName());
        observation.setWmoCode(dto.getWmoCode());
        observation.setAirTemperature(dto.getAirTemperature());
        observation.setWindSpeed(dto.getWindSpeed());
        observation.setPhenomenon(dto.getPhenomenon());
        observation.setTimestamp(timestamp);
        observation.setCityId(cityId);

        observationRepository.save(observation);
    }

    private WeatherDataDto deserializeXml(String xml) {
        XmlMapper mapper = new XmlMapper();
        return mapper.readValue(xml, WeatherDataDto.class);
    }
}
