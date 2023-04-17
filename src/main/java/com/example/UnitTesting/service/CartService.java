package com.example.UnitTesting.service;

import com.example.UnitTesting.repository.entity.Product;

import java.util.Map;

public interface CartService {
    Map<String,Integer> getCart();
    void addProductToCart(String code);
    void removeProductFromCart(String code);
    double getTotalPrice();
    double getDiscountedPrice(Product product, int discountQuantity);
}
