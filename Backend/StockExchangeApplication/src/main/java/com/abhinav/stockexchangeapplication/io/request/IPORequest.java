package com.abhinav.stockexchangeapplication.io.request;

import com.abhinav.stockexchangeapplication.model.Company;
import com.abhinav.stockexchangeapplication.model.StockExchange;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IPORequest {
    private Double pricePerShare;

    private Long totalNumberOfShares;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime openDateTime;

    private List<String> exchangesList;
}
