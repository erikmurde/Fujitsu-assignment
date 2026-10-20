package com.fujitsu.app.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * Object representing a vehicle type.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleType {

    @Id
    private Long id;

    private String name;
}
