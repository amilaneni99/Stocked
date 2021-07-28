package com.abhinav.stockexchangeapplication.repository;

import com.abhinav.stockexchangeapplication.model.StockExchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.OptionalLong;

public interface StockExchangeRepository extends JpaRepository<StockExchange, Long> {
    @Query(value = "select * from STOCK_EXCHANGE where code=:code", nativeQuery = true)
    Optional<StockExchange> findByCode(@Param("code") String code);
}
