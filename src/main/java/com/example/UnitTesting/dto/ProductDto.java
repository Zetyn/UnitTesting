package com.example.UnitTesting.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDto {
    private Long id;
    private String code;
    private String name;
    private float price;
    private int discountQuantity;
    private float discountPrice;
}
