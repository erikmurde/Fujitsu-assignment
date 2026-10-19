package com.fujitsu.app.controller;

import com.fujitsu.app.service.DeliveryFeeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for requesting delivery fees.
 */
@RestController
public class DeliveryFeeController {

    private final DeliveryFeeService deliveryFeeService;

    public DeliveryFeeController(DeliveryFeeService deliveryFeeService) {
        this.deliveryFeeService = deliveryFeeService;
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
        return deliveryFeeService.calculateDeliveryFee(city, vehicle);
    }
}
