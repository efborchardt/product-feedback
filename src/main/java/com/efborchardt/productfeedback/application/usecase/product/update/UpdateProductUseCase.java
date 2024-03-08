package com.efborchardt.productfeedback.application.usecase.product.update;

import com.efborchardt.productfeedback.domain.product.model.Product;
import com.efborchardt.productfeedback.domain.product.service.ProductService;
import com.efborchardt.productfeedback.domain.user.model.User;
import com.efborchardt.productfeedback.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateProductUseCase {

    private final ProductService productService;
    private final UserService userService;

    @Autowired
    public UpdateProductUseCase(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    public UpdateProductResponseDTO execute(UpdateProductRequestDTO request, String senderUsername) {
        final User sender = this.userService.findByUsername(senderUsername);
        final Product updatedProduct = this.productService.updateProduct(request, sender);
        return new UpdateProductResponseDTO(
                updatedProduct.getId(),
                updatedProduct.getName(),
                updatedProduct.getDescription(),
                updatedProduct.getPrice()
        );
    }
}
