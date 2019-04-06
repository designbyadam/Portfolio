/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroSightings.services;

import com.sg.superheroSightings.models.Organization;

/**
 *
 * @author afahrenkamp
 */
public class GetOrganizationResponse extends Response {
    private Organization toDisplay;

    /**
     * @return the toDisplay
     */
    public Organization getToDisplay() {
        return toDisplay;
    }

    /**
     * @param toDisplay the toDisplay to set
     */
    public void setToDisplay(Organization toDisplay) {
        this.toDisplay = toDisplay;
    }
}
