package com.example.UnitTesting.repository;

import com.example.UnitTesting.repository.entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product,Long> {
    Product findByCode(String code);
}
