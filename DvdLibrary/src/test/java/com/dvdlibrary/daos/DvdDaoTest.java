/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dvdlibrary.daos;

import com.dvdlibrary.dtos.Dvd;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author afahrenkamp
 */
public class DvdDaoTest {

    private DvdDao dao = new FileDao("/home/designbyadam/Downloads/testerdvds.txt");

    public DvdDaoTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        List<Dvd> dvdList = dao.listDvds();
        for (Dvd dvd : dvdList) {
            dao.removeDvd(dvd.getId());
        }
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of addDvd method, of class DvdDao.
     */
    @Test
    public void testAddDvd() {
    }

    /**
     * Test of listDvds method, of class DvdDao.
     */
    @Test
    public void testListDvds() {
    }

    /**
     * Test of removeDvd method, of class DvdDao.
     */
    @Test
    public void testRemoveDvd() {
    }

    /**
     * Test of getDvdById method, of class DvdDao.
     */
    @Test
    public void testGetDvdById() {
    }

    /**
     * Test of editDvd method, of class DvdDao.
     */
    @Test
    public void testEditDvd() {
    }

    /**
     * Test of showDvd method, of class DvdDao.
     */
    @Test
    public void testShowDvd() {
    }

}
