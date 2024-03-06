package com.efborchardt.productfeedback.domain.product.repository;

import com.efborchardt.productfeedback.domain._shared.repository.DefaultRepository;
import com.efborchardt.productfeedback.domain.product.model.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends DefaultRepository<Product> {

    List<Product> findProductByName(String name);

    List<Product> list();

    void delete(Product product);

    List<Product> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);
}
