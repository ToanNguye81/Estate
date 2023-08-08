package com.project.estate.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import com.project.estate.entity.Customer;
import com.project.estate.repository.CustomerRepository;

@RestController
@CrossOrigin
@RequestMapping("/")
public class CustomerController {
    @Autowired
    CustomerRepository gCustomerRepository;

    // get all Customer
    @GetMapping("/customer")
    public ResponseEntity<List<Customer>> getAllCustomer() {
        try {
            // tạo ra một đối tượng Pageable để đại diện cho thông tin về phân trang.
            List<Customer> customerList = new ArrayList<Customer>();
            gCustomerRepository.findAll().forEach(customerList::add);

            return new ResponseEntity<>(customerList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // create new customer
    @PostMapping("/customer")
    public ResponseEntity<Object> createNewCustomer(
            @Valid @RequestBody Customer pCustomer) {
        try {
            Customer vCustomer = new Customer();
            vCustomer.setAddress(pCustomer.getAddress());
            vCustomer.setContactName(pCustomer.getContactName());
            vCustomer.setContactTitle(pCustomer.getContactTitle());
            vCustomer.setCreatedAt(pCustomer.getCreatedAt());
            vCustomer.setCreatedBy(pCustomer.getCreatedBy());
            vCustomer.setEmail(pCustomer.getEmail());
            vCustomer.setMobile(pCustomer.getMobile());
            vCustomer.setNote(pCustomer.getNote());
            vCustomer.setRealEstates(pCustomer.getRealEstates());
            vCustomer.setUpdatedAt(pCustomer.getUpdatedAt());
            vCustomer.setDeleted(false);
            vCustomer.setUsername(null);

            Customer vCustomerSave = gCustomerRepository.save(vCustomer);
            return new ResponseEntity<>(vCustomerSave, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity()
                    .body("Failed to Create specified Customer: " + e.getCause().getCause().getMessage());
        }
    }

    // get customer by id
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Object> getCustomerById(
            @PathVariable Long customerId) {
        Optional<Customer> vCustomerData = gCustomerRepository.findById(customerId);
        if (vCustomerData.isPresent()) {
            try {
                Customer vCustomer = vCustomerData.get();
                return new ResponseEntity<>(vCustomer, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            Customer vCustomerNull = new Customer();
            return new ResponseEntity<>(vCustomerNull, HttpStatus.NOT_FOUND);
        }
    }

    // Update customer by id
    @PutMapping("/customer/{customerId}")
    public ResponseEntity<Object> updateCustomer(
            @PathVariable Long customerId,
            @Valid @RequestBody Customer pCustomer) {
        Optional<Customer> vCustomerData = gCustomerRepository.findById(customerId);
        if (vCustomerData.isPresent()) {
            try {
                Customer vCustomer = vCustomerData.get();

                Customer vCustomerSave = gCustomerRepository.save(vCustomer);
                return new ResponseEntity<>(vCustomerSave, HttpStatus.OK);
            } catch (Exception e) {
                return ResponseEntity.unprocessableEntity()
                        .body("Failed to Update specified Customer: " + e.getCause().getCause().getMessage());
            }
        } else {
            Customer vCustomerNull = new Customer();
            return new ResponseEntity<>(vCustomerNull, HttpStatus.NOT_FOUND);
        }
    }

    // Delete customer by id
    @DeleteMapping("/customer/{customerId}")
    private ResponseEntity<Object> deleteCustomerById(
            @PathVariable Long customerId) {
        Optional<Customer> vCustomerData = gCustomerRepository.findById(customerId);
        if (vCustomerData.isPresent()) {
            try {
                gCustomerRepository.deleteById(customerId);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            Customer vCustomerNull = new Customer();
            return new ResponseEntity<>(vCustomerNull, HttpStatus.NOT_FOUND);
        }
    }

}
