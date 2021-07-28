package com.abhinav.stockexchangeapplication.io.response;

import com.abhinav.stockexchangeapplication.model.Company;
import com.abhinav.stockexchangeapplication.model.StockExchange;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IPOResponse {
    private Long id;
    private Double pricePerShare;
    private Long totalNumberOfShares;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime openDateTime;
    private String companyName;
    private List<StockExchange> stockExchanges;
}
