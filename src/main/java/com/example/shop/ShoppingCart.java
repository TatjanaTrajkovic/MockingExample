package com.example.shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCart {
    private Map<Product, Integer> products;
    private int totalDiscount;

    public ShoppingCart(){
        this.products = new HashMap<>();
        this.totalDiscount = 0;
    }

    public void addProduct(Product product) {
        int current = products.getOrDefault(product, 0);
        products.put(product, current + 1);
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products.keySet());
    }

    public void deleteProduct(Product product){
        products.remove(product);
    }

    public int getCartTotalValue() {
        int total = 0;

        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        total -= totalDiscount;
        if(total < 0){
            total = 0;
        }
        return total;
    }

    public void addDiscount(int discount){
        totalDiscount += discount;
    }
}
