package com.project.estate.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "region")
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "region_id", unique = true)
    private Integer id;

    @Column(name = "code", unique = true)
    private String code;

    @Column(name = "name")
    private String name;

    @ManyToOne // mỗi CRegion thuộc về một CCountry duy nhất.
    @JoinColumn(name = "country_id") // quan hệ giữa CRegion và CCountry là một quan hệ quản lý bỏ trên 1 trong 2
    @JsonIgnore
    private Country country;// CRegion sẽ được liên kết với CCountry dựa trên khóa chính country

}
