/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroSightings.services;

import com.sg.superheroSightings.models.Sighting;

/**
 *
 * @author afahrenkamp
 */
public class GetSightingResponse extends Response {
    private Sighting toDisplay;

    /**
     * @return the toDisplay
     */
    public Sighting getToDisplay() {
        return toDisplay;
    }

    /**
     * @param toDisplay the toDisplay to set
     */
    public void setToDisplay(Sighting toDisplay) {
        this.toDisplay = toDisplay;
    }
}
