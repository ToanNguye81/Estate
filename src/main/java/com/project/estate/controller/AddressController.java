package com.project.estate.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.estate.entity.Address;
import com.project.estate.repository.AddressRepository;

@RestController
@CrossOrigin
@RequestMapping("/")
public class AddressController {
    @Autowired
    AddressRepository gAddressRepository;

    // get all Address
    @GetMapping("/address")
    public ResponseEntity<List<Address>> getAllAddress(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        try {
            // tạo ra một đối tượng Pageable để đại diện cho thông tin về phân trang.
            Pageable pageable = PageRequest.of(page, size);
            Page<Address> addressPage = gAddressRepository.findAll(pageable);
            List<Address> addressList = addressPage.getContent();
            Long totalElement = addressPage.getTotalElements();

            return ResponseEntity.ok()
                    .header("totalCount", String.valueOf(totalElement))
                    .body(addressList);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // create new address
    @PostMapping("/address")
    public ResponseEntity<Object> createNewAddress(
            @Valid @RequestBody Address pAddress) {
        try {
            Address vAddress = new Address();
            vAddress.setAddress(pAddress.getAddress());
            vAddress.setLat(pAddress.getLat());
            vAddress.setLng(pAddress.getLng());
            Address vAddressSave = gAddressRepository.save(vAddress);
            return new ResponseEntity<>(vAddressSave, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity()
                    .body("Failed to Create specified Address: " + e.getCause().getCause().getMessage());
        }

    }

    // get address by id
    @GetMapping("/address/{addressId}")
    public ResponseEntity<Object> getAddressById(
            @PathVariable Long addressId) {
        Optional<Address> vAddressData = gAddressRepository.findById(addressId);
        if (vAddressData.isPresent()) {
            try {
                Address vAddress = vAddressData.get();
                return new ResponseEntity<>(vAddress, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            Address vAddressNull = new Address();
            return new ResponseEntity<>(vAddressNull, HttpStatus.NOT_FOUND);
        }
    }

    // Update address by id
    @PutMapping("/address/{addressId}")
    public ResponseEntity<Object> updateAddress(
            @PathVariable Long addressId,
            @Valid @RequestBody Address pAddress) {
        Optional<Address> vAddressData = gAddressRepository.findById(addressId);
        if (vAddressData.isPresent()) {
            try {
                Address vAddress = vAddressData.get();
                vAddress.setAddress(pAddress.getAddress());
                vAddress.setLat(pAddress.getLat());
                vAddress.setLng(pAddress.getLng());
                Address vAddressSave = gAddressRepository.save(vAddress);
                return new ResponseEntity<>(vAddressSave, HttpStatus.OK);
            } catch (Exception e) {
                return ResponseEntity.unprocessableEntity()
                        .body("Failed to Update specified Address: " + e.getCause().getCause().getMessage());
            }
        } else {
            Address vAddressNull = new Address();
            return new ResponseEntity<>(vAddressNull, HttpStatus.NOT_FOUND);
        }
    }

    // Delete address by id
    @DeleteMapping("/address/{addressId}")
    private ResponseEntity<Object> deleteAddressById(
            @PathVariable Long addressId) {
        Optional<Address> vAddressData = gAddressRepository.findById(addressId);
        if (vAddressData.isPresent()) {
            try {
                gAddressRepository.deleteById(addressId);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            Address vAddressNull = new Address();
            return new ResponseEntity<>(vAddressNull, HttpStatus.NOT_FOUND);
        }
    }

}
