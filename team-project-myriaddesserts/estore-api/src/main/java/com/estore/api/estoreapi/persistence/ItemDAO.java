package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import com.estore.api.estoreapi.model.Item;

/**
 * Defines the interface for Item object persistence
 * 
 * @author Ammar
 * @author Giselle
 * @author Avasyu
 * @author Mohammad
 * @author Sai
 */
public interface ItemDAO {
    /**
     * Retrieves all {@linkplain Item items}
     * 
     * @return An array of {@link Item product} objects, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Item[] getItems() throws IOException;

    /**
     * Finds all {@linkplain Item items} whose name contains the given text
     * 
     * @param containsText The text to match against
     * 
     * @return An array of {@link Item items} whose nemes contains the given text, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Item[] findItems(String containsText) throws IOException;

    /**
     * Retrieves a {@linkplain Item product} with the given id
     * 
     * @param id The id of the {@link Item product} to get
     * 
     * @return a {@link Item product} object with the matching id
     * <br>
     * null if no {@link Item product} with a matching id is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    Item getItem(int id) throws IOException;


    /**
     * Updates and saves a {@linkplain Item product}
     * 
     * @param {@link Item product} object to be updated and saved
     * 
     * @return updated {@link Item product} if successful, null if
     * {@link Item product} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Item updateItem(Item product) throws IOException;

    /**
     * Deletes a {@linkplain Item product} with the given id
     * 
     * @param id The id of the {@link Item product}
     * 
     * @return true if the {@link Item product} was deleted
     * <br>
     * false if product with the given id does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteItem(int id) throws IOException;
}
