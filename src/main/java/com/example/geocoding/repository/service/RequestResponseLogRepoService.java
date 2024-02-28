package com.example.geocoding.repository.service;

import com.example.geocoding.model.RequestResponseLog;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface RequestResponseLogRepoService {
    Optional<RequestResponseLog> findTopBySubscriptionId(String subscriptionId);
}
