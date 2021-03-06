package com.abhinav.stockexchangeapplication.service.impl;

import com.abhinav.stockexchangeapplication.exceptions.AlreadyExistsException;
import com.abhinav.stockexchangeapplication.exceptions.NotFoundException;
import com.abhinav.stockexchangeapplication.model.Company;
import com.abhinav.stockexchangeapplication.model.Sector;
import com.abhinav.stockexchangeapplication.repository.SectorRepository;
import com.abhinav.stockexchangeapplication.service.ISectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectorService implements ISectorService {

    private SectorRepository sectorRepository;

    public SectorService(SectorRepository sectorRepository) {
        this.sectorRepository = sectorRepository;
    }

    @Override
    public List<Sector> getAllSectors() {
        return sectorRepository.findAll();
    }

    @Override
    public Sector findSectorById(Long id) throws NotFoundException {
        if (! sectorRepository.existsById(id)) {
            throw new NotFoundException("Sector with id "+id+" Not found");
        }

        return sectorRepository.findById(id).get();
    }

    @Override
    public Sector addSector(Sector sector) {
        return sectorRepository.save(sector);
    }

    @Override
    public List<Company> getCompaniesBySectorId(Long id) throws NotFoundException {
        if (! sectorRepository.existsById(id)) {
            throw new NotFoundException("Sector with id "+id+" Not Found");
        }

        return sectorRepository.findById(id).get().getCompanies();
    }
}
