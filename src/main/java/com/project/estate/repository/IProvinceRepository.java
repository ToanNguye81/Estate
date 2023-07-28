package com.project.estate.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.estate.model.Province;

public interface IProvinceRepository extends JpaRepository<Province, Long> {

    Optional<Province> findByCode(String provinceCode);

}
