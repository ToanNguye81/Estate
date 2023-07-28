package com.project.estate.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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

import com.project.estate.entity.Customer;
import com.project.estate.repository.ICustomerRepository;

@CrossOrigin
@RestController
@RequestMapping("/")
public class CustomerController {
    @Autowired
    ICustomerRepository pICustomerRepository;

    // Get country by id
    @GetMapping("/customers/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        // find country by id
        if (pICustomerRepository.findById(id).isPresent())
            return pICustomerRepository.findById(id).get();
        else
            return null;
    }

    // Get all customers
    @GetMapping(value = "/customers")
    public ResponseEntity<List<Customer>> getAllCustomer(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        // tạo ra một đối tượng Pageable để đại diện cho thông tin về phân trang.
        Pageable pageable = PageRequest.of(page, size);
        // truy vấn CSDL và trả về một trang của đối tượng CCustomer với thông tin trang
        Page<Customer> countryPage = pICustomerRepository.findAll(pageable);
        // để lấy danh sách các đối tượng
        List<Customer> countryList = countryPage.getContent();
        // Đếm tổng phần tử
        Long totalElement = countryPage.getTotalElements();
        // Trả về thành công
        return ResponseEntity.ok()
                .header("totalCount", String.valueOf(totalElement))
                .body(countryList);
    }

    // Create new country
    @PostMapping(value = "/customers")
    public ResponseEntity<Object> createCustomer(@RequestBody Customer pCustomer) {
        try {
            Customer newCustomer = new Customer();
            // get info from Frontend
            newCustomer.setFullName(pCustomer.getFullName());
            newCustomer.setEmail(pCustomer.getEmail());
            newCustomer.setAddress(pCustomer.getAddress());
            newCustomer.setPhone(pCustomer.getPhone());
            newCustomer.setCreateAt(new Date());
            // save new country
            Customer savedCustomer = pICustomerRepository.save(newCustomer);
            return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update country by id
    @PutMapping(value = "/customers/{id}")
    public ResponseEntity<Object> updateCustomer(@PathVariable Long id, @RequestBody Customer pCustomer) {
        // find country by id
        Optional<Customer> countryData = pICustomerRepository.findById(id);
        if (countryData.isPresent()) {
            // get existed country
            Customer newCustomer = countryData.get();
            // update country
            newCustomer.setFullName(pCustomer.getFullName());
            newCustomer.setEmail(pCustomer.getEmail());
            newCustomer.setAddress(pCustomer.getAddress());
            newCustomer.setPhone(pCustomer.getPhone());
            newCustomer.setUpdateAt(new Date());
            Customer savedCustomer = pICustomerRepository.save(newCustomer);
            // return
            return new ResponseEntity<>(savedCustomer, HttpStatus.OK);
        } else {
            // TODO: handle exception
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        }
    }

    // Delete Customer by Id
    @DeleteMapping("/customers/{id}")
    public ResponseEntity<Object> deleteCustomerById(@PathVariable Long id) {
        try {
            pICustomerRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(null,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete all customers
    @DeleteMapping("/customers")
    public ResponseEntity<Object> deleteAllCountries() {
        try {
            pICustomerRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getCause().getCause().getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // get the count of record
    @GetMapping("/customers-count")
    public Long countCustomer() {
        return pICustomerRepository.count();
    }

    // Check country in database
    @GetMapping("/customers/check/{id}")
    public boolean checkCustomerById(@PathVariable Long id) {
        return pICustomerRepository.existsById(id);
    }

}
