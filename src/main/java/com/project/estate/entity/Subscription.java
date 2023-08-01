package com.project.estate.entity;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "subscription")
@Entity
public class Subscription {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "endpoint")
    private String endpoint;

    @Column(name = "publicKey")
    private String publicKey;

    @Column(name = "authenticationToken")
    private String authenticationToken;

    @Column(name = "contentEncoding")
    private String contentEncoding;

}
