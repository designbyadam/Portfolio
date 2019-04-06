/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroSightings.daos;

import com.sg.superheroSightings.models.Location;
import com.sg.superheroSightings.models.Organization;
import com.sg.superheroSightings.models.Sighting;
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
public class LocationDbDao implements LocationDao {

    @Autowired
    JdbcTemplate jdbc;
    
    public LocationDbDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Location getLocationById(int locationId) throws LocationPersistenceException {
        Location toReturn = new Location();
        try {
            toReturn = jdbc.queryForObject("SELECT * FROM Locations WHERE locationId = ?", new locationMapper(), locationId);
        } catch (DataAccessException ex) {
            throw new LocationPersistenceException("Could not access location for retrieval", ex);
        }
        return toReturn;
    }

    @Override
    public List<Location> getAllLocations() throws LocationPersistenceException {
        List<Location> allLocations = new ArrayList();
        try {
            allLocations = jdbc.query("SELECT locationId, name, description, address, latitude, longitude FROM Locations",
                    new locationMapper());
        } catch (DataAccessException ex) {
            throw new LocationPersistenceException("Could not access locations for retrieval", ex);
        }
        return allLocations;
    }

    @Override
    public Location addLocation(Location locationToAdd) throws LocationPersistenceException {
        final String INSERT_LOCATION = "INSERT INTO Locations(name, description, address, latitude, longitude) "
                + "VALUES(?,?,?,?,?)";
        try {
            int rowsAffected = jdbc.update(INSERT_LOCATION,
                    locationToAdd.getName(),
                    locationToAdd.getDescription(),
                    locationToAdd.getAddress(),
                    locationToAdd.getLatitude(),
                    locationToAdd.getLongitude());

            validateNumberOfRows(rowsAffected);

            int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
            locationToAdd.setLocationId(newId);
        } catch (DataAccessException ex) {
            throw new LocationPersistenceException("Could not add location to database", ex);
        }

        return locationToAdd;
    }

    @Override
    public Location updateLocation(Location locationToUpdate) throws LocationPersistenceException {
        final String UPDATE_LOCATION = "UPDATE Locations SET name = ?, "
                + "description = ?, address = ?, latitude = ?, longitude = ? "
                + "WHERE locationId = ?";
        try {
            int rowsAffected = jdbc.update(UPDATE_LOCATION, locationToUpdate.getName(),
                    locationToUpdate.getDescription(), locationToUpdate.getAddress(),
                    locationToUpdate.getLatitude(), locationToUpdate.getLongitude(),
                    locationToUpdate.getLocationId());

            validateNumberOfRows(rowsAffected);
        } catch (DataAccessException ex) {
            throw new LocationPersistenceException("Could not update location in database", ex);
        }

        return getLocationById(locationToUpdate.getLocationId());
    }

    @Override
    public void deleteLocationById(int locationId) throws LocationPersistenceException {
        
        try {
            int rowsAffected = jdbc.update("DELETE FROM Locations WHERE locationId = ?", locationId);
            validateNumberOfRows(rowsAffected);
        } catch (DataAccessException ex) {
            throw new LocationPersistenceException("Could not delete location from database", ex);
        }
        
    }

    private void validateNumberOfRows(int rowsAffected) throws LocationPersistenceException {
        if (rowsAffected != 1) {
            throw new LocationPersistenceException("Number of rows affected were different than expected.");
        }
    }

    private static final class locationMapper implements RowMapper<Location> {

        public Location mapRow(ResultSet rs, int index) throws SQLException {
            Location lcn = new Location();
            lcn.setLocationId(rs.getInt("locationId"));
            lcn.setName(rs.getString("name"));
            lcn.setDescription(rs.getString("description"));
            lcn.setAddress(rs.getString("address"));
            lcn.setLatitude(rs.getDouble("latitude"));
            lcn.setLongitude(rs.getDouble("longitude"));

            return lcn;
        }
    }

}
