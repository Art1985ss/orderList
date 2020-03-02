package com.art1985.orderList.service.product;

import com.art1985.orderList.entities.Product;
import com.art1985.orderList.repository.ProductRepository;
import com.art1985.orderList.service.IService;
import com.art1985.orderList.service.product.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IService<Product> {
    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("No product found with id " + id));
    }

    @Override
    public Product findByName(String name) {
        return productRepository.findByName(name)
                .orElseThrow(() -> new ProductNotFoundException("No product found with name " + name));
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product deleteById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("No product found with id " + id + " to delete"));
        productRepository.deleteById(id);
        return product;
    }

    @Override
    public Product deleteByName(String name) {
        Product product = productRepository.findByName(name)
                .orElseThrow(() -> new ProductNotFoundException("No product found with name " + name + " to delete"));
        productRepository.deleteById(product.getId());
        return product;
    }

    @Override
    public void update(Product product) {
        productRepository.save(product);
    }
}
