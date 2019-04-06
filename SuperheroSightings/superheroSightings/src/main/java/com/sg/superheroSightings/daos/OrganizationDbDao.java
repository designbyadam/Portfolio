/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroSightings.daos;

import com.sg.superheroSightings.models.Organization;
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
public class OrganizationDbDao implements OrganizationDao {

    @Autowired
    JdbcTemplate jdbc;

    public OrganizationDbDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Organization getOrgById(int organizationId) throws OrganizationPersistenceException {
        Organization toReturn = null;
        try {
            toReturn = jdbc.queryForObject("SELECT * FROM Organizations WHERE organizationId = ?",
                    new organizationMapper(), organizationId);
        } catch (DataAccessException ex) {
            throw new OrganizationPersistenceException("Could not access organization for retrieval", ex);
        }

        return toReturn;

    }

    @Override
    public List<Organization> getAllOrgs() throws OrganizationPersistenceException {
        List<Organization> allOrgs = new ArrayList();
        try {
            allOrgs = jdbc.query("SELECT organizationId, name, description, address, phoneNumber, email FROM Organizations",
                    new organizationMapper());
        } catch (DataAccessException ex) {
            throw new OrganizationPersistenceException("Could not access organizations for retrieval", ex);
        }

        return allOrgs;
    }

    @Override
    public Organization addOrg(Organization orgToAdd) throws OrganizationPersistenceException {
        final String INSERT_ORGANIZATION = "INSERT INTO Organizations(name, description, address, phoneNumber, email) "
                + "VALUES(?,?,?,?,?)";
        try {
            int rowsAffected = jdbc.update(INSERT_ORGANIZATION,
                    orgToAdd.getName(),
                    orgToAdd.getDescription(),
                    orgToAdd.getAddress(),
                    orgToAdd.getPhoneNumber(),
                    orgToAdd.getEmail());

            validateNumberOfRows(rowsAffected);

            int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
            orgToAdd.setOrganizationId(newId);
        } catch (DataAccessException ex) {
            throw new OrganizationPersistenceException("Could not add location to database", ex);
        }

        return orgToAdd;
    }

    @Override
    public Organization updateOrg(Organization orgToUpdate) throws OrganizationPersistenceException {
        final String UPDATE_ORGANIZATION = "UPDATE Organizations SET name = ?, "
                + "description = ?, address = ?, phoneNumber = ?, email = ? WHERE organizationId = ?";
        try {
            int rowsAffected = jdbc.update(UPDATE_ORGANIZATION, orgToUpdate.getName(),
                    orgToUpdate.getDescription(),
                    orgToUpdate.getAddress(),
                    orgToUpdate.getPhoneNumber(),
                    orgToUpdate.getEmail(),
                    orgToUpdate.getOrganizationId());

            validateNumberOfRows(rowsAffected);

            final String DELETE_ORGANIZATION_SUPERS = "DELETE FROM Organization_Supers WHERE organizationId = ?";
            jdbc.update(DELETE_ORGANIZATION_SUPERS, orgToUpdate.getOrganizationId());
            insertOrganizationSupers(orgToUpdate);

        } catch (DataAccessException ex) {
            throw new OrganizationPersistenceException("Could not update organization in database", ex);
        }

        return getOrgById(orgToUpdate.getOrganizationId());
    }

    @Override
    public void deleteOrgById(int organizationId) throws OrganizationPersistenceException {
        try {
            jdbc.update("DELETE FROM Organization_Supers WHERE organizationId = ?", organizationId);
            int rowsAffected = jdbc.update("DELETE FROM Organizations WHERE organizationId = ?", organizationId);
            validateNumberOfRows(rowsAffected);
        } catch (DataAccessException ex) {
            throw new OrganizationPersistenceException("Could not delete organization from database", ex);
        }
    }

    @Override
    public List<Organization> getOrgsBySuperId(int superId) throws OrganizationPersistenceException {
        List<Organization> toReturn = new ArrayList();
        try {
            toReturn = jdbc.query("SELECT o.* FROM Organizations o "
                    + "JOIN Organization_Supers os on o.organizationId = os.organizationId "
                    + "WHERE os.superId = ?", new organizationMapper(), superId);
        } catch (DataAccessException ex) {
            throw new OrganizationPersistenceException("Could not access organizations for retrieval", ex);
        }
        return toReturn;
    }

    private void validateNumberOfRows(int rowsAffected) throws OrganizationPersistenceException {
        if (rowsAffected != 1) {
            throw new OrganizationPersistenceException("Number of rows affected were different than expected.");
        }
    }

    private void insertOrganizationSupers(Organization orgToUpdate) throws OrganizationPersistenceException {
         final String INSERT_ORGANIZATION_SUPERS = "INSERT INTO "
                + "Organization_Supers(organizationId, superId) VALUES(?,?)";
        try {
            for (Super toCheck : orgToUpdate.getAllSupers()) {
                jdbc.update(INSERT_ORGANIZATION_SUPERS,
                        orgToUpdate.getOrganizationId(),
                         toCheck.getSuperId());
            }
        } catch (DataAccessException ex) {
            throw new OrganizationPersistenceException("Could not access organizations from database", ex);
        }
    }

    private static final class organizationMapper implements RowMapper<Organization> {

        public Organization mapRow(ResultSet rs, int index) throws SQLException {
            Organization org = new Organization();
            org.setOrganizationId(rs.getInt("organizationId"));
            org.setName(rs.getString("name"));
            org.setDescription(rs.getString("description"));
            org.setAddress(rs.getString("address"));
            org.setPhoneNumber(rs.getString("phoneNumber"));
            org.setEmail(rs.getString("email"));

            return org;
        }
    }
}
