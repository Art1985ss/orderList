package com.art1985.orderList.web.controller.product;

import com.art1985.orderList.entities.Product;
import com.art1985.orderList.service.product.ProductService;
import com.art1985.orderList.web.controller.IController;
import com.art1985.orderList.web.dto.DtoConverter;
import com.art1985.orderList.web.dto.DtoProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController implements IController<DtoProduct> {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Override
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Void> create(DtoProduct dtoProduct) {
        Product product = DtoConverter.fromDto(dtoProduct);
        Long id = productService.create(product).getId();
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED).location(location).build();
    }

    @Override
    public ResponseEntity<List<DtoProduct>> getAll() {
        List<Product> products = productService.findAll();
        List<DtoProduct> dtoProductList = products.stream().map(DtoConverter::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtoProductList);
    }

    @Override
    public ResponseEntity<DtoProduct> findById(Long id) {
        Product product = productService.findById(id);
        DtoProduct dtoProduct = DtoConverter.toDto(product);
        return ResponseEntity.ok(dtoProduct);
    }

    @Override
    public ResponseEntity<DtoProduct> findByName(String name) {
        Product product = productService.findByName(name);
        DtoProduct dtoProduct = DtoConverter.toDto(product);
        return ResponseEntity.ok(dtoProduct);
    }

    @Override
    public ResponseEntity<Void> deleteById(Long id) {
        productService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<Void> deleteByName(String name) {
        productService.deleteByName(name);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Void> update(DtoProduct dtoProduct) {
        Product product = DtoConverter.fromDto(dtoProduct);
        productService.update(product);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
