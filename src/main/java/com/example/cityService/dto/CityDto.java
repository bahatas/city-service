package com.example.cityService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CityDto {

    private String name;
    private String code;
    private String population;
    private double areaMeter;
    private double airQuality;
    private Weather weather;
}
