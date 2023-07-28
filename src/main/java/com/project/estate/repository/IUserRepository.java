package com.project.estate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.estate.entity.User;

public interface IUserRepository extends JpaRepository<User, Long> {

}
