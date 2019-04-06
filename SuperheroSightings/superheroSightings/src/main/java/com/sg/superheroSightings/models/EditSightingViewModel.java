/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroSightings.models;

import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author afahrenkamp
 */
public class EditSightingViewModel {
    private List<Super> allSupers;
    private int[] selectedSupIds;
    private List<Location> allLocations;
    private int selectedLocationId;
    private Sighting sighting;


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
     * @return the selectedSupIds
     */
    public int[] getSelectedSupIds() {
        return selectedSupIds;
    }

    /**
     * @param selectedSupIds the selectedSupIds to set
     */
    public void setSelectedSupIds(int[] selectedSupIds) {
        this.selectedSupIds = selectedSupIds;
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
     * @return the selectedLocationId
     */
    public int getSelectedLocationId() {
        return selectedLocationId;
    }

    /**
     * @param selectedLocationId the selectedLocationId to set
     */
    public void setSelectedLocationId(int selectedLocationId) {
        this.selectedLocationId = selectedLocationId;
    }

    /**
     * @return the sighting
     */
    public Sighting getSighting() {
        return sighting;
    }

    /**
     * @param sighting the sighting to set
     */
    public void setSighting(Sighting sighting) {
        this.sighting = sighting;
    }
}
