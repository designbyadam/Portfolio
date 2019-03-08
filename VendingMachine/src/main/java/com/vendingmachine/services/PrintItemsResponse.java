/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vendingmachine.services;

import com.vendingmachine.dtos.Item;
import java.util.List;

/**
 *
 * @author afahrenkamp
 */
public class PrintItemsResponse extends Response {
    private List<Item> allItems;

    /**
     * @return the allItems
     */
    public List<Item> getAllItems() {
        return allItems;
    }

    /**
     * @param allItems the allItems to set
     */
    public void setAllItems(List<Item> allItems) {
        this.allItems = allItems;
    }
    
    
}
