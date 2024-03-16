package com.efborchardt.productfeedback.infrastructure.persistence.product.repository;

import com.efborchardt.productfeedback.domain.product.model.Product;
import com.efborchardt.productfeedback.domain.product.repository.ProductRepository;
import com.efborchardt.productfeedback.domain.user.model.User;
import com.efborchardt.productfeedback.domain.user.model.UserRole;
import com.efborchardt.productfeedback.infrastructure.persistence.product.model.ProductEntity;
import com.efborchardt.productfeedback.infrastructure.persistence.product.model.SpringDataProductRepository;
import com.efborchardt.productfeedback.infrastructure.persistence.user.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ProductRepositoryImpl implements ProductRepository {

    private final SpringDataProductRepository springDataProductRepository;

    @Autowired
    public ProductRepositoryImpl(SpringDataProductRepository springDataProductRepository) {
        this.springDataProductRepository = springDataProductRepository;
    }

    @Override
    public void save(Product entity) {
        this.springDataProductRepository.save(mapToPersistence(entity));
    }

    @Override
    public Optional<Product> findById(UUID id) {
        Optional<ProductEntity> productEntity = this.springDataProductRepository.findById(id);
        return productEntity.map(this::mapToDomain);
    }

    @Override
    public List<Product> findProductByName(String name) {
        return mapListToDomain(this.springDataProductRepository.findByName(name));
    }

    @Override
    public List<Product> list() {
        return mapListToDomain(this.springDataProductRepository.findAll());
    }

    @Override
    public List<Product> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return mapListToDomain(this.springDataProductRepository.findByPriceBetween(minPrice, maxPrice));
    }

    @Override
    public void delete(Product product) {
        this.springDataProductRepository.delete(mapToPersistence(product));
    }

    private ProductEntity mapToPersistence(Product product) {
        return new ProductEntity(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                new UserEntity(
                    product.getCreatedBy().getId(),
                    product.getCreatedBy().getUsername(),
                    product.getCreatedBy().getEmail(),
                    product.getCreatedBy().getPassword(),
                    product.getCreatedBy().getRole().asString()
                )
        );
    }
    private Product mapToDomain(ProductEntity productEntity) {
        return new Product(
                productEntity.getId(),
                productEntity.getName(),
                productEntity.getDescription(),
                productEntity.getPrice(),
                new User(
                    productEntity.getCreatedBy().getId(),
                    productEntity.getCreatedBy().getUsername(),
                    productEntity.getCreatedBy().getEmail(),
                    productEntity.getCreatedBy().getPassword(),
                    UserRole.fromString(productEntity.getCreatedBy().getRole())
                )
        );
    }

    private List<Product> mapListToDomain(List<ProductEntity> productEntityList) {
        return productEntityList.stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }
}
