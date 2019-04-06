/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroSightings.daos;

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
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author afahrenkamp
 */
@Repository
public class SightingDbDao implements SightingDao {

    @Autowired
    JdbcTemplate jdbc;

    public SightingDbDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Sighting getSightingById(int sightingId) throws SightingPersistenceException {
        Sighting toReturn = null;
        try {
            toReturn = jdbc.queryForObject("SELECT * FROM Sightings WHERE sightingId = ?", new sightingMapper(), sightingId);
        } catch (DataAccessException ex) {
            throw new SightingPersistenceException("Could not access sighting for retrieval", ex);
        }
        return toReturn;
    }

    @Override
    public List<Sighting> getAllSightings() throws SightingPersistenceException {
        List<Sighting> allSightings = new ArrayList();
        try {
            allSightings = jdbc.query("SELECT sightingId, date, locationId FROM Sightings",
                    new sightingMapper());
        } catch (DataAccessException ex) {
            throw new SightingPersistenceException("Could not access sightings for retrieval", ex);
        }
        return allSightings;
    }

    @Override
    public Sighting addSighting(Sighting sightingToAdd) throws SightingPersistenceException {
        Sighting toReturn = new Sighting();
        try {
            final String INSERT_SIGHTING = "INSERT INTO Sightings(date, locationId) VALUES(?,?)";

            int rowsAffected = jdbc.update(INSERT_SIGHTING,
                    sightingToAdd.getDate(),
                    sightingToAdd.getLocationId());

            validateNumberOfRows(rowsAffected);

            int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
            sightingToAdd.setSightingId(newId);
            insertSuperSightings(sightingToAdd);
        } catch (DataAccessException ex) {
            throw new SightingPersistenceException("Could not access sightings for retrieval", ex);
        }
        toReturn = sightingToAdd;
        return toReturn;
    }

    @Override
    public Sighting updateSighting(Sighting sightingToUpdate) throws SightingPersistenceException {

        final String UPDATE_SIGHTING = "UPDATE Sightings SET date = ?, "
                + "locationId = ? WHERE sightingId = ?";
        try {
            int rowsAffected = jdbc.update(UPDATE_SIGHTING, sightingToUpdate.getDate(),
                    sightingToUpdate.getLocationId(),
                    sightingToUpdate.getSightingId());

            validateNumberOfRows(rowsAffected);

            final String DELETE_SUPER_SIGHTINGS = "DELETE FROM Super_Sightings WHERE sightingId = ?";
            jdbc.update(DELETE_SUPER_SIGHTINGS, sightingToUpdate.getSightingId());
            insertSuperSightings(sightingToUpdate);

        } catch (DataAccessException ex) {
            throw new SightingPersistenceException("Could not update sighting in database", ex);
        }

        return getSightingById(sightingToUpdate.getLocationId());
    }

    @Override
    @Transactional
    public void deleteSightingById(int sightingId) throws SightingPersistenceException {

        try {
            jdbc.update("DELETE FROM Super_Sightings WHERE sightingId = ?", sightingId);

            int rowsAffected = jdbc.update("DELETE FROM Sightings WHERE sightingId = ?", sightingId);
            validateNumberOfRows(rowsAffected);
        } catch (DataAccessException ex) {
            throw new SightingPersistenceException("Could not delete sighting from database", ex);
        }
    }

    @Override
    public List<Sighting> getSightingsBySuperId(int superId) throws SightingPersistenceException {
        List<Sighting> toReturn = new ArrayList();
        try {
            toReturn = jdbc.query("SELECT s.* FROM Sightings s "
                    + "JOIN Super_Sightings ss on s.sightingId = ss.sightingId "
                    + "WHERE ss.superId = ?", new sightingMapper(), superId);
        } catch (DataAccessException ex) {
            throw new SightingPersistenceException("Could not access sightings from database", ex);
        }
        return toReturn;
    }

    @Override
    public List<Sighting> getSightingsByLocationId(int locationId) throws SightingPersistenceException {
        List<Sighting> toReturn = new ArrayList();
        try {
            toReturn = jdbc.query("SELECT s.* FROM Sightings s "
                    + "JOIN Locations l on s.locationId = l.locationId "
                    + "WHERE l.locationId = ?", new sightingMapper(), locationId);
        } catch (DataAccessException ex) {
            throw new SightingPersistenceException("Could not access sightings from database", ex);
        }
        return toReturn;
    }

    private void validateNumberOfRows(int rowsAffected) throws SightingPersistenceException {
        if (rowsAffected != 1) {
            throw new SightingPersistenceException("Number of rows affected were different than expected.");
        }
    }

    private void insertSuperSightings(Sighting sightingToUpdate) throws SightingPersistenceException {
        final String INSERT_SUPER_SIGHTINGS = "INSERT INTO "
                + "Super_Sightings(superId, sightingId) VALUES(?,?)";
        try {
            for (Super toCheck : sightingToUpdate.getAllSupers()) {
                jdbc.update(INSERT_SUPER_SIGHTINGS,
                        toCheck.getSuperId(),
                        sightingToUpdate.getSightingId());
            }
        } catch (DataAccessException ex) {
            throw new SightingPersistenceException("Could not access sightings from database", ex);
        }
    }

    private static final class sightingMapper implements RowMapper<Sighting> {

        public Sighting mapRow(ResultSet rs, int index) throws SQLException {
            Sighting stng = new Sighting();
            stng.setSightingId(rs.getInt("sightingId"));
            stng.setDate(rs.getDate("date").toLocalDate());
            stng.setLocationId(rs.getInt("locationId"));

            return stng;
        }
    }

}
