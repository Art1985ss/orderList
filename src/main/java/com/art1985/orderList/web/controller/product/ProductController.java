package com.art1985.orderList.web.controller.product;

import com.art1985.orderList.entities.Product;
import com.art1985.orderList.service.product.ProductService;
import com.art1985.orderList.web.controller.IController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController implements IController<Product> {
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public ResponseEntity<Void> create(Product product) {
        Long id = productService.create(product).getId();
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED).location(location).build();
    }

    @Override
    public ResponseEntity<List<Product>> getAll() {
        List<Product> products = productService.findAll();
        return ResponseEntity.ok(products);
    }

    @Override
    public ResponseEntity<Product> findById(Long id) {
        Product product = productService.findById(id);
        return ResponseEntity.ok(product);
    }

    @Override
    public ResponseEntity<Product> findByName(String name) {
        Product product = productService.findByName(name);
        return ResponseEntity.ok(product);
    }

    @Override
    public ResponseEntity<Void> deleteById(Long id) {
        productService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<Void> deleteById(String name) {
        productService.deleteByName(name);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<Void> update(Product product) {
        productService.update(product);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
