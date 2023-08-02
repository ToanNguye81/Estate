package com.project.estate.controller;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;
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

import com.project.estate.model.Country;
import com.project.estate.model.Region;
import com.project.estate.repository.CountryRepository;
import com.project.estate.repository.RegionRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin
@RestController
@RequestMapping("/")
public class RegionController {

    @Autowired
    RegionRepository pIRegionRepository;
    @Autowired
    CountryRepository pICountryRepository;

    // Get region by id
    @GetMapping("/regions/{id}")
    public Region getById(@PathVariable Long id) {
        // find region by id
        if (pIRegionRepository.findById(id).isPresent())
            return pIRegionRepository.findById(id).get();
        else
            return null;
    }

    // Get all Regions
    @GetMapping(value = "/regions")
    public ResponseEntity<List<Region>> getAllRegion(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        // tạo ra một đối tượng Pageable để đại diện cho thông tin về phân trang.
        Pageable pageable = PageRequest.of(page, size);
        // truy vấn CSDL và trả về một trang của đối tượng CRegion với thông tin trang
        Page<Region> regionPage = pIRegionRepository.findAll(pageable);
        // để lấy danh sách các đối tượng
        List<Region> regionList = regionPage.getContent();
        // Đếm tổng phần tử
        Long totalElement = regionPage.getTotalElements();
        // Trả về thành công
        return ResponseEntity.ok()
                .header("totalCount", String.valueOf(totalElement))
                .body(regionList);
    }

    // Create new region
    @PostMapping(value = "countries/{countryId}/regions")
    public ResponseEntity<Object> createRegion(@PathVariable("countryId") Long countryId,
            @RequestBody Region pRegion) {
        try {
            // Find country by id
            Optional<Country> countryData = pICountryRepository.findById(countryId);
            // create new region
            if (countryData.isPresent()) {
                Country country = countryData.get();
                Region newRegion = new Region();
                // get region info
                newRegion.setCountry(country);
                newRegion.setName(pRegion.getName());
                newRegion.setCode(pRegion.getCode());
                Region savedRole = pIRegionRepository.save(newRegion);
                // return success
                return new ResponseEntity<>(savedRole, HttpStatus.CREATED);
            }
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity()
                    .body("Failed to Create specified Voucher: " + e.getCause().getCause().getMessage());
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    // Update region by id
    @PutMapping(value = "/regions/{id}")
    public ResponseEntity<Object> updateRegion(@PathVariable Long id, @RequestBody Region pRegion) {
        // find region by id
        Optional<Region> regionData = pIRegionRepository.findById(id);
        // update new region
        if (regionData.isPresent()) {
            Region newRegion = regionData.get();
            newRegion.setName(pRegion.getName());
            newRegion.setCode(pRegion.getCode());
            Region savedRegion = pIRegionRepository.save(newRegion);
            return new ResponseEntity<>(savedRegion, HttpStatus.OK);
        } else {
            // TODO: handle exception
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Delete region by Id
    @DeleteMapping("/regions/{id}")
    public ResponseEntity<Object> deleteRegionById(@PathVariable Long id) {
        try {
            pIRegionRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete all region
    @DeleteMapping("/regions")
    public ResponseEntity<Object> deleteAllRegion() {
        try {
            pIRegionRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // get the count of record
    @GetMapping("/regions-count")
    public Long countRegion() {
        return pIRegionRepository.count();
    }

    // Check region in database
    @GetMapping("/regions/check/{id}")
    public boolean checkRegionById(@PathVariable Long id) {
        return pIRegionRepository.existsById(id);
    }

    // Return the region containing the specified code
    @GetMapping("/regions/containing-code/{code}")
    public ResponseEntity<List<Region>> getByContainingCode(@PathVariable String code) {
        List<Region> regionList = new ArrayList<>();
        Region region = pIRegionRepository.findByCodeContaining(code);
        if (region != null) {
            regionList.add(region);
        }
        return new ResponseEntity<>(regionList, HttpStatus.OK);

    }
}
