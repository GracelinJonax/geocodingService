package com.example.geocoding.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Data
public class Plan {
    @Id
    @UuidGenerator
    private String planId;
    private String planDetails;
    private String planType;
    private Long totalRequest;
    private int validDays;
}
