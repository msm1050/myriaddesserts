package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the Item class
 * 
 * @author SWEN Faculty
 */
@Tag("Model-tier")
public class ItemTest {
    @Test
    public void testCtor() {
        // Setup
        int expected_id = 99;
        String expected_name = "Wi-Fire";
        int expected_quantity= 10;
        double expected_price= 10.00;
        String[] expected_ingredients= {"Sugar","Coco"};


        // Invoke
        Item Item = new Item(expected_id,expected_name,expected_quantity, expected_price, expected_ingredients);

        // Analyze
        assertEquals(expected_id,Item.getId());
        assertEquals(expected_name,Item.getName());
        assertEquals(expected_quantity,Item.getQuantity());
        assertEquals(expected_price,Item.getPrice());
    }
}
