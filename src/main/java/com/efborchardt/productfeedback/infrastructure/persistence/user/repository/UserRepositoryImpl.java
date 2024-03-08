package com.efborchardt.productfeedback.infrastructure.persistence.user.repository;

import com.efborchardt.productfeedback.domain.user.model.User;
import com.efborchardt.productfeedback.domain.user.repository.UserRepository;
import com.efborchardt.productfeedback.infrastructure.persistence.user.model.SpringDataUserRepository;
import com.efborchardt.productfeedback.infrastructure.persistence.user.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Primary
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final SpringDataUserRepository springDataUserRepository;

    @Autowired
    public UserRepositoryImpl(SpringDataUserRepository springDataUserRepository) {
        this.springDataUserRepository = springDataUserRepository;
    }

    @Override
    public void save(User user) {
        this.springDataUserRepository.save(mapToPersistence(user));
    }

    @Override
    public Optional<User> findById(UUID id) {
        final Optional<UserEntity> userEntity = this.springDataUserRepository.findById(id);
        return userEntity.map(this::mapToDomain);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        final Optional<UserEntity> userEntity = this.springDataUserRepository.findByUsername(username);
        return userEntity.map(this::mapToDomain);
    }

    @Override
    public List<User> list() {
        return mapUserListToDomain(this.springDataUserRepository.findAll());
    }

    private UserEntity mapToPersistence(User user) {
        return new UserEntity(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getRole()
        );
    }

    private User mapToDomain(UserEntity userEntity) {
        return new User(userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.getRole()
        );
    }

    private List<User> mapUserListToDomain(List<UserEntity> userEntities) {
        return userEntities.stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }
}
