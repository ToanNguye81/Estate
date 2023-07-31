package com.project.estate.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.estate.model.District;
import com.project.estate.model.Province;
import com.project.estate.model.Ward;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "realEstate")
@Entity
public class RealEstate {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    @NotNull(message = "input title")
    private String title;

    private Integer type;
    private Integer request;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "province_id")
    private Province province;

    @ManyToOne
    @JoinColumn(name = "district_id")
    @JsonIgnore
    private District district;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "ward_id")
    private Ward ward;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "street_id")
    private Street street;

    private Integer project_id;
    private String address;
    private Integer customer_id;
    private Integer price;
    private Integer price_min;
    private Integer price_time;
    private Integer date_create;
    private Integer acreage;
    private Integer direction;
    private Integer total_floors;
    private Integer number_floors;
    private Integer bath;
    private Integer apart_code;
    private Integer wall_area;
    private Integer bedroom;
    private Integer balcony;
    private Integer landscape_view;
    private Integer apart_location;
    private Integer apart_type;
    private Integer furniture_type;
    private Integer price_rent;
    private Integer return_rate;
    private Integer legal_doc;
    private Integer description;
    private Integer width_y;
    private Integer long_x;
    private Integer street_house;
    private Integer FSBO;
    private Integer view_num;
    private Integer create_by;
    private Integer update_by;
    private Integer shape;
    private Integer distance2facade;
    private Integer adjacent_facade_num;
    private Integer adjacent_road;
    private Integer alley_min_width;
    private Integer adjacent_alley_min_width;
    private Integer factor;
    private Integer structure;
    private Integer DTSXD;
    private Integer CLCL;
    private Integer CTXD_price;
    private Integer CTXD_value;
    private Integer photo;
    private Integer lat;
    private Integer lng;

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "project_designUnit", joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "designUnit_id"))
    private List<DesignUnit> designUnits;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "project_contractor", joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "contractor_id"))
    private List<Contractor> contractors;

}