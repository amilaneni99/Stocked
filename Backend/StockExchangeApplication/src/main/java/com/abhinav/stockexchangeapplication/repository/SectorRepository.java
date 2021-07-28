package com.abhinav.stockexchangeapplication.repository;

import com.abhinav.stockexchangeapplication.model.Sector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectorRepository extends JpaRepository<Sector, Long> {
}
