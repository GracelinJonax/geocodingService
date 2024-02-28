package com.example.geocoding.repository.service;

import com.example.geocoding.model.SubscriptionView;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public interface SubscriptionViewRepoService {
    SubscriptionView findByCompanyIdAndExpiryDateAfter(String companyId, LocalDate expiryDate);

    Optional<SubscriptionView> findBySubscriptionIdAndExpiryDateAfter(String subscriptionId, LocalDate expiryDate);

    SubscriptionView findBySubscriptionId(String subscriptionId);
}
