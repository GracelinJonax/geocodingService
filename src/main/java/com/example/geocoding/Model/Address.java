package com.example.geocoding.Model;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;


@Data
@MappedSuperclass
public class Address {
    private String doorNo;
    private String streetName;
    private String city;
    private String state;
    private String country;
}
