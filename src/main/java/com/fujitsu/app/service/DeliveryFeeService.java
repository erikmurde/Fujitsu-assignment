package com.fujitsu.app.service;

import com.fujitsu.app.domain.ExtraFee;
import com.fujitsu.app.domain.Observation;
import com.fujitsu.app.domain.exception.AppException;
import com.fujitsu.app.repository.CityRepository;
import com.fujitsu.app.repository.ExtraFeeRepository;
import com.fujitsu.app.repository.RegionalBaseFeeRepository;
import com.fujitsu.app.repository.VehicleTypeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service for performing delivery fee calculations.
 */
@Service
public class DeliveryFeeService {

    private final CityRepository cityRepository;

    private final VehicleTypeRepository vehicleTypeRepository;

    private final RegionalBaseFeeRepository regionalBaseFeeRepository;

    private final ExtraFeeRepository extraFeeRepository;

    public DeliveryFeeService(
            CityRepository cityRepository,
            VehicleTypeRepository vehicleTypeRepository,
            RegionalBaseFeeRepository regionalBaseFeeRepository,
            ExtraFeeRepository extraFeeRepository
    ) {
        this.cityRepository = cityRepository;
        this.vehicleTypeRepository = vehicleTypeRepository;
        this.regionalBaseFeeRepository = regionalBaseFeeRepository;
        this.extraFeeRepository = extraFeeRepository;
    }

    /**
     * Method for calculating delivery fee according to user input.
     * The calculation sums up the regional base fee of the city with extra fees related to weather conditions.
     * If there are no observations then only the regional base fee will be returned.
     *
     * @param city location of delivery
     * @param vehicle method of transportation used
     * @param observation latest weather observation in given location
     * @return calculated delivery fee
     */
    public Double calculateDeliveryFee(String city, String vehicle, Optional<Observation> observation) {
        Long cityId = cityRepository
                .findByName(city.toUpperCase())
                .orElseThrow(() -> new AppException("Invalid city name", HttpStatus.BAD_REQUEST))
                .getId();

        Long vehicleTypeId = vehicleTypeRepository
                .findByName(vehicle.toUpperCase())
                .orElseThrow(() -> new AppException("Invalid vehicle type", HttpStatus.BAD_REQUEST))
                .getId();

        double baseFee = regionalBaseFeeRepository
                .findByCityIdAndVehicleId(cityId, vehicleTypeId)
                .orElseThrow(() -> new AppException("No regional fee found", HttpStatus.NOT_FOUND))
                .getAmount();

        return observation
                .map(value -> baseFee + calculateExtraFees(vehicleTypeId, value))
                .orElse(baseFee);
    }

    private double calculateExtraFees(Long vehicleTypeId, Observation observation) {
        Iterable<ExtraFee> fees = extraFeeRepository.findByVehicleTypeId(vehicleTypeId);
        double total = 0;

        for (ExtraFee fee: fees) {
            total += fee.evaluateCondition(observation);
        }
        return total;
    }
}
