package com.project.estate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.estate.model.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {

    Country findByCountryCodeContaining(String code);
}
