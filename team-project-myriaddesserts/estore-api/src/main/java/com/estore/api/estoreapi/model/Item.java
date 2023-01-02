package com.estore.api.estoreapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.estore.api.estoreapi.model.Product;;
/**
 * Represents a Item entity
 * 
 * @author Ammar
 * @author Giselle
 * @author Avasyu
 * @author Mohammad
 * @author Sai
 */
public class Item {
    private static final Logger LOG = Logger.getLogger(Item.class.getName());

    // Package private for tests

    static final String STRING_FORMAT = "Item [id=%d, name=%s, quantity=%d, price=%f, ingredients=%s]";


    @JsonProperty("id") private int id;
    @JsonProperty("name") private String name;
    @JsonProperty("quantity") private int quantity;
    @JsonProperty("price") private double price;
    @JsonProperty("ingredients") private String[] ingredients;

    /**
     * Create a item with the given id and name
     * @param id The id of the item
     * @param name The name of the item
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public Item(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("quantity") int quantity, @JsonProperty("price") double price, @JsonProperty("ingredients") String[] ingredients) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;    
        this.price = price;
        this.ingredients= ingredients;
    }


    public void setingredients(String[] ingredients){this.ingredients=ingredients;}
    /**
     * Retrieves the id of the item
     * @return The id of the item
     */
    public int getId() {return id;}
    

    /**
     * Sets the name of the item - necessary for JSON object to Java object deserialization
     * @param name The name of the item
     */
    public void setName(String name) {this.name = name;}

    /**
     * Retrieves the name of the item
     * @return The name of the item
     */
    public String getName() {return name;}

    /**
     * Retrieves the quantity of the item
     * @return The quantity of the item
     */
    public int getQuantity() {return quantity;}

    /**
     * Sets the quantity of the item - necessary for JSON object to Java object deserialization
     * @param quantity The quantity of the item
     */
    public void setQuantity(Integer quantity) {this.quantity = quantity;}


    /**
     * Retrieves the price of the item
     * @return The price of the item
     */
    public double getPrice() {return price;}

    /**
     * Sets the price of the item - necessary for JSON object to Java object deserialization
     * @param price The price of the item
     */
    public void setPrice(Double price) {this.price = price;}    
    
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,id,name, quantity,price);
    }
}