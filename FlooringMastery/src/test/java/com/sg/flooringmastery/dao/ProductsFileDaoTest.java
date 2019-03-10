/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Product;
import java.math.BigDecimal;
import java.util.List;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert.*;

/**
 *
 * @author afahrenkamp
 */
public class ProductsFileDaoTest {

    String productFile = "/home/designbyadam/FlooringFiles/Test/Products/Products.txt";
    ProductsDao productsTestDao;

    public ProductsFileDaoTest() {

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
     * Test of getProducts method, of class ProductsFileDao.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testGetProducts() throws Exception {

        ProductsFileDao productsDao = new ProductsFileDao(productFile);

        List<Product> productsToGet = productsDao.getProducts();
        Assert.assertNotNull(productsToGet);
        Assert.assertEquals(4, productsToGet.size());
        Product lastProduct = productsToGet.get(productsToGet.size() - 1);
        Product firstProduct = productsToGet.get(0);
        Assert.assertEquals("Wood", lastProduct.getProductType());
        Assert.assertEquals(new BigDecimal("5.15"), lastProduct.getItemCostSqFt());
        Assert.assertEquals(new BigDecimal("4.75"), lastProduct.getLaborCostSqFt());
        Assert.assertEquals("Carpet", firstProduct.getProductType());
        Assert.assertEquals(new BigDecimal("2.25"), firstProduct.getItemCostSqFt());
        Assert.assertEquals(new BigDecimal("2.10"), firstProduct.getLaborCostSqFt());

    }

    /**
     * Test of getProductCosts method, of class ProductsFileDao.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testGetProductCosts() throws Exception {

        ProductsFileDao productsDao = new ProductsFileDao(productFile);

        List<Product> productsToGet = productsDao.getProducts();
        Product firstProduct = productsToGet.get(0);
        Product lastProduct = productsToGet.get(productsToGet.size() - 1);

        Product carpetCosts = productsDao.getProductCosts(firstProduct.getProductType());
        Product woodCosts = productsDao.getProductCosts(lastProduct.getProductType());
        Assert.assertEquals("Wood", woodCosts.getProductType());
        Assert.assertEquals(new BigDecimal("5.15"), woodCosts.getItemCostSqFt());
        Assert.assertEquals(new BigDecimal("4.75"), woodCosts.getLaborCostSqFt());
        Assert.assertEquals("Carpet", carpetCosts.getProductType());
        Assert.assertEquals(new BigDecimal("2.25"), carpetCosts.getItemCostSqFt());
        Assert.assertEquals(new BigDecimal("2.10"), carpetCosts.getLaborCostSqFt());
    }

}
