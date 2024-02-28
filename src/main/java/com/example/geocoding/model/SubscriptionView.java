package com.example.geocoding.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Immutable;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Immutable
@Data
@Table(name = "subscription_view")
public class SubscriptionView {
    @Id
    private String subscriptionId;
    private String companyId;
    private String companyName;
    private String planId;
    private String planDetails;
    private String planType;
    private Long totalRequest;
    private LocalDate expiryDate;
    private Date createdDate;
}
