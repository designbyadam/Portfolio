/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroSightings.services;

import com.sg.superheroSightings.models.Location;
import java.util.List;

/**
 *
 * @author afahrenkamp
 */
public class ListLocationResponse extends Response {

    private List<Location> allLocations;

    /**
     * @return the allLocations
     */
    public List<Location> getAllLocations() {
        return allLocations;
    }

    /**
     * @param allLocations the allLocations to set
     */
    public void setAllLocations(List<Location> allLocations) {
        this.allLocations = allLocations;
    }
}
