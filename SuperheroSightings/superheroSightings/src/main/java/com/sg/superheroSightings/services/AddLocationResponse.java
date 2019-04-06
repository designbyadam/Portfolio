/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroSightings.services;

import com.sg.superheroSightings.models.Location;

/**
 *
 * @author afahrenkamp
 */
public class AddLocationResponse extends Response {

    private Location toAdd;

    /**
     * @return the toAdd
     */
    public Location getToAdd() {
        return toAdd;
    }

    /**
     * @param toAdd the toAdd to set
     */
    public void setToAdd(Location toAdd) {
        this.toAdd = toAdd;
    }
}
