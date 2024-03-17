package com.efborchardt.productfeedback.domain.product.model;

import com.efborchardt.productfeedback.domain.user.model.User;
import com.efborchardt.productfeedback.domain.user.model.UserRole;
import org.junit.jupiter.api.Test;


import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    private final User user = new User(
            "test",
            "email@email.com",
            "password",
            UserRole.USER
    );

    @Test
    void createValidProduct() {
        assertDoesNotThrow(() -> new Product(UUID.randomUUID(), "Product Name", "Product Description", BigDecimal.TEN, user),
                "Creating a product with all valid fields should not throw an exception.");
    }

    @Test
    void testProductGetters() {
        UUID id = UUID.randomUUID();
        String name = "Test Product";
        String description = "Test Description";
        BigDecimal price = BigDecimal.valueOf(99.99);

        Product product = new Product(id, name, description, price, user);

        assertEquals(id, product.getId());
        assertEquals(name, product.getName());
        assertEquals(description, product.getDescription());
        assertEquals(price, product.getPrice());
        assertEquals(user, product.getCreatedBy());
    }

    @Test
    void testProductSetters() {
        UUID id = UUID.randomUUID();
        String name = "Test Product";
        String description = "Test Description";
        BigDecimal price = BigDecimal.valueOf(99.99);

        Product product = new Product(id, name, description, price, user);

        String newName = "New Test Product";
        String newDescription = "New Description";
        BigDecimal newPrice = BigDecimal.valueOf(199.99);

        product.setName(newName);
        product.setDescription(newDescription);
        product.setPrice(newPrice);

        assertEquals(newName, product.getName());
        assertEquals(newDescription, product.getDescription());
        assertEquals(newPrice, product.getPrice());
    }

    @Test
    void createProductWithoutIdThrowsException() {
        Exception exception = assertThrows(IllegalStateException.class, () -> new Product(null, "Product Name", "Product Description", BigDecimal.TEN, user),
                "Product creation without an ID should throw an exception.");
        assertTrue(exception.getMessage().contains("ID is required"), "Exception message should include missing ID error.");
    }

    @Test
    void createProductWithoutNameThrowsException() {
        Exception exception = assertThrows(IllegalStateException.class, () -> new Product(UUID.randomUUID(), null, "Product Description", BigDecimal.TEN, user),
                "Product creation without a name should throw an exception.");
        assertTrue(exception.getMessage().contains("Name is required"), "Exception message should include missing name error.");
    }

    @Test
    void createProductWithBlankNameThrowsException() {
        Exception exception = assertThrows(IllegalStateException.class, () -> new Product(UUID.randomUUID(), " ", "Product Description", BigDecimal.TEN, user),
                "Product creation with a blank name should throw an exception.");
        assertTrue(exception.getMessage().contains("Name is required"), "Exception message should include missing name error.");
    }

    @Test
    void createProductWithInvalidPriceThrowsException() {
        Exception exception = assertThrows(IllegalStateException.class, () -> new Product(UUID.randomUUID(), "Product Name", "Product Description", BigDecimal.ZERO, user),
                "Product creation with a non-positive price should throw an exception.");
        assertTrue(exception.getMessage().contains("Price must be greater than zero"), "Exception message should include invalid price error.");
    }

    @Test
    void createProductWithoutUserThrowsException() {
        Exception exception = assertThrows(IllegalStateException.class, () -> new Product(UUID.randomUUID(), "Product Name", "Product Description", BigDecimal.TEN, null),
                "Product creation without a user should throw an exception.");
        assertTrue(exception.getMessage().contains("The product must be linked to a user"), "Exception message should include missing user error.");
    }

    @Test
    void testEqualsAndHashCode() {
        Product product = new Product("name", "description", BigDecimal.TEN, user);

        Product sameIdProduct = new Product(product.getId(), "Another Name", "Another Description", BigDecimal.TEN, user);
        assertEquals(product, sameIdProduct, "Same ID must result in equality");
        assertEquals(product.hashCode(), sameIdProduct.hashCode(), "Same ID must result in equal hashCode");

        Product differentIdProduct = new Product(UUID.randomUUID(), "Another Name", "Another Description", BigDecimal.TEN, user);
        assertNotEquals(product, differentIdProduct, "Different ID should result in non-equal objects");
        assertNotEquals(product.hashCode(), differentIdProduct.hashCode(), "Different ID should result in non-equal hashCode");
    }
}