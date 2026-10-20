package com.fujitsu.app.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotNull
    @Size(min = 1, max = 255)
    private String name;
}
