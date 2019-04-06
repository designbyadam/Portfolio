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
public class AddSightingResponse extends Response {
    private Sighting toAdd;

    /**
     * @return the toAdd
     */
    public Sighting getToAdd() {
        return toAdd;
    }

    /**
     * @param toAdd the toAdd to set
     */
    public void setToAdd(Sighting toAdd) {
        this.toAdd = toAdd;
    }
}
