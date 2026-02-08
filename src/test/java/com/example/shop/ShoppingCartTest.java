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

        List<Product> products = shoppingCart.getAllProducts();
        assertThat(products).containsExactlyInAnyOrder(milk);
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
        Product rice = new Product(32);

        shoppingCart.addProduct(bread);
        shoppingCart.addProduct(rice);

        int totalValue = shoppingCart.getCartTotalValue();

        //assert
        assertThat(totalValue).isEqualTo(55);
    }

    @Test
    @DisplayName("Should return discounted total price")
    void shouldReturnDiscountedTotalPrice(){
        ShoppingCart shoppingCart = new ShoppingCart();

        Product milk = new Product(30);
        Product bread = new Product(40);

        shoppingCart.addProduct(milk);
        shoppingCart.addProduct(bread);

        shoppingCart.addDiscount(10);

        int totalValue = shoppingCart.getCartTotalValue();

        //assert
        assertThat(totalValue).isEqualTo(60);
    }

    @Test
    @DisplayName("Should not return negative total after discount")
    void shouldNotReturnNegativeTotalAfterDiscount(){
        ShoppingCart shoppingCart = new ShoppingCart();

        shoppingCart.addProduct(new Product(20));
        shoppingCart.addDiscount(50);

        int totalValue = shoppingCart.getCartTotalValue();

        assertThat(totalValue).isEqualTo(0);
    }

    @Test
    @DisplayName("Should increase quantiy when same produt is added several times")
    void shouldIncreaseQuantityWhenSameProductIsAddedSeveralTimes() {

        ShoppingCart cart = new ShoppingCart();
        Product milk = new Product(10);

        cart.addProduct(milk);
        cart.addProduct(milk);
        cart.addProduct(milk);

        assertThat(cart.getCartTotalValue()).isEqualTo(30);
    }

    @Test
    @DisplayName("Should ignore delete when product is not in cart")
    void shouldIgnoreDeleteWhenProductIsNotInCart() {

        ShoppingCart cart = new ShoppingCart();
        Product milk = new Product(10);

        cart.deleteProduct(milk);

        assertThat(cart.getAllProducts()).isEmpty();
    }

    @Test
    @DisplayName("Should return zero when cart is empty")
    void shouldReturnZeroWhenCartIsEmpty() {

        ShoppingCart cart = new ShoppingCart();

        assertThat(cart.getCartTotalValue()).isEqualTo(0);
    }

    @Test
    @DisplayName("Should return zero when discount is added to empty cart")
    void shouldReturnZeroWhenDiscountIsAddedToEmptyCart() {

        ShoppingCart cart = new ShoppingCart();

        cart.addDiscount(100);

        assertThat(cart.getCartTotalValue()).isEqualTo(0);
    }

    @Test
    @DisplayName("Should remove product completely even if added multiple times")
    void shouldRemoveProductCompletelyEvenIfAddedMultipleTimes() {

        ShoppingCart cart = new ShoppingCart();
        Product milk = new Product(10);

        cart.addProduct(milk);
        cart.addProduct(milk);
        cart.addProduct(milk);

        cart.deleteProduct(milk);

        assertThat(cart.getCartTotalValue()).isEqualTo(0);
        assertThat(cart.getAllProducts()).isEmpty();
    }




}
