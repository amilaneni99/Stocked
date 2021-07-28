package com.abhinav.stockexchangeapplication.service.impl;

import com.abhinav.stockexchangeapplication.exceptions.NotFoundException;
import com.abhinav.stockexchangeapplication.io.request.CompanyRequest;
import com.abhinav.stockexchangeapplication.model.Company;
import com.abhinav.stockexchangeapplication.model.Sector;
import com.abhinav.stockexchangeapplication.repository.CompanyRepository;
import com.abhinav.stockexchangeapplication.service.ICompanyService;
import com.abhinav.stockexchangeapplication.util.Mapper;
import com.abhinav.stockexchangeapplication.util.RestTemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService implements ICompanyService {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    RestTemplateUtil restTemplate;

    @Autowired
    CompanyStockExchangeService mapService;

    @Autowired
    SectorService sectorService;

    @Override
    public Company addCompany(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public void deleteCompany(Long id) throws NotFoundException {
        if (! companyRepository.existsById(id)) {
            throw new NotFoundException("Company doesn't exists");
        }
        companyRepository.deleteById(id);
    }

    @Override
    public Company getCompany(Long id) throws NotFoundException {
        if (! companyRepository.existsById(id)) {
            throw new NotFoundException("Company with id "+id+" Not Found");
        }

        Company company = companyRepository.findById(id).get();

        return company;
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Company editCompany(CompanyRequest company, Long id) throws NotFoundException {
        if (! companyRepository.existsById(id)) {
            throw new NotFoundException("Company with id "+id+" Not Found");
        }
        Company existingCompany = getCompany(id);
        Company updatedDetails = mapper.getModelMapper().map(company, Company.class);
        Sector newSector = sectorService.findSectorById(company.getSectorId());

        updatedDetails.setSector(newSector);
        return companyRepository.save(mapper.copyObject(existingCompany, updatedDetails));
    }
}
