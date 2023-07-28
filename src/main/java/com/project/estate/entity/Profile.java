package com.project.estate.entity;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "profiles")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "phone")
    @NotNull(message = "Vui lòng nhập số điện thoại")
    @Size(min = 10, max = 10, message = "Số điện thoại phải có đúng 10 kí tự")
    private String phone;

    @Column(name = "gender")
    @NotNull(message = "Giới tính không được để trống")
    private String gender;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "day_of_birth", nullable = true, updatable = false)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dateOfBirth;

    @Column(name = "address")
    @NotNull(message = "Địa chỉ không được để trống")
    private String address;

    // Khai báo kiểu quan hệ 1-1: user-profile
    @OneToOne(fetch = FetchType.LAZY, optional = false) // data sẽ chỉ được tải khi nó được truy cập hoặc yêu cầu.
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    public Profile() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getAddress() {
        return address;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public Long getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public User getUser() {
        return user;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
