package com.example.geocoding.repository.service.impl;

import com.example.geocoding.model.StoreCompanyView;
import com.example.geocoding.repository.StoreCompanyViewRepository;
import com.example.geocoding.repository.service.StoreCompanyViewRepoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreCompanyViewRepoServiceImpl implements StoreCompanyViewRepoService {
    private final StoreCompanyViewRepository storeCompanyViewRepository;

    public StoreCompanyViewRepoServiceImpl(StoreCompanyViewRepository storeCompanyViewRepository) {
        this.storeCompanyViewRepository = storeCompanyViewRepository;
    }

    @Override
    public List<StoreCompanyView> findNearByStores(double latitude, double longitude, int distance, String companyId) {
        return storeCompanyViewRepository.findNearByStores(latitude, longitude, distance, companyId);
    }
}
