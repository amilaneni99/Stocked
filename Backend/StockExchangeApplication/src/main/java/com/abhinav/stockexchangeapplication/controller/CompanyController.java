package com.abhinav.stockexchangeapplication.controller;

import com.abhinav.stockexchangeapplication.exceptions.NotFoundException;
import com.abhinav.stockexchangeapplication.io.request.CompanyRequest;
import com.abhinav.stockexchangeapplication.io.request.IPORequest;
import com.abhinav.stockexchangeapplication.model.*;
import com.abhinav.stockexchangeapplication.repository.CompanyRepository;
import com.abhinav.stockexchangeapplication.repository.CompanyStockExchangeMapRepository;
import com.abhinav.stockexchangeapplication.repository.IPORepository;
import com.abhinav.stockexchangeapplication.service.impl.CompanyService;
import com.abhinav.stockexchangeapplication.service.impl.CompanyStockExchangeService;
import com.abhinav.stockexchangeapplication.service.impl.SectorService;
import com.abhinav.stockexchangeapplication.service.impl.StockExchangeService;
import com.abhinav.stockexchangeapplication.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.websocket.server.PathParam;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/companies")
@CrossOrigin(origins = "*")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @Autowired
    StockExchangeService stockExchangeService;

    @Autowired
    CompanyStockExchangeService mapService;

    @Autowired
    SectorService sectorService;

    @Autowired
    IPORepository ipoRepository;

    @Autowired
    Mapper mapper;

    @PostMapping(
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<Object> createCompany(@RequestBody CompanyRequest companyRequest) throws NotFoundException {
        Company company = mapper.getModelMapper().map(companyRequest, Company.class);
        System.out.println(company.getCompanyName());
        Long sectorId = companyRequest.getSectorId();
        Sector sector = sectorService.findSectorById(sectorId);
        company.setSector(sector);
        companyService.addCompany(company);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(company.getId())
                .toUri();

        return new ResponseEntity<>(location, HttpStatus.CREATED);
    }

    @PostMapping(
            path = "/mapExchange",
            consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<String> mapCompanyToExchange(@RequestBody Map<String,String> map) throws NotFoundException {
        if ((!map.containsKey("companyId")) || (! map.containsKey("exchangeCode") || (!map.containsKey("companyCode")))) {
            return new ResponseEntity<>("Require all values", HttpStatus.BAD_REQUEST);
        }

        if (mapService.checkIfCompanyExistsInExchange(Long.parseLong(map.get("companyId")),stockExchangeService.getStockExchangeByCode(map.get("exchangeCode")).getId())) throw new NotFoundException("Company Already Exists in Exchange");


        Company company = companyService.getCompany(Long.parseLong(map.get("companyId")));
        StockExchange stockExchange = stockExchangeService.getStockExchangeByCode(map.get("exchangeCode"));

        CompanyStockExchangeMap companyStockExchangeMap = new CompanyStockExchangeMap();
        companyStockExchangeMap.setCompany(company);
        companyStockExchangeMap.setStockExchange(stockExchange);
        companyStockExchangeMap.setCompanyCode(map.get("companyCode"));

        mapService.save(companyStockExchangeMap);

        return new ResponseEntity<>("Mapped Successfully", HttpStatus.OK);
    }

    @GetMapping(
            value = "/{id}",
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<Company> getCompany(@PathVariable Long id) throws NotFoundException {
        Company company = companyService.getCompany(id);
        return new ResponseEntity<>(company, HttpStatus.OK);
    }

    @GetMapping(
            value = "/getId",
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<Long> getCompany(@PathParam("companyCode") String companyCode) throws NotFoundException {
        Company company = mapService.getCompanyByCompanyCode(companyCode);
        return new ResponseEntity<>(company.getId(), HttpStatus.OK);
    }

    @GetMapping(
            value = "/checkMapping",
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<String> checkMapping(@PathParam("companyCode") String companyCode, @PathParam("exchangeCode") String exchangeCode) throws NotFoundException {
        long exchangeId = 0;
        try {
            exchangeId = stockExchangeService.getStockExchangeByCode(exchangeCode).getId();
        } catch (NotFoundException exception) {
            return new ResponseEntity<>("Not Found", HttpStatus.OK);
        }
        Company company = mapService.getCompanyByCompanyCode(companyCode);
        if(mapService.checkIfCompanyExistsInExchange(company.getId(), exchangeId) && mapService.checkIfCompanyCodeExistsInExchange(companyCode, exchangeId)) return new ResponseEntity(company.getId()+"", HttpStatus.OK);
        return new ResponseEntity("Not Found", HttpStatus.OK);
    }

    @GetMapping(
            value = "/all",
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<List<Company>> getAllCompanies() {
        return new ResponseEntity<>(companyService.getAllCompanies(),HttpStatus.OK);
    }

    @PutMapping(
            path = "/{id}",
            produces = { MediaType.APPLICATION_JSON_VALUE },
            consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<Company> editCompany(@RequestBody CompanyRequest companyDto, @PathVariable Long id)
            throws NotFoundException
    {
        Company updatedCompanyDto = companyService.editCompany(companyDto,id);
        if(updatedCompanyDto == null) {
            throw new NotFoundException("Company with id "+id+" Not found");
        }
        return ResponseEntity.ok(updatedCompanyDto);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable Long id) throws NotFoundException {
        companyService.deleteCompany(id);
        return new ResponseEntity<>("Deleted Company "+id,HttpStatus.OK);
    }

    @PutMapping(
            path = "/{id}/addIPO",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<Company> addIPO(@PathVariable Long id,@RequestBody IPORequest request) throws NotFoundException {
        IPODetails ipoDetails = mapper.getModelMapper().map(request, IPODetails.class);
        List<StockExchange> stockExchanges;
        if (ipoDetails.getStockExchanges() == null) {
            stockExchanges = new ArrayList<>();
        } else {
            stockExchanges = ipoDetails.getStockExchanges();
        }
        for(String exchangeCode: request.getExchangesList()) {
            StockExchange stockExchange = stockExchangeService.getStockExchangeByCode(exchangeCode);
            stockExchanges.add(stockExchange);
        }

        ipoDetails.setStockExchanges(stockExchanges);

        Company company = companyService.getCompany(id);
        company.setIpo(ipoDetails);

        ipoDetails.setCompany(company);
        ipoRepository.save(ipoDetails);

        return new ResponseEntity<>(companyService.addCompany(company),HttpStatus.OK);
    }

    @GetMapping(
            path = "/{id}/IPO",
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<IPODetails> getIPODetails(@PathVariable Long id) throws NotFoundException {
        Company company = companyService.getCompany(id);
        if(company.getIpo() == null){
            throw new NotFoundException("IPOs not found for company with id "+id);
        }
        return new ResponseEntity<>(company.getIpo(),HttpStatus.OK);
    }
}
