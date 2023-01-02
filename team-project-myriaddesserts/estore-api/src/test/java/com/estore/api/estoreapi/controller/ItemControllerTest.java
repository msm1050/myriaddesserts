package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.estore.api.estoreapi.persistence.ItemDAO;
import com.estore.api.estoreapi.persistence.ItemFileDAO;
import com.estore.api.estoreapi.model.Item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Test the Item Controller class
 * 
 * 
 */
@Tag("Controller-tier")
public class ItemControllerTest {
    private ItemController ItemController;
    private ItemDAO mockItemDAO;

    /**
     * Before each test, create a new ItemController object and inject
     * a mock Item DAO
     */
    @BeforeEach
    public void setupItemController() {
        mockItemDAO = mock(ItemDAO.class);
        ItemController = new ItemController(mockItemDAO);
    }


    @Test
    public void testGetItem() throws IOException { // getItem may throw IOException
        String[] ingredients ={"Sugar (Glucose)","Milk Powder", "Gelatin"};
        // Setup
        Item Item = new Item( 105, "Pretezel" , 1, 5,ingredients);
        // When the same id is passed in, our mock Item DAO will return the Item
        // object
        when(mockItemDAO.getItem(Item.getId())).thenReturn(Item);

        // Invoke
        ResponseEntity<Item> response = ItemController.getItem(Item.getId());

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Item, response.getBody());
    }

    @Test
    public void testGetItemNotFound() throws Exception { // createItem may throw IOException
        // Setup
        int ItemId = 99;
        // When the same id is passed in, our mock Item DAO will return null,
        // simulating
        // no Item found
        when(mockItemDAO.getItem(ItemId)).thenReturn(null);

        // Invoke
        ResponseEntity<Item> response = ItemController.getItem(ItemId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetItemHandleException() throws Exception { // createItem may throw IOException
        // Setup
        int ItemId = 99;
        // When getItem is called on the Mock Item DAO, throw an IOException
        doThrow(new IOException()).when(mockItemDAO).getItem(ItemId);

        // Invoke
        ResponseEntity<Item> response = ItemController.getItem(ItemId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }



    @Test
    public void testGetItems() throws IOException { // getItems may throw IOException
        // Setup
        String[] ingredients ={"Sugar (Glucose)","Milk Powder", "Gelatin"};
        Item[] Items = new Item[2];
        Items[0] = new Item(99, "Bolt",10,10.00, ingredients);
        Items[1] = new Item(100, "The Great Iguana",10,10.00, ingredients);
        // When getItems is called return the Items created above
        when(mockItemDAO.getItems()).thenReturn(Items);

        // Invoke
        ResponseEntity<Item[]> response = ItemController.getItems();

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Items, response.getBody());
    }
    
    @Test
    public void testGetItemsFailed() throws IOException { // updateItem may throw IOException
        // Setup
        String[] ingredients ={"Sugar (Glucose)","Milk Powder", "Gelatin"};
        Item[] Items = new Item[2];
        Items[0] = new Item(99, "Bolt",10,10.00, ingredients);
        Items[1] = new Item(100, "The Great Iguana",10,10.00, ingredients);
        // when updateItem is called, return true simulating successful
        // update and save
        when(mockItemDAO.getItems()).thenReturn(null);

        // Invoke
        ResponseEntity<Item[]> response = ItemController.getItems();

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
   

    @Test
    public void testGetItemsHandleException() throws IOException { // getItems may throw IOException
        // Setup
        // When getItems is called on the Mock Item DAO, throw an IOException
        doThrow(new IOException()).when(mockItemDAO).getItems();

        // Invoke
        ResponseEntity<Item[]> response = ItemController.getItems();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testSearchItems() throws IOException { // findItems may throw IOException
        // Setup
        String[] ingredients ={"Sugar (Glucose)","Milk Powder", "Gelatin"};
        String searchString = "la";
        Item[] Items = new Item[2];
        Items[0] = new Item(99, "Galactic Agent",10,10.00, ingredients);
        Items[1] = new Item(100, "Ice Gladiator",10,10.00, ingredients);
        // When findItems is called with the search string, return the two
        /// Items above
        when(mockItemDAO.findItems(searchString)).thenReturn(Items);

        // Invoke
        ResponseEntity<Item[]> response = ItemController.searchItems(searchString);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Items, response.getBody());
    }

    @Test
    public void testSearchItemsHandleException() throws IOException { // findItems may throw IOException
        // Setup
        String searchString = "an";
        // When createItem is called on the Mock Item DAO, throw an IOException
        doThrow(new IOException()).when(mockItemDAO).findItems(searchString);

        // Invoke
        ResponseEntity<Item[]> response = ItemController.searchItems(searchString);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
    

    @Test
    public void testSearchItemNotFound() throws IOException { // searchItem may throw IOException
        // Setup
        String searchString = "an";
        // when deleteItem is called return false, simulating failed deletion
        when(mockItemDAO.findItems(searchString)).thenReturn(null);

        // Invoke
        ResponseEntity<Item[]> response = ItemController.searchItems(searchString);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteItem() throws IOException { // deleteItem may throw IOException
        // Setup
        int ItemId = 99;
        // when deleteItem is called return true, simulating successful deletion
        when(mockItemDAO.deleteItem(ItemId)).thenReturn(true);

        // Invoke
        ResponseEntity<Item> response = ItemController.deleteItem(ItemId);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteItemNotFound() throws IOException { // deleteItem may throw IOException
        // Setup
        int ItemId = 99;
        // when deleteItem is called return false, simulating failed deletion
        when(mockItemDAO.deleteItem(ItemId)).thenReturn(false);

        // Invoke
        ResponseEntity<Item> response = ItemController.deleteItem(ItemId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteItemHandleException() throws IOException { // deleteItem may throw IOException
        // Setup
        int ItemId = 99;
        // When deleteItem is called on the Mock Item DAO, throw an IOException
        doThrow(new IOException()).when(mockItemDAO).deleteItem(ItemId);

        // Invoke
        ResponseEntity<Item> response = ItemController.deleteItem(ItemId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
}
