package com.example.geocoding.Repository.Service;

import com.example.geocoding.Model.RequestResponseLog;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface RequestResponseLogRepoService {
Optional<RequestResponseLog> findTopBySubscriptionId(String subscriptionId);
}
