package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Vector;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.estore.api.estoreapi.model.Cart;
import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.Request;
import com.estore.api.estoreapi.persistence.CartDAO;
import com.estore.api.estoreapi.persistence.ProductDAO;

@Tag("Controller-tier")
public class CartControllerTest {
    private CartController CartController;
    private CartDAO mockCartDAO;
    
    
    @BeforeEach
    public void setupProductController() {
    	mockCartDAO = mock(CartDAO.class);
    	CartController = new CartController(mockCartDAO);
    }
    
    
    @Test
    public void testGetProducts() throws IOException { // getProduct may throw IOException
        // Setup
        int id = 99;
        Vector < Integer > productList = new Vector < Integer > ();

        Cart cart = new Cart(id, productList);
        // When the same id is passed in, our mock Product DAO will return the Product
        // object
        when(mockCartDAO.getProducts(cart.getId())).thenReturn(null);

        // Invoke
        ResponseEntity<Vector<Integer>> response = CartController.getProducts(cart.getId());

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    
    @Test
    public void testAddProducts() throws IOException { // getProduct may throw IOException
        // Setup
        int aid = 99;
        int pid = 99;
        
        Request request = new Request(aid, pid);
        // When the same id is passed in, our mock Product DAO will return the Product
        // object
        when(mockCartDAO.addProduct(request)).thenReturn(true);

        // Invoke
        ResponseEntity<Cart> response = CartController.addProduct(request);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    
    @Test
    public void testNotFoundAddProducts() throws IOException { // getProduct may throw IOException
        // Setup
        int aid = 99;
        int pid = 99;
        
        Request request = new Request(aid, pid);
        // object
        when(mockCartDAO.addProduct(request)).thenReturn(false);

        // Invoke
        ResponseEntity<Cart> response = CartController.addProduct(request);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    
    
    @Test
    public void testRemoveProduct() throws IOException { // getProduct may throw IOException
        // Setup
        int aid = 99;
        int pid = 99;
        
        Request request = new Request(aid, pid);
        // object
        when(mockCartDAO.removeProduct(request)).thenReturn(true);

        // Invoke
        ResponseEntity<Cart> response = CartController.removeProduct(request);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    
    @Test
    public void testNotFoundRemoveProduct() throws IOException { // getProduct may throw IOException
        // Setup
        int aid = 99;
        int pid = 99;
        
        Request request = new Request(aid, pid);
        // object
        when(mockCartDAO.removeProduct(request)).thenReturn(false);

        // Invoke
        ResponseEntity<Cart> response = CartController.removeProduct(request);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}
