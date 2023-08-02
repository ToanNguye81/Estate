
package com.project.estate.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "country")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", unique = true)
    private String code;

    @Column(name = "name")
    private String name;

    @OneToMany(targetEntity = Region.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "country_id")
    @JsonIgnore
    private Set<Region> regions;

}
