/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroSightings.daos;

import com.sg.superheroSightings.models.Sighting;
import com.sg.superheroSightings.models.Super;
import java.util.List;

/**
 *
 * @author afahrenkamp
 */
public interface SightingDao {

    Sighting getSightingById(int sightingId) throws SightingPersistenceException;

    List<Sighting> getAllSightings() throws SightingPersistenceException;

    Sighting addSighting(Sighting sightingToAdd) throws SightingPersistenceException;

    Sighting updateSighting(Sighting sightingToUpdate) throws SightingPersistenceException;

    void deleteSightingById(int sightingId) throws SightingPersistenceException;

    List<Sighting> getSightingsBySuperId(int superId) throws SightingPersistenceException;

    List<Sighting> getSightingsByLocationId(int locationId) throws SightingPersistenceException;
    

}
