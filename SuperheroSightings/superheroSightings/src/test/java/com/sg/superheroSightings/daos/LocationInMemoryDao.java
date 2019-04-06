/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroSightings.daos;

import com.sg.superheroSightings.models.Location;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 *
 * @author afahrenkamp
 */
@Component
@Profile("memory")
public class LocationInMemoryDao implements LocationDao {

    List<Location> allLocations = new ArrayList();

    public LocationInMemoryDao() {

        Location locationOne = new Location();
        locationOne.setLocationId(1);
        locationOne.setName("Gotham City");
        locationOne.setDescription("The darkest city");
        locationOne.setAddress("123 Test Avenue, Alpharetta, GA 30022");
        locationOne.setLatitude(44.977753);
        locationOne.setLongitude(-93.265015);

        Location locationTwo = new Location();
        locationTwo.setLocationId(2);
        locationTwo.setName("Casa Jacob");
        locationTwo.setDescription("Chill place");
        locationTwo.setAddress("15 5th Street North, Minneapolis, MN 55105");
        locationTwo.setLatitude(64.977753);
        locationTwo.setLongitude(-83.265015);

        allLocations.add(locationOne);
        allLocations.add(locationTwo);

    }

    @Override
    public Location getLocationById(int locationId) throws LocationPersistenceException {
        Location toReturn = new Location();
        for (Location toCheck : allLocations) {
            if (locationId == toCheck.getLocationId()) {
                toReturn = toCheck;
            }
        }
        return toReturn;
    }

    @Override
    public List<Location> getAllLocations() throws LocationPersistenceException {
        return allLocations;
    }

    @Override
    public Location addLocation(Location locationToAdd) throws LocationPersistenceException {

        int newLocationNumber = generateNewLocationNumber(allLocations);

        locationToAdd.setLocationId(newLocationNumber);

        allLocations.add(locationToAdd);

        return locationToAdd;
    }

    @Override
    public Location updateLocation(Location locationToUpdate) throws LocationPersistenceException {
        Location toReturn = null;

        int index = Integer.MIN_VALUE;

        for (int i = 0; i < allLocations.size(); i++) {
            Location toCheck = allLocations.get(i);
            if (toCheck.getLocationId() == locationToUpdate.getLocationId()) {
                index = i;
                break;
            }
        }
        allLocations.remove(index);
        allLocations.add(locationToUpdate);
        toReturn = locationToUpdate;
        return toReturn;
    }

    @Override
    public void deleteLocationById(int locationId) throws LocationPersistenceException {

        int index = Integer.MIN_VALUE;

        for (int i = 0; i < allLocations.size(); i++) {
            Location toCheck = allLocations.get(i);
            if (toCheck.getLocationId() == locationId) {
                index = i;
                break;
            }

        }
        allLocations.remove(index);

    }

    private int generateNewLocationNumber(List<Location> allLocations) {
        int toReturn = Integer.MIN_VALUE;

        if (allLocations.isEmpty()) {
            toReturn = 1;
        } else {
            for (Location toInspect : allLocations) {
                if (toInspect.getLocationId() > toReturn) {
                    toReturn = toInspect.getLocationId();
                }
            }

            toReturn++;
        }

        return toReturn;
    }

}
