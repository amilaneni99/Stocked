package com.abhinav.stockexchangeapplication.service;


import com.abhinav.stockexchangeapplication.exceptions.NotFoundException;
import com.abhinav.stockexchangeapplication.io.request.CompanyRequest;
import com.abhinav.stockexchangeapplication.model.Company;

import java.util.List;

public interface ICompanyService {
    Company addCompany(Company company);
    Company getCompany(Long id) throws NotFoundException;
    List<Company> getAllCompanies();
    void deleteCompany(Long id) throws NotFoundException;
    Company editCompany(CompanyRequest company, Long id) throws NotFoundException;
}
