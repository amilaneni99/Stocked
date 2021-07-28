package com.abhinav.stockexchangeapplication.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Company")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private Double turnover;

    @Column(nullable = false)
    private String ceo;

    @Column(nullable = false)
    @Type(type = "text")
    private String boardOfDirectors;

    @Column(nullable = false)
    @Type(type = "text")
    private String companyBrief;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private IPODetails ipo;

    @OneToMany(targetEntity = CompanyStockExchangeMap.class, mappedBy = "company", cascade = CascadeType.REMOVE)
    private List<CompanyStockExchangeMap> companyStockExchangeMap;

    @ManyToOne(fetch = FetchType.EAGER)
    private Sector sector;
}
