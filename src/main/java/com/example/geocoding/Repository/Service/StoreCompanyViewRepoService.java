package com.example.geocoding.Repository.Service;

import com.example.geocoding.Model.StoreCompanyView;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StoreCompanyViewRepoService {
    List<StoreCompanyView> findNearByStores(double latitude, double longitude, int distance, String companyId);
}
