package com.efborchardt.productfeedback.application.usecase.product.list;

import com.efborchardt.productfeedback.application.usecase.common.DefaultUseCase;
import com.efborchardt.productfeedback.domain.product.model.Product;
import com.efborchardt.productfeedback.domain.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ListProductsUseCase implements DefaultUseCase<ListProductFilterCriteriaDTO, ProductResponseListDTO> {

    private final ProductService service;

    @Autowired
    public ListProductsUseCase(ProductService service) {
        this.service = service;
    }

    @Override
    public ProductResponseListDTO execute(ListProductFilterCriteriaDTO criteria) {
        List<Product> productList = this.service.listProducts(criteria);
        return mapProductListToDto(productList);
    }

    private ProductResponseListDTO mapProductListToDto(final List<Product> productList) {
        final List<ProductResponseDTO> responseDTOList = productList.stream()
                .map(this::mapProductToDto)
                .collect(Collectors.toList());

        return new ProductResponseListDTO(responseDTOList);
    }

    private ProductResponseDTO mapProductToDto(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice()
        );
    }
}
