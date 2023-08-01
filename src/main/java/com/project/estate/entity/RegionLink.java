package com.project.estate.entity;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "region_link")
@Entity
public class RegionLink {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "photo")
    private String photo;

    @Column(name = "address")
    private String address;

    @Column(name = "lat")
    private Float lat;

    @Column(name = "lng")
    private Float lng;
}
