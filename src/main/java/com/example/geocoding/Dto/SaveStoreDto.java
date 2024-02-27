package com.example.geocoding.Dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class SaveStoreDto {
    @NotNull(message = "companyId must not be null")
    private String companyId;

    @NotNull(message = "store Name must not be null")
    @Size(max = 50, message = "store Name is too long")
    private String storeName;

    @NotNull(message = "contact Number must not be null")
    @Size(max = 10, min = 10, message = "contact Number must be 10 digit")
    private String contactNo;

    @NotNull(message = "latitude must not be null")
    @Range(max = 90, min = -90, message = "given latitude is not valid")
    private Double latitude;

    @NotNull(message = "longitude must not be null")
    @Range(max = 180, min = -180, message = "given longitude is not valid")
    private Double longitude;

    @NotNull(message = "address must not be null")
    private SaveCompanyDto.Address address;
}
