package com.abhinav.stockexchangeapplication.service;

import com.abhinav.stockexchangeapplication.exceptions.NotFoundException;
import com.abhinav.stockexchangeapplication.io.request.IPORequest;
import com.abhinav.stockexchangeapplication.io.response.IPOResponse;
import com.abhinav.stockexchangeapplication.model.IPODetails;

import java.time.LocalDateTime;
import java.util.List;

public interface IIPOService {
    IPODetails createIPO(IPODetails ipoDetails);
    IPODetails getIPOById(Long id) throws Exception;
    IPODetails editIPO(Long id, IPORequest ipoDetails) throws NotFoundException;
    List<IPOResponse> getIPOsByChronology(LocalDateTime today);
}
