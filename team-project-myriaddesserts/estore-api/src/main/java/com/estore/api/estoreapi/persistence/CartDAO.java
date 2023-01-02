package com.estore.api.estoreapi.persistence;
import java.util.Vector;

import com.estore.api.estoreapi.model.*;

public interface CartDAO {
	
	
	boolean addProduct(Request request);
	boolean removeProduct(Request request);
	Vector<Integer> getProducts(int id);
	
		
	

}
