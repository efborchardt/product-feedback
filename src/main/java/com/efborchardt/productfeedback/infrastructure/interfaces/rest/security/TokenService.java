package com.efborchardt.productfeedback.infrastructure.interfaces.rest.security;

import com.efborchardt.productfeedback.infrastructure.persistence.user.model.UserEntity;

public interface TokenService {

    String generateToken(UserEntity user);
    String validateToken(String token);
}
