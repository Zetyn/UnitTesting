package com.example.UnitTesting.service.impl;

import com.example.UnitTesting.dto.ProductDto;
import com.example.UnitTesting.repository.ProductRepository;
import com.example.UnitTesting.repository.entity.Product;
import com.example.UnitTesting.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class CartServiceImpl implements CartService {
    private Map<String,Integer> cart = new HashMap<>();
    private final ProductRepository productRepository;
    @Autowired
    public CartServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Map<String,Integer> getCart() {
        return cart;
    }

    @Override
    public void addProductToCart(String code) {
        Product product = productRepository.findByCode(code);
        if (product != null) {
            cart.put(code,cart.getOrDefault(code,0)+1);
        } else throw new RuntimeException("Product is null");
    }

    @Override
    public void removeProductFromCart(String code) {
        Product product = productRepository.findByCode(code);
        if (product != null) {
            cart.remove(code);
        } else throw new RuntimeException("Product is null");
    }

    @Override
    public double getTotalPrice() {
        return cart.entrySet()
                .stream()
                .mapToDouble(entry -> {
                    String code = entry.getKey();
                    int quantity = entry.getValue();
                    Product product = productRepository.findByCode(code);
                    if (product != null) {
                        return getDiscountedPrice(product,quantity);
                    } else return 0;
                })
                .sum();
    }

    @Override
    public double getDiscountedPrice(Product product, int quantity) {
        if (quantity >= product.getDiscountQuantity()) {
            int discountedQuantity = quantity / product.getDiscountQuantity();
            int regularQuantity = discountedQuantity % product.getDiscountQuantity();
            return discountedQuantity * product.getDiscountPrice() + regularQuantity * product.getPrice();
        } else {
            return quantity * product.getPrice();
        }
    }
}
