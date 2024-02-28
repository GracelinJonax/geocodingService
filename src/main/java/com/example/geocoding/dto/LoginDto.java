package com.example.geocoding.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginDto {
    @NotNull(message = "company name must not be null")
    @Size(max = 50, message = "company Name is too long")
    private String companyName;
    @NotNull(message = "password must not be null")
    @Size(max = 10, message = "password is too long")
    private String password;
}
