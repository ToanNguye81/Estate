package com.project.estate.model;

import java.util.Set;

import javax.persistence.*;
// import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.estate.entity.Project;
import com.project.estate.entity.Street;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "province")
public class Province {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private int id;

    // @NotNull(message = "Nhập province name")
    @Size(min = 2, message = "Name phải có ít nhất 2 ký tự ")
    @Column(name = "name")
    private String name;

    // @NotNull(message = "Nhập province code")
    @Size(min = 2, message = "Code phải có ít nhất 2 ký tự ")
    @Column(name = "code", unique = true)
    private String code;

    @OneToMany(mappedBy = "province", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<District> districts;

    @OneToMany(mappedBy = "province", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Ward> wards;

    @OneToMany(mappedBy = "province", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Project> projects;

    @OneToMany(mappedBy = "province", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Street> streets;

}
