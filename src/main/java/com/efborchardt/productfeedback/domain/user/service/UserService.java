package com.efborchardt.productfeedback.domain.user.service;

import com.efborchardt.productfeedback.domain.user.exception.UserNotFoundException;
import com.efborchardt.productfeedback.domain.user.model.User;
import com.efborchardt.productfeedback.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User createNewUser(User user) {
        final boolean userNameAlreadyBeingUsed = this.repository.findByUsername(user.getUsername()).isPresent();
        if (userNameAlreadyBeingUsed) {
            throw new RuntimeException("Username is already being used");
        }

        this.repository.save(user);
        return user;
    }

    public List<User> listAllUsers() {
        return this.repository.list();
    }

    public void changePassword(UUID userId, String newPassword) {
        final User user = findById(userId);
        user.changePassword(newPassword);
        this.repository.save(user);
    }

    public User findById(UUID id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Unable to find a user with ID " + id.toString()));
    }
}
