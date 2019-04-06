/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroSightings.daos;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.sg.superheroSightings.models.Sighting;
import com.sg.superheroSightings.models.Super;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author afahrenkamp
 */
public class SightingDbDaoTest {

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    SightingDbDao dao;

    public SightingDbDaoTest() throws SQLException {
        MysqlDataSource ds = new MysqlDataSource();
        ds.setServerName("localhost");
        ds.setDatabaseName("superheroSightingsTest");
        ds.setUser("root");
        ds.setPassword("password");
        ds.setServerTimezone("America/Chicago");
        ds.setUseSSL(false);
        ds.setAllowPublicKeyRetrieval(true);
        this.jdbc = new JdbcTemplate(ds);
        this.dao = new SightingDbDao(jdbc);

    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {

        java.sql.Date locationOne = java.sql.Date.valueOf("2019-03-03");
        java.sql.Date locationTwo = java.sql.Date.valueOf("2018-03-07");
        java.sql.Date locationThree = java.sql.Date.valueOf("2018-04-03");
        java.sql.Date locationThreeTwo = java.sql.Date.valueOf("2017-02-01");

        final String deleteOrganization_SupersTable = "DROP TABLE IF EXISTS Organization_Supers;";
        final String deleteSuper_SightingsTable = "DROP TABLE IF EXISTS Super_Sightings;";
        final String deleteOrganizationsTable = "DROP TABLE IF EXISTS Organizations;";
        final String deleteSupersTable = "DROP TABLE IF EXISTS Supers;";
        final String deleteSightingsTable = "DROP TABLE IF EXISTS Sightings;";
        final String deleteLocationsTable = "DROP TABLE IF EXISTS Locations;";

        final String createSupersTable = "create table Supers(superId int primary key auto_increment, "
                + "`name` varchar(30) not null, description varchar(50) not null, superpower varchar(100) not null);";
        final String createOrgsTable = "create table Organizations(organizationId int primary key auto_increment, "
                + "`name` varchar(30) not null, description varchar(50) not null, address varchar(100) not null, "
                + "phoneNumber varchar(14) not null, email varchar(35) not null);";
        final String createLocationsTable = "create table Locations(locationId int primary key auto_increment, "
                + "`name` varchar(30) not null, description varchar(50) not null, address varchar(100) not null, "
                + "latitude decimal(10,8) not null, longitude decimal(11,8) not null);";
        final String createSightingsTable = "create table Sightings(sightingId int primary key auto_increment, "
                + "`date` date not null, locationId int not null, foreign key (locationId) references Locations(locationId));";
        final String createSSTable = "create table Super_Sightings(superId int not null, sightingId int not null, "
                + "primary key(superId, sightingId), foreign key (superId) references Supers(superId), "
                + "foreign key (sightingId) references Sightings(sightingId));";
        final String createOSTable = "create table Organization_Supers(organizationId int not null, "
                + "superId int not null, primary key(organizationId, superId), "
                + "foreign key (organizationId) references Organizations(organizationId), "
                + "foreign key (superId) references Supers(superId));";

        jdbc.execute(deleteOrganization_SupersTable);

        jdbc.execute(deleteSuper_SightingsTable);

        jdbc.execute(deleteOrganizationsTable);

        jdbc.execute(deleteSupersTable);

        jdbc.execute(deleteSightingsTable);

        jdbc.execute(deleteLocationsTable);

        jdbc.execute(createSupersTable);

        jdbc.execute(createOrgsTable);

        jdbc.execute(createLocationsTable);

        jdbc.execute(createSightingsTable);

        jdbc.execute(createSSTable);

        jdbc.execute(createOSTable);

        jdbc.update("INSERT INTO Supers(`name`, description, superpower) VALUES(?,?,?)", "Superman", "So super", "flight");

        jdbc.update("INSERT INTO Supers(`name`, description, superpower) VALUES(?,?,?)", "Batman", "the bat man", "tech");

        jdbc.update("INSERT INTO Supers(`name`, description, superpower) VALUES(?,?,?)", "Freeze", "cold", "icing");

        jdbc.update("INSERT INTO Locations(`name`, description, address, latitude, longitude) VALUES(?,?,?,?,?)",
                "Casa Jacob", "Chill", "123 North Street Alpharetta, GA 30022", "-23.029365", "43.654734");

        jdbc.update("INSERT INTO Locations(`name`, description, address, latitude, longitude) VALUES(?,?,?,?,?)",
                "SWG", "Place to learn", "15 5th Street Minneapolis, MN", "-33.029375", "56.654734");

        jdbc.update("INSERT INTO Locations(`name`, description, address, latitude, longitude) VALUES(?,?,?,?,?)",
                "Home", "Casa", "1522 Portland Avenue #309, St. Paul, MN 55104", "-24.029365", "45.654734");

        jdbc.update("INSERT INTO Sightings(date, locationId) VALUES(?,?)", locationOne, "1");

        jdbc.update("INSERT INTO Sightings(date, locationId) VALUES(?,?)", locationTwo, "2");

        jdbc.update("INSERT INTO Sightings(date, locationId) VALUES(?,?)", locationThree, "3");

        jdbc.update("INSERT INTO Sightings(date, locationId) VALUES(?,?)", locationThreeTwo, "3");

        jdbc.update("INSERT INTO Organizations(`name`, description, address, phoneNumber, email) VALUES(?,?,?,?,?)",
                "Marvel", "Sweet org", "456 South Street Alpharetta, GA 30022", "7702410271", "marvel@marvel.com");

        jdbc.update("INSERT INTO Organizations(`name`, description, address, phoneNumber, email) VALUES(?,?,?,?,?)",
                "DC", "D org", "125 North Street Alpharetta, GA 30022", "7701234567", "dc@marvel.com");

        jdbc.update("INSERT INTO Organizations(`name`, description, address, phoneNumber, email) VALUES(?,?,?,?,?)",
                "SeaOrg", "Scientology HQ", "123 North Street Clearwater, FL", "7707654321", "lron@hubbard.com");

        jdbc.update("INSERT INTO Organization_Supers(organizationId, superId) VALUES(?,?)",
                1, 1);

        jdbc.update("INSERT INTO Organization_Supers(organizationId, superId) VALUES(?,?)",
                2, 2);

        jdbc.update("INSERT INTO Organization_Supers(organizationId, superId) VALUES(?,?)",
                1, 2);

        jdbc.update("INSERT INTO Organization_Supers(organizationId, superId) VALUES(?,?)",
                2, 1);

        jdbc.update("INSERT INTO Organization_Supers(organizationId, superId) VALUES(?,?)",
                3, 3);

        jdbc.update("INSERT INTO Super_Sightings(superId, sightingId) VALUES(?,?)",
                1, 1);

        jdbc.update("INSERT INTO Super_Sightings(superId, sightingId) VALUES(?,?)",
                2, 2);

        jdbc.update("INSERT INTO Super_Sightings(superId, sightingId) VALUES(?,?)",
                1, 2);

        jdbc.update("INSERT INTO Super_Sightings(superId, sightingId) VALUES(?,?)",
                2, 1);

        jdbc.update("INSERT INTO Super_Sightings(superId, sightingId) VALUES(?,?)",
                3, 3);

        jdbc.update("INSERT INTO Super_Sightings(superId, sightingId) VALUES(?,?)",
                3, 4);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getSightingById method, of class SightingDbDao.
     */
    @Test
    public void testGetSightingByIdSuccess() throws SightingPersistenceException {
        LocalDate locationOne = LocalDate.of(2019, 03, 03);
        LocalDate locationThreeTwo = LocalDate.of(2017, 02, 01);

        Sighting toGetOne = dao.getSightingById(1);
        Assert.assertNotNull(toGetOne);
        Assert.assertSame(1, toGetOne.getSightingId());
        Assert.assertSame(1, toGetOne.getLocationId());
        Assert.assertEquals(locationOne, toGetOne.getDate());

        Sighting toGetFour = dao.getSightingById(4);
        Assert.assertNotNull(toGetFour);
        Assert.assertSame(4, toGetFour.getSightingId());
        Assert.assertSame(3, toGetFour.getLocationId());
        Assert.assertEquals(locationThreeTwo, toGetFour.getDate());

    }

    @Test
    public void testGetSightingByIdFailInvalidId() throws SightingPersistenceException {
        try {
            dao.getSightingById(5);
            junit.framework.Assert.fail("Expected SightingPersistenceException was not thrown.");
        } catch (SightingPersistenceException ex) {

        }

    }

    /**
     * Test of getAllSightings method, of class SightingDbDao.
     */
    @Test
    public void testGetAllSightingsSuccess() throws SightingPersistenceException {

        LocalDate locationOne = LocalDate.of(2019, 03, 03);
        LocalDate locationThreeTwo = LocalDate.of(2017, 02, 01);

        List<Sighting> allSightings = dao.getAllSightings();
        Assert.assertNotNull(allSightings);
        Assert.assertEquals(4, allSightings.size());

        Sighting toGetOne = allSightings.get(0);
        Assert.assertNotNull(toGetOne);
        Assert.assertSame(1, toGetOne.getSightingId());
        Assert.assertSame(1, toGetOne.getLocationId());
        Assert.assertEquals(locationOne, toGetOne.getDate());

        Sighting toGetFour = allSightings.get(allSightings.size() - 1);
        Assert.assertNotNull(toGetFour);
        Assert.assertSame(4, toGetFour.getSightingId());
        Assert.assertSame(3, toGetFour.getLocationId());
        Assert.assertEquals(locationThreeTwo, toGetFour.getDate());
    }

    /**
     * Test of addSighting method, of class SightingDbDao.
     */
    @Test
    public void testAddSightingSuccess() throws LocationPersistenceException, SightingPersistenceException {
        LocalDate toAddOne = LocalDate.of(2017, 04, 13);
        LocalDate toAddTwo = LocalDate.of(2016, 04, 13);

        List<Sighting> allSightings = dao.getAllSightings();
        Assert.assertNotNull(allSightings);
        Assert.assertEquals(4, allSightings.size());

        Sighting toAdd = new Sighting();
        toAdd.setDate(toAddOne);
        toAdd.setLocationId(1);

        dao.addSighting(toAdd);

        List<Sighting> allSightingsNew = dao.getAllSightings();
        Assert.assertEquals(5, allSightingsNew.size());
        Assert.assertNotNull(allSightingsNew);

        Sighting addedSighting = allSightingsNew.get(allSightingsNew.size() - 1);
        Assert.assertNotNull(addedSighting);
        Assert.assertSame(5, addedSighting.getSightingId());
        Assert.assertSame(1, addedSighting.getLocationId());

        Sighting toAddAgain = new Sighting();
        toAddAgain.setDate(toAddTwo);
        toAddAgain.setLocationId(2);

        dao.addSighting(toAddAgain);

        List<Sighting> allSightingsNewTwo = dao.getAllSightings();
        Assert.assertEquals(6, allSightingsNewTwo.size());
        Assert.assertNotNull(allSightingsNewTwo);

        Sighting addedSightingTwo = allSightingsNewTwo.get(allSightingsNewTwo.size() - 1);
        Assert.assertNotNull(addedSightingTwo);
        Assert.assertSame(6, addedSightingTwo.getSightingId());
        Assert.assertSame(2, addedSightingTwo.getLocationId());

    }

    @Test
    public void testAddSightingFailNoDate() throws SightingPersistenceException {
        try {
            Sighting toAdd = new Sighting();
            toAdd.setLocationId(1);

            dao.addSighting(toAdd);

            Assert.fail("Expected SightingPersistenceException was not thrown.");
        } catch (SightingPersistenceException ex) {

        }
    }

    @Test
    public void testAddSightingFailNoLocationId() throws SightingPersistenceException {
        LocalDate toAddOne = LocalDate.of(2017, 04, 13);
        try {
            Sighting toAdd = new Sighting();
            toAdd.setDate(toAddOne);

            dao.addSighting(toAdd);

            Assert.fail("Expected SightingPersistenceException was not thrown.");
        } catch (SightingPersistenceException ex) {

        }
    }

    /**
     * Test of updateSighting method, of class SightingDbDao.
     */
    @Test
    public void testUpdateSightingSuccess() throws LocationPersistenceException, SightingPersistenceException {
        LocalDate toAddOne = LocalDate.of(2017, 04, 13);
        LocalDate toAddTwo = LocalDate.of(2016, 04, 13);
        LocalDate locationOne = LocalDate.of(2019, 03, 03);
        LocalDate locationThreeTwo = LocalDate.of(2017, 02, 01);

        List<Sighting> allSightings = dao.getAllSightings();
        Assert.assertNotNull(allSightings);
        Assert.assertEquals(4, allSightings.size());

        Sighting toEditOne = allSightings.get(0);
        Assert.assertNotNull(toEditOne);
        Assert.assertSame(1, toEditOne.getSightingId());
        Assert.assertSame(1, toEditOne.getLocationId());
        Assert.assertEquals(locationOne, toEditOne.getDate());

        toEditOne.setDate(toAddOne);
        toEditOne.setLocationId(2);

        dao.updateSighting(toEditOne);

        Sighting EditedOne = dao.getSightingById(1);

        Assert.assertNotNull(EditedOne);
        Assert.assertSame(1, EditedOne.getSightingId());
        Assert.assertSame(2, EditedOne.getLocationId());
        Assert.assertEquals(toAddOne, EditedOne.getDate());

        Sighting toEditFour = allSightings.get(allSightings.size() - 1);
        Assert.assertNotNull(toEditFour);
        Assert.assertSame(4, toEditFour.getSightingId());
        Assert.assertSame(3, toEditFour.getLocationId());
        Assert.assertEquals(locationThreeTwo, toEditFour.getDate());

        toEditFour.setDate(toAddTwo);
        toEditFour.setLocationId(1);

        dao.updateSighting(toEditFour);

        Sighting EditedFour = dao.getSightingById(4);

        Assert.assertNotNull(EditedFour);
        Assert.assertSame(4, EditedFour.getSightingId());
        Assert.assertSame(1, EditedFour.getLocationId());
        Assert.assertEquals(toAddTwo, EditedFour.getDate());

    }

    @Test
    public void testUpdateSightingFailNoDate() throws SightingPersistenceException {
        try {
            Sighting toEdit = dao.getSightingById(1);
            toEdit.setLocationId(1);
            toEdit.setDate(null);

            dao.updateSighting(toEdit);

            Assert.fail("Expected SightingPersistenceException was not thrown.");
        } catch (SightingPersistenceException ex) {

        }
    }

    @Test
    public void testUpdateSightingFailNoLocationId() throws SightingPersistenceException {
        LocalDate toAddOne = LocalDate.of(2017, 04, 13);
        try {
            Sighting toEdit = dao.getSightingById(1);
            toEdit.setDate(toAddOne);
            toEdit.setLocationId(null);

            dao.updateSighting(toEdit);

            Assert.fail("Expected SightingPersistenceException was not thrown.");
        } catch (SightingPersistenceException ex) {

        }
    }

    /**
     * Test of deleteSightingById method, of class SightingDbDao.
     */
    @Test
    public void testDeleteSightingByIdSuccess() throws SightingPersistenceException {

        Sighting toDeleteOne = dao.getSightingById(1);
        List<Sighting> allSightings = dao.getAllSightings();
        junit.framework.Assert.assertEquals(4, allSightings.size());
        junit.framework.Assert.assertNotNull(toDeleteOne);
        junit.framework.Assert.assertSame(1, toDeleteOne.getSightingId());

        Assert.assertNotNull(toDeleteOne);
        Assert.assertSame(1, toDeleteOne.getSightingId());
        Assert.assertSame(1, toDeleteOne.getLocationId());

        dao.deleteSightingById(1);

        List<Sighting> allSightingsOneDeleted = dao.getAllSightings();
        junit.framework.Assert.assertEquals(3, allSightingsOneDeleted.size());
        junit.framework.Assert.assertSame(2, allSightingsOneDeleted.get(0).getSightingId());

        Sighting toDeleteTwo = dao.getSightingById(2);
        junit.framework.Assert.assertNotNull(toDeleteTwo);
        junit.framework.Assert.assertSame(2, toDeleteTwo.getSightingId());

        dao.deleteSightingById(2);

        List<Sighting> allSightingsTwoDeleted = dao.getAllSightings();
        junit.framework.Assert.assertEquals(2, allSightingsTwoDeleted.size());
        junit.framework.Assert.assertSame(3, allSightingsTwoDeleted.get(0).getSightingId());

    }

    @Test
    public void testDeleteSuperByIdFail() throws SightingPersistenceException {
        try {
            dao.deleteSightingById(15);
            junit.framework.Assert.fail("Expected SightingPersistenceException was not thrown.");
        } catch (SightingPersistenceException ex) {

        }

    }

    /**
     * Test of getSightingsBySuperId method, of class SightingDbDao.
     */
    @Test
    public void testGetSightingsBySuperIdSuccess() throws SightingPersistenceException {
        List<Sighting> allSightingsSuperOne = dao.getSightingsBySuperId(1);
        Assert.assertNotNull(allSightingsSuperOne);
        Assert.assertEquals(2, allSightingsSuperOne.size());

        List<Sighting> allSightingsSuperThree = dao.getSightingsBySuperId(3);
        Assert.assertNotNull(allSightingsSuperThree);
        Assert.assertEquals(2, allSightingsSuperThree.size());
    }

    /**
     * Test of getSightingsByLocationId method, of class SightingDbDao.
     */
    @Test
    public void testGetSightingsByLocationIdSuccess() throws SightingPersistenceException {
        List<Sighting> allSightingsLocationOne = dao.getSightingsByLocationId(1);
        Assert.assertNotNull(allSightingsLocationOne);
        Assert.assertEquals(1, allSightingsLocationOne.size());

        List<Sighting> allSightingsLocationThree = dao.getSightingsByLocationId(3);
        Assert.assertNotNull(allSightingsLocationThree);
        Assert.assertEquals(2, allSightingsLocationThree.size());
    }
}
