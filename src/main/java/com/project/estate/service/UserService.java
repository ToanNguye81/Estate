package com.project.estate.service;

import com.project.estate.entity.User;
import com.project.estate.security.UserPrincipal;

public interface UserService {
    User createUser(User user);

    UserPrincipal findByUsername(String username);
}
