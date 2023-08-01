package com.project.estate.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "address")
public class Address {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "input address")
    @Size(min = 2, message = "address at least 2 characters ")
    @Column(name = "address")
    private String address;

    @Column(name = "lat")
    @NotNull(message = "input lat")
    private Double lat;

    @Column(name = "lng")
    @NotNull(message = "input lat")
    private Double lng;

    @OneToOne(mappedBy = "address", cascade = CascadeType.ALL)
    private Contractor contractor;

    @OneToOne(mappedBy = "address", cascade = CascadeType.ALL)
    private DesignUnit designUnit;

    @OneToOne(mappedBy = "address", cascade = CascadeType.ALL)
    private Investor investor;

    public Address() {
        super();
        // TODO Auto-generated constructor stub
    }

}
