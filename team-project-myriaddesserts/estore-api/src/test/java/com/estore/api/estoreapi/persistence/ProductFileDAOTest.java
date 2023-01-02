package com.estore.api.estoreapi.persistence;
import com.estore.api.estoreapi.persistence.ProductDAO;
import com.estore.api.estoreapi.persistence.ProductFileDAO;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.estore.api.estoreapi.model.Product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test the Product File DAO class
 * 
 * @author SWEN Faculty
 */
@Tag("Persistence-tier")
public class ProductFileDAOTest {
    ProductFileDAO ProductFileDAO;
    Product[] testProducts;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupProductFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testProducts = new Product[3];
        testProducts[0] = new Product(99,"Wi-Fire",10,10.00);
        testProducts[1] = new Product(100,"Galactic Agent",10,10.00);
        testProducts[2] = new Product(101,"Ice Gladiator",10,10.00);

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the Product array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),Product[].class))
                .thenReturn(testProducts);
        ProductFileDAO = new ProductFileDAO("doesnt_matter.txt",mockObjectMapper);
    }

    @Test
    public void testGetProducts() throws IOException {
       // Invoke
        Product[] Products = ProductFileDAO.getProducts();

        // Analyze
        assertEquals(Products.length,testProducts.length);
        for (int i = 0; i < testProducts.length;++i)
           assertEquals(Products[i],testProducts[i]);
    }

    
    @Test
    public void testGetProduct() throws IOException {
       // Invoke
        Product Product = ProductFileDAO.getProduct(99);

        // Analzye
        assertEquals(Product,testProducts[0]);        
    }

    @Test
    public void testGetProductNotFound() throws IOException {
       // Invoke
        Product Product = ProductFileDAO.getProduct(98);

        // Analyze
        assertEquals(Product,null);
    }

    
    @Test
    public void testUpdateProduct() throws IOException {
        // Setup
        Product Product = new Product(99,"Galactic Agent",10,10.00);

        // Invoke
        Product result = assertDoesNotThrow(() -> ProductFileDAO.updateProduct(Product),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Product actual = ProductFileDAO.getProduct(Product.getId());
        assertEquals(actual,Product);
    }

    
    @Test
    public void testUpdateProductNotFound() {
        // Setup
        Product Product = new Product(98,"Bolt",10,10.00);

        // Invoke
        Product result = assertDoesNotThrow(() -> ProductFileDAO.updateProduct(Product),
                                                "Unexpected exception thrown");

        // Analyze
        assertNull(result);
    }

    @Test
    public void testSaveException() throws IOException{
        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(any(File.class),any(Product[].class));

        Product Product = new Product(102,"Wi-Fire",10,10.00);

        assertThrows(IOException.class,
                        () -> ProductFileDAO.createProduct(Product),
                        "IOException not thrown");
    }

    @Test
    public void testConstructorException() throws IOException {
        // Setup
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        // We want to simulate with a Mock Object Mapper that an
        // exception was raised during JSON object deseerialization
        // into Java objects
        // When the Mock Object Mapper readValue method is called
        // from the ProductFileDAO load method, an IOException is
        // raised
        doThrow(new IOException())
            .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"),Product[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                        () -> new ProductFileDAO("doesnt_matter.txt",mockObjectMapper),
                        "IOException not thrown");
    }
    @Test
    public void testFindProducts() {
        // Invoke
        Product[] Products = ProductFileDAO.findProducts("la");

        // Analyze
        assertEquals(Products.length,2);
        assertEquals(Products[0],testProducts[1]);
        assertEquals(Products[1],testProducts[2]);
    }

    @Test
    public void testDeleteProduct() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> ProductFileDAO.deleteProduct(99),"Unexpected exception thrown");

        // Analzye
        assertEquals(result,true);
        // We check the internal tree map size against the length
        // of the test Products array - 1 (because of the delete)
        // Because Products attribute of ProductFileDAO is package private
        // we can access it directly
        assertEquals(ProductFileDAO.products.size(),testProducts.length-1);
    }

    @Test
    public void testCreateProduct() throws IOException {
        // Setup
        Product Product = new Product(102,"Wonder-Person",10,10.00);

        // Invoke
        Product result = assertDoesNotThrow(() -> ProductFileDAO.createProduct(Product),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Product actual = ProductFileDAO.getProduct(Product.getId());
        assertEquals(actual.getId(),Product.getId());
        assertEquals(actual.getName(),Product.getName());
    }

    @Test
    public void testDeleteProductNotFound() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> ProductFileDAO.deleteProduct(98),
                                                "Unexpected exception thrown");

        // Analyze
        assertEquals(result,false);
        //assertEquals(ProductFileDAO.Products.size(),testProducts.length);
    }
}
