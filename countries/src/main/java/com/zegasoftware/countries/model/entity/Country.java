package com.zegasoftware.countries.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return id == country.id &&
                area == country.area &&
                Objects.equals(name, country.name) &&
                Objects.equals(capital, country.capital) &&
                Objects.equals(timezones, country.timezones) &&
                Objects.equals(subregion, country.subregion) &&
                Objects.equals(borders, country.borders) &&
                Objects.equals(population, country.population);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, capital, timezones, subregion, borders, area, population);
    }
}
