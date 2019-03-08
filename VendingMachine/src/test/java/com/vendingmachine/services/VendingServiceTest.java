/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vendingmachine.services;

import com.vendingmachine.daos.AlwaysFailDao;
import com.vendingmachine.daos.AuditDaoStub;
import com.vendingmachine.daos.InMemoryDao;
import com.vendingmachine.daos.VendingPersistenceException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author afahrenkamp
 */
public class VendingServiceTest extends TestCase {

    ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
    VendingService normalService = ctx.getBean("normalServiceLayer", VendingService.class);
    VendingService failingService = ctx.getBean("failingServiceLayer", VendingService.class);

    public VendingServiceTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of getAllItems method, of class VendingService.
     *
     */
    @Test
    public void testGetAllItemsFail() {
        PrintItemsResponse response = failingService.getAllItems();
        Assert.assertFalse(response.getSuccess());
        Assert.assertEquals("Error retrieving items.", response.getMessage());
    }

    @Test
    public void testGetAllItemsSuccess() {
        PrintItemsResponse response = normalService.getAllItems();
        Assert.assertTrue(response.getSuccess());
        Assert.assertNotNull(response.getAllItems());
        Assert.assertEquals(2, response.getAllItems().size());
    }

    /**
     * Test of enterMoney method, of class VendingService.
     *
     */
    @Test
    public void testEnterMoneySuccess() {
        EnterMoneyResponse response = normalService.enterMoney(new BigDecimal("0.25"));
        Assert.assertEquals(new BigDecimal("0.25"), response.getTotalMoneyEntered());
        response = normalService.enterMoney(new BigDecimal("0.50"));
        Assert.assertEquals(new BigDecimal("0.75"), response.getTotalMoneyEntered());
        response = normalService.enterMoney(new BigDecimal("0.01"));
        Assert.assertEquals(new BigDecimal("0.76"), response.getTotalMoneyEntered());
        response = normalService.enterMoney(new BigDecimal("0.10"));
        Assert.assertEquals(new BigDecimal("0.86"), response.getTotalMoneyEntered());
    }

    /**
     * Test of vendItem method, of class VendingService.
     *
     */
    @Test
    public void testVendItemFailInsufficientFunds() {
        VendItemResponse response = normalService.vendItem(1);
        Assert.assertFalse(response.getSuccess());
        Assert.assertNull(response.getVendedItem());
        Assert.assertEquals("Insufficient Funds Exception: User did not enter enough money to purchase gatorade", response.getMessage());
    }

    @Test
    public void testVendItemFailOutOfItem() {
        normalService.enterMoney(new BigDecimal("1.00"));
        VendItemResponse response = normalService.vendItem(2);
        Assert.assertFalse(response.getSuccess());
        Assert.assertNull(response.getVendedItem());
        Assert.assertEquals("Out Of Stock Exception: The machine is out of snickers", response.getMessage());
    }

    @Test
    public void testVendItemPass() {
        normalService.enterMoney(new BigDecimal("1.00"));
        int oldQuant;
        try {
            oldQuant = normalService.dao.getItemById(1).getQuantity();
            VendItemResponse response = normalService.vendItem(1);
            int newQuant;
            newQuant = normalService.dao.getItemById(1).getQuantity();
            Assert.assertTrue(response.getSuccess());
            Assert.assertNotNull(response.getVendedItem());
            Assert.assertEquals((oldQuant - 1), newQuant);
        } catch (VendingPersistenceException ex) {

        }
    }
   

    /**
     * Test of changeReturn method, of class VendingService.
     *
     */
    @Test
        public void testReturnChangePass() {
        normalService.enterMoney(new BigDecimal("1.31"));
        ChangeReturnResponse response = normalService.returnChange();
        Assert.assertNotNull(response.getChange());
        Assert.assertEquals(1, response.getChange().getDollars());
        Assert.assertEquals(1, response.getChange().getQuarters());
        Assert.assertEquals(0, response.getChange().getDimes());
        Assert.assertEquals(1, response.getChange().getNickels());
        Assert.assertEquals(1, response.getChange().getPennies());
    }
}
