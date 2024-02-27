package com.example.geocoding.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Data
@Table(name = "store_company_view")
public class StoreCompanyView {
    @Id
    private String id;
    private String storeName;
    private String contactNo;
    private Double longitude;
    private Double latitude;
    private String doorNo;
    private String streetName;
    private String city;
    private String state;
    private String country;
    private String companyId;
    private String companyName;
}
