package com.example.shop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ShoppingCartTest {

    @Test
    @DisplayName("Should create an item in shopingcart")
    void addOneItemToShoppingCart(){

        ShoppingCart shoppingCart = new ShoppingCart();

        Product milk = new Product();
        shoppingCart.addProduct(milk);


    }
}
