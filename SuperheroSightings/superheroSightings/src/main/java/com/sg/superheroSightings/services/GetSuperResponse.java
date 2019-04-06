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
public class GetSuperResponse extends Response {
    private Super toDisplay;

    /**
     * @return the toDisplay
     */
    public Super getToDisplay() {
        return toDisplay;
    }

    /**
     * @param toDisplay the toDisplay to set
     */
    public void setToDisplay(Super toDisplay) {
        this.toDisplay = toDisplay;
    }
}
