package com.abhinav.stockexchangeapplication.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "IPO_DETAILS")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class IPODetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Double pricePerShare;

    @Column(nullable = false)
    private Long totalNumberOfShares;

    @Column(name = "open_date_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime openDateTime;

    @OneToOne(mappedBy = "ipo",fetch = FetchType.EAGER)
    @JsonIgnore
    private Company company;

    @ManyToMany
    @Column(nullable = false)
    private List<StockExchange> stockExchanges;
}
