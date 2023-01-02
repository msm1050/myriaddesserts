package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.estore.api.estoreapi.persistence.ProductDAO;
import com.estore.api.estoreapi.persistence.ProductFileDAO;
import com.estore.api.estoreapi.model.Product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Test the Product Controller class
 * 
 * @author SWEN Faculty
 */
@Tag("Controller-tier")
public class ProductControllerTest {
    private ProductController ProductController;
    private ProductDAO mockProductDAO;

    /**
     * Before each test, create a new ProductController object and inject
     * a mock Product DAO
     */
    @BeforeEach
    public void setupProductController() {
        mockProductDAO = mock(ProductDAO.class);
        ProductController = new ProductController(mockProductDAO);
    }


    @Test
    public void testGetProduct() throws IOException { // getProduct may throw IOException
        // Setup
        Product Product = new Product(99, "Galactic Agent", 10, 10.00);
        // When the same id is passed in, our mock Product DAO will return the Product
        // object
        when(mockProductDAO.getProduct(Product.getId())).thenReturn(Product);

        // Invoke
        ResponseEntity<Product> response = ProductController.getProduct(Product.getId());

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Product, response.getBody());
    }

    @Test
    public void testGetProductNotFound() throws Exception { // createProduct may throw IOException
        // Setup
        int ProductId = 99;
        // When the same id is passed in, our mock Product DAO will return null,
        // simulating
        // no Product found
        when(mockProductDAO.getProduct(ProductId)).thenReturn(null);

        // Invoke
        ResponseEntity<Product> response = ProductController.getProduct(ProductId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetProductHandleException() throws Exception { // createProduct may throw IOException
        // Setup
        int ProductId = 99;
        // When getProduct is called on the Mock Product DAO, throw an IOException
        doThrow(new IOException()).when(mockProductDAO).getProduct(ProductId);

        // Invoke
        ResponseEntity<Product> response = ProductController.getProduct(ProductId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    //@Test
    //public void testCreateProduct() throws IOException {  // createHero may throw IOException
    //    // Setup
    //    Product product = new Product(34,"Wi-Fire",0,0.00);
    //    // when createHero is called, return true simulating successful
    //    // creation and save
    //    when(mockProductDAO.createProduct(product)).thenReturn(product);

        // Invoke
    //    ResponseEntity<Product> response = ProductController.createProduct(product);

        // Analyze
    //    assertEquals(HttpStatus.CREATED,response.getStatusCode());
    //    assertEquals(product,response.getBody());
    //}
    
    @Test
    public void testUpdateProduct() throws IOException { // updateProduct may throw IOException
        // Setup
        Product Product = new Product(99, "Wi-Fire",10,10.00);
        // when updateProduct is called, return true simulating successful
        // update and save
        when(mockProductDAO.updateProduct(Product)).thenReturn(Product);
        ResponseEntity<Product> response = ProductController.updateProduct(Product);
        Product.setName("Bolt");
        Product.setPrice(12.00);
        Product.setQuantity(11);

        // Invoke
        response = ProductController.updateProduct(Product);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Product, response.getBody());
    }

    @Test
    public void testUpdateProductFailed() throws IOException { // updateProduct may throw IOException
        // Setup
        Product Product = new Product(99, "Galactic Agent",10,10.00);
        // when updateProduct is called, return true simulating successful
        // update and save
        when(mockProductDAO.updateProduct(Product)).thenReturn(null);

        // Invoke
        ResponseEntity<Product> response = ProductController.updateProduct(Product);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateProductHandleException() throws IOException { // updateProduct may throw IOException
        // Setup
        Product Product = new Product(99,"Galactic Agent",10,10.00);
        // When updateProduct is called on the Mock Product DAO, throw an IOException
        doThrow(new IOException()).when(mockProductDAO).updateProduct(Product);

        // Invoke
        ResponseEntity<Product> response = ProductController.updateProduct(Product);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testGetProducts() throws IOException { // getProducts may throw IOException
        // Setup
        Product[] Products = new Product[2];
        Products[0] = new Product(99, "Bolt",10,10.00);
        Products[1] = new Product(100, "The Great Iguana",10,10.00);
        // When getProducts is called return the Products created above
        when(mockProductDAO.getProducts()).thenReturn(Products);

        // Invoke
        ResponseEntity<Product[]> response = ProductController.getProducts();

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Products, response.getBody());
    }
    
    @Test
    public void testGetProductsFailed() throws IOException { // updateProduct may throw IOException
        // Setup
        Product[] Products = new Product[2];
        Products[0] = new Product(99, "Bolt",10,10.00);
        Products[1] = new Product(100, "The Great Iguana",10,10.00);
        // when updateProduct is called, return true simulating successful
        // update and save
        when(mockProductDAO.getProducts()).thenReturn(null);

        // Invoke
        ResponseEntity<Product[]> response = ProductController.getProducts();

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
   

    @Test
    public void testGetProductsHandleException() throws IOException { // getProducts may throw IOException
        // Setup
        // When getProducts is called on the Mock Product DAO, throw an IOException
        doThrow(new IOException()).when(mockProductDAO).getProducts();

        // Invoke
        ResponseEntity<Product[]> response = ProductController.getProducts();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testSearchProducts() throws IOException { // findProducts may throw IOException
        // Setup
        String searchString = "la";
        Product[] Products = new Product[2];
        Products[0] = new Product(99, "Galactic Agent",10,10.00);
        Products[1] = new Product(100, "Ice Gladiator",10,10.00);
        // When findProducts is called with the search string, return the two
        /// Products above
        when(mockProductDAO.findProducts(searchString)).thenReturn(Products);

        // Invoke
        ResponseEntity<Product[]> response = ProductController.searchProducts(searchString);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Products, response.getBody());
    }

    @Test
    public void testSearchProductsHandleException() throws IOException { // findProducts may throw IOException
        // Setup
        String searchString = "an";
        // When createProduct is called on the Mock Product DAO, throw an IOException
        doThrow(new IOException()).when(mockProductDAO).findProducts(searchString);

        // Invoke
        ResponseEntity<Product[]> response = ProductController.searchProducts(searchString);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
    

    @Test
    public void testSearchProductNotFound() throws IOException { // searchProduct may throw IOException
        // Setup
        String searchString = "an";
        // when deleteProduct is called return false, simulating failed deletion
        when(mockProductDAO.findProducts(searchString)).thenReturn(null);

        // Invoke
        ResponseEntity<Product[]> response = ProductController.searchProducts(searchString);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteProduct() throws IOException { // deleteProduct may throw IOException
        // Setup
        int ProductId = 99;
        // when deleteProduct is called return true, simulating successful deletion
        when(mockProductDAO.deleteProduct(ProductId)).thenReturn(true);

        // Invoke
        ResponseEntity<Product> response = ProductController.deleteProduct(ProductId);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteProductNotFound() throws IOException { // deleteProduct may throw IOException
        // Setup
        int ProductId = 99;
        // when deleteProduct is called return false, simulating failed deletion
        when(mockProductDAO.deleteProduct(ProductId)).thenReturn(false);

        // Invoke
        ResponseEntity<Product> response = ProductController.deleteProduct(ProductId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteProductHandleException() throws IOException { // deleteProduct may throw IOException
        // Setup
        int ProductId = 99;
        // When deleteProduct is called on the Mock Product DAO, throw an IOException
        doThrow(new IOException()).when(mockProductDAO).deleteProduct(ProductId);

        // Invoke
        ResponseEntity<Product> response = ProductController.deleteProduct(ProductId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
}
