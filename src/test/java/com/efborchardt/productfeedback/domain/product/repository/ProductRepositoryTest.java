package com.efborchardt.productfeedback.domain.product.repository;

import com.efborchardt.productfeedback.domain.product.model.Product;
import com.efborchardt.productfeedback.domain.user.model.User;
import com.efborchardt.productfeedback.domain.user.model.UserRole;
import com.efborchardt.productfeedback.infrastructure.persistence.product.repository.ProductRepositoryImpl;
import com.efborchardt.productfeedback.infrastructure.persistence.user.model.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Import(ProductRepositoryImpl.class)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testSave() {
        UserEntity userEntity = new UserEntity(UUID.randomUUID(), "test", "test@test.com", "123pass", UserRole.USER);
        this.entityManager.persist(userEntity);

        User user = new User(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.getRole()
        );

        UUID productId = UUID.randomUUID();
        Product product = new Product(productId, "Test Product", "Description of test product", BigDecimal.valueOf(15.75), user);

        productRepository.save(product);

        Optional<Product> foundProduct = productRepository.findById(productId);
        assertThat(foundProduct).isPresent();
        foundProduct.ifPresent(p -> {
            assertThat(p.getId()).isEqualTo(productId);
            assertThat(p.getName()).isEqualTo("Test Product");
            assertThat(p.getDescription()).isEqualTo("Description of test product");
            assertThat(p.getPrice()).isEqualTo(BigDecimal.valueOf(15.75));
        });
    }

    @Test
    public void testFindById_NotFound() {
        Optional<Product> foundProduct = productRepository.findById(UUID.randomUUID());
        assertThat(foundProduct).isNotPresent();
    }
}