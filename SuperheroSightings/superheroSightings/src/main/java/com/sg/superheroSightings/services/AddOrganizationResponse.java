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
public class AddOrganizationResponse extends Response {

    private Organization toAdd;

    /**
     * @return the toAdd
     */
    public Organization getToAdd() {
        return toAdd;
    }

    /**
     * @param toAdd the toAdd to set
     */
    public void setToAdd(Organization toAdd) {
        this.toAdd = toAdd;
    }
}
