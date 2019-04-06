/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroSightings.daos;

import com.sg.superheroSightings.models.Sighting;
import com.sg.superheroSightings.models.Super;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 *
 * @author afahrenkamp
 */
@Component
@Profile("memory")
public class AlwaysFailSightingDao implements SightingDao {

    @Override
    public Sighting getSightingById(int sightingId) throws SightingPersistenceException {
        throw new SightingPersistenceException("Unable to retrieve sighting data");
    }

    @Override
    public List<Sighting> getAllSightings() throws SightingPersistenceException {
       throw new SightingPersistenceException("Unable to retrieve sighting data");
    }

    @Override
    public Sighting addSighting(Sighting sightingToAdd) throws SightingPersistenceException {
        throw new SightingPersistenceException("Unable to add sighting data");
    }

    @Override
    public Sighting updateSighting(Sighting sightingToUpdate) throws SightingPersistenceException {
        throw new SightingPersistenceException("Unable to update sighting data");
    }

    @Override
    public void deleteSightingById(int sightingId) throws SightingPersistenceException {
        throw new SightingPersistenceException("Unable to delete sighting data");
    }

    @Override
    public List<Sighting> getSightingsBySuperId(int superId) throws SightingPersistenceException {
        throw new SightingPersistenceException("Unable to retrieve sighting data");
    }

    @Override
    public List<Sighting> getSightingsByLocationId(int locationId) throws SightingPersistenceException {
        throw new SightingPersistenceException("Unable to retrieve sighting data");
    }   
}
