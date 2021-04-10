package com.shaoming.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Order {

    private Long id;

    private String tradeNo;

    private BigDecimal price;

    private List<Product> products;

    public Order() { }

    public Order(Long id, String tradeNo, BigDecimal price, List<Product> products) {
        this.id = id;
        this.tradeNo = tradeNo;
        this.price = price;
        this.products = products;
    }
}
