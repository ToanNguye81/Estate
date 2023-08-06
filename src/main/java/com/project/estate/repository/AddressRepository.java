package com.project.estate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.estate.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
