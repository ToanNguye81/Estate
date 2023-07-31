package com.project.estate.entity;

import java.math.BigDecimal;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "location")
@Entity
public class MasterLayout {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotNull(message = "input nảm")
    private String name;

    @Column(name = "description")
    @NotNull(message = "input nảm")
    private String description;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(name = "acreage")
    @NotNull(message = "input nảm")
    private BigDecimal acreage;
}
