package com.estore.api.estoreapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.estore.api.estoreapi.persistence.ItemDAO;
import com.estore.api.estoreapi.persistence.ItemFileDAO;
import com.estore.api.estoreapi.model.Item;

/**
 * Handles the REST API requests for the Item resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST API
 * method handler to the Spring framework
 * 
 * @author Ammar
 * @author Giselle
 * @author Avasyu
 * @author Mohammad
 * @author Sai
 */

@RestController
@RequestMapping("items")
public class ItemController {
    private static final Logger LOG = Logger.getLogger(ItemController.class.getName());
    private ItemDAO productDao;

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param productDao The {@link ItemDAO Item Data Access Object} to perform CRUD operations
     * <br>
     * This dependency is injected by the Spring Framework
     */
    public ItemController(ItemDAO productDao) {
        this.productDao = productDao;
    }

    /**
     * Responds to the GET request for a {@linkplain Item product} for the given id
     * 
     * @param id The id used to locate the {@link Item product}
     * 
     * @return ResponseEntity with {@link Item product} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<Item> getItem(@PathVariable int id) {
        LOG.info("GET /Items/" + id);
        try {
            Item product = productDao.getItem(id);
            if (product != null)
                return new ResponseEntity<Item>(product,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Item Items}
     * 
     * @return ResponseEntity with array of {@link Item product} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<Item[]> getItems() {
        LOG.info("GET /Items");
        try {
            Item[] ProdArray = productDao.getItems();
            if (ProdArray != null)
                return new ResponseEntity<Item[]>(ProdArray,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Item Items} whose name contains
     * the text in name
     * 
     * @param name The name parameter which contains the text used to find the {@link Item Items}
     * 
     * @return ResponseEntity with array of {@link Item product} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * <p>
     * Example: Find all Items that contain the text "ma"
     * GET http://localhost:8080/Items/?name=ma
     */
    @GetMapping("/")
    public ResponseEntity<Item[]> searchItems(@RequestParam String name) {
        LOG.info("GET /Items/?name="+name);
        try {
        	Item[] searchItem = productDao.findItems(name);
        	
        	if (searchItem != null)
        		return new ResponseEntity<Item[]>(searchItem,HttpStatus.OK); 
        	else
        		return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
        }
        
        catch(IOException e) { 
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    

    /**
     * Deletes a {@linkplain Item product} with the given id
     * 
     * @param id The id of the {@link Item product} to deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Item> deleteItem(@PathVariable int id) {
        LOG.info("DELETE /Items/" + id);
        try {
            Boolean product1 = productDao.deleteItem(id);
            if (product1 != false)
                return new ResponseEntity<>(HttpStatus.OK);
            else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); 

        }
        catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }
}
