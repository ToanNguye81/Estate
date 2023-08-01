package com.project.estate.model;

import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.estate.entity.Project;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "district")
public class District {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private int id;

    @NotNull(message = "Input district name")
    @Size(min = 2, message = "Name at least 2 characters ")
    @Column(name = "name")
    private String name;

    @NotNull(message = "Input district prefix")
    @Size(min = 2, message = "Prefix at least 2 characters ")
    @Column(name = "prefix")
    private String prefix;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "province_id", nullable = false)
    private Province province;

    @JsonIgnore
    @OneToMany(mappedBy = "district", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Ward> wards;

    @OneToMany(targetEntity = Project.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id")
    @JsonIgnore
    private Set<Project> projects;
}
