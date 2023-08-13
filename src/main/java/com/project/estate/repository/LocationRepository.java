package com.project.estate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.estate.entity.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {

}
