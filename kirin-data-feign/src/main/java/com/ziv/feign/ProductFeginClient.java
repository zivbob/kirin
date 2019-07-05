package com.ziv.feign;

import com.ziv.entity.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient("PRODUCT-DATA-SERVICE")
public interface ProductFeginClient {

    @GetMapping("/products")
    List<Product> listProdcuts();
}
