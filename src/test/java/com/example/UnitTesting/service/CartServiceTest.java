package com.example.UnitTesting.service;

import com.example.UnitTesting.repository.ProductRepository;
import com.example.UnitTesting.repository.entity.Product;
import com.example.UnitTesting.service.impl.CartServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

public class CartServiceTest {
    private CartService cartService;
    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        cartService = new CartServiceImpl(productRepository);
    }

    @Test
    public void addProductTest() {
        Product productA = Product.builder().name("Apple").price(4).code("A").discountQuantity(2).discountPrice(7).build();
        when(productRepository.findByCode("A")).thenReturn(productA);
        cartService.addProductToCart("A");

        Map<String,Integer> expectedCart = new HashMap<>();
        expectedCart.put("A",1);

        assertEquals(expectedCart,cartService.getCart());
    }

    @Test
    public void addUnknownProductTest() {
        assertThrows(RuntimeException.class,() -> {
            when(productRepository.findByCode("Unknown")).thenReturn(null);
            cartService.addProductToCart("Unknown");
        },"Product is null");
    }

    @Test
    public void removeProductTest() {
        Product productA = Product.builder().name("Apple").price(4).code("A").discountQuantity(2).discountPrice(7).build();
        Product productB = Product.builder().name("Orange").price(15).code("B").discountQuantity(3).discountPrice(42).build();
        when(productRepository.findByCode("A")).thenReturn(productA);
        when(productRepository.findByCode("B")).thenReturn(productB);

        cartService.addProductToCart("A");
        cartService.addProductToCart("A");
        cartService.addProductToCart("B");

        Map<String,Integer> expectedCart = new HashMap<>();
        expectedCart.put("A",2);
        expectedCart.put("B",1);

        assertEquals(expectedCart,cartService.getCart());

        cartService.removeProductFromCart("B");
        expectedCart.remove("B");

        assertEquals(expectedCart,cartService.getCart());
    }

    @Test
    public void removeUnknownProductTest() {
        assertThrows(RuntimeException.class,() -> {
            when(productRepository.findByCode("Unknown")).thenReturn(null);
            cartService.removeProductFromCart("Unknown");
        },"Product is null");
    }


    @Test
    public void getTotalSumTest() {
        String codeA = "A";
        String codeB = "B";

        Product productA = Product.builder().name("Apple").price(4).code(codeA).discountQuantity(2).discountPrice(7).build();
        when(productRepository.findByCode(codeA)).thenReturn(productA);
        cartService.addProductToCart(codeA);
        cartService.addProductToCart(codeA);
        cartService.addProductToCart(codeA);
        assertEquals(11,cartService.getTotalPrice());

        Product productB = Product.builder().name("Orange").price(15).code(codeB).discountQuantity(3).discountPrice(42).build();
        when(productRepository.findByCode(codeB)).thenReturn(productB);
        cartService.addProductToCart(codeB);
        cartService.addProductToCart(codeB);

        Map<String,Integer> expectedCart = new HashMap<>();
        expectedCart.put(codeA,3);
        expectedCart.put(codeB,2);

        assertEquals(expectedCart,cartService.getCart());
        double expectedTotalPrice = 11 + 30;
        assertEquals(expectedTotalPrice,cartService.getTotalPrice());
    }
}
