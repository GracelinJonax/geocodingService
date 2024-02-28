package com.example.geocoding.repository;

import com.example.geocoding.model.RequestResponseLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RequestResponseLogRepository extends MongoRepository<RequestResponseLog, String> {
    Optional<RequestResponseLog> findTopBySubscriptionId(String subscriptionId);
}
