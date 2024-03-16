package com.efborchardt.productfeedback.infrastructure.interfaces.rest.security;

import com.efborchardt.productfeedback.domain.user.model.User;
import com.efborchardt.productfeedback.infrastructure.persistence.user.model.UserEntity;

public interface TokenService {

    String generateToken(User user);
    String validateToken(String token);
}
