package com.art1985.orderList.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/default")
public interface IController<T> {
    @PostMapping
    ResponseEntity<Void> create(@RequestBody T t);

    @GetMapping
    ResponseEntity<List<T>> getAll();

    @GetMapping("/{id}")
    ResponseEntity<T> findById(@PathVariable Long id);

    @GetMapping("/")
    ResponseEntity<T> findByName(@RequestParam String name);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteById(@PathVariable Long id);

    @DeleteMapping("/")
    ResponseEntity<Void> deleteById(@RequestParam String name);

    @PutMapping
    ResponseEntity<Void> update(@RequestBody T t);
}
