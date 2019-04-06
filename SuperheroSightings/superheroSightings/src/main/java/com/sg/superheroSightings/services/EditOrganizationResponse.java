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
public class EditOrganizationResponse extends Response {
    private Organization editedOrganization;

    /**
     * @return the editedOrganization
     */
    public Organization getEditedOrganization() {
        return editedOrganization;
    }

    /**
     * @param editedOrganization the editedOrganization to set
     */
    public void setEditedOrganization(Organization editedOrganization) {
        this.editedOrganization = editedOrganization;
    }
}
