/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroSightings.services;

import com.sg.superheroSightings.models.Super;

/**
 *
 * @author afahrenkamp
 */
public class AddSuperResponse extends Response {

    private Super toAdd;

    /**
     * @return the toAdd
     */
    public Super getToAdd() {
        return toAdd;
    }

    /**
     * @param toAdd the toAdd to set
     */
    public void setToAdd(Super toAdd) {
        this.toAdd = toAdd;
    }
}
