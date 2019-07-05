package com.ziv.entity;

import lombok.Data;

@Data
public class Product {
    private int id;
    private String name;
    private int price;

    public Product() {

    }
    public Product(int id, String name, int price) {
        super();
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
