/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroSightings.daos;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.sg.superheroSightings.models.Organization;
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
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author afahrenkamp
 */
public class SuperDbDaoTest {

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    SuperDbDao dao;

    public SuperDbDaoTest() throws SQLException {
        MysqlDataSource ds = new MysqlDataSource();
        ds.setServerName("localhost");
        ds.setDatabaseName("superheroSightingsTest");
        ds.setUser("root");
        ds.setPassword("password");
        ds.setServerTimezone("America/Chicago");
        ds.setUseSSL(false);
        ds.setAllowPublicKeyRetrieval(true);
        this.jdbc = new JdbcTemplate(ds);
        this.dao = new SuperDbDao(jdbc);

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
     * Test of getSuperById method, of class SuperDbDao.
     */
    @Test
    public void testGetSuperByIdSuccess() throws SuperPersistenceException {

        Super toGetOne = dao.getSuperById(1);
        Assert.assertNotNull(toGetOne);
        Assert.assertSame(1, toGetOne.getSuperId());
        Assert.assertEquals("Superman", toGetOne.getName());
        Assert.assertEquals("So super", toGetOne.getDescription());
        Assert.assertEquals("flight", toGetOne.getSuperpower());

        Super toGetThree = dao.getSuperById(3);
        Assert.assertNotNull(toGetThree);
        Assert.assertSame(3, toGetThree.getSuperId());
        Assert.assertEquals("Freeze", toGetThree.getName());
        Assert.assertEquals("cold", toGetThree.getDescription());
        Assert.assertEquals("icing", toGetThree.getSuperpower());

    }

    @Test
    public void testGetSuperByIdFailInvalidId() throws SuperPersistenceException {

        try {
            dao.getSuperById(5);
            junit.framework.Assert.fail("Expected SuperPersistenceException was not thrown.");
        } catch (SuperPersistenceException ex) {

        }
    }

    /**
     * Test of getAllSupers method, of class SuperDbDao.
     */
    @Test
    public void testGetAllSupers() throws SuperPersistenceException {

        List<Super> allSupers = dao.getAllSupers();
        Assert.assertNotNull(allSupers);
        Assert.assertEquals(3, allSupers.size());

        Super toGetOne = allSupers.get(0);
        Assert.assertNotNull(toGetOne);
        Assert.assertSame(1, toGetOne.getSuperId());
        Assert.assertEquals("Superman", toGetOne.getName());
        Assert.assertEquals("So super", toGetOne.getDescription());
        Assert.assertEquals("flight", toGetOne.getSuperpower());

        Super toGetThree = allSupers.get(allSupers.size() - 1);
        Assert.assertNotNull(toGetThree);
        Assert.assertSame(3, toGetThree.getSuperId());
        Assert.assertEquals("Freeze", toGetThree.getName());
        Assert.assertEquals("cold", toGetThree.getDescription());
        Assert.assertEquals("icing", toGetThree.getSuperpower());

    }

    /**
     * Test of addSuper method, of class SuperDbDao.
     */
    @Test
    public void testAddSuperSuccess() throws SuperPersistenceException {

        List<Super> allSupers = dao.getAllSupers();
        junit.framework.Assert.assertEquals(3, allSupers.size());
        junit.framework.Assert.assertNotNull(allSupers);

        Super toAdd = new Super();
        toAdd.setName("Best Super");
        toAdd.setDescription("The best");
        toAdd.setSuperpower("coolin");
        dao.addSuper(toAdd);

        List<Super> allSupersNew = dao.getAllSupers();
        junit.framework.Assert.assertEquals(4, allSupersNew.size());
        junit.framework.Assert.assertNotNull(allSupersNew);

        Super addedSuper = allSupersNew.get(allSupersNew.size() - 1);
        junit.framework.Assert.assertNotNull(addedSuper);
        junit.framework.Assert.assertSame(4, addedSuper.getSuperId());
        junit.framework.Assert.assertEquals("Best Super", addedSuper.getName());
        junit.framework.Assert.assertEquals("The best", addedSuper.getDescription());
        junit.framework.Assert.assertEquals("coolin", addedSuper.getSuperpower());

        Super toAddTwo = new Super();
        toAddTwo.setName("Best Super Two");
        toAddTwo.setDescription("The best Two");
        toAddTwo.setSuperpower("heatin");
        dao.addSuper(toAddTwo);

        List<Super> allSupersNewTwo = dao.getAllSupers();
        junit.framework.Assert.assertEquals(5, allSupersNewTwo.size());
        junit.framework.Assert.assertNotNull(allSupersNewTwo);

        Super addedSuperTwo = allSupersNewTwo.get(allSupersNewTwo.size() - 1);
        Assert.assertSame(5, addedSuperTwo.getSuperId());
        Assert.assertEquals("Best Super Two", addedSuperTwo.getName());
        Assert.assertEquals("The best Two", addedSuperTwo.getDescription());
        Assert.assertEquals("heatin", addedSuperTwo.getSuperpower());
    }

    @Test
    public void testAddSuperFailNoName() throws SuperPersistenceException {
        try {
            Super toAdd = new Super();
            toAdd.setDescription("The best Two");
            toAdd.setSuperpower("heatin");
            dao.addSuper(toAdd);
            junit.framework.Assert.fail("Expected SuperPersistenceException was not thrown.");
        } catch (SuperPersistenceException ex) {

        }
    }

    @Test
    public void testAddSuperFailMaxName() throws SuperPersistenceException {
        try {
            Super toAdd = new Super();
            toAdd.setName("llllllllllaaaaaaaaaaaalllllllll");
            toAdd.setDescription("The best Two");
            toAdd.setSuperpower("heatin");
            dao.addSuper(toAdd);
            junit.framework.Assert.fail("Expected SuperPersistenceException was not thrown.");
        } catch (SuperPersistenceException ex) {

        }
    }

    @Test
    public void testAddSuperFailNoDescription() throws SuperPersistenceException {
        try {
            Super toAdd = new Super();
            toAdd.setName("Best Super");
            toAdd.setSuperpower("coolin");
            dao.addSuper(toAdd);
            junit.framework.Assert.fail("Expected SuperPersistenceException was not thrown.");
        } catch (SuperPersistenceException ex) {

        }
    }

    @Test
    public void testAddSuperFailMaxDescription() throws OrganizationPersistenceException {
        try {
            Super toAdd = new Super();
            toAdd.setName("Best Super");
            toAdd.setDescription("dsdsdsddsssssjsjsjsjsjsjsjsjsjsjsjsjsjsjsjllllllllllllllllllljsjsjsjsjsjsjsjsjsjsjsjsjsjsjsjlllllllllllllllllll");
            toAdd.setSuperpower("coolin");
            dao.addSuper(toAdd);
            junit.framework.Assert.fail("Expected SuperPersistenceException was not thrown.");
        } catch (SuperPersistenceException ex) {

        }
    }

    @Test
    public void testAddSuperFailNoSuperpower() throws OrganizationPersistenceException {
        try {
            Super toAdd = new Super();
            toAdd.setName("Best Super");
            toAdd.setDescription("The best");
            dao.addSuper(toAdd);
            junit.framework.Assert.fail("Expected SuperPersistenceException was not thrown.");
        } catch (SuperPersistenceException ex) {

        }
    }

    @Test
    public void testAddSuperFailMaxSuperpower() throws SuperPersistenceException {
        try {
            Super toAdd = new Super();
            toAdd.setName("Best Super");
            toAdd.setDescription("The best");
            toAdd.setSuperpower("dsdsdsddsssssjsjsjsjsjsjsjsjsjsjsjsjsjsjsjllllllllllllllllllljsjsjsjsjsjsjsjsjsjsjsjsjsjsjsjlllllllllllllllllll");
            dao.addSuper(toAdd);
            junit.framework.Assert.fail("Expected SuperPersistenceException was not thrown.");
        } catch (SuperPersistenceException ex) {

        }
    }

    /**
     * Test of updateSuper method, of class SuperDbDao.
     */
    @Test
    public void testUpdateSuperSuccess() throws SuperPersistenceException {

        Organization orgOne = new Organization();
        orgOne.setOrganizationId(1);
        orgOne.setName("Marvel");
        orgOne.setDescription("Marveling");
        orgOne.setAddress("456 Test Avenue, Saint Paul, MN 55104");
        orgOne.setPhoneNumber("7702410271");
        orgOne.setEmail("marvel@marvel.com");

        Organization orgTwo = new Organization();
        orgTwo.setOrganizationId(2);
        orgTwo.setName("DC");
        orgTwo.setDescription("DC What");
        orgTwo.setAddress("456 Test Street, Saint Paul, MN 55104");
        orgTwo.setPhoneNumber("7705181492");
        orgTwo.setEmail("dc@dc.com");       
        
        Super toEdit = dao.getSuperById(1);

        Assert.assertNotNull(toEdit);
        Assert.assertSame(1, toEdit.getSuperId());
        Assert.assertEquals("Superman", toEdit.getName());
        Assert.assertEquals("So super", toEdit.getDescription());
        Assert.assertEquals("flight", toEdit.getSuperpower());

        toEdit.setName("Best Super");
        toEdit.setDescription("The best");
        toEdit.setSuperpower("coolin");
        dao.updateSuper(toEdit);

        
        Super editedOne = dao.getSuperById(1);
        Assert.assertNotNull(editedOne);
        Assert.assertSame(1, editedOne.getSuperId());
        Assert.assertEquals("Best Super", editedOne.getName());
        Assert.assertEquals("The best", editedOne.getDescription());
        Assert.assertEquals("coolin", editedOne.getSuperpower());

        Super toEditTwo = dao.getSuperById(3);
        Assert.assertNotNull(toEditTwo);
        Assert.assertSame(3, toEditTwo.getSuperId());
        Assert.assertEquals("Freeze", toEditTwo.getName());
        Assert.assertEquals("cold", toEditTwo.getDescription());
        Assert.assertEquals("icing", toEditTwo.getSuperpower());
        
        toEditTwo.setName("Okay Super");
        toEditTwo.setDescription("The okayest");
        toEditTwo.setSuperpower("warming");
        dao.updateSuper(toEditTwo);

        Super editedTwo = dao.getSuperById(3);
        Assert.assertNotNull(editedTwo);
        Assert.assertSame(3, editedTwo.getSuperId());
        Assert.assertEquals("Okay Super", editedTwo.getName());
        Assert.assertEquals("The okayest", editedTwo.getDescription());
        Assert.assertEquals("warming", editedTwo.getSuperpower());


    }

    @Test
    public void testUpdateSuperFailNoName() throws SuperPersistenceException {
        try {
            Super toEdit = dao.getSuperById(1);
            toEdit.setName(null);
            toEdit.setDescription("The best Two");
            toEdit.setSuperpower("heatin");
            dao.updateSuper(toEdit);
            junit.framework.Assert.fail("Expected SuperPersistenceException was not thrown.");
        } catch (SuperPersistenceException ex) {

        }
    }

    @Test
    public void testUpdateSuperFailMaxName() throws SuperPersistenceException {
        try {
            Super toEdit = dao.getSuperById(1);
            toEdit.setName("llllllllllaaaaaaaaaaaalllllllll");
            toEdit.setDescription("The best Two");
            toEdit.setSuperpower("heatin");
            dao.updateSuper(toEdit);
            junit.framework.Assert.fail("Expected SuperPersistenceException was not thrown.");
        } catch (SuperPersistenceException ex) {

        }
    }

    @Test
    public void testUpdateSuperFailNoDescription() throws SuperPersistenceException {
        try {
            Super toEdit = dao.getSuperById(1);
            toEdit.setName("llllllllllaaaaaaaaaaaalllllllll");
            toEdit.setDescription(null);
            toEdit.setSuperpower("heatin");
            dao.updateSuper(toEdit);
            junit.framework.Assert.fail("Expected SuperPersistenceException was not thrown.");
        } catch (SuperPersistenceException ex) {

        }
    }

    @Test
    public void testUpdateSuperFailMaxDescription() throws OrganizationPersistenceException {
        try {
            Super toEdit = dao.getSuperById(1);
            toEdit.setName("Best Super");
            toEdit.setDescription("dsdsdsddsssssjsjsjsjsjsjsjsjsjsjsjsjsjsjsjllllllllllllllllllljsjsjsjsjsjsjsjsjsjsjsjsjsjsjsjlllllllllllllllllll");
            toEdit.setSuperpower("coolin");
            dao.updateSuper(toEdit);
            junit.framework.Assert.fail("Expected SuperPersistenceException was not thrown.");
        } catch (SuperPersistenceException ex) {

        }
    }

    @Test
    public void testUpdateSuperFailNoSuperpower() throws OrganizationPersistenceException {
        try {
            Super toEdit = dao.getSuperById(1);
            toEdit.setName("Best Super");
            toEdit.setDescription("The best");
            toEdit.setSuperpower(null);
            dao.updateSuper(toEdit);
            junit.framework.Assert.fail("Expected SuperPersistenceException was not thrown.");
        } catch (SuperPersistenceException ex) {

        }
    }

    @Test
    public void testUpdateSuperFailMaxSuperpower() throws SuperPersistenceException {
        try {
            Super toEdit = dao.getSuperById(1);
            toEdit.setName("Best Super");
            toEdit.setDescription("The best");
            toEdit.setSuperpower("dsdsdsddsssssjsjsjsjsjsjsjsjsjsjsjsjsjsjsjllllllllllllllllllljsjsjsjsjsjsjsjsjsjsjsjsjsjsjsjlllllllllllllllllll");
            dao.updateSuper(toEdit);
            junit.framework.Assert.fail("Expected SuperPersistenceException was not thrown.");
        } catch (SuperPersistenceException ex) {

        }
    }

    /**
     * Test of deleteSuperById method, of class SuperDbDao.
     */
    @Test
    public void testDeleteSuperByIdSuccess() throws SuperPersistenceException {

        Super toDeleteOne = dao.getSuperById(1);
        List<Super> allSupers = dao.getAllSupers();
        junit.framework.Assert.assertEquals(3, allSupers.size());
        junit.framework.Assert.assertNotNull(toDeleteOne);
        junit.framework.Assert.assertSame(1, toDeleteOne.getSuperId());

        dao.deleteSuperById(1);

        List<Super> allSupersOneDeleted = dao.getAllSupers();
        junit.framework.Assert.assertEquals(2, allSupersOneDeleted.size());
        junit.framework.Assert.assertSame(2, allSupersOneDeleted.get(0).getSuperId());

        Super toDeleteTwo = dao.getSuperById(2);
        junit.framework.Assert.assertNotNull(toDeleteTwo);
        junit.framework.Assert.assertSame(2, toDeleteTwo.getSuperId());

        dao.deleteSuperById(2);

        List<Super> allSupersTwoDeleted = dao.getAllSupers();
        junit.framework.Assert.assertEquals(1, allSupersTwoDeleted.size());
        junit.framework.Assert.assertSame(3, allSupersTwoDeleted.get(0).getSuperId());

    }

    @Test
    public void testDeleteSuperByIdFail() throws SuperPersistenceException {
        try {
            dao.deleteSuperById(5);
            junit.framework.Assert.fail("Expected SuperPersistenceException was not thrown.");
        } catch (SuperPersistenceException ex) {

        }

    }

    /**
     * Test of getSupersByOrgId method, of class SuperDbDao.
     */
    @Test
    public void testGetSupersByOrgIdSuccess() throws SuperPersistenceException {
        List<Super> allSupersOrgOne = dao.getSupersByOrgId(1);
        Assert.assertNotNull(allSupersOrgOne);
        Assert.assertEquals(2, allSupersOrgOne.size());

        List<Super> allSupersOrgThree = dao.getSupersByOrgId(3);
        Assert.assertNotNull(allSupersOrgThree);
        Assert.assertEquals(1, allSupersOrgThree.size());
    }

    /**
     * Test of getAllSupersBySightingId method, of class SuperDbDao.
     */
    @Test
    public void testGetAllSupersBySightingIdSuccess() throws SuperPersistenceException {
        List<Super> allSupersSightingOne = dao.getAllSupersBySightingId(1);
        Assert.assertNotNull(allSupersSightingOne);
        Assert.assertEquals(2, allSupersSightingOne.size());

        List<Super> allSupersSightingFour = dao.getAllSupersBySightingId(4);
        Assert.assertNotNull(allSupersSightingFour);
        Assert.assertEquals(1, allSupersSightingFour.size());
    }

}
