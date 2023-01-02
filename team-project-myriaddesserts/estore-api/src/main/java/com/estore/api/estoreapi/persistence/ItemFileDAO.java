package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.Item;

/**
 * Implements the functionality for JSON file-based peristance for Items
 * 
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 * 
 * @author Ammar
 * @author Giselle
 * @author Avasyu
 * @author Mohammad
 * @author Sai
 */
@Component
public class ItemFileDAO implements ItemDAO {
    private static final Logger LOG = Logger.getLogger(ItemFileDAO.class.getName());
    Map<Integer,Item> items;   // Provides a local cache of the product objects
                                // so that we don't need to read from the file
                                // each time
    private ObjectMapper objectMapper;  // Provides conversion between Item
                                        // objects and JSON text format written
                                        // to the file
    private static int nextId;  // The next Id to assign to a new product
    private String filename;    // Filename to read from and write to

    /**
     * Creates a Item File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public ItemFileDAO(@Value("${items.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the items from the file
    }

    /**
     * Generates the next id for a new {@linkplain Item product}
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Generates an array of {@linkplain Item items} from the tree map
     * 
     * @return  The array of {@link Item items}, may be empty
     */
    private Item[] getItemsArray() {
        return getItemsArray(null);
    }

    /**
     * Generates an array of {@linkplain Item items} from the tree map for any
     * {@linkplain Item items} that contains the text specified by containsText
     * <br>
     * If containsText is null, the array contains all of the {@linkplain Item items}
     * in the tree map
     * 
     * @return  The array of {@link Item items}, may be empty
     */
    private Item[] getItemsArray(String containsText) { // if containsText == null, no filter
        ArrayList<Item> productArrayList = new ArrayList<>();

        for (Item product : items.values()) {
            if (containsText == null || product.getName().contains(containsText)) {
                productArrayList.add(product);
            }
        }

        Item[] productArray = new Item[productArrayList.size()];
        productArrayList.toArray(productArray);
        return productArray;
    }

    /**
     * Saves the {@linkplain Item items} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link Item items} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Item[] productArray = getItemsArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename),productArray);
        return true;
    }

    /**
     * Loads {@linkplain Item items} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        items = new TreeMap<>();
        nextId = 0;

        // Deserializes the JSON objects from the file into an array of items
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Item[] productArray = objectMapper.readValue(new File(filename),Item[].class);

        // Add each product to the tree map and keep track of the greatest id
        for (Item product : productArray) {
            items.put(product.getId(),product);
            if (product.getId() > nextId)
                nextId = product.getId();
        }
        // Make the next id one greater than the maximum from the file
        ++nextId;
        return true;
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Item[] getItems() throws IOException {
        synchronized(items) {
            return getItemsArray();
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Item[] findItems(String containsText) {
        synchronized(items) {
            return getItemsArray(containsText);
        }
    }

    /**
    ** {@inheritDoc}
     */
   @Override
	public Item getItem(int id) throws IOException {
		synchronized(items) {
            if (items.containsKey(id))
                return items.get(id);
            else
                return null;
        }
	}

    
    /**
    ** {@inheritDoc}
     */
    @Override
    public Item updateItem(Item product) throws IOException {
        synchronized(items) {
            if (items.containsKey(product.getId()) == false)
                return null;  // product does not exist

            items.put(product.getId(),product);
            save(); // may throw an IOException
            return product;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public boolean deleteItem(int id) throws IOException {
        synchronized(items) {
            if (items.containsKey(id)) {
                items.remove(id);
                return save();
            }
            else
                return false;
        }
    }
}
