package com.abhinav.stockexchangeapplication.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Sector {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String sectorName;

    @Column(nullable = false)
    private String brief;

    @OneToMany(mappedBy = "sector", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Company> companies = new ArrayList<>();

}
