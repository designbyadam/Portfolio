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
public class AddOrgViewModel {
    private List<Super> allSupers;
    private int[] selectedSupIds;
    private Organization organization;

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
     * @return the organization
     */
    public Organization getOrganization() {
        return organization;
    }

    /**
     * @param organization the organization to set
     */
    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}
