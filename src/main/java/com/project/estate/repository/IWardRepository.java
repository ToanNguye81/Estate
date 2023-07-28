package com.project.estate.repository;

// import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.estate.model.Ward;

public interface IWardRepository extends JpaRepository<Ward, Long> {

}
