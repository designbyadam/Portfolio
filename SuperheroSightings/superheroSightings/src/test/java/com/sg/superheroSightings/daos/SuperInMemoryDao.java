/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroSightings.daos;

import com.sg.superheroSightings.models.Organization;
import com.sg.superheroSightings.models.Sighting;
import com.sg.superheroSightings.models.Super;
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
public class SuperInMemoryDao implements SuperDao {

    List<Super> allSupers = new ArrayList();

    public SuperInMemoryDao() {

        Super superOne = new Super();
        superOne.setSuperId(1);
        superOne.setName("Batman");
        superOne.setDescription("The bat man");
        superOne.setSuperpower("tech");

        Super superTwo = new Super();
        superTwo.setSuperId(2);
        superTwo.setName("Superman");
        superTwo.setDescription("The super man");
        superTwo.setSuperpower("flight");

        allSupers.add(superOne);
        allSupers.add(superTwo);

    }

    @Override
    public Super getSuperById(int superId) {
        Super toReturn = new Super();
        for (Super toCheck : allSupers) {
            if (superId == toCheck.getSuperId()) {
                toReturn = toCheck;
            }
        }
        return toReturn;
    }

    @Override
    public List<Super> getAllSupers() {
        return allSupers;
    }

    @Override
    public Super addSuper(Super superToAdd) {
        int newSuperNumber = generateNewSuperNumber(allSupers);

        superToAdd.setSuperId(newSuperNumber);

        allSupers.add(superToAdd);

        return superToAdd;
    }

    @Override
    public Super updateSuper(Super superToUpdate) {
        Super toReturn = null;

        int index = Integer.MIN_VALUE;

        for (int i = 0; i < allSupers.size(); i++) {
            Super toCheck = allSupers.get(i);
            if (toCheck.getSuperId() == superToUpdate.getSuperId()) {
                index = i;
                break;
            }
        }
        allSupers.remove(index);
        allSupers.add(superToUpdate);
        toReturn = superToUpdate;

        return toReturn;
    }

    @Override
    public void deleteSuperById(int superId) {
        for (Super toCheck : allSupers) {
            if (superId == toCheck.getSuperId()) {
                allSupers.remove(toCheck);
            }
        }

    }

    @Override
    public List<Super> getSupersByOrgId(int organizationId) throws SuperPersistenceException {
        List<Super> toReturn = new ArrayList();

        for (Super toCheck : allSupers) {
            List<Organization> allOrgs = toCheck.getAllOrganizations();
            for (Organization orgCheck : allOrgs) {
                if (orgCheck.getOrganizationId() == organizationId) {
                    toReturn.add(toCheck);
                    break;
                }
            }
        }
        return toReturn;
    }

    @Override
    public List<Super> getAllSupersBySightingId(int sightingId) throws SuperPersistenceException {
        List<Super> toReturn = new ArrayList();

        for (Super toCheck : allSupers) {
            List<Sighting> allSightings = toCheck.getAllSightings();
            for (Sighting sightCheck : allSightings) {
                if (sightCheck.getSightingId() == sightingId) {
                    toReturn.add(toCheck);
                    break;
                }
            }
        }
        return toReturn;
    }

    private int generateNewSuperNumber(List<Super> allSupers) {
        int toReturn = Integer.MIN_VALUE;

        if (allSupers.isEmpty()) {
            toReturn = 1;
        } else {
            for (Super toInspect : allSupers) {
                if (toInspect.getSuperId() > toReturn) {
                    toReturn = toInspect.getSuperId();
                }
            }

            toReturn++;
        }

        return toReturn;
    }

}
