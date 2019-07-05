package com.ziv.service;

import com.ziv.config.RibbonClient;
import com.ziv.entity.Product;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProductService {

    @Resource
    private RibbonClient ribbonClient;

    public List<Product> listProducts(){
        return ribbonClient.listProducts();

    }
}
