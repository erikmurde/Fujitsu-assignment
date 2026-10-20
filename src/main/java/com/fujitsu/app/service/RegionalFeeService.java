package com.fujitsu.app.service;

import com.fujitsu.app.domain.RegionalBaseFee;
import com.fujitsu.app.domain.exception.AppException;
import com.fujitsu.app.repository.CityRepository;
import com.fujitsu.app.repository.RegionalBaseFeeRepository;
import com.fujitsu.app.repository.VehicleTypeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service for managing data related to regional base fees.
 */
@Service
public class RegionalFeeService {

    private final RegionalBaseFeeRepository regionalBaseFeeRepository;

    private final CityRepository cityRepository;

    private final VehicleTypeRepository vehicleTypeRepository;

    public RegionalFeeService(
            RegionalBaseFeeRepository regionalBaseFeeRepository,
            CityRepository cityRepository,
            VehicleTypeRepository vehicleTypeRepository)
    {
        this.regionalBaseFeeRepository = regionalBaseFeeRepository;
        this.cityRepository = cityRepository;
        this.vehicleTypeRepository = vehicleTypeRepository;
    }

    /**
     * Retrieves all regional base fees from database.
     *
     * @return list of regional base fees
     */
    public List<RegionalBaseFee> getAll() {
        List<RegionalBaseFee> fees = new ArrayList<>();

        for (RegionalBaseFee fee: regionalBaseFeeRepository.findAll()) {
            fees.add(fee);
        }
        return fees;
    }

    /**
     * Retrieves the regional base fee with the given id.
     *
     * @param id unique identifier of the object
     * @return regional base fee with the given id if it exists
     */
    public Optional<RegionalBaseFee> getById(Long id) {
        return regionalBaseFeeRepository.findById(id);
    }

    /**
     * Creates a new regional base fee from the provided values.
     *
     * @param fee values to add to new object
     * @return id of created object
     */
    public Long create(RegionalBaseFee fee) {
        fee.setId(null);

        validateCity(fee.getCityId());
        validateVehicleType(fee.getVehicleTypeId());

        if (getExisting(fee.getCityId(), fee.getVehicleTypeId()).isPresent()) {
            throw new AppException("Another regional fee with given city and vehicle exists", HttpStatus.BAD_REQUEST);
        }
        return regionalBaseFeeRepository.save(fee).getId();
    }

    /**
     * Updates the regional base fee with the given id using provided values.
     *
     * @param id unique identifier of the object
     * @param fee values to replace in existing object
     */
    public void update(Long id, RegionalBaseFee fee) {
        RegionalBaseFee fromDb = regionalBaseFeeRepository
                .findById(id)
                .orElseThrow(() -> new AppException("No regional fee with given id", HttpStatus.NOT_FOUND));

        validateCity(fee.getCityId());
        validateVehicleType(fee.getVehicleTypeId());

        Optional<RegionalBaseFee> existing = getExisting(fee.getCityId(), fee.getVehicleTypeId());

        if (existing.isPresent() && !existing.get().getId().equals(id)) {
            throw new AppException("Another regional fee with given city and vehicle exists", HttpStatus.BAD_REQUEST);
        }
        fromDb.setAmount(fee.getAmount());
        fromDb.setCityId(fee.getCityId());
        fromDb.setVehicleTypeId(fee.getVehicleTypeId());

        regionalBaseFeeRepository.save(fromDb);
    }

    /**
     * Deletes the regional base fee with the given id.
     *
     * @param id unique identifier of the object
     */
    public void delete(Long id) {
        if (regionalBaseFeeRepository.findById(id).isEmpty()) {
            throw new AppException("No regional fee with given id", HttpStatus.NOT_FOUND);
        }
        regionalBaseFeeRepository.deleteById(id);
    }

    private void validateCity(Long id) {
        cityRepository
                .findById(id)
                .orElseThrow(() -> new AppException("Invalid city id", HttpStatus.BAD_REQUEST));
    }

    private void validateVehicleType(Long id) {
        vehicleTypeRepository
                .findById(id)
                .orElseThrow(() -> new AppException("Invalid vehicle type id", HttpStatus.BAD_REQUEST));
    }

    private Optional<RegionalBaseFee> getExisting(Long cityId, Long vehicleTypeId) {
        return regionalBaseFeeRepository
                .findByCityIdAndVehicleId(cityId, vehicleTypeId);
    }
}
