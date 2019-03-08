/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vendingmachine.daos;

import com.vendingmachine.dtos.Item;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import junit.framework.Assert;
import junit.framework.TestCase;

/**
 *
 * @author afahrenkamp
 */
public class FileDaoTest extends TestCase {
    
    String seedPath = "/home/designbyadam/Downloads/copyofvendingitems.txt";
    String testPath = "/home/designbyadam/Downloads/testvendingitems.txt";

    public FileDaoTest(String testName) {
        super(testName);
    }
    

    @Override
    protected void setUp() throws Exception {
        File seedFile = new File(seedPath);
        File testFile = new File(testPath);
        testFile.delete();
        Files.copy(seedFile.toPath(), testFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of getAllItems method, of class FileDao.
     * @throws com.vendingmachine.daos.VendingPersistenceException
     */
    public void testGetAllItems() throws Exception {
        FileDao testDao = new FileDao(testPath);
        List<Item> allItems = testDao.getAllItems();
        Assert.assertNotNull(allItems);
        Assert.assertEquals(5, allItems.size());
        Item lastItem = allItems.get(allItems.size() - 1);
        Assert.assertEquals(5, lastItem.getId());
        Assert.assertEquals("KitKat", lastItem.getName());
        Assert.assertEquals(17, lastItem.getQuantity());
        Assert.assertEquals(75, lastItem.getPriceInPennies());
    }

    
    /**
     * Test of getItemById method, of class FileDao.
     * @throws com.vendingmachine.daos.VendingPersistenceException
     */
    public void testGetItemById() throws Exception {
        FileDao testDao = new FileDao(testPath);
        Item firstItem = testDao.getItemById(1);
        Item lastItem = testDao.getItemById(5);
        Assert.assertNotNull(firstItem);
        Assert.assertNotNull(lastItem);
        Assert.assertEquals("Snickers", firstItem.getName());
        Assert.assertEquals("KitKat", lastItem.getName());
        Assert.assertEquals(15, firstItem.getQuantity());
        Assert.assertEquals(17, lastItem.getQuantity());
        Assert.assertEquals(75, firstItem.getPriceInPennies());
        Assert.assertEquals(75, lastItem.getPriceInPennies());
    }

    /**
     * Test of vendItem method, of class FileDao.
     * @throws com.vendingmachine.daos.VendingPersistenceException
     */
    public void testVendItem() throws Exception {
        FileDao testDao = new FileDao(testPath);
        List<Item> allItems = testDao.getAllItems();
        Item lastItem = allItems.get(allItems.size() - 1);
        Item itemVended = testDao.vendItem(lastItem);
        Assert.assertNotNull(itemVended);
        Assert.assertEquals(5, lastItem.getId());
        Assert.assertEquals("KitKat", lastItem.getName());
        Assert.assertEquals(17, itemVended.getQuantity());
        Assert.assertEquals(75, lastItem.getPriceInPennies());
        
        Item newItem = new Item();
        newItem.setId(1);
        newItem.setName("Snickers");
        newItem.setQuantity(1000);
        newItem.setPriceInPennies(75);
        Item itemToVend = testDao.vendItem(newItem);
        Item firstItem = testDao.getItemById(1);
        Assert.assertEquals(1000, firstItem.getQuantity());    
    }

}
