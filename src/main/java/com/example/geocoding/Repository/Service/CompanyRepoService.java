package com.example.geocoding.Repository.Service;

import com.example.geocoding.Model.Company;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface CompanyRepoService {
    Optional<Company> findByCompanyName(String companyName);
}
