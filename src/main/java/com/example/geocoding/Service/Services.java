package com.example.geocoding.Service;

import com.example.geocoding.Dto.*;
import com.example.geocoding.Model.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.util.List;
import java.util.Optional;

@Service
public interface Services {
    List<Store> saveStoreService(SaveStoreDto storeDto);

    List<Company> saveCompanyService(SaveCompanyDto companyDto);

    List<StoreCompanyView> findNearStoreService(DistanceDto distanceDto);

    List<Plan> savePlanServices(SavePlanDto savePlanDto);

    List<SubscriptionView> saveSubscriptionService(SubscriptionDto subscriptionDto);

    String loginService(LoginDto loginDto);

    String isSubscribed(String jwt);

    void saveRequestResponse(ContentCachingRequestWrapper c, HttpServletRequest request, ContentCachingResponseWrapper responseWrapper, HttpServletResponse response, String jwt);

    Optional<Company> findCompany(String username);

    String generateApi(HttpServletRequest request);
}
