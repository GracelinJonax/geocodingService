package com.example.geocoding.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Data
public class Company extends Address {
    @Id
    @UuidGenerator
    private String companyId;
    private String companyName;
    private String contactNo;
    private String gstNo;
    private String password;
}
