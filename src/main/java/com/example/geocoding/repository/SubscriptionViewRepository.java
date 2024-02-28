package com.example.geocoding.repository;

import com.example.geocoding.model.SubscriptionView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface SubscriptionViewRepository extends JpaRepository<SubscriptionView, String> {
    SubscriptionView findByCompanyIdAndExpiryDateAfter(String companyId, LocalDate expiryDate);

    SubscriptionView findBySubscriptionId(String subscriptionId);

    Optional<SubscriptionView> findBySubscriptionIdAndExpiryDateAfter(String subscriptionId, LocalDate expiryDate);

    @Query("SELECT s FROM SubscriptionView s WHERE s.companyName =?1")
    SubscriptionView findByCompanyId(String name);

}
