/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroSightings.daos;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.sg.superheroSightings.models.Location;
import com.sg.superheroSightings.models.Sighting;
import com.sg.superheroSightings.models.Super;
import java.sql.SQLException;
import java.util.List;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
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
public class LocationDbDaoTest {

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    LocationDbDao dao;

    public LocationDbDaoTest() throws SQLException {
        MysqlDataSource ds = new MysqlDataSource();
        ds.setServerName("localhost");
        ds.setDatabaseName("superheroSightingsTest");
        ds.setUser("root");
        ds.setPassword("password");
        ds.setServerTimezone("America/Chicago");
        ds.setUseSSL(false);
        ds.setAllowPublicKeyRetrieval(true);
        this.jdbc = new JdbcTemplate(ds);
        this.dao = new LocationDbDao(jdbc);

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
     * Test of getLocationById method, of class LocationDbDao.
     */
    @Test
    public void testGetLocationByIdSuccess() throws LocationPersistenceException {

        Location toGetOne = dao.getLocationById(1);
        Assert.assertNotNull(toGetOne);
        Assert.assertSame(1, toGetOne.getLocationId());
        Assert.assertEquals("Casa Jacob", toGetOne.getName());
        Assert.assertEquals("Chill", toGetOne.getDescription());
        Assert.assertEquals("123 North Street Alpharetta, GA 30022", toGetOne.getAddress());
        Assert.assertEquals(-23.029365, toGetOne.getLatitude(), 0.00001);
        Assert.assertEquals(43.654734, toGetOne.getLongitude(), 0.00001);

        Location toGetThree = dao.getLocationById(3);
        Assert.assertNotNull(toGetThree);
        Assert.assertSame(3, toGetThree.getLocationId());
        Assert.assertEquals("Home", toGetThree.getName());
        Assert.assertEquals("Casa", toGetThree.getDescription());
        Assert.assertEquals("1522 Portland Avenue #309, St. Paul, MN 55104", toGetThree.getAddress());
        Assert.assertEquals(-24.029365, toGetThree.getLatitude(), 0.00001);
        Assert.assertEquals(45.654734, toGetThree.getLongitude(), 0.00001);
    }

    @Test
    public void testGetLocationByIdFailInvalidId() throws LocationPersistenceException {
        try {
            dao.getLocationById(5);
            Assert.fail("Expected LocationPersistenceException was not thrown.");
        } catch (LocationPersistenceException ex) {

        }
    }

    /**
     * Test of getAllLocations method, of class LocationDbDao.
     */
    @Test
    public void testGetAllLocationsSuccess() throws LocationPersistenceException {
        List<Location> allLocations = dao.getAllLocations();
        Assert.assertEquals(3, allLocations.size());
        Assert.assertNotNull(allLocations);

        Location locationOne = allLocations.get(0);
        Assert.assertSame(1, locationOne.getLocationId());
        Assert.assertEquals("Casa Jacob", locationOne.getName());
        Assert.assertEquals("Chill", locationOne.getDescription());
        Assert.assertEquals("123 North Street Alpharetta, GA 30022", locationOne.getAddress());
        Assert.assertEquals(-23.029365, locationOne.getLatitude(), .00001);
        Assert.assertEquals(43.654734, locationOne.getLongitude(), .00001);

        Location locationThree = allLocations.get(2);
        Assert.assertSame(3, locationThree.getLocationId());
        Assert.assertEquals("Home", locationThree.getName());
        Assert.assertEquals("Casa", locationThree.getDescription());
        Assert.assertEquals("1522 Portland Avenue #309, St. Paul, MN 55104", locationThree.getAddress());
        Assert.assertEquals(-24.029365, locationThree.getLatitude(), .00001);
        Assert.assertEquals(45.654734, locationThree.getLongitude(), .00001);
    }

    /**
     * Test of addLocation method, of class LocationDbDao.
     */
    @Test
    public void testAddLocationSuccess() throws LocationPersistenceException {
        List<Location> allLocations = dao.getAllLocations();
        Assert.assertEquals(3, allLocations.size());
        Assert.assertNotNull(allLocations);

        Location toAdd = new Location();
        toAdd.setName("Test House");
        toAdd.setDescription("Testing One Two");
        toAdd.setAddress("124 Lets Test Lane, Testing, MN 55165");
        toAdd.setLatitude(24.637495);
        toAdd.setLongitude(-24.659837);
        dao.addLocation(toAdd);

        List<Location> allLocationsNew = dao.getAllLocations();
        Assert.assertEquals(4, allLocationsNew.size());
        Assert.assertNotNull(allLocationsNew);

        Location addedLocation = allLocationsNew.get(allLocationsNew.size() - 1);
        Assert.assertNotNull(addedLocation);
        Assert.assertSame(4, addedLocation.getLocationId());
        Assert.assertEquals("Test House", addedLocation.getName());
        Assert.assertEquals("Testing One Two", addedLocation.getDescription());
        Assert.assertEquals("124 Lets Test Lane, Testing, MN 55165", addedLocation.getAddress());
        Assert.assertEquals(24.637495, addedLocation.getLatitude(), 0.00001);
        Assert.assertEquals(-24.659837, addedLocation.getLongitude(), 0.00001);

        Location toAddTwo = new Location();
        toAddTwo.setName("Test House 2");
        toAddTwo.setDescription("Testing One Two three");
        toAddTwo.setAddress("124 Lets Test Lane Two, Testing, MN 55165");
        toAddTwo.setLatitude(34.637495);
        toAddTwo.setLongitude(-34.659837);
        dao.addLocation(toAddTwo);

        List<Location> allLocationsNewTwo = dao.getAllLocations();
        Assert.assertEquals(5, allLocationsNewTwo.size());
        Assert.assertNotNull(allLocationsNewTwo);

        Location addedLocationTwo = allLocationsNewTwo.get(allLocationsNewTwo.size() - 1);
        Assert.assertNotNull(addedLocationTwo);
        Assert.assertSame(5, addedLocationTwo.getLocationId());
        Assert.assertEquals("Test House 2", addedLocationTwo.getName());
        Assert.assertEquals("Testing One Two three", addedLocationTwo.getDescription());
        Assert.assertEquals("124 Lets Test Lane Two, Testing, MN 55165", addedLocationTwo.getAddress());
        Assert.assertEquals(34.637495, addedLocationTwo.getLatitude(), 0.00001);
        Assert.assertEquals(-34.659837, addedLocationTwo.getLongitude(), 0.00001);

    }

    @Test
    public void testAddLocationFailNoName() throws LocationPersistenceException {
        try {
            Location toAdd = new Location();
            toAdd.setDescription("Testing One Two");
            toAdd.setAddress("124 Lets Test Lane, Testing, MN 55165");
            toAdd.setLatitude(24.637495);
            toAdd.setLongitude(-24.659837);
            dao.addLocation(toAdd);
            Assert.fail("Expected LocationPersistenceException was not thrown.");
        } catch (LocationPersistenceException ex) {

        }
    }

    @Test
    public void testAddLocationFailOverMaxName() throws LocationPersistenceException {
        try {
            Location toAdd = new Location();
            toAdd.setName("hsjdjfkgkdjfhgjfjdskalskdjtifhq");
            toAdd.setDescription("Testing One Two");
            toAdd.setAddress("124 Lets Test Lane, Testing, MN 55165");
            toAdd.setLatitude(24.637495);
            toAdd.setLongitude(-24.659837);
            dao.addLocation(toAdd);
            Assert.fail("Expected LocationPersistenceException was not thrown.");
        } catch (LocationPersistenceException ex) {

        }
    }

    @Test
    public void testAddLocationFailNoDescription() throws LocationPersistenceException {
        try {
            Location toAdd = new Location();
            toAdd.setName("DC");
            toAdd.setAddress("124 Lets Test Lane, Testing, MN 55165");
            toAdd.setLatitude(24.637495);
            toAdd.setLongitude(-24.659837);
            dao.addLocation(toAdd);
            Assert.fail("Expected LocationPersistenceException was not thrown.");
        } catch (LocationPersistenceException ex) {

        }
    }

    @Test
    public void testAddLocationFailOverMaxDescription() throws LocationPersistenceException {
        try {
            Location toAdd = new Location();
            toAdd.setName("DC");
            toAdd.setDescription("hsjdjfkgkdjfhgjfjdskalskdjtifhqhsjdhfjgkfkfkfkfkfkf");
            toAdd.setAddress("124 Lets Test Lane, Testing, MN 55165");
            toAdd.setLatitude(24.637495);
            toAdd.setLongitude(-24.659837);
            dao.addLocation(toAdd);
            Assert.fail("Expected LocationPersistenceException was not thrown.");
        } catch (LocationPersistenceException ex) {

        }
    }

    @Test
    public void testAddLocationFailNoAddress() throws LocationPersistenceException {
        try {
            Location toAdd = new Location();
            toAdd.setName("DC");
            toAdd.setDescription("A chill place");
            toAdd.setLatitude(24.637495);
            toAdd.setLongitude(-24.659837);
            dao.addLocation(toAdd);
            Assert.fail("Expected LocationPersistenceException was not thrown.");
        } catch (LocationPersistenceException ex) {

        }
    }

    @Test
    public void testAddLocationFailOverMaxAddress() throws LocationPersistenceException {
        try {
            Location toAdd = new Location();
            toAdd.setName("DC");
            toAdd.setDescription("A chill place");
            toAdd.setAddress("lalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalal");
            toAdd.setLatitude(24.637495);
            toAdd.setLongitude(-24.659837);
            dao.addLocation(toAdd);
            Assert.fail("Expected LocationPersistenceException was not thrown.");
        } catch (LocationPersistenceException ex) {

        }
    }

    @Test
    public void testAddLocationFailOverMaxIntLatitude() throws LocationPersistenceException {
        try {
            Location toAdd = new Location();
            toAdd.setName("DC");
            toAdd.setAddress("123 Floor St.");
            toAdd.setDescription("A chill place");
            toAdd.setLatitude(124.637495);
            toAdd.setLongitude(-24.659837);
            dao.addLocation(toAdd);
            Assert.fail("Expected LocationPersistenceException was not thrown.");
        } catch (LocationPersistenceException ex) {

        }
    }

    @Test
    public void testAddLocationFailNullLatitude() throws LocationPersistenceException {
        try {
            Location toAdd = new Location();
            toAdd.setName("DC");
            toAdd.setAddress("123 Floor St.");
            toAdd.setDescription("A chill place");
            toAdd.setLongitude(-24.659837);
            dao.addLocation(toAdd);
            Assert.fail("Expected LocationPersistenceException was not thrown.");
        } catch (LocationPersistenceException ex) {

        }
    }

    @Test
    public void testAddLocationFailOverMaxIntLongitude() throws LocationPersistenceException {
        try {
            Location toAdd = new Location();
            toAdd.setName("DC");
            toAdd.setAddress("123 Floor St.");
            toAdd.setDescription("A chill place");
            toAdd.setLatitude(24.637495);
            toAdd.setLongitude(1224.659837);
            dao.addLocation(toAdd);
            Assert.fail("Expected LocationPersistenceException was not thrown.");
        } catch (LocationPersistenceException ex) {

        }
    }

    @Test
    public void testAddLocationFailNullLongitude() throws LocationPersistenceException {
        try {
            Location toAdd = new Location();
            toAdd.setName("DC");
            toAdd.setAddress("123 Floor St.");
            toAdd.setDescription("A chill place");
            toAdd.setLatitude(-24.659837);
            dao.addLocation(toAdd);
            Assert.fail("Expected LocationPersistenceException was not thrown.");
        } catch (LocationPersistenceException ex) {

        }
    }

    /**
     * Test of updateLocation method, of class LocationDbDao.
     */
    @Test
    public void testUpdateLocationSuccess() throws LocationPersistenceException {
        List<Location> allLocations = dao.getAllLocations();
        Assert.assertEquals(3, allLocations.size());
        Assert.assertNotNull(allLocations);

        Location toEditOne = dao.getLocationById(1);
        Assert.assertNotNull(toEditOne);
        Assert.assertSame(1, toEditOne.getLocationId());
        Assert.assertEquals("Casa Jacob", toEditOne.getName());
        Assert.assertEquals("Chill", toEditOne.getDescription());
        Assert.assertEquals("123 North Street Alpharetta, GA 30022", toEditOne.getAddress());
        Assert.assertEquals(-23.029365, toEditOne.getLatitude(), 0.00001);
        Assert.assertEquals(43.654734, toEditOne.getLongitude(), 0.00001);

        toEditOne.setName("Edited loc");
        toEditOne.setDescription("We be editing");
        toEditOne.setAddress("123 Let's Edit Lane, Saint Paul, MN 55104");
        toEditOne.setLatitude(11.111111);
        toEditOne.setLongitude(12.222222);

        Location editedLocation = dao.updateLocation(toEditOne);
        Assert.assertNotNull(editedLocation);
        Assert.assertSame(1, editedLocation.getLocationId());
        Assert.assertEquals("Edited loc", editedLocation.getName());
        Assert.assertEquals("We be editing", editedLocation.getDescription());
        Assert.assertEquals("123 Let's Edit Lane, Saint Paul, MN 55104", editedLocation.getAddress());
        Assert.assertEquals(11.111111, editedLocation.getLatitude(), 0.00001);
        Assert.assertEquals(12.222222, editedLocation.getLongitude(), 0.00001);

        List<Location> allLocationsTwo = dao.getAllLocations();
        Assert.assertEquals(3, allLocationsTwo.size());
        Assert.assertNotNull(allLocationsTwo);

    }

    @Test
    public void testUpdateLocationFailNoName() throws LocationPersistenceException {
        try {
            Location toEdit = new Location();
            toEdit.setDescription("Testing One Two");
            toEdit.setAddress("124 Lets Test Lane, Testing, MN 55165");
            toEdit.setLatitude(24.637495);
            toEdit.setLongitude(-24.659837);
            dao.updateLocation(toEdit);
            Assert.fail("Expected LocationPersistenceException was not thrown.");
        } catch (LocationPersistenceException ex) {

        }
    }

    @Test
    public void testUpdateLocationFailOverMaxName() throws LocationPersistenceException {
        try {
            Location toEdit = new Location();
            toEdit.setName("hsjdjfkgkdjfhgjfjdskalskdjtifhq");
            toEdit.setDescription("Testing One Two");
            toEdit.setAddress("124 Lets Test Lane, Testing, MN 55165");
            toEdit.setLatitude(24.637495);
            toEdit.setLongitude(-24.659837);
            dao.updateLocation(toEdit);
            Assert.fail("Expected LocationPersistenceException was not thrown.");
        } catch (LocationPersistenceException ex) {

        }
    }

    @Test
    public void testUpdateLocationFailNoDescription() throws LocationPersistenceException {
        try {
            Location toEdit = new Location();
            toEdit.setName("DC");
            toEdit.setAddress("124 Lets Test Lane, Testing, MN 55165");
            toEdit.setLatitude(24.637495);
            toEdit.setLongitude(-24.659837);
            dao.updateLocation(toEdit);
            Assert.fail("Expected LocationPersistenceException was not thrown.");
        } catch (LocationPersistenceException ex) {

        }
    }

    @Test
    public void testUpdateLocationFailOverMaxDescription() throws LocationPersistenceException {
        try {
            Location toEdit = new Location();
            toEdit.setName("DC");
            toEdit.setDescription("hsjdjfkgkdjfhgjfjdskalskdjtifhqhsjdhfjgkfkfkfkfkfkf");
            toEdit.setAddress("124 Lets Test Lane, Testing, MN 55165");
            toEdit.setLatitude(24.637495);
            toEdit.setLongitude(-24.659837);
            dao.updateLocation(toEdit);
            Assert.fail("Expected LocationPersistenceException was not thrown.");
        } catch (LocationPersistenceException ex) {

        }
    }

    @Test
    public void testUpdateLocationFailNoAddress() throws LocationPersistenceException {
        try {
            Location toEdit = new Location();
            toEdit.setName("DC");
            toEdit.setDescription("A chill place");
            toEdit.setLatitude(24.637495);
            toEdit.setLongitude(-24.659837);
            dao.updateLocation(toEdit);
            Assert.fail("Expected LocationPersistenceException was not thrown.");
        } catch (LocationPersistenceException ex) {

        }
    }

    @Test
    public void testUpdateLocationFailOverMaxAddress() throws LocationPersistenceException {
        try {
            Location toEdit = new Location();
            toEdit.setName("DC");
            toEdit.setDescription("A chill place");
            toEdit.setAddress("lalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalal");
            toEdit.setLatitude(24.637495);
            toEdit.setLongitude(-24.659837);
            dao.updateLocation(toEdit);
            Assert.fail("Expected LocationPersistenceException was not thrown.");
        } catch (LocationPersistenceException ex) {

        }
    }

    @Test
    public void testUpdateLocationFailOverMaxIntLatitude() throws LocationPersistenceException {
        try {
            Location toEdit = new Location();
            toEdit.setName("DC");
            toEdit.setAddress("123 Floor St.");
            toEdit.setDescription("A chill place");
            toEdit.setLatitude(124.637495);
            toEdit.setLongitude(-24.659837);
            dao.updateLocation(toEdit);
            Assert.fail("Expected LocationPersistenceException was not thrown.");
        } catch (LocationPersistenceException ex) {

        }
    }

    @Test
    public void testUpdateLocationFailNullLatitude() throws LocationPersistenceException {
        try {
            Location toEdit = new Location();
            toEdit.setName("DC");
            toEdit.setAddress("123 Floor St.");
            toEdit.setDescription("A chill place");
            toEdit.setLongitude(-24.659837);
            dao.updateLocation(toEdit);
            Assert.fail("Expected LocationPersistenceException was not thrown.");
        } catch (LocationPersistenceException ex) {

        }
    }

    @Test
    public void testUpdateLocationFailOverMaxIntLongitude() throws LocationPersistenceException {
        try {
            Location toEdit = new Location();
            toEdit.setName("DC");
            toEdit.setAddress("123 Floor St.");
            toEdit.setDescription("A chill place");
            toEdit.setLatitude(24.637495);
            toEdit.setLongitude(1224.659837);
            dao.updateLocation(toEdit);
            Assert.fail("Expected LocationPersistenceException was not thrown.");
        } catch (LocationPersistenceException ex) {

        }
    }

    @Test
    public void testUpdateLocationFailNullLongitude() throws LocationPersistenceException {
        try {
            Location toEdit = new Location();
            toEdit.setName("DC");
            toEdit.setAddress("123 Floor St.");
            toEdit.setDescription("A chill place");
            toEdit.setLatitude(-24.659837);
            dao.updateLocation(toEdit);
            Assert.fail("Expected LocationPersistenceException was not thrown.");
        } catch (LocationPersistenceException ex) {

        }
    }

    /**
     * Test of deleteLocationById method, of class LocationDbDao.
     */
    @Test
    public void testDeleteLocationByIdSuccess() throws LocationPersistenceException {

        Location toDeleteOne = dao.getLocationById(1);
        List<Location> allLocations = dao.getAllLocations();
        Assert.assertEquals(3, allLocations.size());
        Assert.assertNotNull(toDeleteOne);
        Assert.assertSame(1, toDeleteOne.getLocationId());

        deleteSightings(toDeleteOne.getLocationId());

        dao.deleteLocationById(1);

        List<Location> allLocationsOneDeleted = dao.getAllLocations();
        Assert.assertEquals(2, allLocationsOneDeleted.size());
        Assert.assertSame(2, allLocationsOneDeleted.get(0).getLocationId());

        Location toDeleteTwo = dao.getLocationById(2);
        Assert.assertNotNull(toDeleteTwo);
        Assert.assertSame(2, toDeleteTwo.getLocationId());
        deleteSightings(toDeleteTwo.getLocationId());

        dao.deleteLocationById(2);
        List<Location> allLocationsTwoDeleted = dao.getAllLocations();
        Assert.assertEquals(1, allLocationsTwoDeleted.size());
        Assert.assertSame(3, allLocationsTwoDeleted.get(0).getLocationId());

    }

    @Test
    public void testDeleteLocationByIdFail() throws LocationPersistenceException {
        try {
            dao.deleteLocationById(5);
            Assert.fail("Expected LocationPersistenceException was not thrown.");
        } catch (LocationPersistenceException ex) {

        }

    }

    private void deleteSightings(int locationId) {

        String bridgeDelete = "DELETE FROM superheroSightingsTest.Super_Sightings WHERE sightingId IN (SELECT sightingId FROM Sightings WHERE locationId = ?)";

        String query = "DELETE FROM superheroSightingsTest.Sightings WHERE locationId = ?";

        jdbc.update(bridgeDelete, locationId);
        jdbc.update(query, locationId);

    }
}
