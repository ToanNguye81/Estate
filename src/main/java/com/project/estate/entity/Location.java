package com.project.estate.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "location")
@Entity
public class Location {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lat")
    @NotNull(message = "input lat")
    private Float lat;

    @Column(name = "lng")
    @NotNull(message = "input lat")
    private Float lng;
}
