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
public class GetLocationResponse extends Response {
     private Location toDisplay;

    /**
     * @return the toDisplay
     */
    public Location getToDisplay() {
        return toDisplay;
    }

    /**
     * @param toDisplay the toDisplay to set
     */
    public void setToDisplay(Location toDisplay) {
        this.toDisplay = toDisplay;
    }
}
