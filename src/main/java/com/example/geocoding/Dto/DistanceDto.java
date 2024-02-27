package com.example.geocoding.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class DistanceDto {
    @NotNull(message = "latitude must not be null")
    @Range(max = 90, min = -90, message = "given latitude is not valid")
    private Double latitude;
    @NotNull(message = "longitude must not be null")
    @Range(max = 180, min = -180, message = "given longitude is not valid")
    private Double longitude;
    @NotNull(message = "distance must not be null")
    @Range(min = 1, message = "distance must be greater than 0")
    private Integer distance;
    @NotNull(message = "companyId must not be null")
    private String companyId;
}
