package com.project.estate.model;

import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.estate.entity.Project;

@Entity
@Table(name = "ward")
public class Ward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private int id;

    @NotNull(message = "Nhập ward name")
    @Size(min = 2, message = "Name phải có ít nhất 2 ký tự ")
    @Column(name = "name")
    private String name;

    @NotNull(message = "Nhập ward prefix")
    @Size(min = 2, message = "Prefix phải có ít nhất 2 ký tự ")
    @Column(name = "prefix")
    private String prefix;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "district_id")
    private District district;

    @OneToMany(targetEntity = Project.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id")
    @JsonIgnore
    private Set<Project> projects;

    public Ward() {
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

    public void setId(int id) {
        this.id = id;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public District getDistrict() {
        return district;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "{" + getId() + "," + getPrefix().toString() + "," + getName().toString() + "}";
    }

}
