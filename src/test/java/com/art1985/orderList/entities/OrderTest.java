package com.art1985.orderList.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
    Product product;
    Order victim;

    @BeforeEach
    void setUp() {
        product = createProduct();
        victim = createOrder();
    }

    @Test
    void getTotalPrice() {
        victim.addProduct(product, 1);
        assertEquals(new BigDecimal("180.00"), victim.getTotalPrice());
        product = createProduct();
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

    private Order createOrder(){
        Order order = new Order();
        order.setId(1L);
        order.setName("Test");
        order.setUser(null);
        order.setProductListWithAmount(new HashMap<>());
        order.setVersion(1);
        order.setCreated(LocalDate.now().minusDays(10));
        order.setUpdated(LocalDate.now().minusDays(5));
        return order;
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