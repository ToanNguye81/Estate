package com.project.estate.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "master_layout")
@Entity
public class MasterLayout {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotNull(message = "input name")
    private String name;

    @Column(name = "description")
    @NotNull(message = "input name")
    private String description;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name = "apartment_list")
    private String apartmentList;

    @Column(name = "photo")
    private String photo;

    @Column(name = "date_create")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+7")
    private Date dateCreate;

    @Column(name = "date_update")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+7")
    private Date dateUpdate;

    @Column(name = "acreage")
    @NotNull(message = "input name")
    private BigDecimal acreage;
}
