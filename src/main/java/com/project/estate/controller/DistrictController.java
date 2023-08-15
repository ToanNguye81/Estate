package com.project.estate.controller;

import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

import com.project.estate.model.District;
import com.project.estate.model.Province;
import com.project.estate.repository.DistrictRepository;
import com.project.estate.repository.ProvinceRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@CrossOrigin
@RestController
@RequestMapping("/")
public class DistrictController {

    @Autowired
    DistrictRepository pIDistrictRepository;
    @Autowired
    ProvinceRepository pIProvinceRepository;

    // get all district
    @GetMapping(value = "/district")
    public ResponseEntity<List<District>> getAllDistrict(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        // tạo ra một đối tượng Pageable để đại diện cho thông tin về phân trang.
        Pageable pageable = PageRequest.of(page, size);
        // truy vấn CSDL và trả về một trang của đối tượng CDistrict với thông tin trang
        Page<District> districtPage = pIDistrictRepository.findAll(pageable);
        // để lấy danh sách các đối tượng
        List<District> districtList = districtPage.getContent();
        // Đếm tổng phần tử
        Long totalElement = districtPage.getTotalElements();
        // Trả về thành công
        return ResponseEntity.ok()
                .header("totalCount", String.valueOf(totalElement))
                .body(districtList);
    }

    // get district by id
    @GetMapping(value = "/district/{id}")
    public District getDistrictById(@PathVariable Long id) {
        if (pIDistrictRepository.findById(id).isPresent()) {
            return pIDistrictRepository.findById(id).get();
        } else {
            return null;
        }
    }

    // get district by province code
    @GetMapping(value = "/district")
    public Set<District> getDistrictByProvinceCode(
            @RequestParam(value = "provinceCode", required = false) String provinceCode) {
        Optional<Province> provinceData = pIProvinceRepository.findByCode(provinceCode);
        if (provinceData.isPresent()) {
            Province province = provinceData.get();
            return province.getDistricts();
        } else {
            return null;
        }
    }

    // create new district
    @PostMapping(value = "/district/{provinceId}")
    public ResponseEntity<Object> createDistrict(@PathVariable Long provinceId,
            @RequestBody District pDistrict) {
        try {
            // find province by id
            Optional<Province> provinceData = pIProvinceRepository.findById(provinceId);
            // if existed province
            if (provinceData.isPresent()) {
                Province province = provinceData.get();
                District newDistrict = new District();
                newDistrict.setProvince(province);
                newDistrict.setName(pDistrict.getName());
                newDistrict.setPrefix(pDistrict.getPrefix());
                District savedDistrict = pIDistrictRepository.save(newDistrict);
                return new ResponseEntity<>(savedDistrict, HttpStatus.CREATED);
            }
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.unprocessableEntity()
                    .body("Failed to Create specified Ward: " + e.getCause().getCause().getMessage());
        }
        // not found province
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // update district
    @PutMapping(value = "/district/{id}")
    public ResponseEntity<Object> updateDistrictById(@PathVariable Long id, @RequestBody District pDistrict) {
        // find district by id
        Optional<District> districtData = pIDistrictRepository.findById(id);
        if (districtData.isPresent()) {
            // if existed district
            District newDistrict = districtData.get();
            newDistrict.setName(pDistrict.getName());
            newDistrict.setPrefix(pDistrict.getPrefix());
            newDistrict.setProvince(pDistrict.getProvince());
            District savedDistrict = pIDistrictRepository.save(newDistrict);
            return new ResponseEntity<>(savedDistrict, HttpStatus.OK);
        } else {
            // if not found district by id
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // delete District by id
    @DeleteMapping("/district/{id}")
    public ResponseEntity<Object> deleteDistrictById(@PathVariable Long id) {
        try {
            pIDistrictRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(null,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
