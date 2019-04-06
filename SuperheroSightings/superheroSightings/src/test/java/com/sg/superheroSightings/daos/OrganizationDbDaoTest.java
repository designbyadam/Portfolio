/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroSightings.daos;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.sg.superheroSightings.models.Location;
import com.sg.superheroSightings.models.Organization;
import java.sql.SQLException;
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
public class OrganizationDbDaoTest {

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    OrganizationDbDao dao;

    public OrganizationDbDaoTest() throws SQLException {
        MysqlDataSource ds = new MysqlDataSource();
        ds.setServerName("localhost");
        ds.setDatabaseName("superheroSightingsTest");
        ds.setUser("root");
        ds.setPassword("password");
        ds.setServerTimezone("America/Chicago");
        ds.setUseSSL(false);
        ds.setAllowPublicKeyRetrieval(true);
        this.jdbc = new JdbcTemplate(ds);
        this.dao = new OrganizationDbDao(jdbc);

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
     * Test of getOrgById method, of class OrganizationDbDao.
     */
    @Test
    public void testGetOrgByIdSuccess() throws OrganizationPersistenceException {

        Organization toGetOne = dao.getOrgById(1);
        Assert.assertNotNull(toGetOne);
        Assert.assertSame(1, toGetOne.getOrganizationId());
        Assert.assertEquals("Marvel", toGetOne.getName());
        Assert.assertEquals("Sweet org", toGetOne.getDescription());
        Assert.assertEquals("456 South Street Alpharetta, GA 30022", toGetOne.getAddress());
        Assert.assertEquals("7702410271", toGetOne.getPhoneNumber());
        Assert.assertEquals("marvel@marvel.com", toGetOne.getEmail());

        Organization toGetThree = dao.getOrgById(3);
        Assert.assertNotNull(toGetThree);
        Assert.assertEquals("SeaOrg", toGetThree.getName());
        Assert.assertEquals("Scientology HQ", toGetThree.getDescription());
        Assert.assertEquals("123 North Street Clearwater, FL", toGetThree.getAddress());
        Assert.assertEquals("7707654321", toGetThree.getPhoneNumber());
        Assert.assertEquals("lron@hubbard.com", toGetThree.getEmail());
    }

    @Test
    public void testGetOrgByIdFailInvalidId() throws OrganizationPersistenceException {

        try {
            dao.getOrgById(5);
            junit.framework.Assert.fail("Expected OrganizationPersistenceException was not thrown.");
        } catch (OrganizationPersistenceException ex) {

        }
    }

    /**
     * Test of getAllOrgs method, of class OrganizationDbDao.
     */
    @Test
    public void testGetAllOrgsSuccess() throws OrganizationPersistenceException {

        List<Organization> allOrganizations = dao.getAllOrgs();
        Assert.assertNotNull(allOrganizations);
        Assert.assertEquals(3, allOrganizations.size());

        Organization orgOne = allOrganizations.get(0);
        Assert.assertNotNull(orgOne);
        Assert.assertSame(1, orgOne.getOrganizationId());
        Assert.assertEquals("Marvel", orgOne.getName());
        Assert.assertEquals("Sweet org", orgOne.getDescription());
        Assert.assertEquals("456 South Street Alpharetta, GA 30022", orgOne.getAddress());
        Assert.assertEquals("7702410271", orgOne.getPhoneNumber());
        Assert.assertEquals("marvel@marvel.com", orgOne.getEmail());

        Organization orgThree = allOrganizations.get(allOrganizations.size() - 1);
        Assert.assertNotNull(orgThree);
        Assert.assertEquals("SeaOrg", orgThree.getName());
        Assert.assertEquals("Scientology HQ", orgThree.getDescription());
        Assert.assertEquals("123 North Street Clearwater, FL", orgThree.getAddress());
        Assert.assertEquals("7707654321", orgThree.getPhoneNumber());
        Assert.assertEquals("lron@hubbard.com", orgThree.getEmail());

    }

    /**
     * Test of addOrg method, of class OrganizationDbDao.
     */
    @Test
    public void testAddOrgSuccess() throws OrganizationPersistenceException {

        List<Organization> allOrgs = dao.getAllOrgs();
        junit.framework.Assert.assertEquals(3, allOrgs.size());
        junit.framework.Assert.assertNotNull(allOrgs);

        Organization toAdd = new Organization();
        toAdd.setName("Best Org");
        toAdd.setDescription("The best");
        toAdd.setAddress("123 North Street");
        toAdd.setPhoneNumber("7702410271");
        toAdd.setEmail("best@best.com");
        dao.addOrg(toAdd);

        List<Organization> allOrgsNew = dao.getAllOrgs();
        junit.framework.Assert.assertEquals(4, allOrgsNew.size());
        junit.framework.Assert.assertNotNull(allOrgsNew);

        Organization addedOrg = allOrgsNew.get(allOrgsNew.size() - 1);
        junit.framework.Assert.assertNotNull(addedOrg);
        junit.framework.Assert.assertSame(4, addedOrg.getOrganizationId());
        junit.framework.Assert.assertEquals("Best Org", addedOrg.getName());
        junit.framework.Assert.assertEquals("The best", addedOrg.getDescription());
        junit.framework.Assert.assertEquals("123 North Street", addedOrg.getAddress());
        junit.framework.Assert.assertEquals("7702410271", addedOrg.getPhoneNumber());
        junit.framework.Assert.assertEquals("best@best.com", addedOrg.getEmail());

        Organization toAddTwo = new Organization();
        toAddTwo.setName("Best Org Two");
        toAddTwo.setDescription("The best Two");
        toAddTwo.setAddress("2123 North Street");
        toAddTwo.setPhoneNumber("7702410272");
        toAddTwo.setEmail("2best@best.com");
        dao.addOrg(toAddTwo);

        List<Organization> allOrgsNewTwo = dao.getAllOrgs();
        junit.framework.Assert.assertEquals(5, allOrgsNewTwo.size());
        junit.framework.Assert.assertNotNull(allOrgsNewTwo);

        Organization addedOrgTwo = allOrgsNewTwo.get(allOrgsNewTwo.size() - 1);
        Assert.assertSame(5, addedOrgTwo.getOrganizationId());
        Assert.assertEquals("Best Org Two", addedOrgTwo.getName());
        Assert.assertEquals("The best Two", addedOrgTwo.getDescription());
        Assert.assertEquals("2123 North Street", addedOrgTwo.getAddress());
        Assert.assertEquals("7702410272", addedOrgTwo.getPhoneNumber());
        Assert.assertEquals("2best@best.com", addedOrgTwo.getEmail());

    }

    @Test
    public void testAddOrganizationFailNoName() throws OrganizationPersistenceException {
        try {
            Organization toAdd = new Organization();
            toAdd.setDescription("The best");
            toAdd.setAddress("123 North Street");
            toAdd.setPhoneNumber("7702410271");
            toAdd.setEmail("best@best.com");
            dao.addOrg(toAdd);
            junit.framework.Assert.fail("Expected OrganizationPersistenceException was not thrown.");
        } catch (OrganizationPersistenceException ex) {

        }
    }

    @Test
    public void testAddOrganizationFailMaxName() throws OrganizationPersistenceException {
        try {
            Organization toAdd = new Organization();
            toAdd.setName("llllllllllaaaaaaaaaaaalllllllll");
            toAdd.setDescription("The best");
            toAdd.setAddress("123 North Street");
            toAdd.setPhoneNumber("7702410271");
            toAdd.setEmail("best@best.com");
            dao.addOrg(toAdd);
            junit.framework.Assert.fail("Expected OrganizationPersistenceException was not thrown.");
        } catch (OrganizationPersistenceException ex) {

        }
    }

    @Test
    public void testAddOrganizationFailNoDescription() throws OrganizationPersistenceException {
        try {
            Organization toAdd = new Organization();
            toAdd.setName("Best Org");
            toAdd.setAddress("123 North Street");
            toAdd.setPhoneNumber("7702410271");
            toAdd.setEmail("best@best.com");
            dao.addOrg(toAdd);
            junit.framework.Assert.fail("Expected OrganizationPersistenceException was not thrown.");
        } catch (OrganizationPersistenceException ex) {

        }
    }

    @Test
    public void testAddOrganizationFailMaxDescription() throws OrganizationPersistenceException {
        try {
            Organization toAdd = new Organization();
            toAdd.setName("Best Org");
            toAdd.setDescription("dsdsdsddsssssjsjsjsjsjsjsjsjsjsjsjsjsjsjsjllllllllllllllllllljsjsjsjsjsjsjsjsjsjsjsjsjsjsjsjlllllllllllllllllll");
            toAdd.setAddress("123 North Street");
            toAdd.setPhoneNumber("7702410271");
            toAdd.setEmail("best@best.com");
            dao.addOrg(toAdd);
            junit.framework.Assert.fail("Expected OrganizationPersistenceException was not thrown.");
        } catch (OrganizationPersistenceException ex) {

        }
    }

    @Test
    public void testAddOrganizationFailNoAddress() throws OrganizationPersistenceException {
        try {
            Organization toAdd = new Organization();
            toAdd.setName("Best Org");
            toAdd.setDescription("The best");
            toAdd.setPhoneNumber("7702410271");
            toAdd.setEmail("best@best.com");
            dao.addOrg(toAdd);
            junit.framework.Assert.fail("Expected OrganizationPersistenceException was not thrown.");
        } catch (OrganizationPersistenceException ex) {

        }
    }

    @Test
    public void testAddOrganizationFailMaxAddress() throws OrganizationPersistenceException {
        try {
            Organization toAdd = new Organization();
            toAdd.setName("Best Org");
            toAdd.setDescription("The best");
            toAdd.setAddress("jdsdsdsddsssssjsjsjsjsjsjsjsjsjsjsjsjsjsjsjllllllllllllllllllljsjsjsjsjsjsjsjsjsjsjsjsjsjsjsjlllllllllllllllllll");
            toAdd.setPhoneNumber("7702410271");
            toAdd.setEmail("best@best.com");
            dao.addOrg(toAdd);
            junit.framework.Assert.fail("Expected OrganizationPersistenceException was not thrown.");
        } catch (OrganizationPersistenceException ex) {

        }
    }

    @Test
    public void testAddOrganizationFailNoPhoneNumber() throws OrganizationPersistenceException {
        try {
            Organization toAdd = new Organization();
            toAdd.setName("Best Org");
            toAdd.setDescription("The best");
            toAdd.setAddress("123 high Street");
            toAdd.setEmail("best@best.com");
            dao.addOrg(toAdd);
            junit.framework.Assert.fail("Expected OrganizationPersistenceException was not thrown.");
        } catch (OrganizationPersistenceException ex) {

        }
    }

    @Test
    public void testAddOrganizationFailMaxPhoneNumber() throws OrganizationPersistenceException {
        try {
            Organization toAdd = new Organization();
            toAdd.setName("Best Org");
            toAdd.setDescription("The best");
            toAdd.setAddress("123 High Street");
            toAdd.setPhoneNumber("770241027112345");
            toAdd.setEmail("best@best.com");
            dao.addOrg(toAdd);
            junit.framework.Assert.fail("Expected OrganizationPersistenceException was not thrown.");
        } catch (OrganizationPersistenceException ex) {

        }
    }

    @Test
    public void testAddOrganizationFailNoEmail() throws OrganizationPersistenceException {
        try {
            Organization toAdd = new Organization();
            toAdd.setName("Best Org");
            toAdd.setDescription("The best");
            toAdd.setAddress("123 high Street");
            toAdd.setPhoneNumber("7702410271");
            dao.addOrg(toAdd);
            junit.framework.Assert.fail("Expected OrganizationPersistenceException was not thrown.");
        } catch (OrganizationPersistenceException ex) {

        }
    }

    @Test
    public void testAddOrganizationFailMaxEmail() throws OrganizationPersistenceException {
        try {
            Organization toAdd = new Organization();
            toAdd.setName("Best Org");
            toAdd.setDescription("The best");
            toAdd.setAddress("123 High Street");
            toAdd.setPhoneNumber("7702410271");
            toAdd.setEmail("lslslslslslslslslslslslslslslslslslsl");
            dao.addOrg(toAdd);
            junit.framework.Assert.fail("Expected OrganizationPersistenceException was not thrown.");
        } catch (OrganizationPersistenceException ex) {

        }

    }

    @Test
    public void testUpdateOrgSuccess() throws OrganizationPersistenceException {

        List<Organization> allOrgs = dao.getAllOrgs();
        junit.framework.Assert.assertEquals(3, allOrgs.size());
        junit.framework.Assert.assertNotNull(allOrgs);

        Organization toEdit = allOrgs.get(0);

        Assert.assertNotNull(toEdit);
        Assert.assertSame(1, toEdit.getOrganizationId());
        Assert.assertEquals("Marvel", toEdit.getName());
        Assert.assertEquals("Sweet org", toEdit.getDescription());
        Assert.assertEquals("456 South Street Alpharetta, GA 30022", toEdit.getAddress());
        Assert.assertEquals("7702410271", toEdit.getPhoneNumber());
        Assert.assertEquals("marvel@marvel.com", toEdit.getEmail());

        toEdit.setName("Best Org");
        toEdit.setDescription("The best");
        toEdit.setAddress("123 North Street");
        toEdit.setPhoneNumber("7702410271");
        toEdit.setEmail("best@best.com");
        dao.updateOrg(toEdit);

        Organization updatedOrg = dao.getOrgById(1);

        junit.framework.Assert.assertNotNull(updatedOrg);
        junit.framework.Assert.assertSame(1, updatedOrg.getOrganizationId());
        junit.framework.Assert.assertEquals("Best Org", updatedOrg.getName());
        junit.framework.Assert.assertEquals("The best", updatedOrg.getDescription());
        junit.framework.Assert.assertEquals("123 North Street", updatedOrg.getAddress());
        junit.framework.Assert.assertEquals("7702410271", updatedOrg.getPhoneNumber());
        junit.framework.Assert.assertEquals("best@best.com", updatedOrg.getEmail());

        Organization toEditTwo = dao.getOrgById(3);
        Assert.assertNotNull(toEditTwo);
        Assert.assertEquals("SeaOrg", toEditTwo.getName());
        Assert.assertEquals("Scientology HQ", toEditTwo.getDescription());
        Assert.assertEquals("123 North Street Clearwater, FL", toEditTwo.getAddress());
        Assert.assertEquals("7707654321", toEditTwo.getPhoneNumber());
        Assert.assertEquals("lron@hubbard.com", toEditTwo.getEmail());

        toEditTwo.setName("Best Org Two");
        toEditTwo.setDescription("The best Two");
        toEditTwo.setAddress("2123 North Street");
        toEditTwo.setPhoneNumber("7702410271");
        toEditTwo.setEmail("2best@best.com");
        dao.updateOrg(toEditTwo);

        Organization updatedOrgTwo = dao.getOrgById(3);
        Assert.assertSame(3, updatedOrgTwo.getOrganizationId());
        Assert.assertEquals("Best Org Two", updatedOrgTwo.getName());
        Assert.assertEquals("The best Two", updatedOrgTwo.getDescription());
        Assert.assertEquals("2123 North Street", updatedOrgTwo.getAddress());
        Assert.assertEquals("7702410271", updatedOrgTwo.getPhoneNumber());
        Assert.assertEquals("2best@best.com", updatedOrgTwo.getEmail());

    }

    @Test
    public void testUpdateOrganizationFailNoName() throws OrganizationPersistenceException {
        try {
            Organization toEdit = dao.getOrgById(1);
            toEdit.setName(null);
            toEdit.setDescription("The best");
            toEdit.setAddress("123 North Street");
            toEdit.setPhoneNumber("7702410271");
            toEdit.setEmail("best@best.com");
            dao.updateOrg(toEdit);
            junit.framework.Assert.fail("Expected OrganizationPersistenceException was not thrown.");
        } catch (OrganizationPersistenceException ex) {

        }
    }

    @Test
    public void testUpdateOrganizationFailMaxName() throws OrganizationPersistenceException {
        try {
            Organization toEdit = dao.getOrgById(1);
            toEdit.setName("llllllllllaaaaaaaaaaaalllllllll");
            toEdit.setDescription("The best");
            toEdit.setAddress("123 North Street");
            toEdit.setPhoneNumber("7702410271");
            toEdit.setEmail("best@best.com");
            dao.updateOrg(toEdit);
            junit.framework.Assert.fail("Expected OrganizationPersistenceException was not thrown.");
        } catch (OrganizationPersistenceException ex) {

        }
    }

    @Test
    public void testUpdateOrganizationFailNoDescription() throws OrganizationPersistenceException {
        try {
            Organization toEdit = dao.getOrgById(1);
            toEdit.setName("Best Org");
            toEdit.setDescription(null);
            toEdit.setAddress("123 North Street");
            toEdit.setPhoneNumber("7702410271");
            toEdit.setEmail("best@best.com");
            dao.updateOrg(toEdit);
            junit.framework.Assert.fail("Expected OrganizationPersistenceException was not thrown.");
        } catch (OrganizationPersistenceException ex) {

        }
    }

    @Test
    public void testUpdateOrganizationFailMaxDescription() throws OrganizationPersistenceException {
        try {
            Organization toEdit = dao.getOrgById(1);
            toEdit.setName("Best Org");
            toEdit.setDescription("dsdsdsddsssssjsjsjsjsjsjsjsjsjsjsjsjsjsjsjllllllllllllllllllljsjsjsjsjsjsjsjsjsjsjsjsjsjsjsjlllllllllllllllllll");
            toEdit.setAddress("123 North Street");
            toEdit.setPhoneNumber("7702410271");
            toEdit.setEmail("best@best.com");
            dao.updateOrg(toEdit);
            junit.framework.Assert.fail("Expected OrganizationPersistenceException was not thrown.");
        } catch (OrganizationPersistenceException ex) {

        }
    }

    @Test
    public void testUpdateOrganizationFailNoAddress() throws OrganizationPersistenceException {
        try {
            Organization toEdit = dao.getOrgById(1);
            toEdit.setName("Best Org");
            toEdit.setDescription("The best");
            toEdit.setAddress(null);
            toEdit.setPhoneNumber("7702410271");
            toEdit.setEmail("best@best.com");
            dao.updateOrg(toEdit);
            junit.framework.Assert.fail("Expected OrganizationPersistenceException was not thrown.");
        } catch (OrganizationPersistenceException ex) {

        }
    }

    @Test
    public void testUpdateOrganizationFailMaxAddress() throws OrganizationPersistenceException {
        try {
            Organization toEdit = dao.getOrgById(1);
            toEdit.setName("Best Org");
            toEdit.setDescription("The best");
            toEdit.setAddress("jdsdsdsddsssssjsjsjsjsjsjsjsjsjsjsjsjsjsjsjllllllllllllllllllljsjsjsjsjsjsjsjsjsjsjsjsjsjsjsjlllllllllllllllllll");
            toEdit.setPhoneNumber("7702410271");
            toEdit.setEmail("best@best.com");
            dao.updateOrg(toEdit);
            junit.framework.Assert.fail("Expected OrganizationPersistenceException was not thrown.");
        } catch (OrganizationPersistenceException ex) {

        }
    }

    @Test
    public void testUpdateOrganizationFailNoPhoneNumber() throws OrganizationPersistenceException {
        try {
            Organization toEdit = dao.getOrgById(1);
            toEdit.setName("Best Org");
            toEdit.setDescription("The best");
            toEdit.setAddress("123 high Street");
            toEdit.setPhoneNumber(null);
            toEdit.setEmail("best@best.com");
            dao.updateOrg(toEdit);
            junit.framework.Assert.fail("Expected OrganizationPersistenceException was not thrown.");
        } catch (OrganizationPersistenceException ex) {

        }
    }

    @Test
    public void testUpdateOrganizationFailMaxPhoneNumber() throws OrganizationPersistenceException {
        try {
            Organization toEdit = dao.getOrgById(1);
            toEdit.setName("Best Org");
            toEdit.setDescription("The best");
            toEdit.setAddress("123 High Street");
            toEdit.setPhoneNumber("770241027112345");
            toEdit.setEmail("best@best.com");
            dao.updateOrg(toEdit);
            junit.framework.Assert.fail("Expected OrganizationPersistenceException was not thrown.");
        } catch (OrganizationPersistenceException ex) {

        }
    }

    @Test
    public void testUpdateOrganizationFailNoEmail() throws OrganizationPersistenceException {
        try {
            Organization toEdit = dao.getOrgById(1);
            toEdit.setName("Best Org");
            toEdit.setDescription("The best");
            toEdit.setAddress("123 high Street");
            toEdit.setPhoneNumber("7702410271");
            toEdit.setEmail(null);
            dao.updateOrg(toEdit);
            junit.framework.Assert.fail("Expected OrganizationPersistenceException was not thrown.");
        } catch (OrganizationPersistenceException ex) {

        }
    }

    @Test
    public void testUpdateOrganizationFailMaxEmail() throws OrganizationPersistenceException {
        try {
            Organization toEdit = dao.getOrgById(1);
            toEdit.setName("Best Org");
            toEdit.setDescription("The best");
            toEdit.setAddress("123 High Street");
            toEdit.setPhoneNumber("7702410271");
            toEdit.setEmail("lslslslslslslslslslslslslslslslslslsl");
            dao.updateOrg(toEdit);
            junit.framework.Assert.fail("Expected OrganizationPersistenceException was not thrown.");
        } catch (OrganizationPersistenceException ex) {

        }

    }

    /**
     * Test of deleteOrgById method, of class OrganizationDbDao.
     */
    @Test
    public void testDeleteOrgByIdSuccess() throws OrganizationPersistenceException {

        Organization toDeleteOne = dao.getOrgById(1);
        List<Organization> allOrgs = dao.getAllOrgs();
        junit.framework.Assert.assertEquals(3, allOrgs.size());
        junit.framework.Assert.assertNotNull(toDeleteOne);
        junit.framework.Assert.assertSame(1, toDeleteOne.getOrganizationId());

        dao.deleteOrgById(1);

        List<Organization> allOrgsOneDeleted = dao.getAllOrgs();
        junit.framework.Assert.assertEquals(2, allOrgsOneDeleted.size());
        junit.framework.Assert.assertSame(2, allOrgsOneDeleted.get(0).getOrganizationId());

        Organization toDeleteTwo = dao.getOrgById(2);
        junit.framework.Assert.assertNotNull(toDeleteTwo);
        junit.framework.Assert.assertSame(2, toDeleteTwo.getOrganizationId());

        dao.deleteOrgById(2);

        List<Organization> allOrgsTwoDeleted = dao.getAllOrgs();
        junit.framework.Assert.assertEquals(1, allOrgsTwoDeleted.size());
        junit.framework.Assert.assertSame(3, allOrgsTwoDeleted.get(0).getOrganizationId());

    }

    @Test
    public void testDeleteOrganizationByIdFail() throws OrganizationPersistenceException {
        try {
            dao.deleteOrgById(5);
            junit.framework.Assert.fail("Expected OrganizationPersistenceException was not thrown.");
        } catch (OrganizationPersistenceException ex) {

        }

    }

    /**
     * Test of getOrgsBySuperId method, of class OrganizationDbDao.
     */
    @Test
    public void testGetOrgsBySuperIdSuccess() throws OrganizationPersistenceException {
        List<Organization> allOrganizationsSuperOne = dao.getOrgsBySuperId(1);
        Assert.assertNotNull(allOrganizationsSuperOne);
        Assert.assertEquals(2, allOrganizationsSuperOne.size());

        List<Organization> allOrganizationsSuperThree = dao.getOrgsBySuperId(3);
        Assert.assertNotNull(allOrganizationsSuperThree);
        Assert.assertEquals(1, allOrganizationsSuperThree.size());

    }
}
