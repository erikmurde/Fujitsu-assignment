package com.fujitsu.app.repository;

import com.fujitsu.app.domain.City;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for fetching City objects from database.
 */
@Repository
public interface CityRepository extends CrudRepository<City, Long> {

    Optional<City> findByName(String name);
}
