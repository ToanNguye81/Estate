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
@Table(name = "customer")
public class Customer extends BaseEntity {

    @NotNull(message = "Input contact name")
    @Size(min = 2, message = "contact name at least 2 characters ")
    @Column(name = "contactName")
    private String contactName;

    @NotNull(message = "Input Contact title")
    @Size(min = 2, message = "Contact title at least 2 characters ")
    @Column(name = "contactTitle")
    private String contactTitle;

    @Column(name = "address")
    private String address;

    @Column(name = "mobile", unique = true)
    private String mobile;

    @Column(name = "email", unique = true)
    @Email(message = "Email not valid")
    private String email;

    @Column(name = "note")
    private String note;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // Tên cột khóa ngoại trong bảng "customer"
    private User user;
    
    public Customer() {
        super();
        // TODO Auto-generated constructor stub
    }

}
