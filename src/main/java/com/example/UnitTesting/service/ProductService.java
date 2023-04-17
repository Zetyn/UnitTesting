package com.example.UnitTesting.service;

import com.example.UnitTesting.dto.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> getAll();
    ProductDto getById(Long id);
    ProductDto createProduct(ProductDto productDto);
    void deleteProductById(Long id);
}
