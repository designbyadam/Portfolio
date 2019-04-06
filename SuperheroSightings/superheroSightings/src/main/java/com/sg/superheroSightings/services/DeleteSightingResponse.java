/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroSightings.services;

import com.sg.superheroSightings.models.Sighting;

/**
 *
 * @author afahrenkamp
 */
public class DeleteSightingResponse extends Response {

    private Sighting deletedSighting;

    /**
     * @return the deletedSighting
     */
    public Sighting getDeletedSighting() {
        return deletedSighting;
    }

    /**
     * @param deletedSighting the deletedSighting to set
     */
    public void setDeletedSighting(Sighting deletedSighting) {
        this.deletedSighting = deletedSighting;
    }
}
