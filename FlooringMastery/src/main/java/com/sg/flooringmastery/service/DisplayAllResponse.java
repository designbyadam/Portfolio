/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dto.Order;
import java.util.List;

/**
 *
 * @author afahrenkamp
 */
public class DisplayAllResponse extends Response {
        List<Order> allOrders;
    
    public List<Order> getAllOrders() {
        return allOrders;
    }

    void setAllOrders(List<Order> allOrders) {
        this.allOrders = allOrders;
    }
}
