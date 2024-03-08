package com.efborchardt.productfeedback.application.usecase.product.delete;

import com.efborchardt.productfeedback.domain.product.service.ProductService;
import com.efborchardt.productfeedback.domain.user.model.User;
import com.efborchardt.productfeedback.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeleteProductUseCase {

    private final ProductService productService;
    private final UserService userService;

    @Autowired
    public DeleteProductUseCase(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    public DeleteProductResponseDTO execute(DeleteProductRequestDTO request) {
        final User sender = this.userService.findByUsername(request.getSenderUsername());

        this.productService.deleteProduct(request.getProductId(), sender);
        return new DeleteProductResponseDTO();
    }
}
