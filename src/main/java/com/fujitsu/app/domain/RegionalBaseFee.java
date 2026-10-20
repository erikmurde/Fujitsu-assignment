package com.fujitsu.app.domain;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * Object representing a base delivery fee for a location and vehicle combination.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegionalBaseFee {

    @Id
    private Long id;

    @NotNull
    @Min(1)
    private Long cityId;

    @NotNull
    @Min(1)
    private Long vehicleTypeId;

    @NotNull
    @Min(0)
    private double amount;
}
