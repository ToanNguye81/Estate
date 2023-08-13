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
import com.project.estate.entity.MasterLayout;
import com.project.estate.repository.AddressRepository;
import com.project.estate.repository.MasterLayoutRepository;

@RestController
@CrossOrigin
@RequestMapping("/")
public class MasterLayoutController {
    @Autowired
    MasterLayoutRepository gMasterLayoutRepository;
    @Autowired
    AddressRepository gAddressRepository;

    // get all MasterLayout
    @GetMapping("/contractor")
    public ResponseEntity<List<MasterLayout>> getAllMasterLayout() {
        try {
            // tạo ra một đối tượng Pageable để đại diện cho thông tin về phân trang.
            List<MasterLayout> contractorList = new ArrayList<MasterLayout>();
            gMasterLayoutRepository.findAll().forEach(contractorList::add);

            return new ResponseEntity<>(contractorList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // get contractor by id
    @GetMapping("/contractor/{contractorId}")
    public ResponseEntity<Object> getMasterLayoutById(
            @PathVariable Long contractorId) {
        Optional<MasterLayout> vMasterLayoutData = gMasterLayoutRepository.findById(contractorId);
        if (vMasterLayoutData.isPresent()) {
            try {
                MasterLayout vMasterLayout = vMasterLayoutData.get();
                return new ResponseEntity<>(vMasterLayout, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            MasterLayout vMasterLayoutNull = new MasterLayout();
            return new ResponseEntity<>(vMasterLayoutNull, HttpStatus.NOT_FOUND);
        }
    }

    // create new contractor
    @PostMapping("/contractor")
    @Transactional
    public ResponseEntity<Object> createNewMasterLayout(@Valid @RequestBody MasterLayout pMasterLayout) {
        try {
            // // Lấy thông tin về Address từ yêu cầu
            // Address pAddress = pMasterLayout.getAddress();

            // if (pAddress == null) {
            // return new ResponseEntity<>("Address information is missing",
            // HttpStatus.BAD_REQUEST);
            // }
            // Tạo mới Address từ thông tin trong yêu cầu
            Address newAddress = new Address();
            // newAddress.setAddress(pAddress.getAddress());
            // newAddress.setLat(pAddress.getLat());
            // newAddress.setLng(pAddress.getLng());
            Address savedAddress = gAddressRepository.save(newAddress);

            // Tạo mới MasterLayout và liên kết với Address
            MasterLayout vMasterLayout = new MasterLayout();
            vMasterLayout.setName(pMasterLayout.getName());

            // Lưu mới MasterLayout vào cơ sở dữ liệu
            MasterLayout vMasterLayoutSave = gMasterLayoutRepository.save(vMasterLayout);
            return new ResponseEntity<>(vMasterLayoutSave, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity()
                    .body("Failed to Create specified MasterLayout: " + e.getCause().getCause().getMessage());
        }
    }

    @PutMapping("/contractor/{contractorId}")
    @Transactional
    public ResponseEntity<Object> updateMasterLayout(
            @PathVariable Long contractorId,
            @Valid @RequestBody MasterLayout pMasterLayout) {
        Optional<MasterLayout> vMasterLayoutData = gMasterLayoutRepository.findById(contractorId);
        if (vMasterLayoutData.isPresent()) {
            try {
                MasterLayout existingMasterLayout = vMasterLayoutData.get();
                // Address pAddress = pMasterLayout.getAddress();

                // if (pAddress == null) {
                // return new ResponseEntity<>("Address information is missing",
                // HttpStatus.BAD_REQUEST);
                // }

                // Kiểm tra xem addressId có thuộc về MasterLayout hiện tại hay không
                // if (!existingMasterLayout.getAddress().getId().equals(pAddress.getId())) {
                // return new ResponseEntity<>("Address does not belong to this MasterLayout",
                // HttpStatus.BAD_REQUEST);
                // }

                // Optional<Address> existingAddress =
                // gAddressRepository.findById(pAddress.getId());
                // if (existingAddress.isPresent()) {
                // Address savedAddress = existingAddress.get();
                // savedAddress.setAddress(pAddress.getAddress());
                // savedAddress.setLat(pAddress.getLat());
                // savedAddress.setLng(pAddress.getLng());
                // savedAddress = gAddressRepository.save(savedAddress);
                // // update MasterLayout
                // existingMasterLayout.setDescription(pMasterLayout.getDescription());
                // existingMasterLayout.setName(pMasterLayout.getName());

                // MasterLayout updatedMasterLayout =
                // gMasterLayoutRepository.save(existingMasterLayout);
                // return new ResponseEntity<>(updatedMasterLayout, HttpStatus.OK);
                // } else {
                return new ResponseEntity<>("Address not found", HttpStatus.NOT_FOUND);
                // }
            } catch (Exception e) {
                return ResponseEntity.unprocessableEntity()
                        .body("Failed to Update specified MasterLayout: " + e.getCause().getCause().getMessage());
            }
        } else

        {
            return new ResponseEntity<>("MasterLayout not found", HttpStatus.NOT_FOUND);
        }
    }

    // Delete contractor by id
    @DeleteMapping("/contractor/{contractorId}")
    private ResponseEntity<Object> deleteMasterLayoutById(
            @PathVariable Long contractorId) {
        Optional<MasterLayout> vMasterLayoutData = gMasterLayoutRepository.findById(contractorId);
        if (vMasterLayoutData.isPresent()) {
            try {
                gMasterLayoutRepository.deleteById(contractorId);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            MasterLayout vMasterLayoutNull = new MasterLayout();
            return new ResponseEntity<>(vMasterLayoutNull, HttpStatus.NOT_FOUND);
        }
    }

}
