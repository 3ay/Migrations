package com.netology.migrations.controller;


import com.netology.migrations.repo.LayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class LayerController {
    private final LayerRepository repository;

    @GetMapping("/products/fetch-product")
    public ResponseEntity<Optional<List<Object>>> getProductName(@RequestParam(value = "name") String customerName)
    {
        Optional<java.util.List<Object>> list = repository.getProductName(customerName);
        return ResponseEntity.ok(list);
    }

}
