package com.efborchardt.productfeedback.application.usecase.product;

import com.efborchardt.productfeedback.application.usecase.common.DefaultUseCase;
import com.efborchardt.productfeedback.application.usecase.product.create.CreateProductUseCase;
import com.efborchardt.productfeedback.application.usecase.product.delete.DeleteProductUseCase;
import com.efborchardt.productfeedback.application.usecase.product.list.ListProductsUseCase;
import com.efborchardt.productfeedback.application.usecase.product.update.UpdateProductUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductUseCaseAggregator {

    private final CreateProductUseCase createProductUseCase;
    private final ListProductsUseCase listProductsUseCase;
    private final DeleteProductUseCase deleteProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;

    @Autowired
    public ProductUseCaseAggregator(CreateProductUseCase createProductUseCase,
                             ListProductsUseCase listProductsUseCase,
                             DeleteProductUseCase deleteProductUseCase,
                             UpdateProductUseCase updateProductUseCase) {
        this.createProductUseCase = createProductUseCase;
        this.listProductsUseCase = listProductsUseCase;
        this.deleteProductUseCase = deleteProductUseCase;
        this.updateProductUseCase = updateProductUseCase;
    }

    public DefaultUseCase<?,?> create() {
        return this.createProductUseCase;
    }

    public DefaultUseCase<?,?> list() {
        return this.listProductsUseCase;
    }

    public DefaultUseCase<?,?> delete() {
        return this.deleteProductUseCase;
    }

    public DefaultUseCase<?,?> update() {
        return this.updateProductUseCase;
    }
}
