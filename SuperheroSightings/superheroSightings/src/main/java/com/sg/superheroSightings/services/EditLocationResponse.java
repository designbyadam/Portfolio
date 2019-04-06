/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroSightings.services;

import com.sg.superheroSightings.models.Location;

/**
 *
 * @author afahrenkamp
 */
public class EditLocationResponse extends Response {
    private Location editedLocation;

    /**
     * @return the editedLocation
     */
    public Location getEditedLocation() {
        return editedLocation;
    }

    /**
     * @param editedLocation the editedLocation to set
     */
    public void setEditedLocation(Location editedLocation) {
        this.editedLocation = editedLocation;
    }
}
