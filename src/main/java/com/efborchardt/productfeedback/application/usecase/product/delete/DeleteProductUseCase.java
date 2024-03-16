package com.efborchardt.productfeedback.application.usecase.product.delete;

import com.efborchardt.productfeedback.domain.product.service.ProductService;
import com.efborchardt.productfeedback.domain.user.model.User;
import com.efborchardt.productfeedback.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DeleteProductUseCase {

    private final ProductService productService;

    @Autowired
    public DeleteProductUseCase(ProductService productService) {
        this.productService = productService;
    }

    public DeleteProductResponseDTO execute(UUID productId, UserDetails sender) {
        this.productService.deleteProduct(productId, (User) sender);
        return new DeleteProductResponseDTO();
    }
}
