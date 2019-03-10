/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import java.math.BigDecimal;
import java.util.List;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author afahrenkamp
 */
public class TaxFileDaoTest {

    String taxFile = "/home/designbyadam/FlooringFiles/Test/Tax/Tax.txt";
    TaxDao taxesDao;

    public TaxFileDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getTaxes method, of class TaxFileDao.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testGetTaxes() throws Exception {
        TaxFileDao taxesDao = new TaxFileDao(taxFile);

        List<Tax> taxesToGet = taxesDao.getTaxes();
        Assert.assertNotNull(taxesToGet);
        Assert.assertEquals(4, taxesToGet.size());
        Tax firstTax = taxesToGet.get(0);
        Tax lastTax = taxesToGet.get(taxesToGet.size() - 1);
        Assert.assertEquals("OH", firstTax.getStatePurchasedIn());
        Assert.assertEquals(new BigDecimal("6.25"), firstTax.getTaxRate());
        Assert.assertEquals("IN", lastTax.getStatePurchasedIn());
        Assert.assertEquals(new BigDecimal("6.00"), lastTax.getTaxRate());

    }

    /**
     * Test of getTaxRate method, of class TaxFileDao.
     */
    @Test
    public void testGetTaxRate() throws Exception {
        TaxFileDao taxesDao = new TaxFileDao(taxFile);

        List<Tax> taxesToGet = taxesDao.getTaxes();
        Tax firstTax = taxesToGet.get(0);
        Tax lastTax = taxesToGet.get(taxesToGet.size() - 1);
        Tax ohTaxes = taxesDao.getTaxRate(firstTax.getStatePurchasedIn());
        Tax inTaxes = taxesDao.getTaxRate(lastTax.getStatePurchasedIn());
        Assert.assertEquals("OH", ohTaxes.getStatePurchasedIn());
        Assert.assertEquals(new BigDecimal("6.25"), ohTaxes.getTaxRate());
        Assert.assertEquals("IN", inTaxes.getStatePurchasedIn());
        Assert.assertEquals(new BigDecimal("6.00"), inTaxes.getTaxRate());

    }

}
