
package com.estore.api.estoreapi.model;
import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;
import com.estore.api.estoreapi.model.*;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Cart {
    private static final Logger LOG = Logger.getLogger(Cart.class.getName());
    static final String STRING_FORMAT = "Cart [id=%d, cart=%s]";

    @JsonProperty("accountId") private int id;
    @JsonProperty("products") private Vector<Integer> products;
    
    public Cart(@JsonProperty("accountId") int id, @JsonProperty("products") Vector<Integer> products) {
        this.id = id;
        this.products = products;
        
    }
    
    
    /**
     * sets the id of the account
     * @param id of account
     */
    public void setId(int id) {this.id = id;}

    /**
     * Retrieves the id of the account
     * @return id of the account
     */
    public int getId() {return id;}

    /**
     * Sets the list of products in the shopping cart for an account - necessary for JSON object to Java object deserialization
     * @param products The list of products
     */
    public void setProducts(Vector<Integer> products) {this.products = products;}
    
    /**
     * Retrieves the list of products in the shopping cart for an account
     * @return The list of products
     */
    public Vector<Integer> getProducts() {return products;}
    

}
