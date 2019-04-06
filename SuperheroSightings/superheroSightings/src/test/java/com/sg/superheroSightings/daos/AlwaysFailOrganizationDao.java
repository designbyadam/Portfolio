/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroSightings.daos;

import com.sg.superheroSightings.models.Organization;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 *
 * @author afahrenkamp
 */
@Component
@Profile("memory")
public class AlwaysFailOrganizationDao implements OrganizationDao {

    @Override
    public Organization getOrgById(int organizationId) throws OrganizationPersistenceException {
        throw new OrganizationPersistenceException("Unable to retrieve organization data");
    }

    @Override
    public List<Organization> getAllOrgs() throws OrganizationPersistenceException {
        throw new OrganizationPersistenceException("Unable to retrieve organization data");
    }

    @Override
    public Organization addOrg(Organization orgToAdd) throws OrganizationPersistenceException {
        throw new OrganizationPersistenceException("Unable to add organization data");
    }

    @Override
    public Organization updateOrg(Organization orgToUpdate) throws OrganizationPersistenceException {
        throw new OrganizationPersistenceException("Unable to update organization data");
    }

    @Override
    public void deleteOrgById(int organizationId) throws OrganizationPersistenceException {
        throw new OrganizationPersistenceException("Unable to delete organization data");
    }

    @Override
    public List<Organization> getOrgsBySuperId(int superId) throws OrganizationPersistenceException {
        throw new OrganizationPersistenceException("Unable to retrieve organization data");
    }

}
