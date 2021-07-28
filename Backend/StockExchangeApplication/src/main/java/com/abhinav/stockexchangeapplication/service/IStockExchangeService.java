package com.abhinav.stockexchangeapplication.service;

import com.abhinav.stockexchangeapplication.exceptions.AlreadyExistsException;
import com.abhinav.stockexchangeapplication.exceptions.NotFoundException;
import com.abhinav.stockexchangeapplication.io.response.StockExchangeResponse;
import com.abhinav.stockexchangeapplication.model.Company;
import com.abhinav.stockexchangeapplication.model.StockExchange;

import java.util.List;

public interface IStockExchangeService {
    StockExchange addStockExchange(StockExchange stockExchange) throws AlreadyExistsException;
    StockExchange getStockExchangeByCode(String exchangeCode) throws NotFoundException;
    StockExchange getStockExchangeById(Long id) throws NotFoundException;
    List<StockExchangeResponse> getAllStockExchanges();
}
