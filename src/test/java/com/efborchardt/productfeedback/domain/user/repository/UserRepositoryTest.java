package com.efborchardt.productfeedback.domain.user.repository;

import com.efborchardt.productfeedback.domain.user.model.User;
import com.efborchardt.productfeedback.domain.user.model.UserRole;
import com.efborchardt.productfeedback.infrastructure.persistence.user.model.UserEntity;
import com.efborchardt.productfeedback.infrastructure.persistence.user.repository.UserRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Import(UserRepositoryImpl.class)
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    void save() {
        User newUser = new User("testUsername", "testEmail@example.com", "testPassword", UserRole.USER);
        userRepository.save(newUser);
        UserEntity foundEntity = entityManager.find(UserEntity.class, newUser.getId());
        assertThat(foundEntity).isNotNull();
        assertThat(foundEntity.getUsername()).isEqualTo(newUser.getUsername());
        assertThat(foundEntity.getEmail()).isEqualTo(newUser.getEmail());
        assertThat(foundEntity.getPassword()).isEqualTo(newUser.getPassword());
    }

    @Test
    void findById() {
        UserEntity userEntity = new UserEntity(UUID.randomUUID(), "findUsername", "findEmail@example.com", "findPassword", UserRole.USER.asString());
        entityManager.persistAndFlush(userEntity);

        Optional<User> foundUser = userRepository.findById(userEntity.getId());
        assertThat(foundUser).isPresent();
        foundUser.ifPresent(user -> {
            assertThat(user.getId()).isEqualTo(userEntity.getId());
            assertThat(user.getUsername()).isEqualTo(userEntity.getUsername());
            assertThat(user.getEmail()).isEqualTo(userEntity.getEmail());
            assertThat(user.getPassword()).isEqualTo(userEntity.getPassword());
        });
    }

    @Test
    void findById_NotFound() {
        Optional<User> foundUser = userRepository.findById(UUID.randomUUID());
        assertThat(foundUser).isNotPresent();
    }
}