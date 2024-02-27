package com.example.geocoding.Repository.Service.Impl;

import com.example.geocoding.Model.StoreCompanyView;
import com.example.geocoding.Repository.Service.StoreCompanyViewRepoService;
import com.example.geocoding.Repository.StoreCompanyViewRepository;
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
