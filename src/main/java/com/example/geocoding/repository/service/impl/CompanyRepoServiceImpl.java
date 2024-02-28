package com.example.geocoding.repository.service.impl;

import com.example.geocoding.model.Company;
import com.example.geocoding.repository.CompanyRepository;
import com.example.geocoding.repository.service.CompanyRepoService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyRepoServiceImpl implements CompanyRepoService {
    private final CompanyRepository companyRepository;

    public CompanyRepoServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public Optional<Company> findByCompanyName(String companyName) {
        return companyRepository.findByCompanyName(companyName);
    }
}
