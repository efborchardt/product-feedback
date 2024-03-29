package com.efborchardt.productfeedback.domain.product.service;

import com.efborchardt.productfeedback.application.usecase.product.list.ListProductFilterCriteriaDTO;
import com.efborchardt.productfeedback.application.usecase.product.update.UpdateProductRequestDTO;
import com.efborchardt.productfeedback.domain._shared.exception.UnauthorizedActionException;
import com.efborchardt.productfeedback.domain.product.exception.ProductNotFoundException;
import com.efborchardt.productfeedback.domain.product.model.Product;
import com.efborchardt.productfeedback.domain.product.repository.ProductRepository;
import com.efborchardt.productfeedback.domain.user.model.User;
import com.efborchardt.productfeedback.domain.user.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository repository;

    @Autowired
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product createNewProduct(Product product) {
        final boolean productAlreadyExists = !this.repository.findProductByName(product.getName()).isEmpty();
        if (productAlreadyExists) {
            throw new RuntimeException("This product already exists");
        }

        this.repository.save(product);
        return product;
    }

    public List<Product> listProducts(ListProductFilterCriteriaDTO criteria) {
        if (criteria.getMinimumPrice() != null && criteria.getMaximumPrice() != null) {
            return this.repository.findByPriceRange(
                    criteria.getMinimumPrice(),
                    criteria.getMaximumPrice()
            );
        }
        return this.repository.list();
    }

    public Product updateProduct(UpdateProductRequestDTO productData, User sender) {
        Product product = findById(productData.getId());
        throwIfUnauthorized(sender, product);

        product.setName(productData.getName());
        product.setDescription(productData.getDescription());
        product.setPrice(productData.getPrice());

        this.repository.save(product);

        return product;
    }

    public void deleteProduct(UUID id, User sender) {
        final Product product = findById(id);
        throwIfUnauthorized(sender, product);
        this.repository.delete(product);
    }

    public Product findById(UUID id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Unable to find a product with ID" + id.toString()));
    }

    private void throwIfUnauthorized(User sender, Product product) {
        if (sender.getRole() == UserRole.ADMIN) return;
        if (!sender.getUsername().equals(product.getCreatedBy().getUsername()))
            throw new UnauthorizedActionException("User not authorized to perform this action");
    }
}
