package com.example.shop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.predicate;

public class ShoppingCartTest {

    @Test
    @DisplayName("Should create an item in shopingcart")
    void addOneItemToShoppingCart(){

        ShoppingCart shoppingCart = new ShoppingCart();

        Product milk = new Product();
        shoppingCart.addProduct(milk);

    }

    @Test
    @DisplayName("Should return all items in shopping cart")
    void shouldReturnAllItemsInShoppingCart(){

        ShoppingCart shoppingCart = new ShoppingCart();

        Product eggs = new Product();
        Product bread = new Product();

        shoppingCart.addProduct(eggs);
        shoppingCart.addProduct(bread);

        //act
        List<Product> products = shoppingCart.getAllProducts();

        //assert
        assertThat(products).containsExactlyInAnyOrder(eggs, bread);
    }

    @Test
    @DisplayName("Should erase item from shopping cart")
    void shouldEraseItemFromShoppingCart(){
        ShoppingCart shoppingCart = new ShoppingCart();

        Product flowers = new Product();
        shoppingCart.addProduct(flowers);

        shoppingCart.deleteProduct(flowers);

        //act
        List<Product> products = shoppingCart.getAllProducts();

        //assert
        assertThat(products).isEmpty();
    }

    @Test
    @DisplayName("Should return total sum of item prices")
    void shouldReturnTotalSumOfItemPrices(){
        ShoppingCart shoppingCart = new ShoppingCart();

        Product bread = new Product(23);


    }
}
