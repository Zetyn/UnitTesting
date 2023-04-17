package com.example.UnitTesting.service.impl;

import com.example.UnitTesting.dto.ProductDto;
import com.example.UnitTesting.exeption.NotFoundException;
import com.example.UnitTesting.repository.ProductRepository;
import com.example.UnitTesting.repository.entity.Product;
import com.example.UnitTesting.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public List<ProductDto> getAll() {
        List<ProductDto> productsDto = new ArrayList<>();
        productRepository.findAll().forEach(product -> productsDto.add(modelMapper.map(product, ProductDto.class)));
        return productsDto;
    }

    @Override
    public ProductDto getById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(NotFoundException::new);
        return modelMapper.map(product, ProductDto.class);
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        if (productDto != null) {
            Product product = productRepository.save(modelMapper.map(productDto, Product.class));
            return modelMapper.map(product, ProductDto.class);
        } else throw new RuntimeException("Product is null");
    }

    @Override
    public void deleteProductById(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
