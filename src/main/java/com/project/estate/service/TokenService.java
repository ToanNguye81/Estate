package com.project.estate.service;

import com.project.estate.entity.Token;

public interface TokenService {

    Token createToken(Token token);

    Token findByToken(String token);
}
