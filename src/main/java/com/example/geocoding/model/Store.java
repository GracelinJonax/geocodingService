package com.example.geocoding.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Data
public class Store extends Address {
    @Id
    @UuidGenerator
    private String id;
    private String storeName;
    private String contactNo;
    private Double longitude;
    private Double latitude;
    private String companyId;
}
