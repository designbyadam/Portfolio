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
public class ListSuperViewModel {

    private List<Organization> allOrgs;
    private List<Super> allSupers;
    private List<Sighting> allSightings;

    /**
     * @return the allOrgs
     */
    public List<Organization> getAllOrgs() {
        return allOrgs;
    }

    /**
     * @param allOrgs the allOrgs to set
     */
    public void setAllOrgs(List<Organization> allOrgs) {
        this.allOrgs = allOrgs;
    }

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
