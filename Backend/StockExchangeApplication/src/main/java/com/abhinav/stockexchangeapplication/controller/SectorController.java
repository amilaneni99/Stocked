package com.abhinav.stockexchangeapplication.controller;

import com.abhinav.stockexchangeapplication.exceptions.NotFoundException;
import com.abhinav.stockexchangeapplication.model.Company;
import com.abhinav.stockexchangeapplication.model.Sector;
import com.abhinav.stockexchangeapplication.service.impl.SectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/sectors")
@CrossOrigin(origins = "*")
public class SectorController {

    @Autowired
    SectorService sectorService;

    @PostMapping(
            produces = { MediaType.APPLICATION_JSON_VALUE },
            consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<Sector> addSector(@RequestBody Sector sector) {
        return new ResponseEntity<>(sectorService.addSector(sector), HttpStatus.CREATED);
    }

    @GetMapping(
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<List<Sector>> getAllSectors() {
        return new ResponseEntity<>(sectorService.getAllSectors(),HttpStatus.OK);
    }

    @GetMapping(
            value = "/{sectorId}",
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<Sector> getSector(@PathVariable Long sectorId) throws NotFoundException {
        return new ResponseEntity<>(sectorService.findSectorById(sectorId),HttpStatus.OK);
    }

    @GetMapping(
            value = "/{sectorId}/companies",
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<List<Company>> getCompaniesInSector(@PathVariable Long sectorId) throws NotFoundException {
        return new ResponseEntity<>(sectorService.getCompaniesBySectorId(sectorId),HttpStatus.OK);
    }

    @GetMapping(
            value = "/{sectorId}/companies/ids",
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<List<Long>> getCompanyIdsInSector(@PathVariable Long sectorId) throws NotFoundException {
        List<Long> response = sectorService.getCompaniesBySectorId(sectorId).stream().map(company -> company.getId()).collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
