package com.ziv.feign;

import com.ziv.entity.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value = "PRODUCT-DATA-SERVICE", fallback = ProductClientFeignHystrix.class)
public interface ProductFeginClient {

    @GetMapping("/products")
    List<Product> listProdcuts();
}
