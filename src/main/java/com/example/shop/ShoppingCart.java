package com.example.shop;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private List<Product> products;
    private int totalDiscount;

    public ShoppingCart(){
        this.products = new ArrayList<>();
        this.totalDiscount = 0;
    }

    public void addProduct(Product product){
        products.add(product);
    }

    public List<Product> getAllProducts(){
        return products;
    }

    public void deleteProduct(Product product){
        products.remove(product);
    }

    public int getCartTotalValue(){
        int total = 0;

        for(Product product : products){
            total += product.getPrice();
        }
        return total;
    }

    public void addDiscount(int discount){
        totalDiscount += discount;
    }

    public int getTotalAfterDiscount(){
        int sum = 0;
        for(Product product: products){
            sum += product.getPrice();
        }
        int total = sum - totalDiscount;
        if(total < 0){
            return 0;
        }
        return total;
    }

}
