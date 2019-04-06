/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroSightings.models;

import java.util.List;

/**
 *
 * @author afahrenkamp
 */
public class ListSightingViewModel {
    private List<Super> allSupers;
    private List<Location> allLocations;
    private List<Sighting> allSightings;

    /**
     * @return the allSupers
     */
    public List<Super> getAllSupers() {
        return allSupers;
    }

    /**
     * @param allSupers the allSupers to set
     */
    public void setAllSupers(List<Super> allSupers) {
        this.allSupers = allSupers;
    }


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


    /**
     * @return the allSightings
     */
    public List<Sighting> getAllSightings() {
        return allSightings;
    }

    /**
     * @param allSightings the allSightings to set
     */
    public void setAllSightings(List<Sighting> allSightings) {
        this.allSightings = allSightings;
    }
}
