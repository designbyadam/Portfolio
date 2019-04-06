/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroSightings.services;

import com.sg.superheroSightings.daos.AlwaysFailLocationDao;
import com.sg.superheroSightings.daos.AlwaysFailOrganizationDao;
import com.sg.superheroSightings.daos.AlwaysFailSightingDao;
import com.sg.superheroSightings.daos.AlwaysFailSuperDao;
import com.sg.superheroSightings.daos.LocationInMemoryDao;
import com.sg.superheroSightings.daos.LocationPersistenceException;
import com.sg.superheroSightings.daos.OrganizationInMemoryDao;
import com.sg.superheroSightings.daos.OrganizationPersistenceException;
import com.sg.superheroSightings.daos.SightingInMemoryDao;
import com.sg.superheroSightings.daos.SuperInMemoryDao;
import com.sg.superheroSightings.models.Location;
import com.sg.superheroSightings.models.Organization;
import com.sg.superheroSightings.models.Sighting;
import com.sg.superheroSightings.models.Super;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author afahrenkamp
 */
public class SuperServiceTest {

    LocationInMemoryDao locationDao = new LocationInMemoryDao();
    OrganizationInMemoryDao orgDao = new OrganizationInMemoryDao();
    SightingInMemoryDao sightingDao = new SightingInMemoryDao();
    SuperInMemoryDao superDao = new SuperInMemoryDao();

    AlwaysFailLocationDao locationFailDao = new AlwaysFailLocationDao();
    AlwaysFailOrganizationDao orgFailDao = new AlwaysFailOrganizationDao();
    AlwaysFailSightingDao sightingFailDao = new AlwaysFailSightingDao();
    AlwaysFailSuperDao superFailDao = new AlwaysFailSuperDao();

    public SuperServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws OrganizationPersistenceException, LocationPersistenceException {

        locationDao = new LocationInMemoryDao();
        orgDao = new OrganizationInMemoryDao();
        sightingDao = new SightingInMemoryDao();
        superDao = new SuperInMemoryDao();

        Super batman = superDao.getSuperById(1);
        Super superman = superDao.getSuperById(2);
        Organization marvel = orgDao.getOrgById(1);
        Organization dc = orgDao.getOrgById(2);
        batman.getAllOrganizations().add(marvel);
        batman.getAllOrganizations().add(dc);
        superman.getAllOrganizations().add(dc);
        dc.getAllSupers().add(batman);
        dc.getAllSupers().add(superman);
        marvel.getAllSupers().add(batman);
        Location gotham = locationDao.getLocationById(1);
        Location casaJacob = locationDao.getLocationById(2);
        Sighting gothamSightOne = sightingDao.getSightingById(1);
        Sighting gothamSightTwo = sightingDao.getSightingById(3);
        Sighting casaJacobSight = sightingDao.getSightingById(2);
        Sighting sightingFour = sightingDao.getSightingById(4);
        Sighting sightingFive = sightingDao.getSightingById(5);
        Sighting sightingSix = sightingDao.getSightingById(6);
        Sighting sightingSeven = sightingDao.getSightingById(7);
        Sighting sightingEight = sightingDao.getSightingById(8);
        Sighting sightingNine = sightingDao.getSightingById(9);
        Sighting sightingTen = sightingDao.getSightingById(10);
        Sighting sightingEleven = sightingDao.getSightingById(11);
        gothamSightOne.getAllSupers().add(batman);
        gothamSightTwo.getAllSupers().add(batman);
        gothamSightTwo.getAllSupers().add(superman);
        casaJacobSight.getAllSupers().add(superman);
        sightingFour.getAllSupers().add(batman);
        sightingFive.getAllSupers().add(batman);
        sightingSix.getAllSupers().add(batman);
        sightingSeven.getAllSupers().add(batman);
        sightingEight.getAllSupers().add(batman);
        sightingNine.getAllSupers().add(batman);
        sightingTen.getAllSupers().add(batman);
        sightingEleven.getAllSupers().add(batman);
        gotham.getAllSightings().add(gothamSightOne);
        gotham.getAllSightings().add(gothamSightTwo);
        gotham.getAllSightings().add(sightingFour);
        gotham.getAllSightings().add(sightingFive);
        gotham.getAllSightings().add(sightingSix);
        gotham.getAllSightings().add(sightingSeven);
        gotham.getAllSightings().add(sightingEight);
        gotham.getAllSightings().add(sightingNine);
        gotham.getAllSightings().add(sightingTen);
        gotham.getAllSightings().add(sightingEleven);
        casaJacob.getAllSightings().add(casaJacobSight);
        batman.getAllSightings().add(gothamSightOne);
        batman.getAllSightings().add(gothamSightTwo);
        batman.getAllSightings().add(sightingFour);
        batman.getAllSightings().add(sightingFive);
        batman.getAllSightings().add(sightingSix);
        batman.getAllSightings().add(sightingSeven);
        batman.getAllSightings().add(sightingEight);
        batman.getAllSightings().add(sightingNine);
        batman.getAllSightings().add(sightingTen);
        batman.getAllSightings().add(sightingEleven);
        superman.getAllSightings().add(gothamSightTwo);
        superman.getAllSightings().add(casaJacobSight);
        gothamSightOne.setSightLocation(gotham);
        gothamSightTwo.setSightLocation(gotham);
        sightingFour.setSightLocation(gotham);
        sightingFive.setSightLocation(gotham);
        sightingSix.setSightLocation(gotham);
        sightingSeven.setSightLocation(gotham);
        sightingEight.setSightLocation(gotham);
        sightingNine.setSightLocation(gotham);
        sightingTen.setSightLocation(gotham);
        sightingEleven.setSightLocation(gotham);
        casaJacobSight.setSightLocation(casaJacob);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getAllSupers method, of class SuperService.
     */
    @Test
    public void testGetAllSupersSuccess() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);

        ListSuperResponse response = service.getAllSupers();
        List<Super> supersToGet = response.getAllSupers();
        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getAllSupers());
        Assert.assertEquals(2, supersToGet.size());
        Assert.assertSame(1, supersToGet.get(0).getSuperId());
        Assert.assertEquals("Batman", supersToGet.get(0).getName());
        Assert.assertEquals("The bat man", supersToGet.get(0).getDescription());
        Assert.assertEquals("tech", supersToGet.get(0).getSuperpower());

        Assert.assertSame(2, supersToGet.get(1).getSuperId());
        Assert.assertEquals("Superman", supersToGet.get(1).getName());
        Assert.assertEquals("The super man", supersToGet.get(1).getDescription());
        Assert.assertEquals("flight", supersToGet.get(1).getSuperpower());
    }

    @Test
    public void testGetAllSupersFail() {

        SuperService service = new SuperService(superFailDao, locationFailDao, orgFailDao, sightingFailDao);

        ListSuperResponse response = service.getAllSupers();
        List<Super> supersToGet = response.getAllSupers();
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(supersToGet);
    }

    /**
     * Test of getSuper method, of class SuperService.
     */
    @Test
    public void testGetSuperSuccess() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);

        GetSuperResponse response = service.getSuper(1);
        Super superOne = response.getToDisplay();
        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(superOne);

        Assert.assertSame(1, superOne.getSuperId());
        Assert.assertEquals("Batman", superOne.getName());
        Assert.assertEquals("The bat man", superOne.getDescription());
        Assert.assertEquals("tech", superOne.getSuperpower());
        Assert.assertEquals(2, superOne.getAllOrganizations().size());
        Assert.assertEquals(10, superOne.getAllSightings().size());

        GetSuperResponse responseTwo = service.getSuper(2);
        Super superTwo = responseTwo.getToDisplay();
        Assert.assertTrue(responseTwo.isSuccess());
        Assert.assertNotNull(responseTwo.getToDisplay());
        Assert.assertNotNull(superTwo);

        Assert.assertSame(2, superTwo.getSuperId());
        Assert.assertEquals("Superman", superTwo.getName());
        Assert.assertEquals("The super man", superTwo.getDescription());
        Assert.assertEquals("flight", superTwo.getSuperpower());
        Assert.assertEquals(1, superTwo.getAllOrganizations().size());
        Assert.assertEquals(2, superTwo.getAllSightings().size());

    }

    @Test
    public void testGetSuperFailInvalidId() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);

        GetSuperResponse response = service.getSuper(5);
        Super superFive = response.getToDisplay();
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(superFive);

    }

    @Test
    public void testGetSuperFailFailDao() {

        SuperService service = new SuperService(superFailDao, locationFailDao, orgFailDao, sightingFailDao);

        GetSuperResponse response = service.getSuper(1);
        Super superOne = response.getToDisplay();
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(superOne);

    }

    @Test
    public void testAddSuperSuccess() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);

        List<Super> allSupers = service.getAllSupers().getAllSupers();
        Assert.assertEquals(2, allSupers.size());

        Super toAdd = new Super();
        toAdd.setName("Superdude");
        toAdd.setDescription("The bestest");
        toAdd.setSuperpower("Fire");

        AddSuperResponse response = service.addSuper(toAdd);
        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getToAdd());

        Super addedSuper = response.getToAdd();
        Assert.assertSame(3, addedSuper.getSuperId());
        Assert.assertEquals("Superdude", addedSuper.getName());
        Assert.assertEquals("The bestest", addedSuper.getDescription());
        Assert.assertEquals("Fire", addedSuper.getSuperpower());

        List<Super> allSupersTwo = service.getAllSupers().getAllSupers();
        Assert.assertEquals(3, allSupersTwo.size());

        Super toAddTwo = new Super();
        toAddTwo.setName("Coolman");
        toAddTwo.setDescription("Coolest");
        toAddTwo.setSuperpower("Chiller");

        AddSuperResponse responseTwo = service.addSuper(toAddTwo);
        Assert.assertTrue(responseTwo.isSuccess());
        Assert.assertNotNull(responseTwo.getToAdd());

        Super addedSuperTwo = responseTwo.getToAdd();
        Assert.assertSame(4, addedSuperTwo.getSuperId());
        Assert.assertEquals("Coolman", addedSuperTwo.getName());
        Assert.assertEquals("Coolest", addedSuperTwo.getDescription());
        Assert.assertEquals("Chiller", addedSuperTwo.getSuperpower());

        List<Super> allSupersThree = service.getAllSupers().getAllSupers();
        Assert.assertEquals(4, allSupersThree.size());
    }

    @Test
    public void testAddSuperFailBlankName() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        Super toAdd = new Super();
        toAdd.setName("");
        toAdd.setDescription("The bestest");
        toAdd.setSuperpower("Fire");
        AddSuperResponse response = service.addSuper(toAdd);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getToAdd());
    }

    @Test
    public void testAddSuperFailMaxName() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        Super toAdd = new Super();
        toAdd.setName("llllllllllaaaaaaaaaaaalllllllll");
        toAdd.setDescription("The bestest");
        toAdd.setSuperpower("Fire");
        AddSuperResponse response = service.addSuper(toAdd);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getToAdd());
    }

    @Test
    public void testAddSuperFailBlankDescription() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        Super toAdd = new Super();
        toAdd.setName("Superdude");
        toAdd.setDescription("");
        toAdd.setSuperpower("Fire");
        AddSuperResponse response = service.addSuper(toAdd);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getToAdd());
    }

    @Test
    public void testAddSuperFailMaxDescription() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        Super toAdd = new Super();
        toAdd.setName("Superdude");
        toAdd.setDescription("dsdsdsddsssssjsjsjsjsjsjsjsjsjsjsjsjsjsjsjllllllllllllllllllljsjsjsjsjsjsjsjsjsjsjsjsjsjsjsjlllllllllllllllllll");
        toAdd.setSuperpower("Fire");
        AddSuperResponse response = service.addSuper(toAdd);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getToAdd());
    }

    @Test
    public void testAddSuperFailBlankSuperpower() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        Super toAdd = new Super();
        toAdd.setName("Superdude");
        toAdd.setDescription("Cool guy");
        toAdd.setSuperpower("");
        AddSuperResponse response = service.addSuper(toAdd);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getToAdd());
    }

    @Test
    public void testAddSuperFailMaxSuperpower() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        Super toAdd = new Super();
        toAdd.setName("Superdude");
        toAdd.setDescription("Cool guy");
        toAdd.setSuperpower("jdsdsdsddsssssjsjsjsjsjsjsjsjsjsjsjsjsjsjsjllllllllllllllllllljsjsjsjsjsjsjsjsjsjsjsjsjsjsjsjlllllllllllllllllll");
        AddSuperResponse response = service.addSuper(toAdd);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getToAdd());
    }

    @Test
    public void testAddSuperFailFailDao() {

        SuperService service = new SuperService(superFailDao, locationFailDao, orgFailDao, sightingFailDao);
        Super toAdd = new Super();
        toAdd.setName("Superdude");
        toAdd.setDescription("The bestest");
        toAdd.setSuperpower("Fire");
        AddSuperResponse response = service.addSuper(toAdd);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getToAdd());
    }

    @Test
    public void testEditSuperSuccess() {
        
        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);

        GetSuperResponse getSuperResponse = service.getSuper(1);
        Super toEdit = getSuperResponse.getToDisplay();
        Assert.assertTrue(getSuperResponse.isSuccess());
        Assert.assertNotNull(toEdit);

        Assert.assertSame(1, toEdit.getSuperId());
        Assert.assertEquals("Batman", toEdit.getName());
        Assert.assertEquals("The bat man", toEdit.getDescription());
        Assert.assertEquals("tech", toEdit.getSuperpower());

        toEdit.setName("Testman");
        toEdit.setDescription("Testing One Two");
        toEdit.setSuperpower("Supertesting");

        EditSuperResponse response = service.editSuper(toEdit);
        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEditedSup());
        Super EditedOne = response.getEditedSup();

        Assert.assertSame(1, EditedOne.getSuperId());
        Assert.assertEquals("Testman", EditedOne.getName());
        Assert.assertEquals("Testing One Two", EditedOne.getDescription());
        Assert.assertEquals("Supertesting", EditedOne.getSuperpower());

        GetSuperResponse responseTwo = service.getSuper(2);
        Super toEditTwo = responseTwo.getToDisplay();
        Assert.assertTrue(responseTwo.isSuccess());
        Assert.assertNotNull(responseTwo.getToDisplay());
        Assert.assertNotNull(toEditTwo);

        Assert.assertSame(2, toEditTwo.getSuperId());
        Assert.assertEquals("Superman", toEditTwo.getName());
        Assert.assertEquals("The super man", toEditTwo.getDescription());
        Assert.assertEquals("flight", toEditTwo.getSuperpower());

        toEditTwo.setName("Weakman");
        toEditTwo.setDescription("Weaker than most");
        toEditTwo.setSuperpower("Weakening");

        EditSuperResponse responseEditTwo = service.editSuper(toEditTwo);
        Assert.assertTrue(responseEditTwo.isSuccess());
        Assert.assertNotNull(responseEditTwo.getEditedSup());
        Super EditedTwo = responseEditTwo.getEditedSup();

        Assert.assertSame(2, EditedTwo.getSuperId());
        Assert.assertEquals("Weakman", EditedTwo.getName());
        Assert.assertEquals("Weaker than most", EditedTwo.getDescription());
        Assert.assertEquals("Weakening", EditedTwo.getSuperpower());

    }

    @Test
    public void testEditSuperFailBlankName() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        GetSuperResponse getSuperResponse = service.getSuper(1);
        Super toEdit = getSuperResponse.getToDisplay();
        toEdit.setName("");
        toEdit.setDescription("The bestest");
        toEdit.setSuperpower("Fire");
        EditSuperResponse response = service.editSuper(toEdit);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getEditedSup());
    }

    @Test
    public void testEditSuperFailMaxName() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        GetSuperResponse getSuperResponse = service.getSuper(1);
        Super toEdit = getSuperResponse.getToDisplay();
        toEdit.setName("llllllllllaaaaaaaaaaaalllllllll");
        toEdit.setDescription("The bestest");
        toEdit.setSuperpower("Fire");
        EditSuperResponse response = service.editSuper(toEdit);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getEditedSup());
    }

    @Test
    public void testEditSuperFailBlankDescription() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        GetSuperResponse getSuperResponse = service.getSuper(1);
        Super toEdit = getSuperResponse.getToDisplay();
        toEdit.setName("Superdude");
        toEdit.setDescription("");
        toEdit.setSuperpower("Fire");
        EditSuperResponse response = service.editSuper(toEdit);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getEditedSup());
    }

    @Test
    public void testEditSuperFailMaxDescription() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        GetSuperResponse getSuperResponse = service.getSuper(1);
        Super toEdit = getSuperResponse.getToDisplay();
        toEdit.setName("Superdude");
        toEdit.setDescription("dsdsdsddsssssjsjsjsjsjsjsjsjsjsjsjsjsjsjsjllllllllllllllllllljsjsjsjsjsjsjsjsjsjsjsjsjsjsjsjlllllllllllllllllll");
        toEdit.setSuperpower("Fire");
        EditSuperResponse response = service.editSuper(toEdit);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getEditedSup());
    }

    @Test
    public void testEditSuperFailBlankSuperpower() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        GetSuperResponse getSuperResponse = service.getSuper(1);
        Super toEdit = getSuperResponse.getToDisplay();
        toEdit.setName("Superdude");
        toEdit.setDescription("Cool guy");
        toEdit.setSuperpower("");
        EditSuperResponse response = service.editSuper(toEdit);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getEditedSup());
    }

    @Test
    public void testEditSuperFailMaxSuperpower() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        GetSuperResponse getSuperResponse = service.getSuper(1);
        Super toEdit = getSuperResponse.getToDisplay();
        toEdit.setName("Superdude");
        toEdit.setDescription("Cool guy");
        toEdit.setSuperpower("jdsdsdsddsssssjsjsjsjsjsjsjsjsjsjsjsjsjsjsjllllllllllllllllllljsjsjsjsjsjsjsjsjsjsjsjsjsjsjsjlllllllllllllllllll");
        EditSuperResponse response = service.editSuper(toEdit);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getEditedSup());
    }
    
    @Test
    public void testEditSuperFailInvalidId() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        GetSuperResponse getSuperResponse = service.getSuper(1);
        Super toEdit = new Super();
        toEdit.setSuperId(15);
        toEdit.setName("Superdude");
        toEdit.setDescription("Cool guy");
        toEdit.setSuperpower("jdsdsdsddsssssjsjsjsjsjsjsjsjsjsjsjsjsjsjsjllllllllllllllllllljsjsjsjsjsjsjsjsjsjsjsjsjsjsjsjlllllllllllllllllll");
        EditSuperResponse response = service.editSuper(toEdit);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getEditedSup());
    }

    @Test
    public void testEditSuperFailFailDao() {

        SuperService service = new SuperService(superFailDao, locationFailDao, orgFailDao, sightingFailDao);
        Super toEdit = new Super();
        toEdit.setSuperId(6);
        toEdit.setName("Superdude");
        toEdit.setDescription("Cool guy");
        toEdit.setSuperpower("flight");
        EditSuperResponse response = service.editSuper(toEdit);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getEditedSup());
    }
    
    @Test
    public void testDeleteSuperSuccess() {
        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);

        ListSuperResponse listSupResponse = service.getAllSupers();
        List<Super> supersToGet = listSupResponse.getAllSupers();
        Assert.assertTrue(listSupResponse.isSuccess());
        Assert.assertNotNull(listSupResponse.getAllSupers());
        Assert.assertEquals(2, supersToGet.size());

        ListOrganizationResponse listOrgResponse = service.getAllOrgs();
        List<Organization> orgsToGet = listOrgResponse.getAllOrgs();
        Assert.assertTrue(listOrgResponse.isSuccess());
        Assert.assertNotNull(listOrgResponse.getAllOrgs());
        Assert.assertEquals(2, orgsToGet.size());

        ListSightingResponse listSightResponse = service.getAllSightings();
        List<Sighting> sightingsToGet = listSightResponse.getAllSightings();
        Assert.assertTrue(listSightResponse.isSuccess());
        Assert.assertNotNull(listSightResponse.getAllSightings());
        Assert.assertEquals(11, sightingsToGet.size());

        GetOrganizationResponse getOrgResponse = service.getOrganization(1);
        Assert.assertNotNull(getOrgResponse.getToDisplay());
        Organization marvel = getOrgResponse.getToDisplay();
        List<Super> marvelSups = marvel.getAllSupers();
        Assert.assertEquals(1, marvelSups.size());
        Assert.assertEquals("Batman", marvelSups.get(0).getName());

        GetSightingResponse getSightResponse = service.getSighting(1);
        Assert.assertNotNull(getSightResponse.getToDisplay());
        Sighting sightingOne = getSightResponse.getToDisplay();
        List<Super> sightingOneSups = sightingOne.getAllSupers();
        Assert.assertEquals(1, sightingOneSups.size());
        Assert.assertEquals("Batman", sightingOneSups.get(0).getName());

        GetSuperResponse getSupResponse = service.getSuper(1);
        Assert.assertNotNull(getSupResponse.getToDisplay());
        Super batmanSuper = getSupResponse.getToDisplay();
        Assert.assertEquals("Batman", batmanSuper.getName());

        DeleteSuperResponse deleteSupResponse = service.deleteSuper(1);
        Assert.assertTrue(deleteSupResponse.isSuccess());
        GetSuperResponse getSuperResponseDeleted = service.getSuper(1);
        Assert.assertFalse(getSuperResponseDeleted.isSuccess());
        Assert.assertNull(getSuperResponseDeleted.getToDisplay());

        ListSuperResponse listSupDeleteResponse = service.getAllSupers();
        List<Super> supersOneDeleted = listSupDeleteResponse.getAllSupers();
        Assert.assertTrue(listSupDeleteResponse.isSuccess());
        Assert.assertEquals(1, supersOneDeleted.size());

        GetOrganizationResponse getResponseDeleted = service.getOrganization(1);
        Organization deletedSuperOrg = getResponseDeleted.getToDisplay();
        List<Super> listEmptySupers = deletedSuperOrg.getAllSupers();
        Assert.assertTrue(getResponseDeleted.isSuccess());
        Assert.assertTrue(listEmptySupers.isEmpty());

        ListSuperResponse listSupDeletedResponse = service.getAllSupers();
        List<Super> batmanDeleted = listSupDeletedResponse.getAllSupers();
        Assert.assertTrue(getResponseDeleted.isSuccess());
        Assert.assertTrue(listSupDeletedResponse.isSuccess());
        Assert.assertNotNull(listSupDeletedResponse.getAllSupers());
        Assert.assertEquals(1, batmanDeleted.size());
    }

    @Test
    public void testDeleteSuperFailInvalidId() {
        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);

        DeleteSuperResponse deleteResponse = service.deleteSuper(15);
        Assert.assertFalse(deleteResponse.isSuccess());

    }

    @Test
    public void testDeleteSuperFailFailDao() {
        SuperService service = new SuperService(superFailDao, locationFailDao, orgFailDao, sightingFailDao);

        DeleteSuperResponse deleteResponse = service.deleteSuper(1);
        Assert.assertFalse(deleteResponse.isSuccess());

    }

    /**
     * Test of getAllSightings method, of class SuperService.
     */
    @Test
    public void testGetAllSightingsSuccess() {
        LocalDate sightingOneDate = LocalDate.of(2018, 04, 13);
        LocalDate sightingTwoDate = LocalDate.of(2019, 03, 10);
        LocalDate sightingThreeDate = LocalDate.of(2018, 04, 03);

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);

        ListSightingResponse response = service.getAllSightings();
        List<Sighting> sightingsToGet = response.getAllSightings();
        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getAllSightings());
        Assert.assertEquals(11, sightingsToGet.size());
        Assert.assertSame(1, sightingsToGet.get(0).getSightingId());
        Assert.assertEquals(sightingOneDate, sightingsToGet.get(0).getDate());
        Assert.assertSame(1, sightingsToGet.get(0).getLocationId());
        Assert.assertEquals("Gotham City", sightingsToGet.get(0).getSightLocation().getName());

        Assert.assertSame(2, sightingsToGet.get(1).getSightingId());
        Assert.assertEquals(sightingTwoDate, sightingsToGet.get(1).getDate());
        Assert.assertSame(2, sightingsToGet.get(1).getLocationId());
        Assert.assertEquals("Casa Jacob", sightingsToGet.get(1).getSightLocation().getName());

        Assert.assertSame(3, sightingsToGet.get(2).getSightingId());
        Assert.assertEquals(sightingThreeDate, sightingsToGet.get(2).getDate());
        Assert.assertSame(1, sightingsToGet.get(2).getLocationId());
        Assert.assertEquals("Gotham City", sightingsToGet.get(2).getSightLocation().getName());

    }

    @Test
    public void testGetAllSightingsFail() {
        SuperService service = new SuperService(superFailDao, locationFailDao, orgFailDao, sightingFailDao);

        ListSightingResponse response = service.getAllSightings();
        List<Sighting> sightingsToGet = response.getAllSightings();
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(sightingsToGet);

    }

    @Test
    public void testGetLastTenSightingsSuccess() {
        LocalDate dateOne = LocalDate.of(2018, 04, 13);
        LocalDate dateTwo = LocalDate.of(2019, 03, 10);
        LocalDate dateThree = LocalDate.of(2018, 04, 03);
        LocalDate dateFour = LocalDate.of(2010, 04, 13);
        LocalDate dateFive = LocalDate.of(2016, 03, 10);
        LocalDate dateSix = LocalDate.of(2012, 04, 03);
        LocalDate dateSeven = LocalDate.of(2015, 04, 13);
        LocalDate dateEight = LocalDate.of(2013, 03, 10);
        LocalDate dateNine = LocalDate.of(2014, 04, 03);
        LocalDate dateTen = LocalDate.of(2011, 04, 13);
        LocalDate dateEleven = LocalDate.of(2019, 03, 22);

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);

        ListSightingResponse response = service.getLastTenSightings();
        List<Sighting> sightingsToGet = response.getAllSightings();
        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getAllSightings());
        Assert.assertEquals(10, sightingsToGet.size());
        Assert.assertSame(11, sightingsToGet.get(0).getSightingId());
        Assert.assertSame(10, sightingsToGet.get(sightingsToGet.size() - 1).getSightingId());
        Assert.assertEquals(dateEleven, sightingsToGet.get(0).getDate());
        Assert.assertEquals(dateTen, sightingsToGet.get(sightingsToGet.size() - 1).getDate());

    }

    /**
     * Test of getSighting method, of class SuperService.
     */
    @Test
    public void testGetSightingSuccess() {

        LocalDate sightingOneDate = LocalDate.of(2018, 04, 13);
        LocalDate sightingTwoDate = LocalDate.of(2019, 03, 10);
        LocalDate sightingThreeDate = LocalDate.of(2018, 04, 03);

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);

        GetSightingResponse response = service.getSighting(1);
        Sighting sightingOne = response.getToDisplay();
        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(sightingOne);

        Assert.assertSame(1, sightingOne.getSightingId());
        Assert.assertEquals(sightingOneDate, sightingOne.getDate());
        Assert.assertSame(1, sightingOne.getLocationId());
        Assert.assertEquals(1, sightingOne.getAllSupers().size());
        Assert.assertEquals("Gotham City", sightingOne.getSightLocation().getName());

        GetSightingResponse responseTwo = service.getSighting(2);
        Sighting sightingTwo = responseTwo.getToDisplay();
        Assert.assertTrue(responseTwo.isSuccess());
        Assert.assertNotNull(responseTwo.getToDisplay());
        Assert.assertNotNull(sightingTwo);

        Assert.assertSame(2, sightingTwo.getSightingId());
        Assert.assertEquals(sightingTwoDate, sightingTwo.getDate());
        Assert.assertSame(2, sightingTwo.getLocationId());
        Assert.assertEquals(1, sightingTwo.getAllSupers().size());
        Assert.assertEquals("Casa Jacob", sightingTwo.getSightLocation().getName());

        GetSightingResponse responseThree = service.getSighting(3);
        Sighting sightingThree = responseThree.getToDisplay();
        Assert.assertTrue(responseThree.isSuccess());
        Assert.assertNotNull(responseThree.getToDisplay());
        Assert.assertNotNull(sightingThree);

        Assert.assertSame(3, sightingThree.getSightingId());
        Assert.assertEquals(sightingThreeDate, sightingThree.getDate());
        Assert.assertSame(1, sightingThree.getLocationId());
        Assert.assertEquals(2, sightingThree.getAllSupers().size());
        Assert.assertEquals("Gotham City", sightingThree.getSightLocation().getName());

    }

    @Test
    public void testGetSightingFailInvalidId() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);

        GetSightingResponse response = service.getSighting(12);
        Sighting sightingFive = response.getToDisplay();
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(sightingFive);

    }

    @Test
    public void testGetSightingFailFailDao() {

        SuperService service = new SuperService(superFailDao, locationFailDao, orgFailDao, sightingFailDao);

        GetSightingResponse response = service.getSighting(1);
        Sighting sightingOne = response.getToDisplay();
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(sightingOne);

    }

    @Test
    public void testAddSightingSuccess() {

        LocalDate dateOne = LocalDate.of(2017, 04, 13);
        LocalDate dateTwo = LocalDate.of(2016, 04, 13);
        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);

        ListSightingResponse listSightResponse = service.getAllSightings();
        Assert.assertTrue(listSightResponse.isSuccess());
        List<Sighting> allSightings = service.getAllSightings().getAllSightings();
        Assert.assertNotNull(allSightings);
        Assert.assertEquals(11, allSightings.size());

        ListLocationResponse listLocResponse = service.getAllLocations();
        Assert.assertTrue(listLocResponse.isSuccess());
        List<Location> allLocs = service.getAllLocations().getAllLocations();
        Assert.assertNotNull(allLocs);
        Assert.assertEquals(2, allLocs.size());

        ListSuperResponse listSupResponse = service.getAllSupers();
        Assert.assertTrue(listSupResponse.isSuccess());
        List<Super> allSupers = service.getAllSupers().getAllSupers();
        Assert.assertNotNull(allSupers);
        Assert.assertEquals(2, allSupers.size());

        Sighting toAdd = new Sighting();
        toAdd.setDate(dateOne);
        toAdd.setLocationId(1);
        toAdd.setSightLocation(service.getLocation(1).getToDisplay());
        toAdd.getAllSupers().add(allSupers.get(0));
        toAdd.getAllSupers().add(allSupers.get(1));

        AddSightingResponse response = service.addSighting(toAdd);
        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getToAdd());

        ListSightingResponse listSightResponseTwo = service.getAllSightings();
        Assert.assertTrue(listSightResponseTwo.isSuccess());
        List<Sighting> allSightingsTwo = service.getAllSightings().getAllSightings();
        Assert.assertNotNull(allSightingsTwo);
        Assert.assertEquals(12, allSightingsTwo.size());

        Sighting addedSighting = response.getToAdd();
        Assert.assertSame(12, addedSighting.getSightingId());
        Assert.assertEquals(dateOne, addedSighting.getDate());
        Assert.assertSame(1, addedSighting.getLocationId());
        Assert.assertEquals("Gotham City", addedSighting.getSightLocation().getName());

        List<Super> allSupersSightingOne = addedSighting.getAllSupers();
        Assert.assertNotNull(allSupersSightingOne);
        Assert.assertEquals(2, allSupersSightingOne.size());

        Sighting toAddTwo = new Sighting();
        toAddTwo.setDate(dateTwo);
        toAddTwo.setLocationId(2);
        toAddTwo.setSightLocation(service.getLocation(2).getToDisplay());
        toAddTwo.getAllSupers().add(allSupers.get(0));

        AddSightingResponse responseTwo = service.addSighting(toAddTwo);
        Assert.assertTrue(responseTwo.isSuccess());
        Assert.assertNotNull(responseTwo.getToAdd());

        ListSightingResponse listSightResponseThree = service.getAllSightings();
        Assert.assertTrue(listSightResponseThree.isSuccess());
        List<Sighting> allSightingsThree = service.getAllSightings().getAllSightings();
        Assert.assertNotNull(allSightingsThree);
        Assert.assertEquals(13, allSightingsThree.size());

        Sighting addedSightingTwo = responseTwo.getToAdd();
        Assert.assertSame(13, addedSightingTwo.getSightingId());
        Assert.assertEquals(dateTwo, addedSightingTwo.getDate());
        Assert.assertSame(2, addedSightingTwo.getLocationId());
        Assert.assertEquals("Casa Jacob", addedSightingTwo.getSightLocation().getName());

        List<Super> allSupersSightingTwo = addedSightingTwo.getAllSupers();
        Assert.assertNotNull(allSupersSightingTwo);
        Assert.assertEquals(1, allSupersSightingTwo.size());
    }

    @Test
    public void testAddSightingFailFutureDate() {

        LocalDate dateFuture = LocalDate.of(2020, 04, 13);
        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);

        ListSightingResponse listSightResponse = service.getAllSightings();
        Assert.assertTrue(listSightResponse.isSuccess());
        List<Sighting> allSightings = service.getAllSightings().getAllSightings();
        Assert.assertNotNull(allSightings);
        Assert.assertEquals(11, allSightings.size());

        ListLocationResponse listLocResponse = service.getAllLocations();
        Assert.assertTrue(listLocResponse.isSuccess());
        List<Location> allLocs = service.getAllLocations().getAllLocations();
        Assert.assertNotNull(allLocs);
        Assert.assertEquals(2, allLocs.size());

        ListSuperResponse listSupResponse = service.getAllSupers();
        Assert.assertTrue(listSupResponse.isSuccess());
        List<Super> allSupers = service.getAllSupers().getAllSupers();
        Assert.assertNotNull(allSupers);
        Assert.assertEquals(2, allSupers.size());

        Sighting toAdd = new Sighting();
        toAdd.setDate(dateFuture);
        toAdd.setLocationId(1);
        toAdd.setSightLocation(service.getLocation(1).getToDisplay());
        toAdd.getAllSupers().add(allSupers.get(0));
        toAdd.getAllSupers().add(allSupers.get(1));

        AddSightingResponse response = service.addSighting(toAdd);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getToAdd());
    }

    @Test
    public void testAddSightingFailNoDate() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);

        ListSightingResponse listSightResponse = service.getAllSightings();
        Assert.assertTrue(listSightResponse.isSuccess());
        List<Sighting> allSightings = service.getAllSightings().getAllSightings();
        Assert.assertNotNull(allSightings);
        Assert.assertEquals(11, allSightings.size());

        ListLocationResponse listLocResponse = service.getAllLocations();
        Assert.assertTrue(listLocResponse.isSuccess());
        List<Location> allLocs = service.getAllLocations().getAllLocations();
        Assert.assertNotNull(allLocs);
        Assert.assertEquals(2, allLocs.size());

        ListSuperResponse listSupResponse = service.getAllSupers();
        Assert.assertTrue(listSupResponse.isSuccess());
        List<Super> allSupers = service.getAllSupers().getAllSupers();
        Assert.assertNotNull(allSupers);
        Assert.assertEquals(2, allSupers.size());

        Sighting toAdd = new Sighting();
        toAdd.setLocationId(1);
        toAdd.setSightLocation(service.getLocation(1).getToDisplay());
        toAdd.getAllSupers().add(allSupers.get(0));
        toAdd.getAllSupers().add(allSupers.get(1));

        AddSightingResponse response = service.addSighting(toAdd);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getToAdd());
    }

    @Test
    public void testAddSightingFailNoSupers() {

        LocalDate dateOne = LocalDate.of(2018, 04, 13);
        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);

        ListSightingResponse listSightResponse = service.getAllSightings();
        Assert.assertTrue(listSightResponse.isSuccess());
        List<Sighting> allSightings = service.getAllSightings().getAllSightings();
        Assert.assertNotNull(allSightings);
        Assert.assertEquals(11, allSightings.size());

        ListLocationResponse listLocResponse = service.getAllLocations();
        Assert.assertTrue(listLocResponse.isSuccess());
        List<Location> allLocs = service.getAllLocations().getAllLocations();
        Assert.assertNotNull(allLocs);
        Assert.assertEquals(2, allLocs.size());

        List<Super> noSupers = new ArrayList();

        Sighting toAdd = new Sighting();
        toAdd.setDate(dateOne);
        toAdd.setLocationId(1);
        toAdd.setSightLocation(service.getLocation(1).getToDisplay());
        toAdd.setAllSupers(noSupers);

        AddSightingResponse response = service.addSighting(toAdd);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getToAdd());
    }

    @Test
    public void testAddSightingFailNoLocation() {

        LocalDate dateOne = LocalDate.of(2018, 04, 13);
        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);

        ListSightingResponse listSightResponse = service.getAllSightings();
        Assert.assertTrue(listSightResponse.isSuccess());
        List<Sighting> allSightings = service.getAllSightings().getAllSightings();
        Assert.assertNotNull(allSightings);
        Assert.assertEquals(11, allSightings.size());

        ListLocationResponse listLocResponse = service.getAllLocations();
        Assert.assertTrue(listLocResponse.isSuccess());
        List<Location> allLocs = service.getAllLocations().getAllLocations();
        Assert.assertNotNull(allLocs);
        Assert.assertEquals(2, allLocs.size());

        ListSuperResponse listSupResponse = service.getAllSupers();
        Assert.assertTrue(listSupResponse.isSuccess());
        List<Super> allSupers = service.getAllSupers().getAllSupers();
        Assert.assertNotNull(allSupers);
        Assert.assertEquals(2, allSupers.size());

        Sighting toAdd = new Sighting();
        toAdd.setDate(dateOne);
        toAdd.setLocationId(1);
        toAdd.getAllSupers().add(allSupers.get(0));
        toAdd.getAllSupers().add(allSupers.get(1));

        AddSightingResponse response = service.addSighting(toAdd);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getToAdd());
    }

    @Test
    public void testAddSightingInvalidLocationId() {

        LocalDate dateOne = LocalDate.of(2018, 04, 13);
        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);

        ListSightingResponse listSightResponse = service.getAllSightings();
        Assert.assertTrue(listSightResponse.isSuccess());
        List<Sighting> allSightings = service.getAllSightings().getAllSightings();
        Assert.assertNotNull(allSightings);
        Assert.assertEquals(11, allSightings.size());

        ListLocationResponse listLocResponse = service.getAllLocations();
        Assert.assertTrue(listLocResponse.isSuccess());
        List<Location> allLocs = service.getAllLocations().getAllLocations();
        Assert.assertNotNull(allLocs);
        Assert.assertEquals(2, allLocs.size());

        ListSuperResponse listSupResponse = service.getAllSupers();
        Assert.assertTrue(listSupResponse.isSuccess());
        List<Super> allSupers = service.getAllSupers().getAllSupers();
        Assert.assertNotNull(allSupers);
        Assert.assertEquals(2, allSupers.size());

        Sighting toAdd = new Sighting();
        toAdd.setDate(dateOne);
        toAdd.setLocationId(20);
        toAdd.setSightLocation(service.getLocation(1).getToDisplay());
        toAdd.getAllSupers().add(allSupers.get(0));
        toAdd.getAllSupers().add(allSupers.get(1));

        AddSightingResponse response = service.addSighting(toAdd);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getToAdd());
    }

    @Test
    public void testAddSightingFailFailDao() {

        LocalDate dateOne = LocalDate.of(2017, 04, 13);
        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingFailDao);

        ListLocationResponse listLocResponse = service.getAllLocations();
        Assert.assertTrue(listLocResponse.isSuccess());
        List<Location> allLocs = service.getAllLocations().getAllLocations();
        Assert.assertNotNull(allLocs);
        Assert.assertEquals(2, allLocs.size());

        ListSuperResponse listSupResponse = service.getAllSupers();
        Assert.assertTrue(listSupResponse.isSuccess());
        List<Super> allSupers = service.getAllSupers().getAllSupers();
        Assert.assertNotNull(allSupers);
        Assert.assertEquals(2, allSupers.size());

        Sighting toAdd = new Sighting();
        toAdd.setDate(dateOne);
        toAdd.setLocationId(1);
        toAdd.setSightLocation(service.getLocation(1).getToDisplay());
        toAdd.getAllSupers().add(allSupers.get(0));
        toAdd.getAllSupers().add(allSupers.get(1));

        AddSightingResponse response = service.addSighting(toAdd);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getToAdd());
    }

    @Test
    public void testEditSightingSuccess() {

        LocalDate sightingOneDate = LocalDate.of(2018, 04, 13);
        LocalDate sightingTwoDate = LocalDate.of(2019, 03, 10);
        LocalDate dateThree = LocalDate.of(2018, 01, 01);
        LocalDate dateFour = LocalDate.of(2011, 01, 01);

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);

        GetSightingResponse getSightResponse = service.getSighting(1);
        Sighting toEdit = getSightResponse.getToDisplay();
        Assert.assertTrue(getSightResponse.isSuccess());
        Assert.assertNotNull(toEdit);

        Assert.assertSame(1, toEdit.getSightingId());
        Assert.assertEquals(sightingOneDate, toEdit.getDate());
        Assert.assertSame(1, toEdit.getLocationId());
        Assert.assertEquals(1, toEdit.getAllSupers().size());
        Assert.assertEquals("Gotham City", toEdit.getSightLocation().getName());

        ListSuperResponse listSupResponse = service.getAllSupers();
        Assert.assertTrue(listSupResponse.isSuccess());
        List<Super> allSupers = service.getAllSupers().getAllSupers();
        Assert.assertNotNull(allSupers);
        Assert.assertEquals(2, allSupers.size());

        toEdit.setDate(dateThree);
        toEdit.setLocationId(2);
        toEdit.setSightLocation(service.getLocation(2).getToDisplay());
        toEdit.getAllSupers().add(allSupers.get(0));
        toEdit.getAllSupers().add(allSupers.get(1));

        EditSightingResponse response = service.editSighting(toEdit);
        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEditedSighting());

        Sighting editedSighting = response.getEditedSighting();
        Assert.assertSame(1, editedSighting.getSightingId());
        Assert.assertEquals(dateThree, editedSighting.getDate());
        Assert.assertSame(2, editedSighting.getLocationId());
        Assert.assertEquals("Casa Jacob", editedSighting.getSightLocation().getName());

        GetSightingResponse getSightResponseTwo = service.getSighting(2);
        Sighting toEditTwo = getSightResponseTwo.getToDisplay();
        Assert.assertTrue(getSightResponseTwo.isSuccess());
        Assert.assertNotNull(getSightResponseTwo.getToDisplay());
        Assert.assertNotNull(toEditTwo);
        Assert.assertEquals(1, toEditTwo.getAllSupers().size());

        toEditTwo.setDate(dateFour);
        toEditTwo.setLocationId(2);
        toEditTwo.setSightLocation(service.getLocation(2).getToDisplay());
        toEditTwo.getAllSupers().add(allSupers.get(0));

        EditSightingResponse responseTwo = service.editSighting(toEditTwo);
        Assert.assertTrue(responseTwo.isSuccess());
        Assert.assertNotNull(responseTwo.getEditedSighting());

        Sighting editedSightingTwo = responseTwo.getEditedSighting();
        Assert.assertSame(2, editedSightingTwo.getSightingId());
        Assert.assertEquals(dateFour, editedSightingTwo.getDate());
        Assert.assertSame(2, editedSightingTwo.getLocationId());
        Assert.assertEquals("Casa Jacob", editedSightingTwo.getSightLocation().getName());
        Assert.assertEquals(1, editedSightingTwo.getAllSupers().size());

    }

    @Test
    public void testEditSightingFailFutureDate() {

        LocalDate dateFuture = LocalDate.of(2020, 04, 13);
        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);

        ListSightingResponse listSightResponse = service.getAllSightings();
        Assert.assertTrue(listSightResponse.isSuccess());
        List<Sighting> allSightings = service.getAllSightings().getAllSightings();
        Assert.assertNotNull(allSightings);
        Assert.assertEquals(11, allSightings.size());

        ListLocationResponse listLocResponse = service.getAllLocations();
        Assert.assertTrue(listLocResponse.isSuccess());
        List<Location> allLocs = service.getAllLocations().getAllLocations();
        Assert.assertNotNull(allLocs);
        Assert.assertEquals(2, allLocs.size());

        ListSuperResponse listSupResponse = service.getAllSupers();
        Assert.assertTrue(listSupResponse.isSuccess());
        List<Super> allSupers = service.getAllSupers().getAllSupers();
        Assert.assertNotNull(allSupers);
        Assert.assertEquals(2, allSupers.size());

        GetSightingResponse response = service.getSighting(1);
        Sighting toEdit = response.getToDisplay();
        toEdit.setDate(dateFuture);
        toEdit.setLocationId(1);
        toEdit.setSightLocation(service.getLocation(1).getToDisplay());
        toEdit.getAllSupers().add(allSupers.get(0));
        toEdit.getAllSupers().add(allSupers.get(1));

        EditSightingResponse editResponse = service.editSighting(toEdit);
        Assert.assertFalse(editResponse.isSuccess());
        Assert.assertNull(editResponse.getEditedSighting());
    }

    @Test
    public void testEditSightingFailNoDate() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);

        ListSightingResponse listSightResponse = service.getAllSightings();
        Assert.assertTrue(listSightResponse.isSuccess());
        List<Sighting> allSightings = service.getAllSightings().getAllSightings();
        Assert.assertNotNull(allSightings);
        Assert.assertEquals(11, allSightings.size());

        ListLocationResponse listLocResponse = service.getAllLocations();
        Assert.assertTrue(listLocResponse.isSuccess());
        List<Location> allLocs = service.getAllLocations().getAllLocations();
        Assert.assertNotNull(allLocs);
        Assert.assertEquals(2, allLocs.size());

        ListSuperResponse listSupResponse = service.getAllSupers();
        Assert.assertTrue(listSupResponse.isSuccess());
        List<Super> allSupers = service.getAllSupers().getAllSupers();
        Assert.assertNotNull(allSupers);
        Assert.assertEquals(2, allSupers.size());

        GetSightingResponse response = service.getSighting(1);
        Sighting toEdit = response.getToDisplay();
        toEdit.setLocationId(1);
        toEdit.setDate(null);
        toEdit.setSightLocation(service.getLocation(1).getToDisplay());
        toEdit.getAllSupers().add(allSupers.get(0));
        toEdit.getAllSupers().add(allSupers.get(1));

        EditSightingResponse editResponse = service.editSighting(toEdit);
        Assert.assertFalse(editResponse.isSuccess());
        Assert.assertNull(editResponse.getEditedSighting());
    }

    @Test
    public void testEditSightingFailNoSupers() {

        LocalDate dateOne = LocalDate.of(2018, 04, 13);
        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);

        ListSightingResponse listSightResponse = service.getAllSightings();
        Assert.assertTrue(listSightResponse.isSuccess());
        List<Sighting> allSightings = service.getAllSightings().getAllSightings();
        Assert.assertNotNull(allSightings);
        Assert.assertEquals(11, allSightings.size());

        ListLocationResponse listLocResponse = service.getAllLocations();
        Assert.assertTrue(listLocResponse.isSuccess());
        List<Location> allLocs = service.getAllLocations().getAllLocations();
        Assert.assertNotNull(allLocs);
        Assert.assertEquals(2, allLocs.size());

        List<Super> noSupers = new ArrayList();

        GetSightingResponse response = service.getSighting(1);
        Sighting toEdit = response.getToDisplay();
        toEdit.setDate(dateOne);
        toEdit.setLocationId(1);
        toEdit.setSightLocation(service.getLocation(1).getToDisplay());
        toEdit.setAllSupers(noSupers);

        EditSightingResponse editResponse = service.editSighting(toEdit);
        Assert.assertFalse(editResponse.isSuccess());
        Assert.assertNull(editResponse.getEditedSighting());
    }

    @Test
    public void testEditSightingFailNoLocation() {

        LocalDate dateOne = LocalDate.of(2018, 04, 13);
        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);

        ListSightingResponse listSightResponse = service.getAllSightings();
        Assert.assertTrue(listSightResponse.isSuccess());
        List<Sighting> allSightings = service.getAllSightings().getAllSightings();
        Assert.assertNotNull(allSightings);
        Assert.assertEquals(11, allSightings.size());

        ListLocationResponse listLocResponse = service.getAllLocations();
        Assert.assertTrue(listLocResponse.isSuccess());
        List<Location> allLocs = service.getAllLocations().getAllLocations();
        Assert.assertNotNull(allLocs);
        Assert.assertEquals(2, allLocs.size());

        ListSuperResponse listSupResponse = service.getAllSupers();
        Assert.assertTrue(listSupResponse.isSuccess());
        List<Super> allSupers = service.getAllSupers().getAllSupers();
        Assert.assertNotNull(allSupers);
        Assert.assertEquals(2, allSupers.size());

        GetSightingResponse response = service.getSighting(1);
        Sighting toEdit = response.getToDisplay();
        toEdit.setDate(dateOne);
        toEdit.setSightLocation(null);
        toEdit.getAllSupers().add(allSupers.get(0));
        toEdit.getAllSupers().add(allSupers.get(1));

        EditSightingResponse editResponse = service.editSighting(toEdit);
        Assert.assertFalse(editResponse.isSuccess());
        Assert.assertNull(editResponse.getEditedSighting());
    }

    @Test
    public void testEditSightingInvalidSightingId() {

        LocalDate dateOne = LocalDate.of(2018, 04, 13);
        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);

        ListSightingResponse listSightResponse = service.getAllSightings();
        Assert.assertTrue(listSightResponse.isSuccess());
        List<Sighting> allSightings = service.getAllSightings().getAllSightings();
        Assert.assertNotNull(allSightings);
        Assert.assertEquals(11, allSightings.size());

        ListLocationResponse listLocResponse = service.getAllLocations();
        Assert.assertTrue(listLocResponse.isSuccess());
        List<Location> allLocs = service.getAllLocations().getAllLocations();
        Assert.assertNotNull(allLocs);
        Assert.assertEquals(2, allLocs.size());

        ListSuperResponse listSupResponse = service.getAllSupers();
        Assert.assertTrue(listSupResponse.isSuccess());
        List<Super> allSupers = service.getAllSupers().getAllSupers();
        Assert.assertNotNull(allSupers);
        Assert.assertEquals(2, allSupers.size());

        List<Super> allSupersForEdit = new ArrayList();
        allSupersForEdit.add(allSupers.get(0));
        allSupersForEdit.add(allSupers.get(1));
        Sighting toEdit = new Sighting();
        toEdit.setSightingId(13);
        toEdit.setDate(dateOne);
        toEdit.setLocationId(1);
        toEdit.setSightLocation(service.getLocation(1).getToDisplay());
        toEdit.setAllSupers(allSupersForEdit);

        EditSightingResponse editResponse = service.editSighting(toEdit);
        Assert.assertFalse(editResponse.isSuccess());
        Assert.assertNull(editResponse.getEditedSighting());
    }

    @Test
    public void testEditSightingFailFailDao() {

        LocalDate dateOne = LocalDate.of(2017, 04, 13);
        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingFailDao);

        ListLocationResponse listLocResponse = service.getAllLocations();
        Assert.assertTrue(listLocResponse.isSuccess());
        List<Location> allLocs = service.getAllLocations().getAllLocations();
        Assert.assertNotNull(allLocs);
        Assert.assertEquals(2, allLocs.size());

        ListSuperResponse listSupResponse = service.getAllSupers();
        Assert.assertTrue(listSupResponse.isSuccess());
        List<Super> allSupers = service.getAllSupers().getAllSupers();
        Assert.assertNotNull(allSupers);
        Assert.assertEquals(2, allSupers.size());

        GetSightingResponse response = service.getSighting(1);
        Sighting toEdit = new Sighting();
        toEdit.setSightingId(1);
        toEdit.setDate(dateOne);
        toEdit.setLocationId(1);
        toEdit.setSightLocation(service.getLocation(1).getToDisplay());
        toEdit.getAllSupers().add(allSupers.get(0));
        toEdit.getAllSupers().add(allSupers.get(1));

        EditSightingResponse editResponse = service.editSighting(toEdit);
        Assert.assertFalse(editResponse.isSuccess());
        Assert.assertNull(editResponse.getEditedSighting());
    }

    @Test
    public void testDeleteSightingSuccess() {
        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);

        ListSightingResponse listSightResponse = service.getAllSightings();
        List<Sighting> sightingsToGet = listSightResponse.getAllSightings();
        Assert.assertTrue(listSightResponse.isSuccess());
        Assert.assertNotNull(listSightResponse.getAllSightings());
        Assert.assertEquals(11, sightingsToGet.size());

        ListSuperResponse listSupResponse = service.getAllSupers();
        List<Super> supersToGet = listSupResponse.getAllSupers();
        Assert.assertTrue(listSupResponse.isSuccess());
        Assert.assertNotNull(listSupResponse.getAllSupers());
        Assert.assertEquals(2, supersToGet.size());

        ListLocationResponse listLocResponse = service.getAllLocations();
        Assert.assertTrue(listLocResponse.isSuccess());
        List<Location> allLocs = service.getAllLocations().getAllLocations();
        Assert.assertNotNull(allLocs);
        Assert.assertEquals(2, allLocs.size());

        GetSightingResponse getSightingResponse = service.getSighting(1);
        Assert.assertNotNull(getSightingResponse.getToDisplay());
        Sighting sightingOne = getSightingResponse.getToDisplay();
        Assert.assertSame(1, sightingOne.getSightingId());

        DeleteSightingResponse deleteSightingOneResponse = service.deleteSighting(1);
        Assert.assertTrue(deleteSightingOneResponse.isSuccess());

        ListSightingResponse listSightResponseOneDeleted = service.getAllSightings();
        List<Sighting> sightingsToGetOneDeleted = listSightResponseOneDeleted.getAllSightings();
        Assert.assertTrue(listSightResponseOneDeleted.isSuccess());
        Assert.assertNotNull(listSightResponseOneDeleted.getAllSightings());
        Assert.assertEquals(10, sightingsToGetOneDeleted.size());

        GetSightingResponse getSightingResponseDeleteTwo = service.getSighting(11);
        Assert.assertNotNull(getSightingResponseDeleteTwo.getToDisplay());
        Sighting sightingEleven = getSightingResponseDeleteTwo.getToDisplay();
        Assert.assertSame(11, sightingEleven.getSightingId());

        DeleteSightingResponse deleteSightingElevenResponse = service.deleteSighting(11);
        Assert.assertTrue(deleteSightingElevenResponse.isSuccess());

        ListSightingResponse listSightResponseTwoDeleted = service.getAllSightings();
        List<Sighting> sightingsToGetTwoDeleted = listSightResponseTwoDeleted.getAllSightings();
        Assert.assertTrue(listSightResponseTwoDeleted.isSuccess());
        Assert.assertNotNull(listSightResponseTwoDeleted.getAllSightings());
        Assert.assertEquals(9, sightingsToGetTwoDeleted.size());

    }

    @Test
    public void testDeleteSightingFailInvalidId() {
        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);

        DeleteSightingResponse deleteResponse = service.deleteSighting(15);
        Assert.assertFalse(deleteResponse.isSuccess());

    }

    @Test
    public void testDeleteSightingFailFailDao() {
        SuperService service = new SuperService(superFailDao, locationFailDao, orgFailDao, sightingFailDao);

        DeleteSightingResponse deleteResponse = service.deleteSighting(1);
        Assert.assertFalse(deleteResponse.isSuccess());

    }

    /**
     * Test of getAllOrgs method, of class SuperService.
     */
    @Test
    public void testGetAllOrgsSuccess() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);

        ListOrganizationResponse response = service.getAllOrgs();
        List<Organization> orgsToGet = response.getAllOrgs();
        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getAllOrgs());
        Assert.assertEquals(2, orgsToGet.size());
        Assert.assertSame(1, orgsToGet.get(0).getOrganizationId());
        Assert.assertEquals("Marvel", orgsToGet.get(0).getName());
        Assert.assertEquals("Marveling", orgsToGet.get(0).getDescription());
        Assert.assertEquals("456 Test Avenue, Saint Paul, MN 55104", orgsToGet.get(0).getAddress());
        Assert.assertEquals("7702410271", orgsToGet.get(0).getPhoneNumber());
        Assert.assertEquals("marvel@marvel.com", orgsToGet.get(0).getEmail());
        Assert.assertEquals(1, orgsToGet.get(0).getAllSupers().size());

        Assert.assertSame(2, orgsToGet.get(1).getOrganizationId());
        Assert.assertEquals("DC", orgsToGet.get(1).getName());
        Assert.assertEquals("DC What", orgsToGet.get(1).getDescription());
        Assert.assertEquals("456 Test Street, Saint Paul, MN 55104", orgsToGet.get(1).getAddress());
        Assert.assertEquals("7705181492", orgsToGet.get(1).getPhoneNumber());
        Assert.assertEquals("dc@dc.com", orgsToGet.get(1).getEmail());
        Assert.assertEquals(2, orgsToGet.get(1).getAllSupers().size());

    }

    @Test
    public void testGetAllOrgsFail() {

        SuperService service = new SuperService(superFailDao, locationFailDao, orgFailDao, sightingFailDao);

        ListOrganizationResponse response = service.getAllOrgs();
        List<Organization> orgsToGet = response.getAllOrgs();
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(orgsToGet);
    }

    /**
     * Test of getOrganization method, of class SuperService.
     */
    @Test
    public void testGetOrganizationSuccess() {
        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);

        GetOrganizationResponse response = service.getOrganization(1);
        Organization organizationOne = response.getToDisplay();
        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(organizationOne);

        Assert.assertSame(1, organizationOne.getOrganizationId());
        Assert.assertEquals("Marvel", organizationOne.getName());
        Assert.assertEquals("Marveling", organizationOne.getDescription());
        Assert.assertEquals("456 Test Avenue, Saint Paul, MN 55104", organizationOne.getAddress());
        Assert.assertEquals("7702410271", organizationOne.getPhoneNumber());
        Assert.assertEquals("marvel@marvel.com", organizationOne.getEmail());
        Assert.assertEquals(1, organizationOne.getAllSupers().size());

        GetOrganizationResponse responseTwo = service.getOrganization(2);
        Organization organizationTwo = responseTwo.getToDisplay();
        Assert.assertTrue(responseTwo.isSuccess());
        Assert.assertNotNull(organizationTwo);

        Assert.assertSame(2, organizationTwo.getOrganizationId());
        Assert.assertEquals("DC", organizationTwo.getName());
        Assert.assertEquals("DC What", organizationTwo.getDescription());
        Assert.assertEquals("456 Test Street, Saint Paul, MN 55104", organizationTwo.getAddress());
        Assert.assertEquals("7705181492", organizationTwo.getPhoneNumber());
        Assert.assertEquals("dc@dc.com", organizationTwo.getEmail());
        Assert.assertEquals(2, organizationTwo.getAllSupers().size());

    }

    @Test
    public void testGetOrganizationFailInvalidId() {
        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);

        GetOrganizationResponse response = service.getOrganization(4);
        Organization organizationFour = response.getToDisplay();
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(organizationFour);

    }

    @Test
    public void testGetOrganizationFailFailDao() {
        SuperService service = new SuperService(superFailDao, locationFailDao, orgFailDao, sightingFailDao);

        GetOrganizationResponse response = service.getOrganization(1);
        Organization organizationOne = response.getToDisplay();
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(organizationOne);

    }

    @Test
    public void testAddOrganizationSuccess() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);

        List<Organization> allOrgs = service.getAllOrgs().getAllOrgs();
        Assert.assertEquals(2, allOrgs.size());

        Organization toAdd = new Organization();
        toAdd.setName("Best Org");
        toAdd.setDescription("The best");
        toAdd.setAddress("123 North Street");
        toAdd.setPhoneNumber("7702410271");
        toAdd.setEmail("best@best.com");
        AddOrganizationResponse response = service.addOrganization(toAdd);
        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getToAdd());

        Organization addedOrg = response.getToAdd();
        Assert.assertSame(3, addedOrg.getOrganizationId());
        Assert.assertEquals("Best Org", addedOrg.getName());
        Assert.assertEquals("The best", addedOrg.getDescription());
        Assert.assertEquals("123 North Street", addedOrg.getAddress());
        Assert.assertEquals("7702410271", addedOrg.getPhoneNumber());
        Assert.assertEquals("best@best.com", addedOrg.getEmail());

        List<Organization> allOrgsAdded = service.getAllOrgs().getAllOrgs();
        Assert.assertEquals(3, allOrgsAdded.size());

        Organization toAddTwo = new Organization();
        toAddTwo.setName("Best Org Two");
        toAddTwo.setDescription("The best Two");
        toAddTwo.setAddress("2123 North Street");
        toAddTwo.setPhoneNumber("7702410272");
        toAddTwo.setEmail("2best@best.com");
        AddOrganizationResponse responseTwo = service.addOrganization(toAddTwo);
        Assert.assertTrue(responseTwo.isSuccess());
        Assert.assertNotNull(responseTwo.getToAdd());

        Organization addedOrgTwo = responseTwo.getToAdd();
        Assert.assertSame(4, addedOrgTwo.getOrganizationId());
        Assert.assertEquals("Best Org Two", addedOrgTwo.getName());
        Assert.assertEquals("The best Two", addedOrgTwo.getDescription());
        Assert.assertEquals("2123 North Street", addedOrgTwo.getAddress());
        Assert.assertEquals("7702410272", addedOrgTwo.getPhoneNumber());
        Assert.assertEquals("2best@best.com", addedOrgTwo.getEmail());

        List<Organization> allOrgsTwo = service.getAllOrgs().getAllOrgs();
        Assert.assertEquals(4, allOrgsTwo.size());
    }

    @Test
    public void testAddOrganizationFailBlankName() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        Organization toAdd = new Organization();
        toAdd.setName("");
        toAdd.setDescription("The best");
        toAdd.setAddress("123 North Street");
        toAdd.setPhoneNumber("7702410271");
        toAdd.setEmail("best@best.com");
        AddOrganizationResponse response = service.addOrganization(toAdd);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getToAdd());
    }

    @Test
    public void testAddOrganizationFailMaxName() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        Organization toAdd = new Organization();
        toAdd.setName("llllllllllaaaaaaaaaaaalllllllll");
        toAdd.setDescription("The best");
        toAdd.setAddress("123 North Street");
        toAdd.setPhoneNumber("7702410271");
        toAdd.setEmail("best@best.com");
        AddOrganizationResponse response = service.addOrganization(toAdd);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getToAdd());
    }

    @Test
    public void testAddOrganizationFailBlankDescription() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        Organization toAdd = new Organization();
        toAdd.setName("Best Org");
        toAdd.setDescription("");
        toAdd.setAddress("123 North Street");
        toAdd.setPhoneNumber("7702410271");
        toAdd.setEmail("best@best.com");
        AddOrganizationResponse response = service.addOrganization(toAdd);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getToAdd());
    }

    @Test
    public void testAddOrganizationFailMaxDescription() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        Organization toAdd = new Organization();
        toAdd.setName("Best Org");
        toAdd.setDescription("dsdsdsddsssssjsjsjsjsjsjsjsjsjsjsjsjsjsjsjllllllllllllllllllljsjsjsjsjsjsjsjsjsjsjsjsjsjsjsjlllllllllllllllllll");
        toAdd.setAddress("123 North Street");
        toAdd.setPhoneNumber("7702410271");
        toAdd.setEmail("best@best.com");
        AddOrganizationResponse response = service.addOrganization(toAdd);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getToAdd());
    }

    @Test
    public void testAddOrganizationFailBlankAddress() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        Organization toAdd = new Organization();
        toAdd.setName("Best Org");
        toAdd.setDescription("The best");
        toAdd.setAddress("");
        toAdd.setPhoneNumber("7702410271");
        toAdd.setEmail("best@best.com");
        AddOrganizationResponse response = service.addOrganization(toAdd);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getToAdd());
    }

    @Test
    public void testAddOrganizationFailMaxAddress() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        Organization toAdd = new Organization();
        toAdd.setName("Best Org");
        toAdd.setDescription("The best");
        toAdd.setAddress("jdsdsdsddsssssjsjsjsjsjsjsjsjsjsjsjsjsjsjsjllllllllllllllllllljsjsjsjsjsjsjsjsjsjsjsjsjsjsjsjlllllllllllllllllll");
        toAdd.setPhoneNumber("7702410271");
        toAdd.setEmail("best@best.com");
        AddOrganizationResponse response = service.addOrganization(toAdd);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getToAdd());
    }

    @Test
    public void testAddOrganizationFailBlankPhoneNumber() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        Organization toAdd = new Organization();
        toAdd.setName("Best Org");
        toAdd.setDescription("The best");
        toAdd.setAddress("123 high Street");
        toAdd.setPhoneNumber("");
        toAdd.setEmail("best@best.com");
        AddOrganizationResponse response = service.addOrganization(toAdd);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getToAdd());
    }

    @Test
    public void testAddOrganizationFailMaxPhoneNumber() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        Organization toAdd = new Organization();
        toAdd.setName("Best Org");
        toAdd.setDescription("The best");
        toAdd.setAddress("123 High Street");
        toAdd.setPhoneNumber("770241027112345");
        toAdd.setEmail("best@best.com");
        AddOrganizationResponse response = service.addOrganization(toAdd);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getToAdd());
    }

    @Test
    public void testAddOrganizationFailMinPhoneNumber() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        Organization toAdd = new Organization();
        toAdd.setName("Best Org");
        toAdd.setDescription("The best");
        toAdd.setAddress("123 High Street");
        toAdd.setPhoneNumber("770241");
        toAdd.setEmail("best@best.com");
        AddOrganizationResponse response = service.addOrganization(toAdd);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getToAdd());
    }

    @Test
    public void testAddOrganizationFailBlankEmail() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        Organization toAdd = new Organization();
        toAdd.setName("Best Org");
        toAdd.setDescription("The best");
        toAdd.setAddress("123 high Street");
        toAdd.setPhoneNumber("7702410271");
        toAdd.setEmail("");
        AddOrganizationResponse response = service.addOrganization(toAdd);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getToAdd());
    }

    @Test
    public void testAddOrganizationFailMinEmail() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        Organization toAdd = new Organization();
        toAdd.setName("Best Org");
        toAdd.setDescription("The best");
        toAdd.setAddress("123 High Street");
        toAdd.setPhoneNumber("7702410271");
        toAdd.setEmail("be");
        AddOrganizationResponse response = service.addOrganization(toAdd);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getToAdd());
    }

    @Test
    public void testAddOrganizationFailMaxEmail() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        Organization toAdd = new Organization();
        toAdd.setName("Best Org");
        toAdd.setDescription("The best");
        toAdd.setAddress("123 High Street");
        toAdd.setPhoneNumber("7702410271");
        toAdd.setEmail("lslslslslslslslslslslslslslslslslslsl");
        AddOrganizationResponse response = service.addOrganization(toAdd);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getToAdd());
    }

    @Test
    public void testAddOrganizationFailFailDao() {

        SuperService service = new SuperService(superFailDao, locationFailDao, orgFailDao, sightingFailDao);
        Organization toAdd = new Organization();
        toAdd.setName("Best Org");
        toAdd.setDescription("The best");
        toAdd.setAddress("123 High Street");
        toAdd.setPhoneNumber("7702410271");
        toAdd.setEmail("lslslslslsls");
        AddOrganizationResponse response = service.addOrganization(toAdd);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getToAdd());
    }

    @Test
    public void testEditOrganizationSuccess() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);

        List<Organization> allOrgs = service.getAllOrgs().getAllOrgs();
        Assert.assertEquals(2, allOrgs.size());

        GetOrganizationResponse responseOne = service.getOrganization(1);
        Organization toEditOne = responseOne.getToDisplay();
        Assert.assertTrue(responseOne.isSuccess());
        Assert.assertNotNull(toEditOne);

        Assert.assertSame(1, toEditOne.getOrganizationId());
        Assert.assertEquals("Marvel", toEditOne.getName());
        Assert.assertEquals("Marveling", toEditOne.getDescription());
        Assert.assertEquals("456 Test Avenue, Saint Paul, MN 55104", toEditOne.getAddress());
        Assert.assertEquals("7702410271", toEditOne.getPhoneNumber());
        Assert.assertEquals("marvel@marvel.com", toEditOne.getEmail());

        toEditOne.setName("Best Org Two");
        toEditOne.setDescription("The best Two");
        toEditOne.setAddress("2123 North Street");
        toEditOne.setPhoneNumber("7702410272");
        toEditOne.setEmail("2best@best.com");
        EditOrganizationResponse editResponse = service.editOrganization(toEditOne);
        Assert.assertTrue(editResponse.isSuccess());
        Assert.assertNotNull(editResponse.getEditedOrganization());

        Organization editedOrg = editResponse.getEditedOrganization();
        Assert.assertSame(1, editedOrg.getOrganizationId());
        Assert.assertEquals("Best Org Two", editedOrg.getName());
        Assert.assertEquals("The best Two", editedOrg.getDescription());
        Assert.assertEquals("2123 North Street", editedOrg.getAddress());
        Assert.assertEquals("7702410272", editedOrg.getPhoneNumber());
        Assert.assertEquals("2best@best.com", editedOrg.getEmail());

        GetOrganizationResponse responseTwo = service.getOrganization(2);
        Organization toEditTwo = responseTwo.getToDisplay();
        Assert.assertTrue(responseTwo.isSuccess());
        Assert.assertNotNull(toEditTwo);

        Assert.assertSame(2, toEditTwo.getOrganizationId());
        Assert.assertEquals("DC", toEditTwo.getName());
        Assert.assertEquals("DC What", toEditTwo.getDescription());
        Assert.assertEquals("456 Test Street, Saint Paul, MN 55104", toEditTwo.getAddress());
        Assert.assertEquals("7705181492", toEditTwo.getPhoneNumber());
        Assert.assertEquals("dc@dc.com", toEditTwo.getEmail());

        toEditTwo.setName("Best Org Kinda");
        toEditTwo.setDescription("The best kinda");
        toEditTwo.setAddress("3333 North Street");
        toEditTwo.setPhoneNumber("4702410272");
        toEditTwo.setEmail("notthebest@best.com");
        EditOrganizationResponse editResponseTwo = service.editOrganization(toEditTwo);
        Assert.assertTrue(editResponseTwo.isSuccess());
        Assert.assertNotNull(editResponseTwo.getEditedOrganization());

        Organization editedOrgTwo = editResponseTwo.getEditedOrganization();
        Assert.assertSame(2, editedOrgTwo.getOrganizationId());
        Assert.assertEquals("Best Org Kinda", editedOrgTwo.getName());
        Assert.assertEquals("The best kinda", editedOrgTwo.getDescription());
        Assert.assertEquals("3333 North Street", editedOrgTwo.getAddress());
        Assert.assertEquals("4702410272", editedOrgTwo.getPhoneNumber());
        Assert.assertEquals("notthebest@best.com", editedOrgTwo.getEmail());

    }

    @Test
    public void testEditOrganizationFailBlankName() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        GetOrganizationResponse responseTwo = service.getOrganization(2);
        Organization toEdit = responseTwo.getToDisplay();
        Assert.assertTrue(responseTwo.isSuccess());
        Assert.assertNotNull(toEdit);
        toEdit.setName("");
        toEdit.setDescription("The best kinda");
        toEdit.setAddress("3333 North Street");
        toEdit.setPhoneNumber("4702410272");
        toEdit.setEmail("notthebest@best.com");
        EditOrganizationResponse editResponse = service.editOrganization(toEdit);
        Assert.assertFalse(editResponse.isSuccess());
        Assert.assertNull(editResponse.getEditedOrganization());

    }

    @Test
    public void testEditOrganizationFailMaxName() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        GetOrganizationResponse responseTwo = service.getOrganization(2);
        Organization toEdit = responseTwo.getToDisplay();
        Assert.assertTrue(responseTwo.isSuccess());
        Assert.assertNotNull(toEdit);
        toEdit.setName("llllllllllaaaaaaaaaaaalllllllll");
        toEdit.setDescription("The best kinda");
        toEdit.setAddress("3333 North Street");
        toEdit.setPhoneNumber("4702410272");
        toEdit.setEmail("notthebest@best.com");
        EditOrganizationResponse editResponse = service.editOrganization(toEdit);
        Assert.assertFalse(editResponse.isSuccess());
        Assert.assertNull(editResponse.getEditedOrganization());
    }

    @Test
    public void testEditOrganizationFailBlankDescription() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        GetOrganizationResponse responseTwo = service.getOrganization(2);
        Organization toEdit = responseTwo.getToDisplay();
        Assert.assertTrue(responseTwo.isSuccess());
        Assert.assertNotNull(toEdit);
        toEdit.setName("Marvel");
        toEdit.setDescription("");
        toEdit.setAddress("3333 North Street");
        toEdit.setPhoneNumber("4702410272");
        toEdit.setEmail("notthebest@best.com");
        EditOrganizationResponse editResponse = service.editOrganization(toEdit);
        Assert.assertFalse(editResponse.isSuccess());
        Assert.assertNull(editResponse.getEditedOrganization());
    }

    @Test
    public void testEditOrganizationFailMaxDescription() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        GetOrganizationResponse responseTwo = service.getOrganization(2);
        Organization toEdit = responseTwo.getToDisplay();
        Assert.assertTrue(responseTwo.isSuccess());
        Assert.assertNotNull(toEdit);
        toEdit.setName("Marvel");
        toEdit.setDescription("dsdsdsddsssssjsjsjsjsjsjsjsjsjsjsjsjsjsjsjllllllllllllllllllljsjsjsjsjsjsjsjsjsjsjsjsjsjsjsjlllllllllllllllllll");
        toEdit.setAddress("3333 North Street");
        toEdit.setPhoneNumber("4702410272");
        toEdit.setEmail("notthebest@best.com");
        EditOrganizationResponse editResponse = service.editOrganization(toEdit);
        Assert.assertFalse(editResponse.isSuccess());
        Assert.assertNull(editResponse.getEditedOrganization());
    }

    @Test
    public void testEditOrganizationFailBlankAddress() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        GetOrganizationResponse responseTwo = service.getOrganization(2);
        Organization toEdit = responseTwo.getToDisplay();
        Assert.assertTrue(responseTwo.isSuccess());
        Assert.assertNotNull(toEdit);
        toEdit.setName("Marvel");
        toEdit.setDescription("A good org");
        toEdit.setAddress("");
        toEdit.setPhoneNumber("4702410272");
        toEdit.setEmail("notthebest@best.com");
        EditOrganizationResponse editResponse = service.editOrganization(toEdit);
        Assert.assertFalse(editResponse.isSuccess());
        Assert.assertNull(editResponse.getEditedOrganization());
    }

    @Test
    public void testEditOrganizationFailMaxAddress() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        GetOrganizationResponse responseTwo = service.getOrganization(2);
        Organization toEdit = responseTwo.getToDisplay();
        Assert.assertTrue(responseTwo.isSuccess());
        Assert.assertNotNull(toEdit);
        toEdit.setName("Marvel");
        toEdit.setDescription("A good org");
        toEdit.setAddress("jdsdsdsddsssssjsjsjsjsjsjsjsjsjsjsjsjsjsjsjllllllllllllllllllljsjsjsjsjsjsjsjsjsjsjsjsjsjsjsjlllllllllllllllllll");
        toEdit.setPhoneNumber("4702410272");
        toEdit.setEmail("notthebest@best.com");
        EditOrganizationResponse editResponse = service.editOrganization(toEdit);
        Assert.assertFalse(editResponse.isSuccess());
        Assert.assertNull(editResponse.getEditedOrganization());
    }

    @Test
    public void testEditOrganizationFailBlankPhoneNumber() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        GetOrganizationResponse responseTwo = service.getOrganization(2);
        Organization toEdit = responseTwo.getToDisplay();
        Assert.assertTrue(responseTwo.isSuccess());
        Assert.assertNotNull(toEdit);
        toEdit.setName("Marvel");
        toEdit.setDescription("A good org");
        toEdit.setAddress("123 Main Street Alpharetta, Ga 30022");
        toEdit.setPhoneNumber("");
        toEdit.setEmail("notthebest@best.com");
        EditOrganizationResponse editResponse = service.editOrganization(toEdit);
        Assert.assertFalse(editResponse.isSuccess());
        Assert.assertNull(editResponse.getEditedOrganization());
    }

    @Test
    public void testEditOrganizationFailMaxPhoneNumber() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        GetOrganizationResponse responseTwo = service.getOrganization(2);
        Organization toEdit = responseTwo.getToDisplay();
        Assert.assertTrue(responseTwo.isSuccess());
        Assert.assertNotNull(toEdit);
        toEdit.setName("Marvel");
        toEdit.setDescription("A good org");
        toEdit.setAddress("123 Main Street Alpharetta, Ga 30022");
        toEdit.setPhoneNumber("770241027112345");
        toEdit.setEmail("notthebest@best.com");
        EditOrganizationResponse editResponse = service.editOrganization(toEdit);
        Assert.assertFalse(editResponse.isSuccess());
        Assert.assertNull(editResponse.getEditedOrganization());
    }

    @Test
    public void testEditOrganizationFailMinPhoneNumber() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        GetOrganizationResponse responseTwo = service.getOrganization(2);
        Organization toEdit = responseTwo.getToDisplay();
        Assert.assertTrue(responseTwo.isSuccess());
        Assert.assertNotNull(toEdit);
        toEdit.setName("Marvel");
        toEdit.setDescription("A good org");
        toEdit.setAddress("123 Main Street Alpharetta, Ga 30022");
        toEdit.setPhoneNumber("770241");
        toEdit.setEmail("notthebest@best.com");
        EditOrganizationResponse editResponse = service.editOrganization(toEdit);
        Assert.assertFalse(editResponse.isSuccess());
        Assert.assertNull(editResponse.getEditedOrganization());
    }

    @Test
    public void testEditOrganizationFailBlankEmail() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        GetOrganizationResponse responseTwo = service.getOrganization(2);
        Organization toEdit = responseTwo.getToDisplay();
        Assert.assertTrue(responseTwo.isSuccess());
        Assert.assertNotNull(toEdit);
        toEdit.setName("Marvel");
        toEdit.setDescription("A good org");
        toEdit.setAddress("123 Main Street Alpharetta, Ga 30022");
        toEdit.setPhoneNumber("7702410271");
        toEdit.setEmail("");
        EditOrganizationResponse editResponse = service.editOrganization(toEdit);
        Assert.assertFalse(editResponse.isSuccess());
        Assert.assertNull(editResponse.getEditedOrganization());
    }

    @Test
    public void testEditOrganizationFailMinEmail() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        GetOrganizationResponse responseTwo = service.getOrganization(2);
        Organization toEdit = responseTwo.getToDisplay();
        Assert.assertTrue(responseTwo.isSuccess());
        Assert.assertNotNull(toEdit);
        toEdit.setName("Marvel");
        toEdit.setDescription("A good org");
        toEdit.setAddress("123 Main Street Alpharetta, Ga 30022");
        toEdit.setPhoneNumber("7702410271");
        toEdit.setEmail("be");
        EditOrganizationResponse editResponse = service.editOrganization(toEdit);
        Assert.assertFalse(editResponse.isSuccess());
        Assert.assertNull(editResponse.getEditedOrganization());
    }

    @Test
    public void testEditOrganizationFailMaxEmail() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        GetOrganizationResponse responseTwo = service.getOrganization(2);
        Organization toEdit = responseTwo.getToDisplay();
        Assert.assertTrue(responseTwo.isSuccess());
        Assert.assertNotNull(toEdit);
        toEdit.setName("Marvel");
        toEdit.setDescription("A good org");
        toEdit.setAddress("123 Main Street Alpharetta, Ga 30022");
        toEdit.setPhoneNumber("7702410271");
        toEdit.setEmail("lslslslslslslslslslslslslslslslslslsl");
        EditOrganizationResponse editResponse = service.editOrganization(toEdit);
        Assert.assertFalse(editResponse.isSuccess());
        Assert.assertNull(editResponse.getEditedOrganization());
    }

    @Test
    public void testEditOrganizationFailInvalidId() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        GetOrganizationResponse responseTwo = service.getOrganization(2);
        Organization toEdit = new Organization();
        toEdit.setOrganizationId(15);
        toEdit.setName("Marvel");
        toEdit.setDescription("A good org");
        toEdit.setAddress("123 Main Street Alpharetta, Ga 30022");
        toEdit.setPhoneNumber("7702410271");
        toEdit.setEmail("marvel@marvel.com");
        EditOrganizationResponse editResponse = service.editOrganization(toEdit);
        Assert.assertFalse(editResponse.isSuccess());
        Assert.assertNull(editResponse.getEditedOrganization());
    }

    @Test
    public void testEditOrganizationFailFailDao() {

        SuperService service = new SuperService(superFailDao, locationFailDao, orgFailDao, sightingFailDao);
        Organization toEdit = new Organization();
        toEdit.setOrganizationId(15);
        toEdit.setName("Marvel");
        toEdit.setDescription("A good org");
        toEdit.setAddress("123 Main Street Alpharetta, Ga 30022");
        toEdit.setPhoneNumber("7702410271");
        toEdit.setEmail("marvel@marvel.com");
        EditOrganizationResponse editResponse = service.editOrganization(toEdit);
        Assert.assertFalse(editResponse.isSuccess());
        Assert.assertNull(editResponse.getEditedOrganization());
    }

    @Test
    public void testDeleteOrganizationSuccess() {
        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);

        ListOrganizationResponse listResponse = service.getAllOrgs();
        List<Organization> orgsToGet = listResponse.getAllOrgs();
        Assert.assertTrue(listResponse.isSuccess());
        Assert.assertNotNull(listResponse.getAllOrgs());
        Assert.assertEquals(2, orgsToGet.size());

        ListSuperResponse listSuperResponse = service.getAllSupers();
        List<Super> supersToGet = listSuperResponse.getAllSupers();
        Assert.assertTrue(listSuperResponse.isSuccess());
        Assert.assertNotNull(listSuperResponse.getAllSupers());
        Assert.assertEquals(2, supersToGet.size());

        GetSuperResponse getSuperResponse = service.getSuper(1);
        Super batmanSuper = getSuperResponse.getToDisplay();
        List<Organization> batmanOrgs = batmanSuper.getAllOrganizations();
        Assert.assertEquals(2, batmanOrgs.size());
        Assert.assertEquals("Marvel", batmanOrgs.get(0).getName());

        GetOrganizationResponse getResponse = service.getOrganization(1);
        Assert.assertNotNull(getResponse.getToDisplay());
        Organization marvelSuper = getResponse.getToDisplay();
        Assert.assertEquals("Marvel", marvelSuper.getName());

        DeleteOrganizationResponse deleteResponse = service.deleteOrganization(1);
        Assert.assertTrue(deleteResponse.isSuccess());

        ListOrganizationResponse listOrgsDeletedResponse = service.getAllOrgs();
        List<Organization> orgsToGetDeleted = listOrgsDeletedResponse.getAllOrgs();
        Assert.assertTrue(listOrgsDeletedResponse.isSuccess());
        Assert.assertNotNull(listOrgsDeletedResponse.getAllOrgs());
        Assert.assertEquals(1, orgsToGetDeleted.size());

        GetSuperResponse getSuperResponseDeleted = service.getSuper(1);
        Super batmanSuperDeleted = getSuperResponseDeleted.getToDisplay();
        List<Organization> batmanOrgsDeleted = batmanSuperDeleted.getAllOrganizations();
        Assert.assertEquals(1, batmanOrgsDeleted.size());
        Assert.assertEquals("DC", batmanOrgsDeleted.get(0).getName());

        GetOrganizationResponse getResponseDeleted = service.getOrganization(1);
        Assert.assertFalse(getResponseDeleted.isSuccess());
        Assert.assertNull(getResponseDeleted.getToDisplay());
    }

    @Test
    public void testDeleteOrganizationFailInvalidId() {
        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);

        DeleteOrganizationResponse deleteResponse = service.deleteOrganization(15);
        Assert.assertFalse(deleteResponse.isSuccess());

    }

    @Test
    public void testDeleteOrganizationFailFailDao() {
        SuperService service = new SuperService(superFailDao, locationFailDao, orgFailDao, sightingFailDao);

        DeleteLocationResponse deleteResponse = service.deleteLocation(1);
        Assert.assertFalse(deleteResponse.isSuccess());

    }

    /**
     * Test of getAllLocations method, of class SuperService.
     */
    @Test
    public void testGetAllLocationsSuccess() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);

        ListLocationResponse response = service.getAllLocations();
        List<Location> locationsToGet = response.getAllLocations();
        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getAllLocations());
        Assert.assertEquals(2, locationsToGet.size());
        Assert.assertSame(1, locationsToGet.get(0).getLocationId());
        Assert.assertEquals("Gotham City", locationsToGet.get(0).getName());
        Assert.assertEquals("The darkest city", locationsToGet.get(0).getDescription());
        Assert.assertEquals("123 Test Avenue, Alpharetta, GA 30022", locationsToGet.get(0).getAddress());
        Assert.assertEquals(44.977753, locationsToGet.get(0).getLatitude(), 0.00001);
        Assert.assertEquals(-93.265015, locationsToGet.get(0).getLongitude(), 0.00001);

        Assert.assertSame(2, locationsToGet.get(1).getLocationId());
        Assert.assertEquals("Casa Jacob", locationsToGet.get(1).getName());
        Assert.assertEquals("Chill place", locationsToGet.get(1).getDescription());
        Assert.assertEquals("15 5th Street North, Minneapolis, MN 55105", locationsToGet.get(1).getAddress());
        Assert.assertEquals(64.977753, locationsToGet.get(1).getLatitude(), 0.00001);
        Assert.assertEquals(-83.265015, locationsToGet.get(1).getLongitude(), 0.00001);

    }

    @Test
    public void testGetAllLocationsFail() {

        SuperService service = new SuperService(superFailDao, locationFailDao, orgFailDao, sightingFailDao);

        ListLocationResponse response = service.getAllLocations();
        List<Location> locationsToGet = service.getAllLocations().getAllLocations();
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(locationsToGet);

    }

    /**
     * Test of getLocation method, of class SuperService.
     */
    @Test
    public void testGetLocationSuccess() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);

        GetLocationResponse response = service.getLocation(1);
        Location locationOne = response.getToDisplay();
        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(locationOne);

        Assert.assertSame(1, locationOne.getLocationId());
        Assert.assertEquals("Gotham City", locationOne.getName());
        Assert.assertEquals("The darkest city", locationOne.getDescription());
        Assert.assertEquals("123 Test Avenue, Alpharetta, GA 30022", locationOne.getAddress());
        Assert.assertEquals(44.977753, locationOne.getLatitude(), 0.00001);
        Assert.assertEquals(-93.265015, locationOne.getLongitude(), 0.00001);
        Assert.assertEquals(10, locationOne.getAllSightings().size());

        GetLocationResponse responseTwo = service.getLocation(2);
        Location locationTwo = responseTwo.getToDisplay();
        Assert.assertTrue(responseTwo.isSuccess());
        Assert.assertNotNull(locationTwo);

        Assert.assertSame(2, locationTwo.getLocationId());
        Assert.assertEquals("Casa Jacob", locationTwo.getName());
        Assert.assertEquals("Chill place", locationTwo.getDescription());
        Assert.assertEquals("15 5th Street North, Minneapolis, MN 55105", locationTwo.getAddress());
        Assert.assertEquals(64.977753, locationTwo.getLatitude(), 0.00001);
        Assert.assertEquals(-83.265015, locationTwo.getLongitude(), 0.00001);
        Assert.assertEquals(1, locationTwo.getAllSightings().size());

    }

    @Test
    public void testGetLocationFailInvalidId() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);

        GetLocationResponse response = service.getLocation(6);
        Location locationSix = response.getToDisplay();
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(locationSix);

    }

    @Test
    public void testGetLocationFailFailDao() {

        SuperService service = new SuperService(superFailDao, locationFailDao, orgFailDao, sightingFailDao);

        GetLocationResponse response = service.getLocation(1);
        Location locationOne = response.getToDisplay();
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(locationOne);

    }

    /**
     * Test of addLocation method, of class SuperService.
     */
    @Test
    public void testAddLocationSuccess() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);

        List<Location> allLocations = service.getAllLocations().getAllLocations();
        Assert.assertEquals(2, allLocations.size());

        Location toAdd = new Location();
        toAdd.setName("The Swamp");
        toAdd.setDescription("Dark and dank");
        toAdd.setAddress("456 North Street");
        toAdd.setLatitude(34.944452);
        toAdd.setLongitude(-45.827364);
        AddLocationResponse response = service.addLocation(toAdd);
        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getToAdd());

        Location addedLoc = response.getToAdd();
        Assert.assertSame(3, addedLoc.getLocationId());
        Assert.assertEquals("The Swamp", addedLoc.getName());
        Assert.assertEquals("Dark and dank", addedLoc.getDescription());
        Assert.assertEquals("456 North Street", addedLoc.getAddress());
        Assert.assertEquals(34.944452, addedLoc.getLatitude(), .00001);
        Assert.assertEquals(-45.827364, addedLoc.getLongitude(), .00001);

        List<Location> allLocationsTwo = service.getAllLocations().getAllLocations();
        Assert.assertEquals(3, allLocationsTwo.size());

        Location toAddTwo = new Location();
        toAddTwo.setName("The Field");
        toAddTwo.setDescription("Light");
        toAddTwo.setAddress("457 North Street");
        toAddTwo.setLatitude(64.944452);
        toAddTwo.setLongitude(-43.827364);
        AddLocationResponse responseTwo = service.addLocation(toAddTwo);
        Assert.assertTrue(responseTwo.isSuccess());
        Assert.assertNotNull(responseTwo.getToAdd());

        Location addedLocTwo = responseTwo.getToAdd();
        Assert.assertSame(4, addedLocTwo.getLocationId());
        Assert.assertEquals("The Field", addedLocTwo.getName());
        Assert.assertEquals("Light", addedLocTwo.getDescription());
        Assert.assertEquals("457 North Street", addedLocTwo.getAddress());
        Assert.assertEquals(64.944452, addedLocTwo.getLatitude(), .00001);
        Assert.assertEquals(-43.827364, addedLocTwo.getLongitude(), .00001);

        List<Location> allLocationsThree = service.getAllLocations().getAllLocations();
        Assert.assertEquals(4, allLocationsThree.size());
    }

    @Test
    public void testAddLocationFailBlankName() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);

        Location toAdd = new Location();
        toAdd.setName("");
        toAdd.setDescription("Dark and dank");
        toAdd.setAddress("456 North Street");
        toAdd.setLatitude(34.944452);
        toAdd.setLongitude(-45.827364);
        AddLocationResponse response = service.addLocation(toAdd);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getToAdd());

    }

    @Test
    public void testAddLocationFailMaxName() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);

        Location toAdd = new Location();
        toAdd.setName("jsjsjsjsjsjsjsjsjsjsjsjsjsjsjsj");
        toAdd.setDescription("Dark and dank");
        toAdd.setAddress("456 North Street");
        toAdd.setLatitude(34.944452);
        toAdd.setLongitude(-45.827364);
        AddLocationResponse response = service.addLocation(toAdd);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getToAdd());

    }

    @Test
    public void testAddLocationFailBlankDescription() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        Location toAdd = new Location();
        toAdd.setName("The Swamp");
        toAdd.setDescription("");
        toAdd.setAddress("456 North Street");
        toAdd.setLatitude(34.944452);
        toAdd.setLongitude(-45.827364);
        AddLocationResponse response = service.addLocation(toAdd);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getToAdd());

    }

    @Test
    public void testAddLocationFailMaxDescription() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        Location toAdd = new Location();
        toAdd.setName("The Swamp");
        toAdd.setDescription("jsjsjsjsjsjsjsjsjsjsjsjsjsjsjsjllllllllllllllllllkskskskskskskskskskskskskl");
        toAdd.setAddress("456 North Street");
        toAdd.setLatitude(34.944452);
        toAdd.setLongitude(-45.827364);
        AddLocationResponse response = service.addLocation(toAdd);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getToAdd());

    }

    @Test
    public void testAddLocationFailOverMaxLatitude() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        Location toAdd = new Location();
        toAdd.setName("The Swamp");
        toAdd.setDescription("Dark and dank");
        toAdd.setAddress("456 North Street");
        toAdd.setLatitude(90.01);
        toAdd.setLongitude(-45.827364);
        AddLocationResponse response = service.addLocation(toAdd);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getToAdd());

    }

    @Test
    public void testAddLocationFailUnderMinLatitude() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        Location toAdd = new Location();
        toAdd.setName("The Swamp");
        toAdd.setDescription("Dark and dank");
        toAdd.setAddress("456 North Street");
        toAdd.setLatitude(-90.01);
        toAdd.setLongitude(-45.827364);
        AddLocationResponse response = service.addLocation(toAdd);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getToAdd());

    }

    @Test
    public void testAddLocationFailNullLatitude() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        Location toAdd = new Location();
        toAdd.setName("The Swamp");
        toAdd.setDescription("Dark and dank");
        toAdd.setAddress("456 North Street");
        toAdd.setLatitude(null);
        toAdd.setLongitude(-45.827364);
        AddLocationResponse response = service.addLocation(toAdd);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getToAdd());

    }

    @Test
    public void testAddLocationFailOverMaxLongitude() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        Location toAdd = new Location();
        toAdd.setName("The Swamp");
        toAdd.setDescription("Dark and dank");
        toAdd.setAddress("456 North Street");
        toAdd.setLatitude(34.944452);
        toAdd.setLongitude(180.01);
        AddLocationResponse response = service.addLocation(toAdd);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getToAdd());

    }

    @Test
    public void testAddLocationFailUnderMinLongitude() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        Location toAdd = new Location();
        toAdd.setName("The Swamp");
        toAdd.setDescription("Dark and dank");
        toAdd.setAddress("456 North Street");
        toAdd.setLatitude(34.944452);
        toAdd.setLongitude(-180.01);
        AddLocationResponse response = service.addLocation(toAdd);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getToAdd());

    }

    @Test
    public void testAddLocationFailNullLongitude() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        Location toAdd = new Location();
        toAdd.setName("The Swamp");
        toAdd.setDescription("Dark and dank");
        toAdd.setAddress("456 North Street");
        toAdd.setLatitude(34.944452);
        toAdd.setLongitude(null);
        AddLocationResponse response = service.addLocation(toAdd);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getToAdd());
    }

    @Test
    public void testAddLocationFailBlankAddress() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        Location toAdd = new Location();
        toAdd.setName("The Swamp");
        toAdd.setDescription("Dark and dank");
        toAdd.setAddress("");
        toAdd.setLatitude(34.944452);
        toAdd.setLongitude(-45.827364);
        AddLocationResponse response = service.addLocation(toAdd);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getToAdd());
    }

    @Test
    public void testAddLocationFailMaxAddress() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        Location toAdd = new Location();
        toAdd.setName("The Swamp");
        toAdd.setDescription("Dark and dank");
        toAdd.setAddress("jdsdsdsddsssssjsjsjsjsjsjsjsjsjsjsjsjsjsjsjllllllllllllllllllljsjsjsjsjsjsjsjsjsjsjsjsjsjsjsjlllllllllllllllllll");
        toAdd.setLatitude(34.944452);
        toAdd.setLongitude(-45.827364);
        AddLocationResponse response = service.addLocation(toAdd);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getToAdd());
    }

    @Test
    public void testAddLocationFailFailDao() {

        SuperService service = new SuperService(superFailDao, locationFailDao, orgFailDao, sightingFailDao);
        Location toAdd = new Location();
        toAdd.setName("The Swamp");
        toAdd.setDescription("Dark and dank");
        toAdd.setAddress("jdsdsdsddsss");
        toAdd.setLatitude(34.944452);
        toAdd.setLongitude(-45.827364);
        AddLocationResponse response = service.addLocation(toAdd);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getToAdd());
    }

    /**
     * Test of deleteLocation method, of class SuperService.
     */
    @Test
    public void testDeleteLocationSuccess() {
        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);

        ListLocationResponse listResponse = service.getAllLocations();
        List<Location> locationsToGet = listResponse.getAllLocations();
        Assert.assertTrue(listResponse.isSuccess());
        Assert.assertNotNull(listResponse.getAllLocations());
        Assert.assertEquals(2, locationsToGet.size());

        ListSightingResponse sightingResponse = service.getAllSightings();
        List<Sighting> sightingsToGet = sightingResponse.getAllSightings();
        Assert.assertTrue(sightingResponse.isSuccess());
        Assert.assertNotNull(sightingResponse.getAllSightings());
        Assert.assertEquals(11, sightingsToGet.size());

        GetLocationResponse getResponse = service.getLocation(1);
        Assert.assertNotNull(getResponse.getToDisplay());

        DeleteLocationResponse deleteResponse = service.deleteLocation(1);
        Assert.assertTrue(deleteResponse.isSuccess());

        ListLocationResponse listResponseDeleted = service.getAllLocations();
        List<Location> locationsToGetDeleted = listResponseDeleted.getAllLocations();
        Assert.assertTrue(listResponseDeleted.isSuccess());
        Assert.assertNotNull(listResponseDeleted.getAllLocations());
        Assert.assertEquals(1, locationsToGetDeleted.size());

        GetLocationResponse getDeletedResponse = service.getLocation(1);
        Assert.assertFalse(getDeletedResponse.isSuccess());
        Assert.assertNull(getDeletedResponse.getToDisplay());

        ListSightingResponse sightingDeletedResponse = service.getAllSightings();
        List<Sighting> sightingsDeletedToGet = sightingDeletedResponse.getAllSightings();
        Assert.assertTrue(sightingDeletedResponse.isSuccess());
        Assert.assertNotNull(sightingDeletedResponse.getAllSightings());
        Assert.assertEquals(1, sightingsDeletedToGet.size());

    }

    @Test
    public void testDeleteLocationFailInvalidId() {
        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);

        DeleteLocationResponse deleteResponse = service.deleteLocation(5);
        Assert.assertFalse(deleteResponse.isSuccess());

    }

    @Test
    public void testDeleteLocationFailFailDao() {
        SuperService service = new SuperService(superFailDao, locationFailDao, orgFailDao, sightingFailDao);

        DeleteLocationResponse deleteResponse = service.deleteLocation(1);
        Assert.assertFalse(deleteResponse.isSuccess());

    }

    /**
     * Test of editLocation method, of class SuperService.
     */
    @Test
    public void testEditLocationSuccess() {
        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);

        GetLocationResponse getResponse = service.getLocation(1);
        Location toEdit = getResponse.getToDisplay();
        Assert.assertTrue(getResponse.isSuccess());
        Assert.assertNotNull(toEdit);

        Assert.assertSame(1, toEdit.getLocationId());
        Assert.assertEquals("Gotham City", toEdit.getName());
        Assert.assertEquals("The darkest city", toEdit.getDescription());
        Assert.assertEquals("123 Test Avenue, Alpharetta, GA 30022", toEdit.getAddress());
        Assert.assertEquals(44.977753, toEdit.getLatitude(), 0.00001);
        Assert.assertEquals(-93.265015, toEdit.getLongitude(), 0.00001);
        Assert.assertEquals(10, toEdit.getAllSightings().size());

        toEdit.setName("SWG");
        toEdit.setDescription("A chill place to learn");
        toEdit.setAddress("456 Test Street, Alpharetta, GA 30022");
        toEdit.setLatitude(64.977753);
        toEdit.setLongitude(-93.265015);

        EditLocationResponse editResponse = service.editLocation(toEdit);
        Assert.assertTrue(editResponse.isSuccess());
        Location editedLocation = service.getLocation(1).getToDisplay();
        Assert.assertNotNull(editedLocation);
        Assert.assertSame(1, editedLocation.getLocationId());
        Assert.assertEquals("SWG", editedLocation.getName());
        Assert.assertEquals("A chill place to learn", editedLocation.getDescription());
        Assert.assertEquals("456 Test Street, Alpharetta, GA 30022", editedLocation.getAddress());
        Assert.assertEquals(64.977753, editedLocation.getLatitude(), 0.00001);
        Assert.assertEquals(-93.265015, editedLocation.getLongitude(), 0.00001);
        Assert.assertEquals(10, editedLocation.getAllSightings().size());

    }

    @Test
    public void testEditLocationFailBlankName() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);

        GetLocationResponse getResponse = service.getLocation(1);
        Location toEdit = getResponse.getToDisplay();
        toEdit.setName("");
        toEdit.setDescription("Dark and dank");
        toEdit.setAddress("456 North Street");
        toEdit.setLatitude(34.944452);
        toEdit.setLongitude(-45.827364);
        EditLocationResponse response = service.editLocation(toEdit);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getEditedLocation());

    }

    @Test
    public void testEditLocationFailMaxName() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);

        GetLocationResponse getResponse = service.getLocation(1);
        Location toEdit = getResponse.getToDisplay();
        toEdit.setName("llllllllllllllllaaaaaaaaaaaaaaa");
        toEdit.setDescription("Dark and dank");
        toEdit.setAddress("456 North Street");
        toEdit.setLatitude(34.944452);
        toEdit.setLongitude(-45.827364);
        EditLocationResponse response = service.editLocation(toEdit);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getEditedLocation());

    }

    @Test
    public void testEditLocationFailBlankDescription() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        GetLocationResponse getResponse = service.getLocation(1);
        Location toEdit = getResponse.getToDisplay();
        toEdit.setName("The Swamp");
        toEdit.setDescription("");
        toEdit.setAddress("456 North Street");
        toEdit.setLatitude(34.944452);
        toEdit.setLongitude(-45.827364);
        EditLocationResponse response = service.editLocation(toEdit);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getEditedLocation());

    }

    @Test
    public void testEditLocationFailMaxDescription() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        GetLocationResponse getResponse = service.getLocation(1);
        Location toEdit = getResponse.getToDisplay();
        toEdit.setName("The Swamp");
        toEdit.setDescription("jsjsjsjsjsjsjsjsjsjsjsjsjsjsjsjllllllllllllllllllkskskskskskskskskskskskskl");
        toEdit.setAddress("456 North Street");
        toEdit.setLatitude(34.944452);
        toEdit.setLongitude(-45.827364);
        EditLocationResponse response = service.editLocation(toEdit);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getEditedLocation());

    }

    @Test
    public void testEditLocationFailOverMaxLatitude() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        GetLocationResponse getResponse = service.getLocation(1);
        Location toEdit = getResponse.getToDisplay();
        toEdit.setName("The Swamp");
        toEdit.setDescription("Dark and dank");
        toEdit.setAddress("456 North Street");
        toEdit.setLatitude(90.01);
        toEdit.setLongitude(-45.827364);
        EditLocationResponse response = service.editLocation(toEdit);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getEditedLocation());

    }

    @Test
    public void testEditLocationFailUnderMinLatitude() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        GetLocationResponse getResponse = service.getLocation(1);
        Location toEdit = getResponse.getToDisplay();
        toEdit.setName("The Swamp");
        toEdit.setDescription("Dark and dank");
        toEdit.setAddress("456 North Street");
        toEdit.setLatitude(-90.01);
        toEdit.setLongitude(-45.827364);
        EditLocationResponse response = service.editLocation(toEdit);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getEditedLocation());

    }

    @Test
    public void testEditLocationFailNullLatitude() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        GetLocationResponse getResponse = service.getLocation(1);
        Location toEdit = getResponse.getToDisplay();
        toEdit.setName("The Swamp");
        toEdit.setDescription("Dark and dank");
        toEdit.setAddress("456 North Street");
        toEdit.setLatitude(null);
        toEdit.setLongitude(-45.827364);
        EditLocationResponse response = service.editLocation(toEdit);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getEditedLocation());

    }

    @Test
    public void testEditLocationFailOverMaxLongitude() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        GetLocationResponse getResponse = service.getLocation(1);
        Location toEdit = getResponse.getToDisplay();
        toEdit.setName("The Swamp");
        toEdit.setDescription("Dark and dank");
        toEdit.setAddress("456 North Street");
        toEdit.setLatitude(34.944452);
        toEdit.setLongitude(180.01);
        EditLocationResponse response = service.editLocation(toEdit);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getEditedLocation());

    }

    @Test
    public void testEditLocationFailUnderMinLongitude() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        GetLocationResponse getResponse = service.getLocation(1);
        Location toEdit = getResponse.getToDisplay();
        toEdit.setName("The Swamp");
        toEdit.setDescription("Dark and dank");
        toEdit.setAddress("456 North Street");
        toEdit.setLatitude(34.944452);
        toEdit.setLongitude(-180.01);
        EditLocationResponse response = service.editLocation(toEdit);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getEditedLocation());

    }

    @Test
    public void testEditLocationFailNullLongitude() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        GetLocationResponse getResponse = service.getLocation(1);
        Location toEdit = getResponse.getToDisplay();
        toEdit.setName("The Swamp");
        toEdit.setDescription("Dark and dank");
        toEdit.setAddress("456 North Street");
        toEdit.setLatitude(34.944452);
        toEdit.setLongitude(null);
        EditLocationResponse response = service.editLocation(toEdit);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getEditedLocation());
    }

    @Test
    public void testEditLocationFailBlankAddress() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        GetLocationResponse getResponse = service.getLocation(1);
        Location toEdit = getResponse.getToDisplay();
        toEdit.setName("The Swamp");
        toEdit.setDescription("Dark and dank");
        toEdit.setAddress("");
        toEdit.setLatitude(34.944452);
        toEdit.setLongitude(-45.827364);
        EditLocationResponse response = service.editLocation(toEdit);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getEditedLocation());
    }

    @Test
    public void testEditLocationFailMaxAddress() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        GetLocationResponse getResponse = service.getLocation(1);
        Location toEdit = getResponse.getToDisplay();
        toEdit.setName("The Swamp");
        toEdit.setDescription("Dark and dank");
        toEdit.setAddress("jdsdsdsddsssssjsjsjsjsjsjsjsjsjsjsjsjsjsjsjllllllllllllllllllljsjsjsjsjsjsjsjsjsjsjsjsjsjsjsjlllllllllllllllllll");
        toEdit.setLatitude(34.944452);
        toEdit.setLongitude(-45.827364);
        EditLocationResponse response = service.editLocation(toEdit);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getEditedLocation());
    }

    @Test
    public void testEditLocationFailInvalidId() {

        SuperService service = new SuperService(superDao, locationDao, orgDao, sightingDao);
        Location toEdit = new Location();
        toEdit.setLocationId(13);
        toEdit.setName("The Swamp");
        toEdit.setDescription("Dark and dank");
        toEdit.setAddress("jdsdsdsddsssssjsjsjsj");
        toEdit.setLatitude(34.944452);
        toEdit.setLongitude(-45.827364);
        EditLocationResponse response = service.editLocation(toEdit);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getEditedLocation());
    }

    @Test
    public void testEditLocationFailFailDao() {

        SuperService service = new SuperService(superFailDao, locationFailDao, orgFailDao, sightingFailDao);
        Location toEdit = new Location();
        toEdit.setLocationId(1);
        toEdit.setName("The Swamp");
        toEdit.setDescription("Dark and dank");
        toEdit.setAddress("jdsdsdsddsssssjsjsj");
        toEdit.setLatitude(34.944452);
        toEdit.setLongitude(-45.827364);
        EditLocationResponse response = service.editLocation(toEdit);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getEditedLocation());
    }
}
