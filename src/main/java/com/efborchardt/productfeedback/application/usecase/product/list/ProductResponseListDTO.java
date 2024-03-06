package com.efborchardt.productfeedback.application.usecase.product.list;

import java.util.List;

public class ProductResponseListDTO {

    List<ProductResponseDTO> products;

    public ProductResponseListDTO(List<ProductResponseDTO> products) {
        this.products = products;
    }

    public List<ProductResponseDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductResponseDTO> products) {
        this.products = products;
    }
}
