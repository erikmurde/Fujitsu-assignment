package com.fujitsu.app.repository;

import com.fujitsu.app.domain.Observation;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository managing database operations for weather observations.
 */
@Repository
public interface ObservationRepository extends CrudRepository<Observation, Long> {

    @Query("SELECT * FROM OBSERVATION " +
            "WHERE name = :name " +
            "AND TIMESTAMP = (SELECT MAX(timestamp) FROM OBSERVATION WHERE name = :name)"
    )
    Optional<Observation> findLatestObservation(@Param("name") String name);
}
