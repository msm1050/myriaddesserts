package com.estore.api.estoreapi.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.estore.api.estoreapi.model.*;
import com.estore.api.estoreapi.persistence.*;




@RestController
@RequestMapping("carts")
public class CartController {
    private static final Logger LOG = Logger.getLogger(CartController.class.getName());
    private CartDAO cartDao;

    public CartController(CartDAO cartDao) {
        this.cartDao = cartDao;
    }
    
    
    @GetMapping("/{id}")
    public ResponseEntity<Vector<Integer>> getProducts(@PathVariable int id) throws IOException {
        LOG.info("GET /cart/?id="+id);

        Vector<Integer> cart = cartDao.getProducts(id);
		return new ResponseEntity<Vector<Integer>>(cart,HttpStatus.OK);
    }
    
    @PostMapping("")
    public ResponseEntity<Cart>addProduct(@RequestBody Request request) throws IOException{
        LOG.info("POST /cart/" + request.getId());
        
        boolean result = cartDao.addProduct(request);
		if (result) {
		    return new ResponseEntity<>(HttpStatus.OK);
		}else {
		    return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		}   
    }
    
    @DeleteMapping("")
    public ResponseEntity<Cart>removeProduct(@RequestBody Request request) throws IOException{
        LOG.info("DELETE /cart/remove/" + request.getId());
        
        boolean result = cartDao.removeProduct(request);
		if (result) {
		    return new ResponseEntity<>(HttpStatus.OK);
		}else {
		    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    
    }

}
