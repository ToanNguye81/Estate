package com.project.estate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.estate.model.Region;

public interface IRegionRepository extends JpaRepository<Region, Long> {

    Region findByRegionCodeContaining(String code);

    List<Region> findByCountryId(Long countryId);

}
