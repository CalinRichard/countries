package com.zegasoftware.countries.service;

import com.zegasoftware.countries.model.dto.CountryDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CountryService {
    List<CountryDto> getAllCountries();

    Optional<CountryDto> getCountryById(int id);

    Page<CountryDto> findCountriesWithPaginationAndSorting(int offset, int pageSize, String field);
}
