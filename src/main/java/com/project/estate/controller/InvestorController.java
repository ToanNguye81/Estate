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
import com.project.estate.entity.Investor;
import com.project.estate.repository.AddressRepository;
import com.project.estate.repository.InvestorRepository;

@RestController
@CrossOrigin
@RequestMapping("/")
public class InvestorController {
    @Autowired
    InvestorRepository gInvestorRepository;
    @Autowired
    AddressRepository gAddressRepository;

    // get all Investor
    @GetMapping("/investor")
    public ResponseEntity<List<Investor>> getAllInvestor() {
        try {
            // tạo ra một đối tượng Pageable để đại diện cho thông tin về phân trang.
            List<Investor> investorList = new ArrayList<Investor>();
            gInvestorRepository.findAll().forEach(investorList::add);

            return new ResponseEntity<>(investorList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // get investor by id
    @GetMapping("/investor/{investorId}")
    public ResponseEntity<Object> getInvestorById(
            @PathVariable Long investorId) {
        Optional<Investor> vInvestorData = gInvestorRepository.findById(investorId);
        if (vInvestorData.isPresent()) {
            try {
                Investor vInvestor = vInvestorData.get();
                return new ResponseEntity<>(vInvestor, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            Investor vInvestorNull = new Investor();
            return new ResponseEntity<>(vInvestorNull, HttpStatus.NOT_FOUND);
        }
    }

    // create new investor
    @PostMapping("/investor")
    @Transactional
    public ResponseEntity<Object> createNewInvestor(@Valid @RequestBody Investor pInvestor) {
        try {
            // Lấy thông tin về Address từ yêu cầu
            Address pAddress = pInvestor.getAddress();

            if (pAddress == null) {
                return new ResponseEntity<>("Address information is missing", HttpStatus.BAD_REQUEST);
            }
            // Tạo mới Address từ thông tin trong yêu cầu
            Address newAddress = new Address();
            newAddress.setAddress(pAddress.getAddress());
            newAddress.setLat(pAddress.getLat());
            newAddress.setLng(pAddress.getLng());
            Address savedAddress = gAddressRepository.save(newAddress);

            // Tạo mới Investor và liên kết với Address
            Investor vInvestor = new Investor();
            vInvestor.setAddress(savedAddress);
            vInvestor.setDescription(pInvestor.getDescription());
            vInvestor.setEmail(pInvestor.getEmail());
            vInvestor.setFax(pInvestor.getFax());
            vInvestor.setName(pInvestor.getName());
            vInvestor.setNote(pInvestor.getNote());
            vInvestor.setPhone(pInvestor.getPhone());
            vInvestor.setPhone2(pInvestor.getPhone2());
            vInvestor.setWebsite(pInvestor.getWebsite());

            // Lưu mới Investor vào cơ sở dữ liệu
            Investor vInvestorSave = gInvestorRepository.save(vInvestor);
            return new ResponseEntity<>(vInvestorSave, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity()
                    .body("Failed to Create specified Investor: " + e.getCause().getCause().getMessage());
        }
    }

    @PutMapping("/investor/{investorId}")
    @Transactional
    public ResponseEntity<Object> updateInvestor(
            @PathVariable Long investorId,
            @Valid @RequestBody Investor pInvestor) {
        Optional<Investor> vInvestorData = gInvestorRepository.findById(investorId);
        if (vInvestorData.isPresent()) {
            try {
                Investor existingInvestor = vInvestorData.get();
                Address pAddress = pInvestor.getAddress();

                if (pAddress == null) {
                    return new ResponseEntity<>("Address information is missing", HttpStatus.BAD_REQUEST);
                }

                // Kiểm tra xem addressId có thuộc về Investor hiện tại hay không
                if (!existingInvestor.getAddress().getId().equals(pAddress.getId())) {
                    return new ResponseEntity<>("Address does not belong to this Investor", HttpStatus.BAD_REQUEST);
                }

                Optional<Address> existingAddress = gAddressRepository.findById(pAddress.getId());
                if (existingAddress.isPresent()) {
                    Address savedAddress = existingAddress.get();
                    savedAddress.setAddress(pAddress.getAddress());
                    savedAddress.setLat(pAddress.getLat());
                    savedAddress.setLng(pAddress.getLng());
                    savedAddress = gAddressRepository.save(savedAddress);
                    // update Investor
                    existingInvestor.setDescription(pInvestor.getDescription());
                    existingInvestor.setAddress(savedAddress);
                    existingInvestor.setEmail(pInvestor.getEmail());
                    existingInvestor.setFax(pInvestor.getFax());
                    existingInvestor.setName(pInvestor.getName());
                    existingInvestor.setNote(pInvestor.getNote());
                    existingInvestor.setPhone(pInvestor.getPhone());
                    existingInvestor.setPhone2(pInvestor.getPhone2());
                    existingInvestor.setWebsite(pInvestor.getWebsite());

                    Investor updatedInvestor = gInvestorRepository.save(existingInvestor);
                    return new ResponseEntity<>(updatedInvestor, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Address not found", HttpStatus.NOT_FOUND);
                }
            } catch (Exception e) {
                return ResponseEntity.unprocessableEntity()
                        .body("Failed to Update specified Investor: " + e.getCause().getCause().getMessage());
            }
        } else {
            return new ResponseEntity<>("Investor not found", HttpStatus.NOT_FOUND);
        }
    }

    // Delete investor by id
    @DeleteMapping("/investor/{investorId}")
    private ResponseEntity<Object> deleteInvestorById(
            @PathVariable Long investorId) {
        Optional<Investor> vInvestorData = gInvestorRepository.findById(investorId);
        if (vInvestorData.isPresent()) {
            try {
                gInvestorRepository.deleteById(investorId);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            Investor vInvestorNull = new Investor();
            return new ResponseEntity<>(vInvestorNull, HttpStatus.NOT_FOUND);
        }
    }

}
