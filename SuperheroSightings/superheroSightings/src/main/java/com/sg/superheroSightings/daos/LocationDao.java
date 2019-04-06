/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroSightings.daos;

import com.sg.superheroSightings.models.Location;
import java.util.List;

/**
 *
 * @author afahrenkamp
 */
public interface LocationDao {

    Location getLocationById(int locationId) throws LocationPersistenceException ;

    List<Location> getAllLocations() throws LocationPersistenceException ;

    Location addLocation(Location locationToAdd) throws LocationPersistenceException ;

    Location updateLocation(Location locationToUpdate) throws LocationPersistenceException ;

    void deleteLocationById(int locationId) throws LocationPersistenceException ;
}
