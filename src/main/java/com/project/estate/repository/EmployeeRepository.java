package com.project.estate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.estate.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
