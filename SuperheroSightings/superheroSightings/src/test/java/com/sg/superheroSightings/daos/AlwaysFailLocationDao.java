/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroSightings.daos;

import com.sg.superheroSightings.models.Location;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 *
 * @author afahrenkamp
 */
@Component
@Profile("memory")
public class AlwaysFailLocationDao implements LocationDao {

    @Override
    public Location getLocationById(int locationId) throws LocationPersistenceException {
        throw new LocationPersistenceException("Unable to retrieve location data");
    }

    @Override
    public List<Location> getAllLocations() throws LocationPersistenceException {
        throw new LocationPersistenceException("Unable to retrieve location data");
    }

    @Override
    public Location addLocation(Location locationToAdd) throws LocationPersistenceException {
        throw new LocationPersistenceException("Unable to add Location data");
    }

    @Override
    public Location updateLocation(Location locationToUpdate) throws LocationPersistenceException {
        throw new LocationPersistenceException("Unable to update location data");
    }

    @Override
    public void deleteLocationById(int locationId) throws LocationPersistenceException {
        throw new LocationPersistenceException("Unable to delete location data");
    }
    
}
