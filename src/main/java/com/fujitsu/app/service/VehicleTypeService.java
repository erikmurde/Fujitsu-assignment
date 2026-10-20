package com.fujitsu.app.service;

import com.fujitsu.app.domain.VehicleType;
import com.fujitsu.app.domain.exception.AppException;
import com.fujitsu.app.repository.VehicleTypeRepository;
import com.fujitsu.app.service.base.CrudService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service for managing vehicle types.
 */
@Service
public class VehicleTypeService implements CrudService<VehicleType> {

    private final VehicleTypeRepository repository;

    public VehicleTypeService(VehicleTypeRepository repository) {
        this.repository = repository;
    }

    /**
     * Retrieves all vehicle types from database.
     *
     * @return list of vehicle types
     */
    public List<VehicleType> getAll() {
        List<VehicleType> vehicleTypes = new ArrayList<>();

        for (VehicleType vehicleType: repository.findAll()) {
            vehicleTypes.add(vehicleType);
        }
        return vehicleTypes;
    }

    /**
     * Retrieves the vehicle type with the given id.
     *
     * @param id unique identifier of the object
     * @return extra fee with the given id if it exists
     */
    public Optional<VehicleType> getById(Long id) {
        return repository.findById(id);
    }

    /**
     * Creates a vehicle type from the provided values.
     *
     * @param vehicleType values to add to new object
     * @return id of created object
     */
    public Long create(VehicleType vehicleType) {
        vehicleType.setId(null);

        if (repository.findByName(vehicleType.getName()).isPresent()) {
            throw new AppException("A vehicle type with the given name already exists", HttpStatus.BAD_REQUEST);
        }
        return repository.save(vehicleType).getId();
    }

    /**
     * Updates the vehicle type with the given id using provided values.
     *
     * @param id unique identifier of the object
     * @param vehicleType values to replace in existing object
     */
    public void update(Long id, VehicleType vehicleType) {
       VehicleType fromDb = repository
                .findById(id)
                .orElseThrow(() -> new AppException("No vehicle type with given id", HttpStatus.NOT_FOUND));

        fromDb.setName(vehicleType.getName());

        Optional<VehicleType> existing = repository.findByName(vehicleType.getName());

        if (existing.isPresent() && !existing.get().getId().equals(fromDb.getId())) {
            throw new AppException("A vehicle type with the given name already exists", HttpStatus.BAD_REQUEST);
        }
        repository.save(fromDb);
    }

    /**
     * Deletes the vehicle type with the given id.
     *
     * @param id unique identifier of the object
     */
    public void delete(Long id) {
        if (repository.findById(id).isEmpty()) {
            throw new AppException("No vehicle type with given id", HttpStatus.NOT_FOUND);
        }
        repository.deleteById(id);
    }
}
