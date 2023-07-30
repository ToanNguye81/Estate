package com.project.estate.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "construction_contractor")
public class ConstructionContractor {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "input name")
    @Size(min = 2, message = "name at least 2 characters ")
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "phone")
    private String phone;

    @Column(name = "phone2")
    private String phone2;

    @Column(name = "fax")
    private String fax;

    @Column(name = "website")
    private String website;

    @Column(name = "note")
    private String note;

    @Column(name = "email", unique = true)
    @Email(message = "Email not valid")
    private String email;

    @Column(name = "projects")
    private String projects;

    @Column(name = "address")
    private String address;

    public ConstructionContractor() {
        super();
        // TODO Auto-generated constructor stub
    }

}
