package com.abhinav.stockexchangeapplication.service.impl;

import com.abhinav.stockexchangeapplication.exceptions.NotFoundException;
import com.abhinav.stockexchangeapplication.model.Company;
import com.abhinav.stockexchangeapplication.model.CompanyStockExchangeMap;
import com.abhinav.stockexchangeapplication.model.StockExchange;
import com.abhinav.stockexchangeapplication.repository.CompanyStockExchangeMapRepository;
import com.abhinav.stockexchangeapplication.repository.StockExchangeRepository;
import com.abhinav.stockexchangeapplication.service.ICompanyStockExchangeMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.OptionalLong;

@Service
public class CompanyStockExchangeService implements ICompanyStockExchangeMapService {

    @Autowired
    CompanyStockExchangeMapRepository mapRepository;

    @Autowired
    StockExchangeRepository stockExchangeRepository;

    @Override
    public Company getCompanyByCompanyCode(String companyCode) throws NotFoundException {
        Optional<CompanyStockExchangeMap> companyMapOptional = mapRepository.findByCompanyCode(companyCode);

        if (companyMapOptional.isEmpty()) throw new NotFoundException("Company doesn't exist");

        return companyMapOptional.get().getCompany();
    }

    @Override
    public boolean checkIfCompanyExistsInExchange(Long companyId, Long exchangeId) {
        boolean existsInExchange = mapRepository.checkIfCompanyExistsInExchange(companyId, exchangeId);
        return existsInExchange;
    }

    @Override
    public boolean checkIfCompanyCodeExistsInExchange(String companyCode, Long exchangeId) {
        boolean existsInExchange = mapRepository.checkIfCompanyExistsInExchange(companyCode, exchangeId);
        return existsInExchange;
    }

    @Override
    public void save(CompanyStockExchangeMap map) {
        mapRepository.save(map);
    }

    @Override
    public Integer getNumberOfCompaniesInExchange(Long id) {
        return mapRepository.getNumberOfCompaniesByExchange(id);
    }

    @Override
    public void deleteByCompanyId(Long id) {
        mapRepository.deleteByCompanyId(id);
    }


}
