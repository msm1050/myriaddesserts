package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the Product class
 * 
 * @author SWEN Faculty
 */
@Tag("Model-tier")
public class ProductTest {
    @Test
    public void testCtor() {
        // Setup
        int expected_id = 99;
        String expected_name = "Wi-Fire";
        int expected_quantity= 10;
        double expected_price= 10.00;

        // Invoke
        Product Product = new Product(expected_id,expected_name,expected_quantity, expected_price);

        // Analyze
        assertEquals(expected_id,Product.getId());
        assertEquals(expected_name,Product.getName());
        assertEquals(expected_quantity,Product.getQuantity());
        assertEquals(expected_price,Product.getPrice());
    }
}