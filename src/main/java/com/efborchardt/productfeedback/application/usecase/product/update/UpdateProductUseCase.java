package com.efborchardt.productfeedback.application.usecase.product.update;

import com.efborchardt.productfeedback.application.usecase.common.DefaultUseCase;
import com.efborchardt.productfeedback.domain.product.model.Product;
import com.efborchardt.productfeedback.domain.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateProductUseCase implements DefaultUseCase<UpdateProductRequestDTO, UpdateProductResponseDTO> {

    private final ProductService service;

    @Autowired
    public UpdateProductUseCase(ProductService service) {
        this.service = service;
    }

    @Override
    public UpdateProductResponseDTO execute(UpdateProductRequestDTO request) {
        final Product updatedProduct = this.service.updateProduct(request);
        return new UpdateProductResponseDTO(
                updatedProduct.getId(),
                updatedProduct.getName(),
                updatedProduct.getDescription(),
                updatedProduct.getPrice()
        );
    }
}
