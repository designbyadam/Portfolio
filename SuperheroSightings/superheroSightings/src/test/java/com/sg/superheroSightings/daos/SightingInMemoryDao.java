/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroSightings.daos;

import com.sg.superheroSightings.models.Location;
import com.sg.superheroSightings.models.Sighting;
import com.sg.superheroSightings.models.Super;
import java.sql.Date;
import java.time.LocalDate;
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
public class SightingInMemoryDao implements SightingDao {

    LocalDate dateOne = LocalDate.of(2018, 04, 13);
    LocalDate dateTwo = LocalDate.of(2019, 03, 10);
    LocalDate dateThree = LocalDate.of(2018, 04, 03);
    LocalDate dateFour = LocalDate.of(2010, 04, 13);
    LocalDate dateFive = LocalDate.of(2016, 03, 10);
    LocalDate dateSix = LocalDate.of(2012, 04, 03);
    LocalDate dateSeven = LocalDate.of(2015, 04, 13);
    LocalDate dateEight = LocalDate.of(2013, 03, 10);
    LocalDate dateNine = LocalDate.of(2014, 04, 03);
    LocalDate dateTen = LocalDate.of(2011, 04, 13);
    LocalDate dateEleven = LocalDate.of(2019, 03, 22);

    List<Sighting> allSightings = new ArrayList();

    public SightingInMemoryDao() {

        Sighting sightingOne = new Sighting();
        sightingOne.setSightingId(1);
        sightingOne.setLocationId(1);
        sightingOne.setDate(dateOne);

        Sighting sightingTwo = new Sighting();
        sightingTwo.setSightingId(2);
        sightingTwo.setLocationId(2);
        sightingTwo.setDate(dateTwo);

        Sighting sightingThree = new Sighting();
        sightingThree.setSightingId(3);
        sightingThree.setLocationId(1);
        sightingThree.setDate(dateThree);

        Sighting sightingFour = new Sighting();
        sightingFour.setSightingId(4);
        sightingFour.setLocationId(1);
        sightingFour.setDate(dateFour);

        Sighting sightingFive = new Sighting();
        sightingFive.setSightingId(5);
        sightingFive.setLocationId(1);
        sightingFive.setDate(dateFive);

        Sighting sightingSix = new Sighting();
        sightingSix.setSightingId(6);
        sightingSix.setLocationId(2);
        sightingSix.setDate(dateSix);

        Sighting sightingSeven = new Sighting();
        sightingSeven.setSightingId(7);
        sightingSeven.setLocationId(1);
        sightingSeven.setDate(dateSeven);

        Sighting sightingEight = new Sighting();
        sightingEight.setSightingId(8);
        sightingEight.setLocationId(1);
        sightingEight.setDate(dateEight);

        Sighting sightingNine = new Sighting();
        sightingNine.setSightingId(9);
        sightingNine.setLocationId(2);
        sightingNine.setDate(dateNine);

        Sighting sightingTen = new Sighting();
        sightingTen.setSightingId(10);
        sightingTen.setLocationId(1);
        sightingTen.setDate(dateTen);

        Sighting sightingEleven = new Sighting();
        sightingEleven.setSightingId(11);
        sightingEleven.setLocationId(1);
        sightingEleven.setDate(dateEleven);

        allSightings.add(sightingOne);
        allSightings.add(sightingTwo);
        allSightings.add(sightingThree);
        allSightings.add(sightingFour);
        allSightings.add(sightingFive);
        allSightings.add(sightingSix);
        allSightings.add(sightingSeven);
        allSightings.add(sightingEight);
        allSightings.add(sightingNine);
        allSightings.add(sightingTen);
        allSightings.add(sightingEleven);
    }

    @Override
    public Sighting getSightingById(int sightingId) {
        Sighting toReturn = new Sighting();

        for (int i = 0; i < allSightings.size(); i++) {
            Sighting toCheck = allSightings.get(i);
            if (toCheck.getSightingId() == sightingId) {
                toReturn = toCheck;
                break;
            }
        }
        return toReturn;
    }

    @Override
    public List<Sighting> getAllSightings() {
        return allSightings;
    }

    @Override
    public Sighting addSighting(Sighting sightingToAdd) {
        int newSightingNumber = generateNewSightingNumber(allSightings);

        sightingToAdd.setSightingId(newSightingNumber);

        allSightings.add(sightingToAdd);

        return sightingToAdd;
    }

    @Override
    public Sighting updateSighting(Sighting sightingToUpdate) {
        Sighting toReturn = null;

        int index = Integer.MIN_VALUE;

        for (int i = 0; i < allSightings.size(); i++) {
            Sighting toCheck = allSightings.get(i);
            if (toCheck.getSightingId() == sightingToUpdate.getSightingId()) {
                index = i;
                break;
            }
        }
        allSightings.remove(index);
        allSightings.add(sightingToUpdate);
        toReturn = sightingToUpdate;
        return toReturn;
    }

    @Override
    public void deleteSightingById(int sightingId) {
        int index = Integer.MIN_VALUE;

        for (int i = 0; i < allSightings.size(); i++) {
            Sighting toCheck = allSightings.get(i);
            if (toCheck.getSightingId() == sightingId) {
                index = i;
                break;
            }

        }
        allSightings.remove(index);
    }

    @Override
    public List<Sighting> getSightingsBySuperId(int superId) {
        List<Sighting> toReturn = new ArrayList();

        for (Sighting toCheck : allSightings) {
            List<Super> allSupers = toCheck.getAllSupers();
            for (Super superCheck : allSupers) {
                if (superCheck.getSuperId() == superId) {
                    toReturn.add(toCheck);
                    break;
                }
            }
        }
        return toReturn;
    }

    @Override
    public List<Sighting> getSightingsByLocationId(int locationId) {
        List<Sighting> toReturn = new ArrayList();

        for (Sighting toCheck : allSightings) {
            Location currentLocation = toCheck.getSightLocation();

            if (currentLocation.getLocationId() == locationId) {
                toReturn.add(toCheck);
            }

        }
        return toReturn;
    }

    private int generateNewSightingNumber(List<Sighting> allSightings) {
        int toReturn = Integer.MIN_VALUE;

        if (allSightings.isEmpty()) {
            toReturn = 1;
        } else {
            for (Sighting toInspect : allSightings) {
                if (toInspect.getSightingId() > toReturn) {
                    toReturn = toInspect.getSightingId();
                }
            }

            toReturn++;
        }

        return toReturn;
    }
}
