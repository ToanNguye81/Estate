package com.project.estate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.estate.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByUserId(Long userId);

}
