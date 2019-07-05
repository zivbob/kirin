package com.ziv.service;

import com.ziv.entity.Product;
import com.ziv.feign.ProductFeginClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProductService {
    @Resource
    private ProductFeginClient productFeginClient;

    public List<Product> listProducts(){
        return productFeginClient.listProdcuts();

    }
}
