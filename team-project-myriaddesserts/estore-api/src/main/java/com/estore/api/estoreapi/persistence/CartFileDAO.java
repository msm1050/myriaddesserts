package com.estore.api.estoreapi.persistence;

import java.util.logging.Logger;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.Request;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.estore.api.estoreapi.model.Cart;

@Component
public class CartFileDAO implements CartDAO {
	
    private static final Logger LOG = Logger.getLogger(CartFileDAO.class.getName());
    Map<Integer, Cart> carts;
    private ObjectMapper objectMapper;  
    private String filename;
    
    public CartFileDAO(@Value("${customerCarts.file}") String filename,ObjectMapper objectMapper) {
        this.filename = filename;
        this.objectMapper = objectMapper;
        try {
			load();
		} catch (IOException e) {
			e.printStackTrace();
		}  
    }
    
    
    private boolean load() throws IOException {
        carts = new TreeMap<>();
        Cart[] cartArray = objectMapper.readValue(new File(filename),Cart[].class);
        for (Cart cart : cartArray) {
            carts.put(cart.getId(),cart);
        }
        return false;
    }    
    
    private boolean save(int accountID) throws IOException {
        Cart[] cartArray = getCartArray(0);

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename),cartArray);
        return true;
    }
    
    private Cart[] getCartArray(int accountID) { // if containsID == null, no filter
        ArrayList<Cart> cartArrayList = new ArrayList<>();

        for (Cart cart : carts.values()) {
            if (accountID == 0 || cart.getId() == accountID) {
                cartArrayList.add(cart);
            }
        }

        Cart[] cartArray = new Cart[cartArrayList.size()];
        cartArrayList.toArray(cartArray);
        return cartArray;
    }

	
	@Override
	public boolean addProduct(Request request) {
		synchronized(carts) {
			int id = request.getId();
			int pid = request.getPid();
			
            if (carts.containsKey(id)) {
            	
            	Vector<Integer> cart = carts.get(id).getProducts(); // get the vector of products
            	cart.add(pid); // add the new product to cart
            	carts.get(id).setProducts(cart); // set the new cart with the addded product id
     
                try {
					save(id);
				} catch (IOException e) {
					e.printStackTrace();
				}
                return true;
                  
            }else {
            	return false;
            }
        }
	}

	@Override
	public boolean removeProduct(Request request) {
		synchronized(carts) {
			
			int id = request.getId();
			Integer pid = request.getPid(); // has to be a integer cause .remove will use it as index if you use primitve type
			
            if (carts.containsKey(id)) {
            	
            	Vector<Integer> cart = carts.get(id).getProducts(); // get the vector of products
            	cart.remove(pid); // remove the product from cart
            	carts.get(id).setProducts(cart); // set the new cart with removed product 
     
                try {
					save(id);
				} catch (IOException e) {
					e.printStackTrace();
				}
                return true;
                  
            }else {
            	return false;
            }
        }
	}

	@Override
	public Vector<Integer> getProducts(int id) {
		synchronized(carts) {
            Vector<Integer> cartVector = new Vector<>();
    
            for (Cart cart : carts.values()) {
                if (id == 0 || cart.getId() == id) {
                    cartVector.addAll(cart.getProducts());
                }
            }
    
            return cartVector;
        }
    }

}
