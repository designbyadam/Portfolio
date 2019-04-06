/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroSightings.services;

import com.sg.superheroSightings.models.Organization;
import java.util.List;

/**
 *
 * @author afahrenkamp
 */
public class ListOrganizationResponse extends Response {
    private List<Organization> allOrgs;

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
}
