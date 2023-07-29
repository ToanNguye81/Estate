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
@Table(name = "customers")
public class Customer extends User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Input First Name")
    @Size(min = 2, message = "First Name at least 2 characters ")
    @Column(name = "contactName")
    private String contactName;

    @NotNull(message = "Input Last Name")
    @Size(min = 2, message = "Last Name at least 2 characters ")
    @Column(name = "contactTitle")
    private String contactTitle;

    @NotNull(message = "Input phone")
    @Size(min = 10, max = 10, message = "Phone Number have 10 digitals")
    @Column(name = "mobile", unique = true)
    private String mobile;

    @Column(name = "email", unique = true)
    @NotNull(message = "Input email")
    @Email(message = "Email not valid")
    private String email;

    @NotNull(message = "Input address ")
    @Size(min = 2, message = "address at least 2 characters ")
    @Column(name = "address")
    private String address;

    @NotNull(message = "Input note")
    @Column(name = "note")
    private String note;

    public Customer() {
        super();
        // TODO Auto-generated constructor stub
    }

}
