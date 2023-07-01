package com.zegasoftware.countries.controller;

import com.zegasoftware.countries.model.dto.CountryDto;
import com.zegasoftware.countries.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("countries")
public class CountryController {

    private final CountryService countryService;

    @GetMapping
    public List<CountryDto> getAllCountries() {
        return countryService.getAllCountries();
    }

    @GetMapping("/{id}")
    public Optional<CountryDto> getCountryById(@PathVariable int id) {
        return countryService.getCountryById(id);
    }

    @GetMapping("/paginationAndSort/{offset}/{pageSize}/{field}")
    public Page<CountryDto> findCountriesWithPaginationAndSorting(@PathVariable int offset, @PathVariable int pageSize, @PathVariable String field) {
        return countryService.findCountriesWithPaginationAndSorting(offset, pageSize, field);
    }
}
