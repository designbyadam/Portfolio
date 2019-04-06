/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroSightings.daos;

import com.sg.superheroSightings.models.Organization;
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
public class OrganizationInMemoryDao implements OrganizationDao {

    List<Organization> allOrgs = new ArrayList();

    public OrganizationInMemoryDao() {

        Organization orgOne = new Organization();
        orgOne.setOrganizationId(1);
        orgOne.setName("Marvel");
        orgOne.setDescription("Marveling");
        orgOne.setAddress("456 Test Avenue, Saint Paul, MN 55104");
        orgOne.setPhoneNumber("7702410271");
        orgOne.setEmail("marvel@marvel.com");

        Organization orgTwo = new Organization();
        orgTwo.setOrganizationId(2);
        orgTwo.setName("DC");
        orgTwo.setDescription("DC What");
        orgTwo.setAddress("456 Test Street, Saint Paul, MN 55104");
        orgTwo.setPhoneNumber("7705181492");
        orgTwo.setEmail("dc@dc.com");

        allOrgs.add(orgOne);
        allOrgs.add(orgTwo);

    }

    @Override
    public Organization getOrgById(int organizationId) throws OrganizationPersistenceException {
        Organization toReturn = new Organization();
        for (Organization toCheck : allOrgs) {
            if (organizationId == toCheck.getOrganizationId()) {
                toReturn = toCheck;
            }
        }
        return toReturn;
    }

    @Override
    public List<Organization> getAllOrgs() throws OrganizationPersistenceException {
        return allOrgs;
    }

    @Override
    public Organization addOrg(Organization orgToAdd) throws OrganizationPersistenceException {
        int newOrganizationNumber = generateNewOrganizationNumber(allOrgs);

        orgToAdd.setOrganizationId(newOrganizationNumber);

        allOrgs.add(orgToAdd);

        return orgToAdd;
    }

    @Override
    public Organization updateOrg(Organization orgToUpdate) throws OrganizationPersistenceException {
        Organization toReturn = null;

        int index = Integer.MIN_VALUE;

        for (int i = 0; i < allOrgs.size(); i++) {
            Organization toCheck = allOrgs.get(i);
            if (toCheck.getOrganizationId() == orgToUpdate.getOrganizationId()) {
                index = i;
                break;
            }
        }

        allOrgs.remove(index);
        allOrgs.add(orgToUpdate);
        toReturn = orgToUpdate;

        return toReturn;
    }

    @Override
    public void deleteOrgById(int organizationId) throws OrganizationPersistenceException {
        for (Organization toCheck : allOrgs) {
            if (organizationId == toCheck.getOrganizationId()) {
                allOrgs.remove(toCheck);
            }
        }
    }

    @Override
    public List<Organization> getOrgsBySuperId(int superId) throws OrganizationPersistenceException {
        List<Organization> toReturn = new ArrayList();

        for (Organization toCheck : allOrgs) {
            List<Super> allSupers = toCheck.getAllSupers();
            for (Super superCheck : allSupers) {
                if (superCheck.getSuperId() == superId) {
                    toReturn.add(toCheck);
                }
            }
        }
        return toReturn;
    }

    private int generateNewOrganizationNumber(List<Organization> allOrgs) {
        int toReturn = Integer.MIN_VALUE;

        if (allOrgs.isEmpty()) {
            toReturn = 1;
        } else {
            for (Organization toInspect : allOrgs) {
                if (toInspect.getOrganizationId() > toReturn) {
                    toReturn = toInspect.getOrganizationId();
                }
            }

            toReturn++;
        }

        return toReturn;
    }

}
