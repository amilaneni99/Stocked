package com.abhinav.stockexchangeapplication.service.impl;

import com.abhinav.stockexchangeapplication.exceptions.AlreadyExistsException;
import com.abhinav.stockexchangeapplication.exceptions.NotFoundException;
import com.abhinav.stockexchangeapplication.io.response.StockExchangeResponse;
import com.abhinav.stockexchangeapplication.model.Company;
import com.abhinav.stockexchangeapplication.model.StockExchange;
import com.abhinav.stockexchangeapplication.repository.StockExchangeRepository;
import com.abhinav.stockexchangeapplication.service.IStockExchangeService;
import com.abhinav.stockexchangeapplication.util.Mapper;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StockExchangeService implements IStockExchangeService {

    @Autowired
    StockExchangeRepository stockExchangeRepository;

    @Autowired
    CompanyStockExchangeService mapService;

    @Autowired
    Mapper mapper;

    @Override
    public StockExchange addStockExchange(StockExchange stockExchange) throws AlreadyExistsException {
        StockExchange returnValue = null;
        try {
            returnValue = stockExchangeRepository.save(stockExchange);
        } catch (Exception ex) {
            throw new AlreadyExistsException("Stock Exchange with Name "+stockExchange.getName()+" already exists");
        }
        return returnValue;
    }

    @Override
    public StockExchange getStockExchangeByCode(String exchangeCode) throws NotFoundException {
        Optional<StockExchange> stockExchange = stockExchangeRepository.findByCode(exchangeCode);
        if (stockExchange.isEmpty()) {
            throw new NotFoundException("Exchange "+exchangeCode+" Not Found");
        }
        return stockExchange.get();
    }

    @Override
    public StockExchange getStockExchangeById(Long id) throws NotFoundException {
        Optional<StockExchange> stockExchange = stockExchangeRepository.findById(id);

        if (stockExchange.isEmpty()) {
            throw new NotFoundException("Exchange with id "+id+" Not Found");
        }

        return stockExchange.get();
    }

    @Override
    public List<StockExchangeResponse> getAllStockExchanges() {
        List<StockExchange> stockExchanges = stockExchangeRepository.findAll();
        return stockExchanges.stream().map(stockExchange -> {
            StockExchangeResponse response = mapper.getModelMapper().map(stockExchange, StockExchangeResponse.class);
            response.setNumberOfCompanies(mapService.getNumberOfCompaniesInExchange(response.getId()));
            return response;
        }).collect(Collectors.toList());
    }
}
