package com.abhinav.stockexchangeapplication.service;

import com.abhinav.stockexchangeapplication.exceptions.NotFoundException;
import com.abhinav.stockexchangeapplication.model.Company;
import com.abhinav.stockexchangeapplication.model.CompanyStockExchangeMap;

public interface ICompanyStockExchangeMapService {
    Company getCompanyByCompanyCode(String companyCode) throws NotFoundException;
    boolean checkIfCompanyExistsInExchange(Long companyId, Long exchangeId);
    boolean checkIfCompanyCodeExistsInExchange(String companyCode, Long exchangeId);
    void save(CompanyStockExchangeMap map);
    Integer getNumberOfCompaniesInExchange(Long id);
    void deleteByCompanyId(Long id);
}
