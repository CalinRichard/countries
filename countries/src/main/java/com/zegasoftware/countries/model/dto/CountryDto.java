package com.zegasoftware.countries.model.dto;

import java.util.List;
public record CountryDto(int id, String name, String capital, List<String> timezones, String subregion, List<String> borders, int area, Integer population) {}
