package com.project.estate.entity;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @NotNull(message = "Nhập Full Name")
    @Size(min = 2, message = "Full Name phải có ít nhất 2 ký tự ")
    @Column(name = "full_name")
    private String fullName;

    @NotNull(message = "Nhập email")
    @Email(message = "Email không hợp lệ")
    @Column(name = "email", unique = true)
    private String email;

    @NotNull(message = "Nhập phone")
    @Size(min = 10, max = 10, message = "Phone phải là chuỗi có đúng 10 chữ số")
    @Column(name = "phone", unique = true)
    private String phone;

    @Column(name = "address")
    private String address;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_at", nullable = true, updatable = false)
    @CreatedDate
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date createAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_at", nullable = true)
    @LastModifiedDate
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date updateAt;

    public Customer() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public Long getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

}
