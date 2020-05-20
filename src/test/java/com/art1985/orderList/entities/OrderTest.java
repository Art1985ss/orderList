package com.art1985.orderList.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
    Product product;
    Order victim;

    @BeforeEach
    void setUp() {
        product = EntityCreator.createProduct();
        victim = EntityCreator.createOrder();
    }

    @Test
    void getTotalPrice() {
        victim.addProduct(product, 1);
        assertEquals(new BigDecimal("180.00"), victim.getTotalPrice());
        product = EntityCreator.createProduct();
        product.setId(2L);
        product.setName("TestProduct2");
        product.setPrice(new BigDecimal("300"));
        victim.addProduct(product, 2);
        assertEquals(new BigDecimal("720.00"), victim.getTotalPrice());
    }

    @Test
    void addProduct() {
        victim.addProduct(product, 1);
        assertEquals(1, victim.getProductListWithAmount().size());
    }

    @Test
    void removeProduct() {
        victim.addProduct(product,1);
        assertEquals(1, victim.getProductListWithAmount().size());
        victim.removeProduct(product);
        assertEquals(0, victim.getProductListWithAmount().size());
    }
}