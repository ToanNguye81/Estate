package com.project.estate.entity;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Email;
// import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "design_unit")
public class DesignUnit {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @NotNull(message = "input name")
    @Size(min = 2, message = "name at least 2 characters ")
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id") // Tên cột khóa ngoại trong bảng "design_unit"
    private Address address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "phone2")
    private String phone2;

    @Column(name = "email", unique = true)
    @Email(message = "Email not valid")
    private String email;

    @Column(name = "fax")
    private String fax;

    @Column(name = "website")
    private String website;

    @Column(name = "note")
    private String note;

    @OneToMany(mappedBy = "designUnit")
    private List<Project> projects;

    public DesignUnit() {
        super();
        // TODO Auto-generated constructor stub
    }

}
