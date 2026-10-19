package com.fujitsu.app.controller;

import com.fujitsu.app.domain.RegionalBaseFee;
import com.fujitsu.app.service.RegionalFeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing regional base fees (RBF).
 */
@RestController
public class RegionalFeeController {

    private final RegionalFeeService service;

    public RegionalFeeController(RegionalFeeService service) {
        this.service = service;
    }

    /**
     * Endpoint for retrieving all regional base fees.
     *
     * @return list of regional base fees
     */
    @GetMapping("api/regional-fee")
    public List<RegionalBaseFee> getAll() {
        return service.getAll();
    }

    /**
     * Endpoint for retrieving the regional base fee with the given id.
     *
     * @param id unique identifier of the object
     * @return regional base fee with the given id if it exists
     */
    @GetMapping("api/regional-fee/{id}")
    public RegionalBaseFee getById(@PathVariable Long id) {
        return service.getById(id);
    }

    /**
     * Endpoint for creating a new regional base fee.
     *
     * @param fee values to add to new object
     * @return id of created object
     */
    @PostMapping("api/regional-fee")
    public Long add(@RequestBody RegionalBaseFee fee) {
        return service.create(fee);
    }

    /**
     * Endpoint for updating the regional base fee with the given id.
     *
     * @param id unique identifier of the object
     * @param fee values to replace in existing object
     */
    @PutMapping("api/regional-fee/{id}")
    public void update(@PathVariable Long id, @RequestBody RegionalBaseFee fee) {
        service.update(id, fee);
    }

    /**
     * Endpoint for deleting the regional base fee with the given id.
     *
     * @param id unique identifier of the object
     */
    @DeleteMapping("api/regional-fee/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
