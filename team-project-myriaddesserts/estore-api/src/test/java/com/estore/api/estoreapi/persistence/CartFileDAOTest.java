package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
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
import com.fasterxml.jackson.databind.ObjectMapper;

@Tag("Persistence-tier")
public class CartFileDAOTest {
	CartFileDAO CartFileDAO;
    Cart[] testCarts;
    ObjectMapper mockObjectMapper;
    
    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupProductFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        
        int id = 99;
        Vector < Integer > productList = new Vector < Integer > ();
        for (int i = 10; i <= 15; i++)
        	productList.add(i);
        
        testCarts = new Cart[1];
        testCarts[0] = new Cart(id, productList);

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the Product array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),Cart[].class))
                .thenReturn(testCarts);
        CartFileDAO = new CartFileDAO("doesnt_matter.txt",mockObjectMapper);
    }
    
    @Test
    public void testaddProduct() throws IOException {
        // Setup
        int aid = 99;
        int pid = 11;
        
        Request request = new Request(aid, pid);
        
        // Invoke
        Boolean result = assertDoesNotThrow(() -> CartFileDAO.addProduct(request),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        boolean actual = CartFileDAO.addProduct(request);
        assertEquals(actual,true);
    }
    
    @Test
    public void testNotFoundaddProduct() throws IOException {
        // Setup
        int aid = 21;
        int pid = 12;
        
        Request request = new Request(aid, pid);
        
        // Invoke
        Boolean result = assertDoesNotThrow(() -> CartFileDAO.addProduct(request),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        boolean actual = CartFileDAO.addProduct(request);
        assertEquals(actual,false);
    }
    
    @Test
    public void testRemoveProduct() throws IOException {
        // Setup
        int aid = 99;
        int pid = 11;
        
        Request request = new Request(aid, pid);
        
        // Invoke
        Boolean result = assertDoesNotThrow(() -> CartFileDAO.removeProduct(request),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        boolean actual = CartFileDAO.removeProduct(request);
        assertEquals(actual,true);
    }
    
    @Test
    public void testNotFoundRemoveProduct() throws IOException {
        // Setup
        int aid = 119;
        int pid = 1;
        
        Request request = new Request(aid, pid);
        
        // Invoke
        Boolean result = assertDoesNotThrow(() -> CartFileDAO.removeProduct(request),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        boolean actual = CartFileDAO.removeProduct(request);
        assertEquals(actual,false);
    }
    
    @Test
    public void testGetProducts() throws IOException {

        int id = 98;
        Vector < Integer > productList = new Vector < Integer > ();
        
        Cart cart = new Cart(id, productList);
        // Invoke
        Vector<Integer> result = assertDoesNotThrow(() -> CartFileDAO.getProducts(cart.getId()),
                                "Unexpected exception thrown");

        // Analyze
        Vector<Integer> actual = CartFileDAO.getProducts(cart.getId());
        assertEquals(actual,result);
    }
    


}
