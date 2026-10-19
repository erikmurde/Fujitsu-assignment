package com.fujitsu.app.dto;

import lombok.Data;
import tools.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

@Data
public class WeatherDataDto {

    @JacksonXmlProperty(isAttribute = true)
    private Long timestamp;

    @JacksonXmlProperty(localName = "station")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<ObservationDto> observations;
}
