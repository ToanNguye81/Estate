package com.project.estate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.estate.entity.User;
import com.project.estate.repository.UserRepository;
import com.project.estate.service.UserService;

/**
 * @author
 *
 */
@RestController
public class WithoutAuthorizeController {
    @Autowired
    private UserRepository userRepository;

    /**
     * Test trường hợp khôngcheck quyền Authorize lấy là danh sách user
     * 
     * @return
     */
    @GetMapping("luli/users")
    public ResponseEntity<List<Object>> getUsers() {
        return new ResponseEntity(userRepository.findAll(), HttpStatus.OK);
    }

    /**
     * Test trường hợp không check quyền Authorize
     * Tạo mới user
     * 
     * @param user
     * @return
     */
    @PostMapping("/users/createAdmin")
    public ResponseEntity<Object> create(@RequestBody User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return new ResponseEntity(userRepository.save(user), HttpStatus.CREATED);
    }

    @Autowired
    private UserService userService;

    /**
     * Test có kiểm tra quyền.
     * 
     * @param user
     * @return
     */
    @PostMapping("/users/create")
    @PreAuthorize("hasAnyAuthority('USER_CREATE')")
    public User register(@RequestBody User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        return userService.createUser(user);
    }
}
