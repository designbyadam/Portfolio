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
public class DeleteLocationResponse extends Response {
    private Location deletedLocation;

    /**
     * @return the deletedLocation
     */
    public Location getDeletedLocation() {
        return deletedLocation;
    }

    /**
     * @param deletedLocation the deletedLocation to set
     */
    public void setDeletedLocation(Location deletedLocation) {
        this.deletedLocation = deletedLocation;
    }

}
