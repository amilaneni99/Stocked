package com.abhinav.stockexchangeapplication.service;

import com.abhinav.stockexchangeapplication.exceptions.NotFoundException;
import com.abhinav.stockexchangeapplication.io.request.CompanyRequest;
import com.abhinav.stockexchangeapplication.model.Company;
import com.abhinav.stockexchangeapplication.model.Sector;
import com.abhinav.stockexchangeapplication.repository.CompanyRepository;
import com.abhinav.stockexchangeapplication.repository.SectorRepository;
import com.abhinav.stockexchangeapplication.service.impl.CompanyService;
import com.abhinav.stockexchangeapplication.service.impl.SectorService;
import com.abhinav.stockexchangeapplication.util.Mapper;
import org.aspectj.weaver.ast.Not;
import org.checkerframework.checker.nullness.Opt;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.Null;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CompanyServiceTest {

    @InjectMocks
    CompanyService companyService;

    @Mock
    CompanyRepository companyRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addCompanyTest() {
        Company company = new Company(1l, "MRF", 2000.0, "Mr. Ramamoorthy", "Mr. Ramamoorthy", "MRF is a leading tyre manufacturer", null, null, null);
        companyService.addCompany(company);
        verify(companyRepository, times(1)).save(company);
    }

    @Test
    public void deleteCompanyTest() throws NotFoundException {
        when(companyRepository.existsById(1l)).thenReturn(true);
        companyService.deleteCompany(1l);
        verify(companyRepository, times(1)).deleteById(1l);
    }

    @Test
    public void deleteCompanyTestException() throws NotFoundException {
        try {
            companyService.deleteCompany(1l);
        } catch (NotFoundException ex) {
            assertEquals("Company doesn't exists", ex.getMessage());
        }
    }

    @Test
    public void getCompanyByIdTest() throws NotFoundException {
        when(companyRepository.existsById(1l)).thenReturn(true);
        when(companyRepository.findById(1l)).thenReturn(Optional.of(new Company(1l, "MRF", 2000.0, "Mr. Ramamoorthy", "Mr. Ramamoorthy", "MRF is a leading tyre manufacturer", null, null, null)));

        Company company = companyService.getCompany(1l);

        assertEquals("MRF", company.getCompanyName());
        assertEquals(Double.valueOf(2000.0), company.getTurnover());
        assertEquals("Mr. Ramamoorthy", company.getCeo());
    }

    @Test
    public void getCompanyByIdTestException() throws NotFoundException {
        try {
            Company company = companyService.getCompany(1l);
        } catch (NotFoundException ex) {
            assertEquals("Company with id 1 Not Found", ex.getMessage());
        }
    }

    @Test
    public void getAllCompaniesTest() {
        List<Company> companyList = new ArrayList<>();
        Company companyOne = new Company(1l, "MRF", 2000.0, "Mr. Ramamoorthy", "Mr. Ramamoorthy", "MRF is a leading tyre manufacturer", null, null, null);
        Company companyTwo = new Company(2l, "Apollo", 2000.0, "Mrs. Sneha Reddy", "Mr. Ramamoorthy", "Apollo is a leading hospital chain", null, null, null);
        companyList.add(companyOne);
        companyList.add(companyTwo);

        when(companyRepository.findAll()).thenReturn(companyList);

        List<Company> companies = companyService.getAllCompanies();

        assertEquals(2, companies.size());
        assertEquals("MRF", companies.get(0).getCompanyName());
        assertEquals("Mrs. Sneha Reddy", companies.get(1).getCeo());
    }

    @Test(expected = NullPointerException.class)
    public void editCompanyTest() throws NotFoundException {
        when(companyRepository.existsById(1l)).thenReturn(true);
        when(companyRepository.findById(1l)).thenReturn(Optional.of(new Company(2l, "Apollo", 2000.0, "Mrs. Sneha Reddy", "Mr. Ramamoorthy", "Apollo is a leading hospital chain", null, null, null)));
        CompanyRequest companyRequest = new CompanyRequest("Apollo Med", 2000.0, "Mr. Prathap Reddy", "Mrs. Sneha Reddy", "Apollo is a leading hospital chain", 1l);

        Company company = companyService.editCompany(companyRequest, 1l);

    }

    @Test
    public void editCompanyTestException() throws NotFoundException {
        CompanyRequest companyRequest = new CompanyRequest("Apollo Med", 2000.0, "Mr. Prathap Reddy", "Mrs. Sneha Reddy", "Apollo is a leading hospital chain", 1l);

        try {
            Company company = companyService.editCompany(companyRequest, 1l);
        }catch (NotFoundException ex) {
            assertEquals("Company with id 1 Not Found", ex.getMessage());
        }

    }
}
