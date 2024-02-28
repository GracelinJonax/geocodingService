package com.example.geocoding.repository.service;

import com.example.geocoding.model.Company;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface CompanyRepoService {
    Optional<Company> findByCompanyName(String companyName);
}
