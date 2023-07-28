package com.project.estate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.estate.entity.Customer;

public interface ICustomerRepository extends JpaRepository<Customer, Long> {

}
