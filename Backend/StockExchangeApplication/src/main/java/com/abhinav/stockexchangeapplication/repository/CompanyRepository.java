package com.abhinav.stockexchangeapplication.repository;

import com.abhinav.stockexchangeapplication.model.AppUser;
import com.abhinav.stockexchangeapplication.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
}
