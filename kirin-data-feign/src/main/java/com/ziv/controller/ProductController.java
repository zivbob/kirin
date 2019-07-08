package com.ziv.controller;

import com.ziv.entity.Product;
import com.ziv.service.ProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.List;

@RestController
@RefreshScope
public class ProductController {

    @Value("${mina.appId}")
    private String appId;

    @Resource
    private ProductService productService;

    @RequestMapping("/products")
    public Object products() {
        List<Product> ps = productService.listProducts();
        return ps;
    }

    @RequestMapping("/config")
    public Object config() {
        return appId;
    }

}
