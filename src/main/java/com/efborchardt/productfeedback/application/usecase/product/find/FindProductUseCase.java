package com.efborchardt.productfeedback.application.usecase.product.find;

import com.efborchardt.productfeedback.domain.product.model.Product;
import com.efborchardt.productfeedback.domain.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FindProductUseCase {

    private final ProductService service;

    @Autowired
    public FindProductUseCase(ProductService service) {
        this.service = service;
    }

    public FindProductResponseDTO execute(UUID id) {
        final Product product = this.service.findById(id);
        return new FindProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice()
        );
    }
}
