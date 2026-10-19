package com.fujitsu.app.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * Object representing a weather observation.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Observation {

    @Id
    private Long id;

    private Long cityId;

    private String name;

    private Integer wmoCode;

    private Double airTemperature;

    private Double windSpeed;

    private String phenomenon;

    private Long timestamp;
}
