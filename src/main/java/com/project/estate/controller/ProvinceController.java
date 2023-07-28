package com.project.estate.controller;

import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.estate.model.Province;
import com.project.estate.repository.IProvinceRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@CrossOrigin
@RestController
@RequestMapping("/")
public class ProvinceController {

    @Autowired
    IProvinceRepository pIProvinceRepository;

    // get all province
    @GetMapping(value = "/province/all")
    public ResponseEntity<List<Province>> getAllProvince(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        // tạo ra một đối tượng Pageable để đại diện cho thông tin về phân trang.
        Pageable pageable = PageRequest.of(page, size);
        // truy vấn CSDL và trả về một trang của đối tượng CProvince với thông tin trang
        Page<Province> provincePage = pIProvinceRepository.findAll(pageable);
        // để lấy danh sách các đối tượng
        List<Province> provinceList = provincePage.getContent();
        // Đếm tổng phần tử
        Long totalElement = provincePage.getTotalElements();
        // Trả về thành công
        return ResponseEntity.ok()
                .header("totalCount", String.valueOf(totalElement))
                .body(provinceList);
    }

    // get province by id
    @GetMapping(value = "/province/details/{id}")
    public Province getProvinceById(@PathVariable Long id) {
        if (pIProvinceRepository.findById(id).isPresent()) {
            return pIProvinceRepository.findById(id).get();
        } else {
            return null;
        }
    }

    // create new province
    @PostMapping(value = "/province/create")
    public ResponseEntity<Object> createProvince(@RequestBody Province pProvince) {
        try {
            // create new province
            Province newProvince = new Province();
            newProvince.setName(pProvince.getName());
            newProvince.setCode(pProvince.getCode());
            Province savedProvince = pIProvinceRepository.save(newProvince);
            System.out.println(savedProvince);
            return new ResponseEntity<>(savedProvince, HttpStatus.CREATED);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // Update province by id
    @PutMapping(value = "/province/update/{id}")
    public ResponseEntity<Object> updateProvince(@PathVariable Long id, @RequestBody Province pProvince) {
        // find province by id
        Optional<Province> provinceData = pIProvinceRepository.findById(id);
        if (provinceData.isPresent()) {
            // get province
            Province newProvince = provinceData.get();
            // update province
            newProvince.setName(pProvince.getName());
            newProvince.setCode(pProvince.getCode());
            Province savedProvince = pIProvinceRepository.save(newProvince);
            return new ResponseEntity<>(savedProvince, HttpStatus.OK);
        } else {
            // TODO: handle exception
            System.out.println("not find province by id");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Delete province by Id
    @DeleteMapping("/province/delete/{id}")
    public ResponseEntity<Object> deleteProvinceById(@PathVariable Long id) {
        try {
            pIProvinceRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(null,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}