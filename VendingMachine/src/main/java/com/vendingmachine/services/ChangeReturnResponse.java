/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vendingmachine.services;

import com.vendingmachine.dtos.Change;
import com.vendingmachine.dtos.VendingMachine;

/**
 *
 * @author afahrenkamp
 */
public class ChangeReturnResponse extends Response {
    
    private Change change;

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
