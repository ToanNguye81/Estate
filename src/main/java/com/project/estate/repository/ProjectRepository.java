package com.project.estate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.estate.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}
