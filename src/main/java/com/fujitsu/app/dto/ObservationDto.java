package com.fujitsu.app.dto;

import lombok.Data;
import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@Data
public class ObservationDto {

    private String name;

    @JacksonXmlProperty(localName = "wmocode")
    private Integer wmoCode;

    @JacksonXmlProperty(localName = "airtemperature")
    private Double airTemperature;

    @JacksonXmlProperty(localName = "windspeed")
    private Double windSpeed;

    private String phenomenon;
}
