package com.art1985.orderList.entities;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "Products")
public class Product {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "name")
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "discount")
    private BigDecimal discount;
    @Version
    @Column(name = "version")
    private int version;
    @CreatedDate
    @Column(name = "created")
    private LocalDate created;
    @LastModifiedDate
    @Column(name = "updated")
    private LocalDate updated;

    public Product(String name, Category category, BigDecimal price, BigDecimal discount) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.discount = discount;
        this.version = 0;
        this.created = LocalDate.now();
        this.updated = LocalDate.now();
    }

    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public LocalDate getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDate updated) {
        this.updated = updated;
    }

    //TODO test logic
    public BigDecimal getTotalDiscount() {
        return price.multiply(discount.multiply(new BigDecimal("0.01")));
    }

    public BigDecimal getTotalPrice() {
        return price.subtract(getTotalDiscount());
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", price=" + price +
                ", discount=" + discount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return version == product.version &&
                id.equals(product.id) &&
                name.equals(product.name) &&
                category == product.category &&
                price.equals(product.price) &&
                discount.equals(product.discount) &&
                created.equals(product.created) &&
                updated.equals(product.updated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, category, price, discount, version, created, updated);
    }
}
