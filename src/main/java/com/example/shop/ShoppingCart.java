package com.example.shop;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    public List<Product> products = new ArrayList<>();

    public void addProduct(Product product){
        products.add(product);
    }

    public List<Product> getAllProducts(){
        return products;
    }

    public void deleteProduct(Product product){
        products.remove(product);
    }


}
