package com.art1985.orderList.service.product;

import com.art1985.orderList.entities.EntityCreator;
import com.art1985.orderList.entities.Product;
import com.art1985.orderList.repository.ProductRepository;
import com.art1985.orderList.service.product.exception.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {
    private final ProductRepository productRepository = mock(ProductRepository.class);
    private Product product;
    private ProductService victim;

    @BeforeEach
    void setUp() {
        victim = new ProductService(productRepository);
        product = EntityCreator.createProduct();
    }

    @Test
    void create() {
        when(productRepository.save(product)).thenReturn(product);
        assertEquals(product, victim.create(product));
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void findByIdFail() {
        when(productRepository.findById(product.getId())).thenReturn(Optional.empty());
        Exception exception = assertThrows(ProductNotFoundException.class, () -> victim.findById(product.getId()));
        assertEquals("No product found with id " + product.getId(), exception.getMessage());
        verify(productRepository, times(1)).findById(product.getId());
    }

    @Test
    void findById() {
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        assertEquals(product, victim.findById(product.getId()));
        verify(productRepository, times(1)).findById(product.getId());
    }

    @Test
    void findByNameFail() {
        when(productRepository.findByName(product.getName())).thenReturn(Optional.empty());
        Exception exception = assertThrows(ProductNotFoundException.class, () -> victim.findByName(product.getName()));
        assertEquals("No product found with name " + product.getName(), exception.getMessage());
        verify(productRepository, times(1)).findByName(product.getName());
    }

    @Test
    void findByName() {
        when(productRepository.findByName(product.getName())).thenReturn(Optional.of(product));
        assertEquals(product, victim.findByName(product.getName()));
        verify(productRepository, times(1)).findByName(product.getName());
    }

    @Test
    void findAll() {
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        product = EntityCreator.createProduct();
        product.setId(2L);
        product.setName("TestName2");
        productList.add(product);
        when(productRepository.findAll()).thenReturn(productList);
        assertEquals(productList, victim.findAll());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void deleteByIdFail() {
        when(productRepository.findById(product.getId())).thenReturn(Optional.empty());
        Exception exception = assertThrows(ProductNotFoundException.class, () -> victim.deleteById(product.getId()));
        assertEquals("No product found with id " + product.getId() + " to delete", exception.getMessage());
        verify(productRepository, times(1)).findById(product.getId());
    }

    @Test
    void deleteById() {
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        assertEquals(product, victim.deleteById(product.getId()));
        verify(productRepository, times(1)).findById(product.getId());
        verify(productRepository, times(1)).deleteById(product.getId());
    }

    @Test
    void deleteByNameFail() {
        when(productRepository.findByName(product.getName())).thenReturn(Optional.empty());
        Exception exception = assertThrows(ProductNotFoundException.class, () -> victim.deleteByName(product.getName()));
        assertEquals("No product found with name " + product.getName() + " to delete", exception.getMessage());
        verify(productRepository, times(1)).findByName(product.getName());
    }

    @Test
    void deleteByName() {
        when(productRepository.findByName(product.getName())).thenReturn(Optional.of(product));
        assertEquals(product, victim.deleteByName(product.getName()));
        verify(productRepository, times(1)).findByName(product.getName());
        verify(productRepository, times(1)).deleteById(product.getId());
    }

    @Test
    void update() {
        when(productRepository.save(product)).thenReturn(product);
        product = EntityCreator.createProduct();
        product.setVersion(2);
        victim.update(product);
        verify(productRepository, times(1)).save(product);
    }
}