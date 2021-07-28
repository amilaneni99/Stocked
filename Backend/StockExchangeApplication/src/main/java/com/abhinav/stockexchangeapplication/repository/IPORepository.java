package com.abhinav.stockexchangeapplication.repository;

import com.abhinav.stockexchangeapplication.model.IPODetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IPORepository extends JpaRepository<IPODetails, Long> {
    @Query(value = "select * from IPO_DETAILS where open_date_time > :today order by open_date_time", nativeQuery = true)
    List<IPODetails> getAllByChronologicalOrder(@Param("today") LocalDateTime today);
}
