package com.abhinav.stockexchangeapplication.service;

import com.abhinav.stockexchangeapplication.exceptions.NotFoundException;
import com.abhinav.stockexchangeapplication.model.Company;
import com.abhinav.stockexchangeapplication.model.Sector;

import java.util.List;

public interface ISectorService {
    List<Sector> getAllSectors();
    Sector findSectorById(Long id) throws NotFoundException;
    Sector addSector(Sector sector);
    List<Company> getCompaniesBySectorId(Long id) throws NotFoundException;
}
