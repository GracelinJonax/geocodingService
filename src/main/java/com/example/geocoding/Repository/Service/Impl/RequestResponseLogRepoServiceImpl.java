package com.example.geocoding.Repository.Service.Impl;

import com.example.geocoding.Model.RequestResponseLog;
import com.example.geocoding.Repository.RequestResponseLogRepository;
import com.example.geocoding.Repository.Service.RequestResponseLogRepoService;
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
