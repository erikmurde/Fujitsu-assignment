package com.fujitsu.app.service;

import com.fujitsu.app.domain.exception.AppException;
import com.fujitsu.app.repository.CityRepository;
import com.fujitsu.app.repository.RegionalBaseFeeRepository;
import com.fujitsu.app.repository.VehicleTypeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * Service for performing delivery fee calculations.
 */
@Service
public class DeliveryFeeService {

    private final CityRepository cityRepository;

    private final VehicleTypeRepository vehicleTypeRepository;

    private final RegionalBaseFeeRepository regionalBaseFeeRepository;

    public DeliveryFeeService(
            CityRepository cityRepository,
            VehicleTypeRepository vehicleTypeRepository,
            RegionalBaseFeeRepository regionalBaseFeeRepository
    ) {
        this.cityRepository = cityRepository;
        this.vehicleTypeRepository = vehicleTypeRepository;
        this.regionalBaseFeeRepository = regionalBaseFeeRepository;
    }

    /**
     * Method for calculating delivery fee according to user input.
     * Currently only the regional fee portion of the calculation is developed
     *
     * @param city location of delivery
     * @param vehicle method of transportation used
     * @return calculated delivery fee
     */
    public Double calculateDeliveryFee(String city, String vehicle) {
        Long cityId = cityRepository
                .findByName(city.toUpperCase())
                .orElseThrow(() -> new AppException("Invalid city name", HttpStatus.BAD_REQUEST))
                .getId();

        Long vehicleTypeId = vehicleTypeRepository
                .findByName(vehicle.toUpperCase())
                .orElseThrow(() -> new AppException("Invalid vehicle type", HttpStatus.BAD_REQUEST))
                .getId();

        return regionalBaseFeeRepository
                .findByCityIdAndVehicleId(cityId, vehicleTypeId)
                .orElseThrow(() -> new AppException("No regional fee found", HttpStatus.NOT_FOUND))
                .getAmount();
    }
}
