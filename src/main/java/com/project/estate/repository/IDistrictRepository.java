package com.project.estate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.estate.model.District;

public interface IDistrictRepository extends JpaRepository<District, Long> {

}
