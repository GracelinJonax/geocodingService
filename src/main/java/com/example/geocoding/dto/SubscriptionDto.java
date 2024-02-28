package com.example.geocoding.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SubscriptionDto {
    @NotNull(message = "companyId must not be null")
    private String companyId;
    @NotNull(message = "planId must not be null")
    private String planId;
}
