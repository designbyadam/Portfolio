/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroSightings.services;

import com.sg.superheroSightings.models.Sighting;
import java.util.List;

/**
 *
 * @author afahrenkamp
 */
public class ListSightingResponse extends Response {
    private List<Sighting> allSightings;

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
