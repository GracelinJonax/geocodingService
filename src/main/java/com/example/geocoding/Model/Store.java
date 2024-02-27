package com.example.geocoding.Model;

import jakarta.persistence.*;
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
