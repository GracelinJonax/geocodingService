package com.example.geocoding.repository.service.impl;

import com.example.geocoding.model.RequestResponseLog;
import com.example.geocoding.repository.RequestResponseLogRepository;
import com.example.geocoding.repository.service.RequestResponseLogRepoService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RequestResponseLogRepoServiceImpl implements RequestResponseLogRepoService {
    private final RequestResponseLogRepository requestResponseLogRepository;

    public RequestResponseLogRepoServiceImpl(RequestResponseLogRepository requestResponseLogRepository) {
        this.requestResponseLogRepository = requestResponseLogRepository;
    }


    @Override
    public Optional<RequestResponseLog> findTopBySubscriptionId(String subscriptionId) {
        return requestResponseLogRepository.findTopBySubscriptionId(subscriptionId);
    }
}
