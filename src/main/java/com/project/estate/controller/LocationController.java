package com.project.estate.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.estate.entity.Address;
import com.project.estate.entity.Location;
import com.project.estate.repository.AddressRepository;
import com.project.estate.repository.LocationRepository;

@RestController
@CrossOrigin
@RequestMapping("/")
public class LocationController {
    @Autowired
    LocationRepository gLocationRepository;
    @Autowired
    AddressRepository gAddressRepository;

    // get all Location
    @GetMapping("/location")
    public ResponseEntity<List<Location>> getAllLocation() {
        try {
            // tạo ra một đối tượng Pageable để đại diện cho thông tin về phân trang.
            List<Location> locationList = new ArrayList<Location>();
            gLocationRepository.findAll().forEach(locationList::add);

            return new ResponseEntity<>(locationList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // get location by id
    @GetMapping("/location/{locationId}")
    public ResponseEntity<Object> getLocationById(
            @PathVariable Long locationId) {
        Optional<Location> vLocationData = gLocationRepository.findById(locationId);
        if (vLocationData.isPresent()) {
            try {
                Location vLocation = vLocationData.get();
                return new ResponseEntity<>(vLocation, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            Location vLocationNull = new Location();
            return new ResponseEntity<>(vLocationNull, HttpStatus.NOT_FOUND);
        }
    }

    // create new location
    @PostMapping("/location")
    @Transactional
    public ResponseEntity<Object> createNewLocation(@Valid @RequestBody Location pLocation) {
        try {

            // Tạo mới Location và liên kết với Address
            Location vLocation = new Location();
            vLocation.setLat(pLocation.getLat());
            vLocation.setLng(pLocation.getLng());

            // Lưu mới Location vào cơ sở dữ liệu
            Location vLocationSave = gLocationRepository.save(vLocation);
            return new ResponseEntity<>(vLocationSave, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity()
                    .body("Failed to Create specified Location: " + e.getCause().getCause().getMessage());
        }
    }

    @PutMapping("/location/{locationId}")
    @Transactional
    public ResponseEntity<Object> updateLocation(
            @PathVariable Long locationId,
            @Valid @RequestBody Location pLocation) {
        Optional<Location> vLocationData = gLocationRepository.findById(locationId);
        if (vLocationData.isPresent()) {
            try {
                Location existingLocation = vLocationData.get();
                // update Location
                existingLocation.setLat(pLocation.getLat());
                existingLocation.setLng(pLocation.getLng());

                Location updatedLocation = gLocationRepository.save(existingLocation);
                return new ResponseEntity<>(updatedLocation, HttpStatus.OK);

            } catch (Exception e) {
                return ResponseEntity.unprocessableEntity()
                        .body("Failed to Update specified Location: " + e.getCause().getCause().getMessage());
            }
        } else {
            return new ResponseEntity<>("Location not found", HttpStatus.NOT_FOUND);
        }
    }

    // Delete location by id
    @DeleteMapping("/location/{locationId}")
    private ResponseEntity<Object> deleteLocationById(
            @PathVariable Long locationId) {
        Optional<Location> vLocationData = gLocationRepository.findById(locationId);
        if (vLocationData.isPresent()) {
            try {
                gLocationRepository.deleteById(locationId);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            Location vLocationNull = new Location();
            return new ResponseEntity<>(vLocationNull, HttpStatus.NOT_FOUND);
        }
    }

}
