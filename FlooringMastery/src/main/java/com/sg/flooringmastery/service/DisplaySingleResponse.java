/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dto.Order;

/**
 *
 * @author afahrenkamp
 */
public class DisplaySingleResponse extends Response {
    
    
    private Order orderToDisplay;

    /**
     * @return the orderToDisplay
     */
    public Order getOrderToDisplay() {
        return orderToDisplay;
    }

    /**
     * @param orderToDisplay the orderToDisplay to set
     */
    public void setOrderToDisplay(Order orderToDisplay) {
        this.orderToDisplay = orderToDisplay;
    }
}
