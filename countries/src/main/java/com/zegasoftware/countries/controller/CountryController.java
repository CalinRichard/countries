package com.zegasoftware.countries.controller;

import com.zegasoftware.countries.model.dto.CountryDto;
import com.zegasoftware.countries.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;

    @GetMapping("/countries")
    public List<CountryDto> getAllCountries() {
        return countryService.getAllCountries();
    }

    @GetMapping("/countries/{id}")
    public Optional<CountryDto> getCountryById(@PathVariable("id") int id) {
        return countryService.getCountryById(id);
    }
}
