package com.example.geocoding.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Subscription {
    @Id
    @UuidGenerator
    private String subscriptionId;
    private String companyId;
    private String planId;
    private LocalDate expiryDate;
    @CreatedDate
    private Date createdDate;
}
