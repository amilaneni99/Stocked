package com.abhinav.stockexchangeapplication.io.request;

import com.abhinav.stockexchangeapplication.model.CompanyStockExchangeMap;
import com.abhinav.stockexchangeapplication.model.IPODetails;
import com.abhinav.stockexchangeapplication.model.Sector;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyRequest {

    private String companyName;

    private Double turnover;

    private String ceo;

    private String boardOfDirectors;

    private String companyBrief;

    private Long sectorId;
}
