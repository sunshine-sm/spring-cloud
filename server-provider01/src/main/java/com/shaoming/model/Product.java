package com.shaoming.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class Product implements Serializable {

    private Long id;
    private String name;
    private Long count;
    private BigDecimal price;

    public Product() {
    }

    public Product(Long id, String name, Long count, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.price = price;
    }

}
