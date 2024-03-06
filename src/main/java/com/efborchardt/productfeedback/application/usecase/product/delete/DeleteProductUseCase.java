package com.efborchardt.productfeedback.application.usecase.product.delete;

import com.efborchardt.productfeedback.application.usecase.common.DefaultUseCase;
import com.efborchardt.productfeedback.domain.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DeleteProductUseCase implements DefaultUseCase<UUID, DeleteProductResponseDTO> {

    private final ProductService service;

    @Autowired
    public DeleteProductUseCase(ProductService service) {
        this.service = service;
    }

    @Override
    public DeleteProductResponseDTO execute(UUID productId) {
        this.service.deleteProduct(productId);
        return new DeleteProductResponseDTO();
    }
}
