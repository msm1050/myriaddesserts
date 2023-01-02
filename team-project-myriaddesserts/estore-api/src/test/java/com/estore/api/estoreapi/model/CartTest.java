package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


@Tag("Model-tier")
public class CartTest {
    @Test
    public void testCart() {
        // Setup
        int expected_id = 99;
        Vector < Integer > expected_productList = new Vector < Integer > ();

        for (int i = 10; i <= 15; i++)
        	expected_productList.add(i);

        // Invoke
        Cart cart = new Cart(expected_id,expected_productList);

        // Analyze
        assertEquals(expected_id,cart.getId());
        assertEquals(expected_productList,cart.getProducts());
    }
    
    @Test
    public void testID() {
        // Setup
        int id = 99;
        Vector < Integer > productList = new Vector < Integer > ();

        Cart cart = new Cart(id,productList);

        int expected_ID = 2;

        // Invoke
        cart.setId(expected_ID);

        // Analyze
        assertEquals(expected_ID,cart.getId());
    }
    
    @Test
    public void testProduct() {
        // Setup
        int id = 99;
        Vector < Integer > productList = new Vector < Integer > ();

        Cart cart = new Cart(id,productList);

        Vector < Integer > expected_productList = new Vector < Integer > ();


        // Invoke
        cart.setProducts(expected_productList);

        // Analyze
        assertEquals(expected_productList,cart.getProducts());
    }
    
    @Test
    public void testPId() {
        // Setup
        int aid = 99;
        int pid = 99;

        Request cart = new Request(aid,pid);

        // Analyze
        assertEquals(pid,cart.getPid());
    }
    
    
    
}
