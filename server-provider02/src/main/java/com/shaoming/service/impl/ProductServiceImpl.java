package com.shaoming.service.impl;

import com.shaoming.model.Product;
import com.shaoming.service.ProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Override
    public List<Product> getList() {
        return Arrays.asList(
                new Product(1L, "iPhone SE", 50L, BigDecimal.valueOf(2588)),
                new Product(2L, "iPhone 11", 60L, BigDecimal.valueOf(5699)),
                new Product(3L, "iPhone 12", 70L, BigDecimal.valueOf(6888)),
                new Product(4L, "iPhone 12 pro", 80L, BigDecimal.valueOf(8599))
        );
    }

}
