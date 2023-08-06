package com.project.estate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.estate.entity.Contractor;

public interface ContractorRepository extends JpaRepository<Contractor, Long> {

}
