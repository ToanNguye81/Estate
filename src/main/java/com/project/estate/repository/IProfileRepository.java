package com.project.estate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.estate.entity.Profile;

public interface IProfileRepository extends JpaRepository<Profile, Long> {

}
