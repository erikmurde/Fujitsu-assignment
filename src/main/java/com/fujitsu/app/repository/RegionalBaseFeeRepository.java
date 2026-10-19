package com.fujitsu.app.repository;

import com.fujitsu.app.domain.RegionalBaseFee;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegionalBaseFeeRepository extends CrudRepository<RegionalBaseFee, Long> {

    @Query("SELECT * FROM regional_base_fee " +
            "WHERE city_id = :cityId AND vehicle_type_id = :vehicleTypeId"
    )
    Optional<RegionalBaseFee> findByCityIdAndVehicleId(
            @Param("cityId") Long cityId,
            @Param("vehicleTypeId") Long vehicleTypeId);
}
