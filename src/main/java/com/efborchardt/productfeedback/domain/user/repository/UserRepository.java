package com.efborchardt.productfeedback.domain.user.repository;

import com.efborchardt.productfeedback.domain._shared.repository.DefaultRepository;
import com.efborchardt.productfeedback.domain.user.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends DefaultRepository<User> {

    Optional<User> findByUsername(String username);
    List<User> list();
}
