package com.art1985.orderList.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    private Product victim;

    @BeforeEach
    void setUp() {
        victim = createProduct();
    }

    @Test
    void getTotalDiscount() {
        assertEquals(new BigDecimal("20.00"), victim.getTotalDiscount());
    }

    @Test
    void getTotalPrice() {
        assertEquals(new BigDecimal("180.00"), victim.getTotalPrice());
    }


    private Product createProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Test");
        product.setCategory(Category.FLOUR_PRODUCTS);
        product.setPrice(new BigDecimal("200"));
        product.setDiscount(new BigDecimal("10"));
        product.setVersion(1);
        product.setCreated(LocalDate.now().minusDays(10));
        product.setUpdated(LocalDate.now().minusDays(5));
        return product;
    }

}