package com.project.estate.entity;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "employees")
@Entity
public class Employee extends User {

    @NotNull(message = "Nhập First Name")
    @Size(min = 2, message = "First Name at least 2 characters ")
    @Column(name = "firstName")
    private String firstName;

    @NotNull(message = "Nhập Last Name")
    @Size(min = 2, message = "Last Name at least 2 characters ")
    @Column(name = "lastName")
    private String lastName;

    @Column(name = "title")
    private String title;

    @Column(name = "litleOfCourtesy")
    private String titleOfCourtesy;

    @Column(name = "birthDate")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+7")
    private String birthDate;

    @Column(name = "hireDate")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+7")
    private String hireDate;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "region")
    private String region;

    @Column(name = "postalCode")
    private String postalCode;

    @Column(name = "country")
    private String country;

    @Column(name = "homePhone")
    private String homePhone;

    @Column(name = "extension")
    private String extension;

    @Column(name = "photo")
    private String photo;

    @Column(name = "reportsTo")
    private String reportsTo;

    @Column(name = "notes")
    private String note;

    @NotNull(message = "Nhập email")
    @Email(message = "Email not valid")
    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "profile")
    private String profile;

}
