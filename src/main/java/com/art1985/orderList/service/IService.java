package com.art1985.orderList.service;

import java.util.List;

public interface IService<T> {
    T create(T t);

    T findById(Long id);

    T findByName(String name);

    List<T> findAll();

    T deleteById(Long id);

    T deleteByName(String name);

    void update(T t);
}
