package com.zegasoftware.countries.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Data
@Entity
@Table(name = "countries")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "countries_seq")
    @SequenceGenerator(name = "countries_seq", sequenceName = "countries_seq", allocationSize = 1)
    private int id;
    @Column
    private String name;
    @Column
    private String capital;
    @Column
    private List<String> timezones;
    @Column
    private String subregion;
    @Column
    private List<String> borders;
    @Column
    private int area;
    @Column
    private Integer population;
}
