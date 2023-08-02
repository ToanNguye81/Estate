package com.project.estate.entity;

import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.estate.model.District;
import com.project.estate.model.Province;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "street")
@Entity
public class Street {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotNull(message = "input name")
    private String name;

    @Column(name = "prefix")
    @NotNull(message = "input prefix")
    private String prefix;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "province_id")
    private Province province;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "district_id")
    private District district;

}