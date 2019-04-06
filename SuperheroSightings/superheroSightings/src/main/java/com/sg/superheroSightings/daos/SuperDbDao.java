/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroSightings.daos;

import com.sg.superheroSightings.models.Organization;
import com.sg.superheroSightings.models.Sighting;
import com.sg.superheroSightings.models.Super;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author afahrenkamp
 */
@Repository
public class SuperDbDao implements SuperDao {

    @Autowired
    JdbcTemplate jdbc;

    public SuperDbDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Super getSuperById(int superId) throws SuperPersistenceException {
        Super toReturn = null;
        try {
            toReturn = jdbc.queryForObject("SELECT * FROM Supers WHERE superId = ?", new superMapper(), superId);
        } catch (DataAccessException ex) {
            throw new SuperPersistenceException("Could not access super for retrieval", ex);
        }
        return toReturn;

    }

    @Override
    public List<Super> getAllSupers() throws SuperPersistenceException {
        List<Super> allSupers = new ArrayList();
        try {
            allSupers = jdbc.query("SELECT superId, name, description, superpower FROM Supers",
                    new superMapper());
        } catch (DataAccessException ex) {
            throw new SuperPersistenceException("Could not access supers for retrieval", ex);
        }
        return allSupers;
    }

    @Override
    public Super addSuper(Super superToAdd) throws SuperPersistenceException {
        final String INSERT_SUPER = "INSERT INTO Supers(name, description, superpower) "
                + "VALUES(?,?,?)";
        try {
            int rowsAffected = jdbc.update(INSERT_SUPER,
                    superToAdd.getName(),
                    superToAdd.getDescription(),
                    superToAdd.getSuperpower());

            validateNumberOfRows(rowsAffected);

            int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
            superToAdd.setSuperId(newId);
        } catch (DataAccessException ex) {
            throw new SuperPersistenceException("Could not add super to database", ex);
        }

        return superToAdd;
    }

    @Override
    public Super updateSuper(Super superToUpdate) throws SuperPersistenceException {
        final String UPDATE_SUPER = "UPDATE Supers SET name = ?, "
                + "description = ?, superpower = ? WHERE superId = ?";
        try {
            int rowsAffected = jdbc.update(UPDATE_SUPER, superToUpdate.getName(),
                    superToUpdate.getDescription(),
                    superToUpdate.getSuperpower(),
                    superToUpdate.getSuperId());

            validateNumberOfRows(rowsAffected);

            final String DELETE_ORGANIZATION_SUPERS = "DELETE FROM Organization_Supers WHERE superId = ?";
            final String DELETE_SUPER_SIGHTINGS = "DELETE FROM Super_Sightings WHERE superId = ?";
            jdbc.update(DELETE_ORGANIZATION_SUPERS, superToUpdate.getSuperId());
            jdbc.update(DELETE_SUPER_SIGHTINGS, superToUpdate.getSuperId());
            insertOrganizationSupers(superToUpdate);
            insertSuperSightings(superToUpdate);

        } catch (DataAccessException ex) {
            throw new SuperPersistenceException("Could not update super in database", ex);
        }

        return getSuperById(superToUpdate.getSuperId());
    }

    @Override
    public void deleteSuperById(int superId) throws SuperPersistenceException {
        try {
            jdbc.update("DELETE FROM Organization_Supers WHERE superId = ?", superId);
            jdbc.update("DELETE FROM Super_Sightings WHERE superId = ?", superId);
            int rowsAffected = jdbc.update("DELETE FROM Supers WHERE superId = ?", superId);
            validateNumberOfRows(rowsAffected);
        } catch (DataAccessException ex) {
            throw new SuperPersistenceException("Could not delete super from database", ex);
        }
    }

    @Override
    public List<Super> getSupersByOrgId(int organizationId) throws SuperPersistenceException {
        List<Super> toReturn = new ArrayList();
        try {
            toReturn = jdbc.query("SELECT s.* FROM Supers s "
                    + "JOIN Organization_Supers os on s.superId = os.superId "
                    + "WHERE os.organizationId = ?", new superMapper(), organizationId);
        } catch (DataAccessException ex) {
            throw new SuperPersistenceException("Could not access supers for retrieval", ex);
        }
        return toReturn;
    }

    @Override
    public List<Super> getAllSupersBySightingId(int sightingId) throws SuperPersistenceException {
        List<Super> toReturn = new ArrayList();
        try {
            toReturn = jdbc.query("SELECT s.* FROM Supers s "
                    + "JOIN Super_Sightings ss on s.superId = ss.superId "
                    + "WHERE ss.sightingId = ?", new superMapper(), sightingId);
        } catch (DataAccessException ex) {
            throw new SuperPersistenceException("Could not access supers for retrieval", ex);
        }
        return toReturn;
    }

    private void validateNumberOfRows(int rowsAffected) throws SuperPersistenceException {
        if (rowsAffected != 1) {
            throw new SuperPersistenceException("Number of rows affected were different than expected.");
        }
    }

    private void insertOrganizationSupers(Super superToUpdate) throws SuperPersistenceException {
        final String INSERT_ORGANIZATION_SUPERS = "INSERT INTO "
                + "Organization_Supers(organizationId, superId) VALUES(?,?)";
        try {
            for (Organization toCheck : superToUpdate.getAllOrganizations()) {
                jdbc.update(INSERT_ORGANIZATION_SUPERS,
                        toCheck.getOrganizationId(),
                         superToUpdate.getSuperId());
            }
        } catch (DataAccessException ex) {
            throw new SuperPersistenceException("Could not access supers from database", ex);
        }
    }

    private void insertSuperSightings(Super superToUpdate) throws SuperPersistenceException {
        final String INSERT_SUPER_SIGHTINGS = "INSERT INTO "
                + "Super_Sightings(superId, sightingId) VALUES(?,?)";
        try {
            for (Sighting toCheck : superToUpdate.getAllSightings()) {
                jdbc.update(INSERT_SUPER_SIGHTINGS,
                         superToUpdate.getSuperId(),
                         toCheck.getSightingId());
            }
        } catch (DataAccessException ex) {
            throw new SuperPersistenceException("Could not access supers from database", ex);
        }
    }

    private static final class superMapper implements RowMapper<Super> {

        public Super mapRow(ResultSet rs, int index) throws SQLException {
            Super spr = new Super();
            spr.setSuperId(rs.getInt("superId"));
            spr.setName(rs.getString("name"));
            spr.setDescription(rs.getString("description"));
            spr.setSuperpower(rs.getString("superpower"));

            return spr;
        }
    }
}
