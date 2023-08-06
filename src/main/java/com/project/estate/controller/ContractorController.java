package com.project.estate.controller;

import java.util.ArrayList;
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

    // create new contractor
    @PostMapping("/contractor")
    public ResponseEntity<Object> createNewContractor(
            @Valid @RequestBody Contractor pContractor,
            @RequestParam(value = "addressId") Long addressId) {
        try {
            Optional<Address> addressData = gAddressRepository.findById(addressId);
            if (addressData.isPresent()) {
                Contractor vContractor = new Contractor();
                vContractor.setAddress(addressData.get());
                vContractor.setDescription(pContractor.getDescription());
                vContractor.setEmail(pContractor.getEmail());
                vContractor.setFax(pContractor.getFax());
                vContractor.setName(pContractor.getName());
                vContractor.setNote(pContractor.getNote());
                vContractor.setPhone(pContractor.getPhone());
                vContractor.setPhone2(pContractor.getPhone2());
                vContractor.setWebsite(pContractor.getWebsite());
                Contractor vContractorSave = gContractorRepository.save(vContractor);
                return new ResponseEntity<>(vContractorSave, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Not found Address", HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity()
                    .body("Failed to Create specified Contractor: " + e.getCause().getCause().getMessage());
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

    // Update contractor by id
    @PutMapping("/contractor/{contractorId}")
    public ResponseEntity<Object> updateContractor(
            @PathVariable Long contractorId,
            @Valid @RequestBody Contractor pContractor,
            @RequestParam(value = "addressId") Long addressId) {
        Optional<Contractor> vContractorData = gContractorRepository.findById(contractorId);
        if (vContractorData.isPresent()) {
            try {
                Optional<Address> addressData = gAddressRepository.findById(addressId);
                if (addressData.isPresent()) {
                    Contractor vContractor = new Contractor();
                    vContractor.setAddress(addressData.get());
                    vContractor.setDescription(pContractor.getDescription());
                    vContractor.setEmail(pContractor.getEmail());
                    vContractor.setFax(pContractor.getFax());
                    vContractor.setName(pContractor.getName());
                    vContractor.setNote(pContractor.getNote());
                    vContractor.setPhone(pContractor.getPhone());
                    vContractor.setPhone2(pContractor.getPhone2());
                    vContractor.setWebsite(pContractor.getWebsite());
                    Contractor vContractorSave = gContractorRepository.save(vContractor);
                    return new ResponseEntity<>(vContractorSave, HttpStatus.CREATED);
                } else {
                    return new ResponseEntity<>("Not found Address", HttpStatus.NOT_FOUND);
                }

            } catch (Exception e) {
                return ResponseEntity.unprocessableEntity()
                        .body("Failed to Update specified Contractor: " + e.getCause().getCause().getMessage());
            }
        } else {
            Contractor vContractorNull = new Contractor();
            return new ResponseEntity<>(vContractorNull, HttpStatus.NOT_FOUND);
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
