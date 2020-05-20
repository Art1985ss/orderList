package com.art1985.orderList.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    private Product victim;

    @BeforeEach
    void setUp() {
        victim = EntityCreator.createProduct();
    }

    @Test
    void getTotalDiscount() {
        assertEquals(new BigDecimal("20.00"), victim.getTotalDiscount());
    }

    @Test
    void getTotalPrice() {
        assertEquals(new BigDecimal("180.00"), victim.getTotalPrice());
    }
}