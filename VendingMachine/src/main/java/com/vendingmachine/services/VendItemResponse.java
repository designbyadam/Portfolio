/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vendingmachine.services;

import com.vendingmachine.dtos.Change;
import com.vendingmachine.dtos.Item;

/**
 *
 * @author afahrenkamp
 */
public class VendItemResponse extends Response {

    private Item vendedItem;
    private Change change;

    /**
     * @return the vendedItem
     */
    public Item getVendedItem() {
        return vendedItem;
    }

    /**
     * @param vendedItem the vendedItem to set
     */
    public void setVendedItem(Item vendedItem) {
        this.vendedItem = vendedItem;
    }

    /**
     * @return the change
     */
    public Change getChange() {
        return change;
    }

    /**
     * @param change the change to set
     */
    public void setChange(Change change) {
        this.change = change;
    }
    
}
