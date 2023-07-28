package com.project.estate.controller;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.estate.entity.Profile;
import com.project.estate.repository.IProfileRepository;

import org.springframework.web.bind.annotation.GetMapping;

@CrossOrigin
@RestController
@RequestMapping("/")
public class ProfileController {
    @Autowired
    IProfileRepository pIProfileRepository;

    @GetMapping("/profile/all")
    public ResponseEntity<List<Profile>> getAllProfiles() {

        try {
            List<Profile> pProfiles = new ArrayList<Profile>();
            pIProfileRepository.findAll().forEach(pProfiles::add);
            return new ResponseEntity<>(pProfiles, HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
