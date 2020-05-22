package com.art1985.orderList.web.dto;

import com.art1985.orderList.entities.Product;
import com.art1985.orderList.entities.User;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

public class DtoOrder {
    private Long id;
    private String name;
    private DtoUser dtoUser;
    private Map<DtoProduct, Integer> productListWithAmount;
    private int version;
    private LocalDate created;
    private LocalDate updated;

    public DtoOrder() {
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

    public DtoUser getUser() {
        return dtoUser;
    }

    public void setUser(DtoUser dtoUser) {
        this.dtoUser = dtoUser;
    }

    public Map<DtoProduct, Integer> getProductListWithAmount() {
        return productListWithAmount;
    }

    public void setProductListWithAmount(Map<DtoProduct, Integer> productListWithAmount) {
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

    public LocalDate getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDate updated) {
        this.updated = updated;
    }

    @Override
    public String toString() {
        return "DtoOrder{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", user=" + dtoUser +
                ", productListWithAmount=" + productListWithAmount +
                ", version=" + version +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DtoOrder dtoOrder = (DtoOrder) o;
        return id.equals(dtoOrder.id) &&
                name.equals(dtoOrder.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
