/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroSightings.daos;

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
public class AlwaysFailSuperDao implements SuperDao {

    @Override
    public Super getSuperById(int superId) throws SuperPersistenceException {
        throw new SuperPersistenceException("Unable to retrieve super data");
    }

    @Override
    public List<Super> getAllSupers() throws SuperPersistenceException {
        throw new SuperPersistenceException("Unable to retrieve super data");
    }

    @Override
    public Super addSuper(Super superToAdd) throws SuperPersistenceException {
        throw new SuperPersistenceException("Unable to add super data");
    }

    @Override
    public Super updateSuper(Super superToUpdate) throws SuperPersistenceException {
        throw new SuperPersistenceException("Unable to update super data");
    }

    @Override
    public void deleteSuperById(int superId) throws SuperPersistenceException {
        throw new SuperPersistenceException("Unable to delete super data");
    }

    @Override
    public List<Super> getSupersByOrgId(int organizationId) throws SuperPersistenceException {
        throw new SuperPersistenceException("Unable to retrieve super data");
    }

    @Override
    public List<Super> getAllSupersBySightingId(int sightingId) throws SuperPersistenceException {
        throw new SuperPersistenceException("Unable to retrieve super data");
    }

}
