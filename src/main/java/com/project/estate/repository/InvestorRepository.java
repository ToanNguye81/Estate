package com.project.estate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.estate.entity.Investor;

public interface InvestorRepository extends JpaRepository<Investor, Long> {

}
