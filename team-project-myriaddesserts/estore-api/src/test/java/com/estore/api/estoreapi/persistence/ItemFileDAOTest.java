package com.estore.api.estoreapi.persistence;
import com.estore.api.estoreapi.persistence.ItemDAO;
import com.estore.api.estoreapi.persistence.ItemFileDAO;
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
import com.estore.api.estoreapi.model.Item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test the Item File DAO class
 * 
 * 
 */
@Tag("Persistence-tier")
public class ItemFileDAOTest {
    ItemFileDAO ItemFileDAO;
    Item[] testItems;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupItemFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testItems = new Item[3];
        String[] ingredients ={"Sugar (Glucose)","Milk Powder", "Gelatin"};
        testItems[0] = new Item(99,"Wi-Fire",10,10.00,ingredients);
        testItems[1] = new Item(100,"Galactic Agent",10,10.00,ingredients);
        testItems[2] = new Item(101,"Ice Gladiator",10,10.00,ingredients);

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the Item array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),Item[].class))
                .thenReturn(testItems);
        ItemFileDAO = new ItemFileDAO("doesnt_matter.txt",mockObjectMapper);
    }

    @Test
    public void testGetItems() throws IOException {
       // Invoke
        Item[] Items = ItemFileDAO.getItems();

        // Analyze
        assertEquals(Items.length,testItems.length);
        for (int i = 0; i < testItems.length;++i)
           assertEquals(Items[i],testItems[i]);
    }

    
    @Test
    public void testGetItem() throws IOException {
       // Invoke
        Item Item = ItemFileDAO.getItem(99);

        // Analzye
        assertEquals(Item,testItems[0]);        
    }

    @Test
    public void testGetItemNotFound() throws IOException {
       // Invoke
        Item Item = ItemFileDAO.getItem(98);

        // Analyze
        assertEquals(Item,null);
    }

    
    @Test
    public void testUpdateItem() throws IOException {
        // Setup
        String[] ingredients ={"Sugar (Glucose)","Milk Powder", "Gelatin"};
        Item Item = new Item(99,"Galactic Agent",10,10.00, ingredients);

        // Invoke
        Item result = assertDoesNotThrow(() -> ItemFileDAO.updateItem(Item),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Item actual = ItemFileDAO.getItem(Item.getId());
        assertEquals(actual,Item);
    }

    
    @Test
    public void testUpdateItemNotFound() {
        // Setup
        String[] ingredients ={"Sugar (Glucose)","Milk Powder", "Gelatin"};
        Item Item = new Item(98,"Bolt",10,10.00, ingredients);

        // Invoke
        Item result = assertDoesNotThrow(() -> ItemFileDAO.updateItem(Item),
                                                "Unexpected exception thrown");

        // Analyze
        assertNull(result);
    }


    @Test
    public void testConstructorException() throws IOException {
        // Setup
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        // We want to simulate with a Mock Object Mapper that an
        // exception was raised during JSON object deseerialization
        // into Java objects
        // When the Mock Object Mapper readValue method is called
        // from the ItemFileDAO load method, an IOException is
        // raised
        doThrow(new IOException())
            .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"),Item[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                        () -> new ItemFileDAO("doesnt_matter.txt",mockObjectMapper),
                        "IOException not thrown");
    }
    @Test
    public void testFindItems() {
        // Invoke
        Item[] Items = ItemFileDAO.findItems("la");

        // Analyze
        assertEquals(Items.length,2);
        assertEquals(Items[0],testItems[1]);
        assertEquals(Items[1],testItems[2]);
    }

    @Test
    public void testDeleteItem() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> ItemFileDAO.deleteItem(99),"Unexpected exception thrown");

        // Analzye
        assertEquals(result,true);
        // We check the internal tree map size against the length
        // of the test Items array - 1 (because of the delete)
        // Because Items attribute of ItemFileDAO is package private
        // we can access it directly
        assertEquals(ItemFileDAO.items.size(),testItems.length-1);
    }


    @Test
    public void testDeleteItemNotFound() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> ItemFileDAO.deleteItem(98),
                                                "Unexpected exception thrown");

        // Analyze
        assertEquals(result,false);
        //assertEquals(ItemFileDAO.Items.size(),testItems.length);
    }
}
