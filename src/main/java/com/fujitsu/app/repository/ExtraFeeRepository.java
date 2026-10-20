package com.fujitsu.app.repository;

import com.fujitsu.app.domain.ExtraFee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository managing database operations for extra fees related to weather conditions.
 */
@Repository
public interface ExtraFeeRepository extends CrudRepository<ExtraFee, Long> {

    Iterable<ExtraFee> findByVehicleTypeId(Long vehicleTypeId);
}
