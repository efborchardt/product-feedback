package com.efborchardt.productfeedback.infrastructure.interfaces.rest.routes.product;

import com.efborchardt.productfeedback.application.usecase.product.create.CreateProductRequestDTO;
import com.efborchardt.productfeedback.application.usecase.product.create.CreateProductResponseDTO;
import com.efborchardt.productfeedback.application.usecase.product.create.CreateProductUseCase;
import com.efborchardt.productfeedback.application.usecase.product.delete.DeleteProductRequestDTO;
import com.efborchardt.productfeedback.application.usecase.product.delete.DeleteProductResponseDTO;
import com.efborchardt.productfeedback.application.usecase.product.delete.DeleteProductUseCase;
import com.efborchardt.productfeedback.application.usecase.product.find.FindProductResponseDTO;
import com.efborchardt.productfeedback.application.usecase.product.find.FindProductUseCase;
import com.efborchardt.productfeedback.application.usecase.product.list.ListProductFilterCriteriaDTO;
import com.efborchardt.productfeedback.application.usecase.product.list.ListProductsUseCase;
import com.efborchardt.productfeedback.application.usecase.product.list.ProductResponseListDTO;
import com.efborchardt.productfeedback.application.usecase.product.update.UpdateProductRequestDTO;
import com.efborchardt.productfeedback.application.usecase.product.update.UpdateProductResponseDTO;
import com.efborchardt.productfeedback.application.usecase.product.update.UpdateProductUseCase;
import com.efborchardt.productfeedback.infrastructure.interfaces.rest.security.AuthenticationContextUtil;
import com.efborchardt.productfeedback.infrastructure.interfaces.rest.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final CreateProductUseCase createProductUseCase;
    private final ListProductsUseCase listProductsUseCase;
    private final DeleteProductUseCase deleteProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final FindProductUseCase findProductUseCase;
    private final TokenService tokenService;

    @Autowired
    public ProductController(CreateProductUseCase createProductUseCase,
                             ListProductsUseCase listProductsUseCase,
                             DeleteProductUseCase deleteProductUseCase,
                             UpdateProductUseCase updateProductUseCase, FindProductUseCase findProductUseCase, TokenService tokenService) {
        this.createProductUseCase = createProductUseCase;
        this.listProductsUseCase = listProductsUseCase;
        this.deleteProductUseCase = deleteProductUseCase;
        this.updateProductUseCase = updateProductUseCase;
        this.findProductUseCase = findProductUseCase;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<CreateProductResponseDTO> createProduct(@RequestBody CreateProductRequestDTO request) {
        final CreateProductResponseDTO response = this.createProductUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<ProductResponseListDTO> listProducts(@RequestParam(required = false) BigDecimal minPrice,
                                                               @RequestParam(required = false) BigDecimal maxPrice) {
        final ListProductFilterCriteriaDTO criteria = new ListProductFilterCriteriaDTO(minPrice, maxPrice);
        final ProductResponseListDTO response = this.listProductsUseCase.execute(criteria);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FindProductResponseDTO> findProduct(@PathVariable UUID id) {
        final FindProductResponseDTO response = this.findProductUseCase.execute(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<UpdateProductResponseDTO> updateProduct(@RequestBody UpdateProductRequestDTO request) {
        final UpdateProductResponseDTO response = this.updateProductUseCase.execute(
                request,
                AuthenticationContextUtil.getAuthenticatedUser()
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteProductResponseDTO> deleteProduct(@PathVariable UUID id) {
        final DeleteProductResponseDTO response = this.deleteProductUseCase.execute(
                id,
                AuthenticationContextUtil.getAuthenticatedUser());
        return ResponseEntity.ok(response);
    }

}
