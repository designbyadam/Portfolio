/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroSightings.daos;

import com.sg.superheroSightings.models.Organization;
import java.util.List;

/**
 *
 * @author afahrenkamp
 */
public interface OrganizationDao {

    Organization getOrgById(int organizationId) throws OrganizationPersistenceException;

    List<Organization> getAllOrgs() throws OrganizationPersistenceException;

    Organization addOrg(Organization orgToAdd) throws OrganizationPersistenceException;

    Organization updateOrg(Organization orgToUpdate) throws OrganizationPersistenceException;

    void deleteOrgById(int organizationId) throws OrganizationPersistenceException;

    List<Organization> getOrgsBySuperId(int superId) throws OrganizationPersistenceException;
}
