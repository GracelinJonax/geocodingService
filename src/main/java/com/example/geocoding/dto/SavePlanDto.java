package com.example.geocoding.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class SavePlanDto {
    @NotNull(message = "plan Details must not be null")
    @Size(max = 250, message = "plan Details is too long")
    private String planDetails;
    @NotNull(message = "plan Type must not be null")
    @Size(max = 50, message = "plan Type is too long")
    private String planType;
    private Long totalRequest;
    @NotNull(message = "valid Days must not be null")
    @Range(min = 1, message = "valid days must be greater 0")
    private int validDays;
}
