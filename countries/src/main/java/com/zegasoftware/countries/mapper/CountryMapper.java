package com.zegasoftware.countries.mapper;

import com.zegasoftware.countries.model.dto.CountryDto;
import com.zegasoftware.countries.model.entity.Country;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring")
public interface CountryMapper {
    CountryMapper INSTANCE = Mappers.getMapper(CountryMapper.class);

    CountryDto map(Country country);

    @InheritInverseConfiguration(name = "map")
    Country map(CountryDto countryDto);
}
