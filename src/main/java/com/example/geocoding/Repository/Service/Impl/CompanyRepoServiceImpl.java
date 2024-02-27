package com.example.geocoding.Repository.Service.Impl;

import com.example.geocoding.Model.Company;
import com.example.geocoding.Repository.CompanyRepository;
import com.example.geocoding.Repository.Service.CompanyRepoService;
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
