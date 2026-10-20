package com.fujitsu.app.controller;

import com.fujitsu.app.domain.Observation;
import com.fujitsu.app.service.DeliveryFeeService;
import com.fujitsu.app.service.ObservationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Controller for requesting delivery fees.
 */
@RestController
public class DeliveryFeeController {

    private final DeliveryFeeService deliveryFeeService;

    private final ObservationService observationService;

    public DeliveryFeeController(DeliveryFeeService deliveryFeeService, ObservationService observationService) {
        this.deliveryFeeService = deliveryFeeService;
        this.observationService = observationService;
    }

    /**
     * Calculates and returns the delivery fee based on city, vehicle and latest weather observation data.
     *
     * @param city location where the delivery takes place in
     * @param vehicle method of transportation used
     * @return the calculated delivery fee
     */
    @GetMapping("api/delivery-fee")
    public Double calculateDeliveryFee(@RequestParam String city, @RequestParam String vehicle) {
        Optional<Observation> observation = observationService.getLatestObservation(city);

        return deliveryFeeService.calculateDeliveryFee(city, vehicle, observation);
    }
}
