package com.project.estate.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.estate.entity.Employee;
import com.project.estate.entity.Employee;
import com.project.estate.entity.User;
import com.project.estate.repository.EmployeeRepository;
import com.project.estate.repository.UserRepository;
import com.project.estate.security.UserPrincipal;

@RestController
@CrossOrigin
@RequestMapping("/")
public class EmployeeController {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    UserRepository userRepository;

    // get all Employee
    @GetMapping("/employee")
    public ResponseEntity<List<Employee>> getAllEmployee() {
        try {
            // tạo ra một đối tượng Pageable để đại diện cho thông tin về phân trang.
            List<Employee> employeeList = new ArrayList<Employee>();
            employeeRepository.findAll().forEach(employeeList::add);

            return new ResponseEntity<>(employeeList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // create new employee
    @PostMapping("/employee")
    public ResponseEntity<Object> createNewEmployee(
            @Valid @RequestBody Employee pEmployee,
            @AuthenticationPrincipal UserPrincipal currentUser) {
        try {
            // Kiểm tra xem bản ghi khách hàng đã tồn tại với ID người dùng này chưa
            Employee existingEmployee = employeeRepository.findByUserId(currentUser.getUserId());
            if (existingEmployee != null) {
                return ResponseEntity.badRequest()
                        .body("A employee record already exists for this user.");
            }

            // Lấy thông tin người dùng từ bảng User
            User user = userRepository.findByUsername(currentUser.getUsername());
            // Tạo employee
            Employee vEmployee = new Employee();
            vEmployee.setActive(pEmployee.getActive());
            vEmployee.setAddress(pEmployee.getAddress());
            vEmployee.setBirthDate(pEmployee.getBirthDate());
            vEmployee.setCity(pEmployee.getCity());
            vEmployee.setCountry(pEmployee.getCountry());
            vEmployee.setEmail(pEmployee.getEmail());
            vEmployee.setExtension(pEmployee.getExtension());
            vEmployee.setFirstName(pEmployee.getFirstName());
            vEmployee.setHireDate(pEmployee.getHireDate());
            vEmployee.setUser(user);
            Employee vEmployeeSave = employeeRepository.save(vEmployee);
            // ghi khách hàng

            return new ResponseEntity<>(vEmployeeSave, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity()
                    .body("Failed to Create specified Employee: " + e.getCause().getCause().getMessage());
        }
    }

    // get employee by id
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<Object> getEmployeeById(
            @PathVariable Long employeeId) {
        Optional<Employee> vEmployeeData = employeeRepository.findById(employeeId);
        if (vEmployeeData.isPresent()) {
            try {
                Employee vEmployee = vEmployeeData.get();
                return new ResponseEntity<>(vEmployee, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            Employee vEmployeeNull = new Employee();
            return new ResponseEntity<>(vEmployeeNull, HttpStatus.NOT_FOUND);
        }
    }

    // Update employee by id
    @PutMapping("/employee/{employeeId}")
    public ResponseEntity<Object> updateEmployee(
            @PathVariable Long employeeId,
            @Valid @RequestBody Employee pEmployee) {
        Optional<Employee> vEmployeeData = employeeRepository.findById(employeeId);
        if (vEmployeeData.isPresent()) {
            try {
                Employee vEmployee = vEmployeeData.get();
                vEmployee.setActive(pEmployee.getActive());
                vEmployee.setAddress(pEmployee.getAddress());
                vEmployee.setBirthDate(pEmployee.getBirthDate());
                vEmployee.setCity(pEmployee.getCity());
                vEmployee.setCountry(pEmployee.getCountry());
                vEmployee.setEmail(pEmployee.getEmail());
                vEmployee.setExtension(pEmployee.getExtension());
                vEmployee.setFirstName(pEmployee.getFirstName());
                vEmployee.setHireDate(pEmployee.getHireDate());
                Employee vEmployeeSave = employeeRepository.save(vEmployee);
                return new ResponseEntity<>(vEmployeeSave, HttpStatus.OK);
            } catch (Exception e) {
                return ResponseEntity.unprocessableEntity()
                        .body("Failed to Update specified Employee: " + e.getCause().getCause().getMessage());
            }
        } else {
            Employee vEmployeeNull = new Employee();
            return new ResponseEntity<>(vEmployeeNull, HttpStatus.NOT_FOUND);
        }
    }

    // Delete employee by id
    @DeleteMapping("/employee/{employeeId}")
    private ResponseEntity<Object> deleteEmployeeById(
            @PathVariable Long employeeId) {
        Optional<Employee> vEmployeeData = employeeRepository.findById(employeeId);
        if (vEmployeeData.isPresent()) {
            try {
                employeeRepository.deleteById(employeeId);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            Employee vEmployeeNull = new Employee();
            return new ResponseEntity<>(vEmployeeNull, HttpStatus.NOT_FOUND);
        }
    }

}
