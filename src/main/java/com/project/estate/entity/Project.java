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
@Table(name = "project")
@Entity
public class Project {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotNull(message = "input name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "slogan")
    private String slogan;

    @Column(name = "description")
    private String description;

    @Column(name = "acreage")
    private BigDecimal acreage;

    @Column(name = "constructArea")
    private BigDecimal constructArea;

    @Column(name = "numBlock")
    private Short numBlock;

    @Column(name = "numFloors")
    private String numFloors;

    @Column(name = "numApartment")
    private Integer numApartment;

    @Column(name = "apartmentArea")
    private String apartmentArea;

    @Column(name = "investor")
    private Integer investor;

    @Column(name = "utilities")
    private String utilities;

    @Column(name = "regionLink")
    private String regionLink;

    @Column(name = "photo")
    private String photo;

    @Column(name = "lat")
    private Double lat;

    @Column(name = "lng")
    private Double lng;

    // @ManyToOne
    // @JsonIgnore
    // @JoinColumn(name = "province_id")
    // private Province province;

    // @ManyToOne
    // @JoinColumn(name = "district_id")
    // @JsonIgnore
    // private District district;

    // @ManyToOne
    // @JsonIgnore
    // @JoinColumn(name = "ward_id")
    // private Ward ward;

    // @ManyToOne
    // @JsonIgnore
    // @JoinColumn(name = "street_id")
    // private Street street;

    // @ManyToMany
    // @JsonIgnore
    // @JoinTable(name = "project_designUnit", joinColumns = @JoinColumn(name =
    // "project_id"), inverseJoinColumns = @JoinColumn(name = "designUnit_id"))
    // private List<DesignUnit> designUnits;

    // @JsonIgnore
    // @ManyToMany
    // @JoinTable(name = "project_contractor", joinColumns = @JoinColumn(name =
    // "project_id"), inverseJoinColumns = @JoinColumn(name = "contractor_id"))
    // private List<Contractor> contractors;

    // @OneToMany(mappedBy = "project")
    // private List<RealEstate> realEstates;
}