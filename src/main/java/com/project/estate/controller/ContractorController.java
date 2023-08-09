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
import com.project.estate.entity.Contractor;
import com.project.estate.repository.AddressRepository;
import com.project.estate.repository.ContractorRepository;

@RestController
@CrossOrigin
@RequestMapping("/")
public class ContractorController {
    @Autowired
    ContractorRepository gContractorRepository;
    @Autowired
    AddressRepository gAddressRepository;

    // get all Contractor
    @GetMapping("/contractor")
    public ResponseEntity<List<Contractor>> getAllContractor() {
        try {
            // tạo ra một đối tượng Pageable để đại diện cho thông tin về phân trang.
            List<Contractor> contractorList = new ArrayList<Contractor>();
            gContractorRepository.findAll().forEach(contractorList::add);

            return new ResponseEntity<>(contractorList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // get contractor by id
    @GetMapping("/contractor/{contractorId}")
    public ResponseEntity<Object> getContractorById(
            @PathVariable Long contractorId) {
        Optional<Contractor> vContractorData = gContractorRepository.findById(contractorId);
        if (vContractorData.isPresent()) {
            try {
                Contractor vContractor = vContractorData.get();
                return new ResponseEntity<>(vContractor, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            Contractor vContractorNull = new Contractor();
            return new ResponseEntity<>(vContractorNull, HttpStatus.NOT_FOUND);
        }
    }

    // create new contractor
    @PostMapping("/contractor")
    @Transactional
    public ResponseEntity<Object> createNewContractor(@Valid @RequestBody Contractor pContractor) {
        try {
            // Lấy thông tin về Address từ yêu cầu
            Address pAddress = pContractor.getAddress();

            if (pAddress == null) {
                return new ResponseEntity<>("Address information is missing", HttpStatus.BAD_REQUEST);
            }
            // Tạo mới Address từ thông tin trong yêu cầu
            Address newAddress = new Address();
            newAddress.setAddress(pAddress.getAddress());
            newAddress.setLat(pAddress.getLat());
            newAddress.setLng(pAddress.getLng());
            Address savedAddress = gAddressRepository.save(newAddress);

            // Tạo mới Contractor và liên kết với Address
            Contractor vContractor = new Contractor();
            vContractor.setAddress(savedAddress);
            vContractor.setDescription(pContractor.getDescription());
            vContractor.setEmail(pContractor.getEmail());
            vContractor.setFax(pContractor.getFax());
            vContractor.setName(pContractor.getName());
            vContractor.setNote(pContractor.getNote());
            vContractor.setPhone(pContractor.getPhone());
            vContractor.setPhone2(pContractor.getPhone2());
            vContractor.setWebsite(pContractor.getWebsite());

            // Lưu mới Contractor vào cơ sở dữ liệu
            Contractor vContractorSave = gContractorRepository.save(vContractor);
            return new ResponseEntity<>(vContractorSave, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity()
                    .body("Failed to Create specified Contractor: " + e.getCause().getCause().getMessage());
        }
    }

    @PutMapping("/contractor/{contractorId}")
    @Transactional
    public ResponseEntity<Object> updateContractor(
            @PathVariable Long contractorId,
            @Valid @RequestBody Contractor pContractor) {
        Optional<Contractor> vContractorData = gContractorRepository.findById(contractorId);
        if (vContractorData.isPresent()) {
            try {
                Contractor existingContractor = vContractorData.get();
                Address pAddress = pContractor.getAddress();

                if (pAddress == null) {
                    return new ResponseEntity<>("Address information is missing", HttpStatus.BAD_REQUEST);
                }

                // Kiểm tra xem addressId có thuộc về Contractor hiện tại hay không
                if (!existingContractor.getAddress().getId().equals(pAddress.getId())) {
                    return new ResponseEntity<>("Address does not belong to this Contractor", HttpStatus.BAD_REQUEST);
                }

                Optional<Address> existingAddress = gAddressRepository.findById(pAddress.getId());
                if (existingAddress.isPresent()) {
                    Address savedAddress = existingAddress.get();
                    savedAddress.setAddress(pAddress.getAddress());
                    savedAddress.setLat(pAddress.getLat());
                    savedAddress.setLng(pAddress.getLng());
                    savedAddress = gAddressRepository.save(savedAddress);
                    // update Contractor
                    existingContractor.setDescription(pContractor.getDescription());
                    existingContractor.setAddress(savedAddress);
                    existingContractor.setEmail(pContractor.getEmail());
                    existingContractor.setFax(pContractor.getFax());
                    existingContractor.setName(pContractor.getName());
                    existingContractor.setNote(pContractor.getNote());
                    existingContractor.setPhone(pContractor.getPhone());
                    existingContractor.setPhone2(pContractor.getPhone2());
                    existingContractor.setWebsite(pContractor.getWebsite());

                    Contractor updatedContractor = gContractorRepository.save(existingContractor);
                    return new ResponseEntity<>(updatedContractor, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Address not found", HttpStatus.NOT_FOUND);
                }
            } catch (Exception e) {
                return ResponseEntity.unprocessableEntity()
                        .body("Failed to Update specified Contractor: " + e.getCause().getCause().getMessage());
            }
        } else {
            return new ResponseEntity<>("Contractor not found", HttpStatus.NOT_FOUND);
        }
    }

    // Delete contractor by id
    @DeleteMapping("/contractor/{contractorId}")
    private ResponseEntity<Object> deleteContractorById(
            @PathVariable Long contractorId) {
        Optional<Contractor> vContractorData = gContractorRepository.findById(contractorId);
        if (vContractorData.isPresent()) {
            try {
                gContractorRepository.deleteById(contractorId);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            Contractor vContractorNull = new Contractor();
            return new ResponseEntity<>(vContractorNull, HttpStatus.NOT_FOUND);
        }
    }

}
