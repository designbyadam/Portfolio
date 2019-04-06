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
public class EditSuperViewModel {

    private List<Organization> allOrgs;
    private int[] selectedOrgIds;
    private int[] selectedSightIds;
    private List<Sighting> allSightings;
    private Super sup;

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
     * @return the selectedOrgIds
     */
    public int[] getSelectedOrgIds() {
        return selectedOrgIds;
    }

    /**
     * @param selectedOrgIds the selectedOrgIds to set
     */
    public void setSelectedOrgIds(int[] selectedOrgIds) {
        this.selectedOrgIds = selectedOrgIds;
    }

    /**
     * @return the selectedSightIds
     */
    public int[] getSelectedSightIds() {
        return selectedSightIds;
    }

    /**
     * @param selectedSightIds the selectedSightIds to set
     */
    public void setSelectedSightIds(int[] selectedSightIds) {
        this.selectedSightIds = selectedSightIds;
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

    /**
     * @return the sup
     */
    public Super getSup() {
        return sup;
    }

    /**
     * @param sup the sup to set
     */
    public void setSup(Super sup) {
        this.sup = sup;
    }

}
