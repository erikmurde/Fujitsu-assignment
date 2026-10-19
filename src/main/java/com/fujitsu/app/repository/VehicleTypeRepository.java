package com.fujitsu.app.repository;

import com.fujitsu.app.domain.VehicleType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository managing database operations for vehicle types.
 */
@Repository
public interface VehicleTypeRepository extends CrudRepository<VehicleType, Long> {

    Optional<VehicleType> findByName(String name);
}
