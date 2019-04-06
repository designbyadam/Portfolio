/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroSightings.daos;

import com.sg.superheroSightings.models.Sighting;
import com.sg.superheroSightings.models.Super;
import java.util.List;

/**
 *
 * @author afahrenkamp
 */
public interface SuperDao {

    Super getSuperById(int superId) throws SuperPersistenceException;

    List<Super> getAllSupers() throws SuperPersistenceException;
    
    public List<Super> getSupersByOrgId(int organizationId) throws SuperPersistenceException;
    
    public List<Super> getAllSupersBySightingId(int sightingId) throws SuperPersistenceException;

    Super addSuper(Super superToAdd) throws SuperPersistenceException;

    Super updateSuper(Super superToUpdate) throws SuperPersistenceException;

    void deleteSuperById(int superId) throws SuperPersistenceException;

}
