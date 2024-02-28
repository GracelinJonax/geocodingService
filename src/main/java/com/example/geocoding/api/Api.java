package com.example.geocoding.api;

import com.example.geocoding.dto.*;
import com.example.geocoding.model.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public interface Api {
    @PostMapping("${storeApi}")
    ResponseEntity<List<Store>> saveStore(@RequestBody @Valid SaveStoreDto storeDto);

    @PostMapping("${companyApi}")
    ResponseEntity<List<Company>> saveCompany(@RequestBody @Valid SaveCompanyDto companyDto);

    @GetMapping("${distanceApi}")
    ResponseEntity<List<StoreCompanyView>> findNearStore(@RequestBody @Valid DistanceDto distanceDto);

    @PostMapping("${planApi}")
    ResponseEntity<List<Plan>> savePlan(@RequestBody @Valid SavePlanDto planDto);

    @PostMapping("${subscriptionApi}")
    ResponseEntity<List<SubscriptionView>> saveSubscription(@RequestBody @Valid SubscriptionDto subscriptionDto);

    @PostMapping("${companyLoginApi}")
    ResponseEntity<String> login(@RequestBody LoginDto loginDto);

    @GetMapping("${apiKey}")
    ResponseEntity<String> generateApikey(HttpServletRequest request);
}
