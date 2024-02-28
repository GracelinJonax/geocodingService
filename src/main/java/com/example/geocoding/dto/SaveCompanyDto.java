package com.example.geocoding.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class SaveCompanyDto {
    @NotNull(message = "company name must not be null")
    @Size(max = 50, message = "company Name is too long")
    private String companyName;

    @NotNull(message = "address must not be null")
    private Address address;

    @NotNull(message = "contact Number must not be null")
    @Size(max = 10, min = 10, message = "contact Number must be 10 digit")
    private String contactNo;

    @NotNull(message = "gst Number must not be null")
    @Size(max = 15, min = 15, message = "gst Number must be 15 digit")
    private String gstNo;

    @Data
    public static class Address {
        @NotNull(message = "door Number must not be null")
        @Size(max = 10, message = "door Number is too long")
        private String doorNo;

        @NotNull(message = "street Name must not be null")
        @Size(max = 20, message = "street Name is too long")
        private String streetName;

        @NotNull(message = "city must not be null")
        @Size(max = 20, message = "city is too long")
        private String city;

        @NotNull(message = "state must not be null")
        @Size(max = 20, message = "state is too long")
        private String state;

        @NotNull(message = "country must not be null")
        @Size(max = 20, message = "country is too long")
        private String country;
    }
}
