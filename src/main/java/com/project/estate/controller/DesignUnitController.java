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
import com.project.estate.entity.DesignUnit;
import com.project.estate.repository.AddressRepository;
import com.project.estate.repository.DesignUnitRepository;

@RestController
@CrossOrigin
@RequestMapping("/")
public class DesignUnitController {
    @Autowired
    DesignUnitRepository gDesignUnitRepository;
    @Autowired
    AddressRepository gAddressRepository;

    // get all DesignUnit
    @GetMapping("/designUnit")
    public ResponseEntity<List<DesignUnit>> getAllDesignUnit() {
        try {
            // tạo ra một đối tượng Pageable để đại diện cho thông tin về phân trang.
            List<DesignUnit> designUnitList = new ArrayList<DesignUnit>();
            gDesignUnitRepository.findAll().forEach(designUnitList::add);

            return new ResponseEntity<>(designUnitList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // get designUnit by id
    @GetMapping("/designUnit/{designUnitId}")
    public ResponseEntity<Object> getDesignUnitById(
            @PathVariable Long designUnitId) {
        Optional<DesignUnit> vDesignUnitData = gDesignUnitRepository.findById(designUnitId);
        if (vDesignUnitData.isPresent()) {
            try {
                DesignUnit vDesignUnit = vDesignUnitData.get();
                return new ResponseEntity<>(vDesignUnit, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            DesignUnit vDesignUnitNull = new DesignUnit();
            return new ResponseEntity<>(vDesignUnitNull, HttpStatus.NOT_FOUND);
        }
    }

    // create new designUnit
    @PostMapping("/designUnit")
    @Transactional
    public ResponseEntity<Object> createNewDesignUnit(@Valid @RequestBody DesignUnit pDesignUnit) {
        try {
            // Lấy thông tin về Address từ yêu cầu
            Address pAddress = pDesignUnit.getAddress();

            if (pAddress == null) {
                return new ResponseEntity<>("Address information is missing", HttpStatus.BAD_REQUEST);
            }
            // Tạo mới Address từ thông tin trong yêu cầu
            Address newAddress = new Address();
            newAddress.setAddress(pAddress.getAddress());
            newAddress.setLat(pAddress.getLat());
            newAddress.setLng(pAddress.getLng());
            Address savedAddress = gAddressRepository.save(newAddress);

            // Tạo mới DesignUnit và liên kết với Address
            DesignUnit vDesignUnit = new DesignUnit();
            vDesignUnit.setAddress(savedAddress);
            vDesignUnit.setDescription(pDesignUnit.getDescription());
            vDesignUnit.setEmail(pDesignUnit.getEmail());
            vDesignUnit.setFax(pDesignUnit.getFax());
            vDesignUnit.setName(pDesignUnit.getName());
            vDesignUnit.setNote(pDesignUnit.getNote());
            vDesignUnit.setPhone(pDesignUnit.getPhone());
            vDesignUnit.setPhone2(pDesignUnit.getPhone2());
            vDesignUnit.setWebsite(pDesignUnit.getWebsite());

            // Lưu mới DesignUnit vào cơ sở dữ liệu
            DesignUnit vDesignUnitSave = gDesignUnitRepository.save(vDesignUnit);
            return new ResponseEntity<>(vDesignUnitSave, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity()
                    .body("Failed to Create specified DesignUnit: " + e.getCause().getCause().getMessage());
        }
    }

    @PutMapping("/designUnit/{designUnitId}")
    @Transactional
    public ResponseEntity<Object> updateDesignUnit(
            @PathVariable Long designUnitId,
            @Valid @RequestBody DesignUnit pDesignUnit) {
        Optional<DesignUnit> vDesignUnitData = gDesignUnitRepository.findById(designUnitId);
        if (vDesignUnitData.isPresent()) {
            try {
                DesignUnit existingDesignUnit = vDesignUnitData.get();
                Address pAddress = pDesignUnit.getAddress();

                if (pAddress == null) {
                    return new ResponseEntity<>("Address information is missing", HttpStatus.BAD_REQUEST);
                }

                // Kiểm tra xem addressId có thuộc về DesignUnit hiện tại hay không
                if (!existingDesignUnit.getAddress().getId().equals(pAddress.getId())) {
                    return new ResponseEntity<>("Address does not belong to this DesignUnit", HttpStatus.BAD_REQUEST);
                }

                Optional<Address> existingAddress = gAddressRepository.findById(pAddress.getId());
                if (existingAddress.isPresent()) {
                    Address savedAddress = existingAddress.get();
                    savedAddress.setAddress(pAddress.getAddress());
                    savedAddress.setLat(pAddress.getLat());
                    savedAddress.setLng(pAddress.getLng());
                    savedAddress = gAddressRepository.save(savedAddress);
                    // update DesignUnit
                    existingDesignUnit.setDescription(pDesignUnit.getDescription());
                    existingDesignUnit.setAddress(savedAddress);
                    existingDesignUnit.setEmail(pDesignUnit.getEmail());
                    existingDesignUnit.setFax(pDesignUnit.getFax());
                    existingDesignUnit.setName(pDesignUnit.getName());
                    existingDesignUnit.setNote(pDesignUnit.getNote());
                    existingDesignUnit.setPhone(pDesignUnit.getPhone());
                    existingDesignUnit.setPhone2(pDesignUnit.getPhone2());
                    existingDesignUnit.setWebsite(pDesignUnit.getWebsite());

                    DesignUnit updatedDesignUnit = gDesignUnitRepository.save(existingDesignUnit);
                    return new ResponseEntity<>(updatedDesignUnit, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Address not found", HttpStatus.NOT_FOUND);
                }
            } catch (Exception e) {
                return ResponseEntity.unprocessableEntity()
                        .body("Failed to Update specified DesignUnit: " + e.getCause().getCause().getMessage());
            }
        } else {
            return new ResponseEntity<>("DesignUnit not found", HttpStatus.NOT_FOUND);
        }
    }

    // Delete designUnit by id
    @DeleteMapping("/designUnit/{designUnitId}")
    private ResponseEntity<Object> deleteDesignUnitById(
            @PathVariable Long designUnitId) {
        Optional<DesignUnit> vDesignUnitData = gDesignUnitRepository.findById(designUnitId);
        if (vDesignUnitData.isPresent()) {
            try {
                gDesignUnitRepository.deleteById(designUnitId);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            DesignUnit vDesignUnitNull = new DesignUnit();
            return new ResponseEntity<>(vDesignUnitNull, HttpStatus.NOT_FOUND);
        }
    }

}
