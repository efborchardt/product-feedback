package com.efborchardt.productfeedback.application.usecase.product.update;

import com.efborchardt.productfeedback.domain.product.model.Product;
import com.efborchardt.productfeedback.domain.product.service.ProductService;
import com.efborchardt.productfeedback.domain.user.model.User;
import com.efborchardt.productfeedback.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UpdateProductUseCase {

    private final ProductService productService;

    @Autowired
    public UpdateProductUseCase(ProductService productService) {
        this.productService = productService;
    }

    public UpdateProductResponseDTO execute(UpdateProductRequestDTO request, UserDetails sender) {
        final Product updatedProduct = this.productService.updateProduct(request, (User) sender);
        return new UpdateProductResponseDTO(
                updatedProduct.getId(),
                updatedProduct.getName(),
                updatedProduct.getDescription(),
                updatedProduct.getPrice()
        );
    }
}
