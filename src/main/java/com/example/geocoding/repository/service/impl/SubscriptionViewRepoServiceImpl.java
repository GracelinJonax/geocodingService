package com.example.geocoding.repository.service.impl;

import com.example.geocoding.model.SubscriptionView;
import com.example.geocoding.repository.SubscriptionViewRepository;
import com.example.geocoding.repository.service.SubscriptionViewRepoService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class SubscriptionViewRepoServiceImpl implements SubscriptionViewRepoService {
    private final SubscriptionViewRepository subscriptionViewRepository;

    public SubscriptionViewRepoServiceImpl(SubscriptionViewRepository subscriptionViewRepository) {
        this.subscriptionViewRepository = subscriptionViewRepository;
    }

    @Override
    public SubscriptionView findByCompanyIdAndExpiryDateAfter(String companyId, LocalDate expiryDate) {
        return subscriptionViewRepository.findByCompanyIdAndExpiryDateAfter(companyId, expiryDate);
    }

    @Override
    public Optional<SubscriptionView> findBySubscriptionIdAndExpiryDateAfter(String subscriptionId, LocalDate expiryDate) {
        return subscriptionViewRepository.findBySubscriptionIdAndExpiryDateAfter(subscriptionId, expiryDate);
    }

    @Override
    public SubscriptionView findBySubscriptionId(String subscriptionId) {
        return subscriptionViewRepository.findBySubscriptionId(subscriptionId);
    }
}
