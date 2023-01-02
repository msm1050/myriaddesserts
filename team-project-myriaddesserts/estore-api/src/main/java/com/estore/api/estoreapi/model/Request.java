package com.estore.api.estoreapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Request {
	
	private static final Logger LOG = Logger.getLogger(Product.class.getName());

    // Package private for tests

    static final String STRING_FORMAT = "Product [id=%d, name=%s, quantity=%d, price=%f]";


    @JsonProperty("account") private int id;
    @JsonProperty("product") private int pid;
    
    public Request(@JsonProperty("account") int id, @JsonProperty("product") int pid) {
        this.id = id;
        this.pid = pid;

    }
    
    //getters
    public int getId() {return id;}
    public int getPid() {return pid;}

}
