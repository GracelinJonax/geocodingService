package com.example.geocoding.Repository;

import com.example.geocoding.Model.StoreCompanyView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StoreCompanyViewRepository extends JpaRepository<StoreCompanyView, Long> {

    @Query("SELECT s from StoreCompanyView s where s.companyId=?4 AND ROUND(ACOS(COS(RADIANS( ?1 )-RADIANS(s.latitude))*COS(RADIANS(s.longitude)-RADIANS( ?2 ))) * 6371 ) <=?3")
    List<StoreCompanyView> findNearByStores(double latitude, double longitude, int Distance, String companyId);
}
