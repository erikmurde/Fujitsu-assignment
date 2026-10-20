package com.fujitsu.app.controller;

import com.fujitsu.app.domain.ExtraFee;
import com.fujitsu.app.domain.exception.AppException;
import com.fujitsu.app.service.ExtraFeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing extra fees related to weather conditions.
 */
@RestController
public class ExtraFeeController {

    private final ExtraFeeService service;

    public ExtraFeeController(ExtraFeeService service) {
        this.service = service;
    }

    /**
     * Endpoint for retrieving all extra fees from database.
     *
     * @return list of extra fees
     */
    @GetMapping("api/extra-fee")
    public List<ExtraFee> getAll() {
        return service.getAll();
    }

    /**
     * Endpoint for retrieving the extra fee with the given id.
     *
     * @param id unique identifier of the object
     * @return extra fee with the given id if it exists
     */
    @GetMapping("api/extra-fee/{id}")
    public ExtraFee getById(@PathVariable Long id) {
        return service
                .getById(id)
                .orElseThrow(() -> new AppException("No extra fee with given id", HttpStatus.NOT_FOUND));
    }

    /**
     * Endpoint for creating a new extra fee with the provided values.
     *
     * @param fee values to add to new object
     * @return id of created object
     */
    @PostMapping("api/extra-fee")
    public Long add(@RequestBody @Valid ExtraFee fee) {
        return service.create(fee);
    }

    /**
     * Endpoint for updating the extra fee with the given id.
     *
     * @param id unique identifier of the object
     * @param fee values to replace in existing object
     */
    @PutMapping("api/extra-fee/{id}")
    public void update(@PathVariable Long id, @RequestBody @Valid ExtraFee fee) {
        service.update(id, fee);
    }

    /**
     * Endpoint for deleting the extra fee with the given id.
     *
     * @param id unique identifier of the object
     */
    @DeleteMapping("api/extra-fee/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
