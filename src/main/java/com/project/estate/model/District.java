package com.project.estate.model;

import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "district")
public class District {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private int id;

    @NotNull(message = "Nhập district name")
    @Size(min = 2, message = "Name phải có ít nhất 2 ký tự ")
    @Column(name = "name")
    private String name;

    @NotNull(message = "Nhập district prefix")
    @Size(min = 2, message = "Prefix phải có ít nhất 2 ký tự ")
    @Column(name = "prefix")
    private String prefix;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "province_id")
    private Province province;

    @OneToMany(mappedBy = "district", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Ward> wards;

    public District() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public void setWards(Set<Ward> wards) {
        this.wards = wards;
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public int getId() {
        return id;
    }

    public Set<Ward> getWards() {
        return wards;
    }

    public Province getProvince() {
        return province;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return getWards().toString();
    }
}
