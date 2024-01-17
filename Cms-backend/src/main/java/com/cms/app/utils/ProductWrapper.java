package com.cms.app.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class ProductWrapper {
    public ProductWrapper(Integer id, String name, String description,Integer price, String status,  Integer categoryId, String categoryName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.status = status;

        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public ProductWrapper() {
    }

    Integer id;

    String name;
    String description;
    String status;
    Integer price;
    Integer categoryId;
    String categoryName;

    public ProductWrapper(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public ProductWrapper(Integer id, String name, String description, Integer price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
