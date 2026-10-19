package com.fujitsu.app.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegionalBaseFee {

    @Id
    private Long id;

    private Long cityId;

    private Long vehicleTypeId;

    private double amount;
}
