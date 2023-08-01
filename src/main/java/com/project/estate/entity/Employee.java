package com.project.estate.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "employee")
@Entity
public class Employee {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "input First Name")
    @Size(min = 2, message = "First Name at least 2 characters ")
    @Column(name = "firstNsame")
    private String firstName;

    @NotNull(message = "input Last Name")
    @Size(min = 2, message = "Last Name at least 2 characters ")
    @Column(name = "lastName")
    private String lastName;

    @Column(name = "title")
    private String title;

    @Column(name = "titleOfCourtesy")
    private String titleOfCourtesy;

    @Column(name = "birthDate")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+7")
    private Date birthDate;

    @Column(name = "hireDate")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+7")
    private Date hireDate;

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

    @NotNull(message = "Input email")
    @Email(message = "Email not valid")
    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "profile")
    private String profile;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // Tên cột khóa ngoại trong bảng "customer"
    private User user;

}
