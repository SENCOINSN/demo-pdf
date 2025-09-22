package com.gl.si.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Product {
    private String matricule;
    private String description;
    private String name;
    private double price;
    private int quantity;

    public Product() {
        this.matricule = UUID.randomUUID()
                .toString().substring(0, 8);
    }
    public Product(String description, String name, double price, int quantity) {
        this();
        this.description = description;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}
