package com.example.bikproject.models.adapters;

public class ProductCart {

    private String name;
    private Double price;
    private String description;

    public ProductCart(String name, double price) {
        this.name = name;
        this.price = price;
//        this.description = description;
    }
    public ProductCart() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
