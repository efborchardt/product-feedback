package com.efborchardt.productfeedback.application.usecase.product.create;

import com.efborchardt.productfeedback.application.usecase.common.DefaultUseCase;
import com.efborchardt.productfeedback.domain.product.model.Product;
import com.efborchardt.productfeedback.domain.product.service.ProductService;
import com.efborchardt.productfeedback.domain.user.model.User;
import com.efborchardt.productfeedback.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateProductUseCase implements DefaultUseCase<CreateProductRequestDTO, CreateProductResponseDTO> {

    private final ProductService productService;
    private final UserService userService;

    @Autowired
    public CreateProductUseCase(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @Override
    public CreateProductResponseDTO execute(CreateProductRequestDTO request) {
        final User user = this.userService.findById(request.getUserId());
        final Product product = new Product(
              request.getName(),
              request.getDescription(),
              request.getPrice(),
              user
        );

        final Product createdProduct = this.productService.createNewProduct(product);

        return new CreateProductResponseDTO(
                createdProduct.getId(),
                createdProduct.getName(),
                createdProduct.getDescription(),
                createdProduct.getPrice()
        );
    }
}
