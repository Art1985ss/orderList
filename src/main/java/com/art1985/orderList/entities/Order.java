package com.art1985.orderList.entities;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "name")
    private String name;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @ElementCollection
    @CollectionTable(name = "Order_product_list", joinColumns = @JoinColumn(name = "order_id"))
    @Column(name = "count")
    @MapKeyJoinColumn(name = "product_id")
    private Map<Product, Integer> productListWithAmount;
    @Version
    @Column(name = "version")
    private int version;
    @CreatedDate
    @Column(name = "created")
    private LocalDate created;
    @LastModifiedDate
    @Column(name = "modified")
    private LocalDate modified;

    public Order(String name, User user) {
        this.name = name;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Map<Product, Integer> getProductListWithAmount() {
        return productListWithAmount;
    }

    public void setProductListWithAmount(Map<Product, Integer> productListWithAmount) {
        this.productListWithAmount = productListWithAmount;
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

    public LocalDate getModified() {
        return modified;
    }

    public void setModified(LocalDate modified) {
        this.modified = modified;
    }

    public BigDecimal getTotalPrice() {
        return productListWithAmount.entrySet().stream().map(productIntegerEntry -> {
            Product product = productIntegerEntry.getKey();
            int amount = productIntegerEntry.getValue();
            return product.getTotalPrice().multiply(new BigDecimal(amount));
        }).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", customer=" + user +
                ", productListWithAmount=" + productListWithAmount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return version == order.version &&
                id.equals(order.id) &&
                name.equals(order.name) &&
                user.equals(order.user) &&
                Objects.equals(productListWithAmount, order.productListWithAmount) &&
                created.equals(order.created) &&
                modified.equals(order.modified);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, user, productListWithAmount, version, created, modified);
    }
}
