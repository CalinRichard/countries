package com.zegasoftware.countries.service;

import com.zegasoftware.countries.mapper.CountryMapper;
import com.zegasoftware.countries.model.dto.CountryDto;
import com.zegasoftware.countries.model.entity.Country;
import com.zegasoftware.countries.repository.CountryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CountryServiceImpl implements CountryService {
    private final RestTemplate restTemplate;
    private final CountryRepository countryRepository;
    private final Environment environment;
    private final CountryMapper countryMapper;
    private final Logger logger = LoggerFactory.getLogger(CountryServiceImpl.class);

    public CountryServiceImpl(RestTemplate restTemplate, CountryRepository countryRepository, Environment environment, CountryMapper countryMapper) {
        this.restTemplate = restTemplate;
        this.countryRepository = countryRepository;
        this.environment = environment;
        this.countryMapper = countryMapper;
    }
    @Override
    public List<CountryDto> getAllCountries() {
        saveCountriesToDatabase();
        return countryRepository.findAll().stream()
                .map(countryMapper::map)
                .toList();
    }

    @Override
    public Optional<CountryDto> getCountryById(int id) {
        return countryRepository.findById(id).map(countryMapper::map);
    }

    private void saveCountriesToDatabase() {
        String url = "https://restcountries.com/v2/all";

        ResponseEntity<Country[]> response = restTemplate.getForEntity(url, Country[].class);
        Optional<Country[]> countriesOptional = Optional.ofNullable(response.getBody());
        List<Country> countries = countriesOptional.map(Arrays::asList).orElse(Collections.emptyList());

        List<Country> existingCountries = countryRepository.findAll();

        if (isModified(countries, existingCountries) && !existingCountries.isEmpty()) {
            countryRepository.deleteAll();
            restartSequence();
            countryRepository.saveAll(countries);
            logger.info("The data has been deleted and reinserted into the database.");
        }
        if (isModified(countries, existingCountries) && existingCountries.isEmpty()) {
            countryRepository.saveAll(countries);
            logger.info("The data has been inserted into the database.");
        }
    }

    private boolean isModified(List<Country> retrievedCountries, List<Country> existingCountries) {
        // Compare the number of countries
        if (retrievedCountries.size() != existingCountries.size()) {
            return true;
        }

        // Compare each country individually
        for (Country retrievedCountry : retrievedCountries) {
            boolean exists = false;
            for (Country existingCountry : existingCountries) {
                if (retrievedCountry.getName().equals(existingCountry.getName()) &&
                        retrievedCountry.getPopulation().equals(existingCountry.getPopulation())) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                return true; // Modified country found
            }
        }

        return false; // No modifications
    }

    private void restartSequence() {
        // Retrieve connection parameters from application.properties
        String url = environment.getProperty("spring.datasource.url");
        String username = environment.getProperty("spring.datasource.username");
        String password = environment.getProperty("spring.datasource.password");

        // SQL query
        String query = "ALTER SEQUENCE countries_seq RESTART WITH 1";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement()) {

            // Execute the SQL query
            statement.execute(query);

            logger.info("Sequence restarted with 1.");
        } catch (SQLException e) {
            logger.error("Failed to restart sequence: {}", e.getMessage());
        }
    }
}
