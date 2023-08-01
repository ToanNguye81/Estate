package com.project.estate.entity;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "utility")
@Entity
public class Utility {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private User name;

    @Column(name = "description")
    private String description;

    @Column(name = "photo")
    private String photo;

}
