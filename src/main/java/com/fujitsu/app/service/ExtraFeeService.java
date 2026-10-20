package com.fujitsu.app.service;

import com.fujitsu.app.domain.ExtraFee;
import com.fujitsu.app.domain.Observation;
import com.fujitsu.app.domain.exception.AppException;
import com.fujitsu.app.repository.ExtraFeeRepository;
import com.fujitsu.app.repository.VehicleTypeRepository;
import com.fujitsu.app.service.base.CrudService;
import org.springframework.expression.ExpressionException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service for managing extra fees related to weather conditions.
 */
@Service
public class ExtraFeeService implements CrudService<ExtraFee> {

    private final ExtraFeeRepository extraFeeRepository;

    private final VehicleTypeRepository vehicleTypeRepository;

    public ExtraFeeService(ExtraFeeRepository extraFeeRepository, VehicleTypeRepository vehicleTypeRepository) {
        this.extraFeeRepository = extraFeeRepository;
        this.vehicleTypeRepository = vehicleTypeRepository;
    }

    /**
     * Retrieves all weather related extra fees from database.
     *
     * @return list of extra fees
     */
    public List<ExtraFee> getAll() {
        List<ExtraFee> fees = new ArrayList<>();

        for (ExtraFee fee: extraFeeRepository.findAll()) {
            fees.add(fee);
        }
        return fees;
    }

    /**
     * Retrieves the weather related extra fee with the given id.
     *
     * @param id unique identifier of the object
     * @return extra fee with the given id if it exists
     */
    public Optional<ExtraFee> getById(Long id) {
        return extraFeeRepository.findById(id);
    }

    /**
     * Creates a new weather related extra fee from the provided values.
     *
     * @param fee values to add to new object
     * @return id of created object
     */
    public Long create(ExtraFee fee) {
        fee.setId(null);

        validateVehicleType(fee.getVehicleTypeId());
        validateCondition(fee);

        return extraFeeRepository.save(fee).getId();
    }

    /**
     * Updates the weather related extra fee with the given id using provided values.
     *
     * @param id unique identifier of the object
     * @param fee values to replace in existing object
     */
    public void update(Long id, ExtraFee fee) {
        ExtraFee fromDb = extraFeeRepository
                .findById(id)
                .orElseThrow(() -> new AppException("No extra fee with given id", HttpStatus.NOT_FOUND));

        validateVehicleType(fee.getVehicleTypeId());
        validateCondition(fee);

        fromDb.setVehicleTypeId(fee.getVehicleTypeId());
        fromDb.setCondition(fee.getCondition());
        fromDb.setAmount(fee.getAmount());

        extraFeeRepository.save(fromDb);
    }

    /**
     * Deletes the weather related extra fee with the given id.
     *
     * @param id unique identifier of the object
     */
    public void delete(Long id) {
        if (extraFeeRepository.findById(id).isEmpty()) {
            throw new AppException("No extra fee with given id", HttpStatus.NOT_FOUND);
        }
        extraFeeRepository.deleteById(id);
    }

    private void validateVehicleType(Long id) {
        vehicleTypeRepository
                .findById(id)
                .orElseThrow(() -> new AppException("Invalid vehicle type id", HttpStatus.BAD_REQUEST));
    }

    private void validateCondition(ExtraFee fee) {
        try {
            Observation validationTest = new Observation(
                    1L, 1L, "", 0, 0d, 0d, "", 1L
            );
            fee.evaluateCondition(validationTest);

        } catch (ExpressionException ex) {
            throw new AppException("Invalid condition", HttpStatus.BAD_REQUEST);
        }
    }
}
