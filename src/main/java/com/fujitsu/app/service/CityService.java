package com.fujitsu.app.service;

import com.fujitsu.app.domain.City;
import com.fujitsu.app.domain.exception.AppException;
import com.fujitsu.app.repository.CityRepository;
import com.fujitsu.app.service.base.CrudService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service for managing cities.
 */
@Service
public class CityService implements CrudService<City> {

    private final CityRepository repository;

    public CityService(CityRepository repository) {
        this.repository = repository;
    }

    /**
     * Retrieves all cities from database.
     *
     * @return list of cities
     */
    public List<City> getAll() {
        List<City> cities = new ArrayList<>();

        for (City city: repository.findAll()) {
            cities.add(city);
        }
        return cities;
    }

    /**
     * Retrieves the city with the given id.
     *
     * @param id unique identifier of the object
     * @return extra fee with the given id if it exists
     */
    public Optional<City> getById(Long id) {
        return repository.findById(id);
    }

    /**
     * Creates a city from the provided values.
     *
     * @param city values to add to new object
     * @return id of created object
     */
    public Long create(City city) {
        city.setId(null);

        if (repository.findByName(city.getName()).isPresent()) {
            throw new AppException("A city with the given name already exists", HttpStatus.BAD_REQUEST);
        }
        return repository.save(city).getId();
    }

    /**
     * Updates the city with the given id using provided values.
     *
     * @param id unique identifier of the object
     * @param city values to replace in existing object
     */
    public void update(Long id, City city) {
        City fromDb = repository
                .findById(id)
                .orElseThrow(() -> new AppException("No city with given id", HttpStatus.NOT_FOUND));

        fromDb.setName(city.getName());

        Optional<City> existing = repository.findByName(city.getName());

        if (existing.isPresent() && !existing.get().getId().equals(fromDb.getId())) {
            throw new AppException("A city with the given name already exists", HttpStatus.BAD_REQUEST);
        }
        repository.save(fromDb);
    }

    /**
     * Deletes the city with the given id.
     *
     * @param id unique identifier of the object
     */
    public void delete(Long id) {
        if (repository.findById(id).isEmpty()) {
            throw new AppException("No city with given id", HttpStatus.NOT_FOUND);
        }
        repository.deleteById(id);
    }
}
