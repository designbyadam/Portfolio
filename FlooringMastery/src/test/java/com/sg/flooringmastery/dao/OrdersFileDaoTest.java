/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static java.util.stream.Collectors.toList;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Before;

/**
 *
 * @author afahrenkamp
 */
public class OrdersFileDaoTest {

    OrdersDao ordersTestDao;
    OrdersDao ordersSeedDao;
    ProductsDao productsTestDao;
    TaxDao taxesTestDao;

    String seedFolder = "/home/designbyadam/FlooringFiles/Test/Orders/Seed";
    String testFolder = "/home/designbyadam/FlooringFiles/Test/Orders/Test/";
    String taxFolder = "/home/designbyadam/FlooringFiles/Test/Tax/Tax.txt";
    String productFolder = "/home/designbyadam/FlooringFiles/Test/Products/Products.txt";

    LocalDate blankFileDate = LocalDate.of(2020, 4, 13);
    LocalDate oneOrderFourDate = LocalDate.of(2025, 4, 13);
    LocalDate twoOrdersDate = LocalDate.of(2013, 6, 1);
    LocalDate noDate = LocalDate.of(2020, 2, 1);

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws IOException {

        File sourceDir = new File(seedFolder);
        File[] directoryListing = sourceDir.listFiles();

        for (File currentFile : directoryListing) {
            Path path = Paths.get(testFolder + currentFile.getName());
            String currentPath = testFolder + currentFile.getName();
            Files.createFile(path);
            Files.copy(currentFile.toPath(), (new File(currentPath).toPath()), StandardCopyOption.REPLACE_EXISTING);
        }
    }

    @After
    public void tearDown() {
        Arrays.stream(new File("/home/designbyadam/FlooringFiles/Test/Orders/Test").listFiles()).forEach(File::delete);
    }

    /**
     * Test of addOrder method, of class OrdersFileDao.
     */
    @Test
    public void testAddOrder() throws Exception {

        OrdersFileDao ordersDao = new OrdersFileDao(testFolder);
        ProductsFileDao productsDao = new ProductsFileDao(productFolder);
        TaxFileDao taxesDao = new TaxFileDao(taxFolder);

        List<Order> dateToAddOrder = ordersDao.displayOrdersByDate(blankFileDate);
        Assert.assertTrue(dateToAddOrder.isEmpty());
        Order orderToAdd = new Order();
        orderToAdd.setDate(blankFileDate);
        orderToAdd.setCustomerName("Fahrenkamp");
        orderToAdd.setProductType("Wood");
        orderToAdd.setState("OH");
        orderToAdd.setArea(new BigDecimal("10"));
        Product productForAdd = productsDao.getProductCosts(orderToAdd.getProductType());
        Tax taxForAdd = taxesDao.getTaxRate(orderToAdd.getState());
        orderToAdd.setItemCostSqFt(productForAdd.getItemCostSqFt());
        orderToAdd.setLaborCostSqFt(productForAdd.getLaborCostSqFt());
        orderToAdd.setTaxRate(taxForAdd.getTaxRate());

        ordersDao.addOrder(orderToAdd);
        List<Order> orderAdded = ordersDao.displayOrdersByDate(blankFileDate);
        Assert.assertFalse(orderAdded.isEmpty());
        Assert.assertEquals(1, orderAdded.size());
        Order newOrder = ordersDao.displayOrderByNumber(1, blankFileDate);
        Assert.assertEquals(1, newOrder.getOrderNumber());
        Assert.assertEquals("Fahrenkamp", newOrder.getCustomerName());
        Assert.assertEquals("Wood", newOrder.getProductType());
        Assert.assertEquals("OH", newOrder.getState());
        Assert.assertEquals(new BigDecimal("10"), newOrder.getArea());
        Assert.assertEquals(new BigDecimal("5.15"), newOrder.getItemCostSqFt());
        Assert.assertEquals(new BigDecimal("4.75"), newOrder.getLaborCostSqFt());
        Assert.assertEquals(new BigDecimal("51.50"), newOrder.getTotalMaterialCost());
        Assert.assertEquals(new BigDecimal("47.50"), newOrder.getTotalLaborCost());
        Assert.assertEquals(new BigDecimal("6.25"), newOrder.getTaxRate());
        Assert.assertEquals(new BigDecimal("6.19"), newOrder.getTotalTax());
        Assert.assertEquals(new BigDecimal("105.19"), newOrder.getOrderTotal());

        Order orderTwo = new Order();
        orderTwo.setDate(blankFileDate);
        orderTwo.setCustomerName("Crighton");
        orderTwo.setProductType("Tile");
        orderTwo.setState("IN");
        orderTwo.setArea(new BigDecimal("5"));
        Product productForTwo = productsDao.getProductCosts(orderTwo.getProductType());
        Tax taxForTwo = taxesDao.getTaxRate(orderTwo.getState());
        orderTwo.setItemCostSqFt(productForTwo.getItemCostSqFt());
        orderTwo.setLaborCostSqFt(productForTwo.getLaborCostSqFt());
        orderTwo.setTaxRate(taxForTwo.getTaxRate());

        ordersDao.addOrder(orderTwo);
        List<Order> secondOrderAdded = ordersDao.displayOrdersByDate(blankFileDate);
        Assert.assertEquals(2, secondOrderAdded.size());
        Order secondOrder = ordersDao.displayOrderByNumber(2, blankFileDate);
        Assert.assertEquals(2, secondOrder.getOrderNumber());
        Assert.assertEquals("Crighton", secondOrder.getCustomerName());
        Assert.assertEquals("Tile", secondOrder.getProductType());
        Assert.assertEquals("IN", secondOrder.getState());
        Assert.assertEquals(new BigDecimal("5"), secondOrder.getArea());
        Assert.assertEquals(new BigDecimal("3.50"), secondOrder.getItemCostSqFt());
        Assert.assertEquals(new BigDecimal("4.15"), secondOrder.getLaborCostSqFt());
        Assert.assertEquals(new BigDecimal("17.50"), secondOrder.getTotalMaterialCost());
        Assert.assertEquals(new BigDecimal("20.75"), secondOrder.getTotalLaborCost());
        Assert.assertEquals(new BigDecimal("6.00"), secondOrder.getTaxRate());
        Assert.assertEquals(new BigDecimal("2.30"), secondOrder.getTotalTax());
        Assert.assertEquals(new BigDecimal("40.55"), secondOrder.getOrderTotal());

    }

    /**
     * Test of displayOrdersByDate method, of class OrdersFileDao.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testDisplayOrdersByDate() throws Exception {
        OrdersFileDao ordersDao = new OrdersFileDao(testFolder);
        List<Order> noOrders = ordersDao.displayOrdersByDate(blankFileDate);
        Assert.assertTrue(noOrders.isEmpty());

        List<Order> oneOrderFour = ordersDao.displayOrdersByDate(oneOrderFourDate);
        Assert.assertNotNull(oneOrderFour);
        Assert.assertEquals(1, oneOrderFour.size());
        Order onlyOrder = oneOrderFour.get(oneOrderFour.size() - 1);
        Assert.assertEquals(4, onlyOrder.getOrderNumber());
        Assert.assertEquals("Crighton", onlyOrder.getCustomerName());
        Assert.assertEquals("oh", onlyOrder.getState());
        Assert.assertEquals(new BigDecimal("6.25"), onlyOrder.getTaxRate());
        Assert.assertEquals("carpet", onlyOrder.getProductType());
        Assert.assertEquals(new BigDecimal("1000.00"), onlyOrder.getArea());
        Assert.assertEquals(new BigDecimal("2.25"), onlyOrder.getItemCostSqFt());
        Assert.assertEquals(new BigDecimal("2.10"), onlyOrder.getLaborCostSqFt());
        Assert.assertEquals(new BigDecimal("2250.00"), onlyOrder.getTotalMaterialCost());
        Assert.assertEquals(new BigDecimal("2100.00"), onlyOrder.getTotalLaborCost());
        Assert.assertEquals(new BigDecimal("271.88"), onlyOrder.getTotalTax());
        Assert.assertEquals(new BigDecimal("4621.88"), onlyOrder.getOrderTotal());

    }

    /**
     * Test of editOrder method, of class OrdersFileDao.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testEditOrder() throws Exception {

        OrdersFileDao ordersDao = new OrdersFileDao(testFolder);
        ProductsFileDao productsDao = new ProductsFileDao(productFolder);
        TaxFileDao taxesDao = new TaxFileDao(taxFolder);

        List<Order> dateToEditOrder = ordersDao.displayOrdersByDate(oneOrderFourDate);
        Assert.assertFalse(dateToEditOrder.isEmpty());
        Assert.assertEquals(1, dateToEditOrder.size());
        Order orderToEdit = ordersDao.displayOrderByNumber(4, oneOrderFourDate);

        Assert.assertEquals(4, orderToEdit.getOrderNumber());
        Assert.assertEquals("Crighton", orderToEdit.getCustomerName());
        Assert.assertEquals("oh", orderToEdit.getState());
        Assert.assertEquals(new BigDecimal("6.25"), orderToEdit.getTaxRate());
        Assert.assertEquals("carpet", orderToEdit.getProductType());
        Assert.assertEquals(new BigDecimal("1000.00"), orderToEdit.getArea());
        Assert.assertEquals(new BigDecimal("2.25"), orderToEdit.getItemCostSqFt());
        Assert.assertEquals(new BigDecimal("2.10"), orderToEdit.getLaborCostSqFt());
        Assert.assertEquals(new BigDecimal("2250.00"), orderToEdit.getTotalMaterialCost());
        Assert.assertEquals(new BigDecimal("2100.00"), orderToEdit.getTotalLaborCost());
        Assert.assertEquals(new BigDecimal("271.88"), orderToEdit.getTotalTax());
        Assert.assertEquals(new BigDecimal("4621.88"), orderToEdit.getOrderTotal());

        orderToEdit.setCustomerName("Fahrenkamp");
        orderToEdit.setProductType("Wood");
        orderToEdit.setState("OH");
        orderToEdit.setArea(new BigDecimal("10"));
        Product productForEdit = productsDao.getProductCosts(orderToEdit.getProductType());
        Tax taxForEdit = taxesDao.getTaxRate(orderToEdit.getState());
        orderToEdit.setItemCostSqFt(productForEdit.getItemCostSqFt());
        orderToEdit.setLaborCostSqFt(productForEdit.getLaborCostSqFt());
        orderToEdit.setTaxRate(taxForEdit.getTaxRate());

        List<Order> editedList = ordersDao.displayOrdersByDate(oneOrderFourDate);
        Assert.assertFalse(editedList.isEmpty());
        Assert.assertEquals(1, editedList.size());

        ordersDao.editOrder(orderToEdit);
        List<Order> orderEdited = ordersDao.displayOrdersByDate(oneOrderFourDate);
        Assert.assertFalse(orderEdited.isEmpty());
        Assert.assertEquals(1, orderEdited.size());
        Order newOrder = ordersDao.displayOrderByNumber(4, oneOrderFourDate);
        Assert.assertEquals(4, newOrder.getOrderNumber());
        Assert.assertEquals("Fahrenkamp", newOrder.getCustomerName());
        Assert.assertEquals("Wood", newOrder.getProductType());
        Assert.assertEquals("OH", newOrder.getState());
        Assert.assertEquals(new BigDecimal("10"), newOrder.getArea());
        Assert.assertEquals(new BigDecimal("5.15"), newOrder.getItemCostSqFt());
        Assert.assertEquals(new BigDecimal("4.75"), newOrder.getLaborCostSqFt());
        Assert.assertEquals(new BigDecimal("51.50"), newOrder.getTotalMaterialCost());
        Assert.assertEquals(new BigDecimal("47.50"), newOrder.getTotalLaborCost());
        Assert.assertEquals(new BigDecimal("6.25"), newOrder.getTaxRate());
        Assert.assertEquals(new BigDecimal("6.19"), newOrder.getTotalTax());
        Assert.assertEquals(new BigDecimal("105.19"), newOrder.getOrderTotal());

    }

    /**
     * Test of displayOrderByNumber method, of class OrdersFileDao.
     */
    @Test
    public void testDisplayOrderByNumber() throws Exception {

        OrdersFileDao ordersDao = new OrdersFileDao(testFolder);
        Order orderOne = ordersDao.displayOrderByNumber(1, twoOrdersDate);
        Assert.assertNotNull(orderOne);
        Assert.assertEquals(1, orderOne.getOrderNumber());
        Assert.assertEquals("Wise", orderOne.getCustomerName());
        Assert.assertEquals("OH", orderOne.getState());
        Assert.assertEquals(new BigDecimal("6.25"), orderOne.getTaxRate());
        Assert.assertEquals("Wood", orderOne.getProductType());
        Assert.assertEquals(new BigDecimal("100.00"), orderOne.getArea());
        Assert.assertEquals(new BigDecimal("5.15"), orderOne.getItemCostSqFt());
        Assert.assertEquals(new BigDecimal("4.75"), orderOne.getLaborCostSqFt());
        Assert.assertEquals(new BigDecimal("515.00"), orderOne.getTotalMaterialCost());
        Assert.assertEquals(new BigDecimal("475.00"), orderOne.getTotalLaborCost());
        Assert.assertEquals(new BigDecimal("61.88"), orderOne.getTotalTax());
        Assert.assertEquals(new BigDecimal("1051.88"), orderOne.getOrderTotal());

        Order orderTwo = ordersDao.displayOrderByNumber(2, twoOrdersDate);
        Assert.assertNotNull(orderTwo);
        Assert.assertEquals(2, orderTwo.getOrderNumber());
        Assert.assertEquals("Fahrenkamp", orderTwo.getCustomerName());
        Assert.assertEquals("IN", orderTwo.getState());
        Assert.assertEquals(new BigDecimal("6.25"), orderTwo.getTaxRate());
        Assert.assertEquals("Wood", orderOne.getProductType());
        Assert.assertEquals(new BigDecimal("100.00"), orderTwo.getArea());
        Assert.assertEquals(new BigDecimal("5.15"), orderTwo.getItemCostSqFt());
        Assert.assertEquals(new BigDecimal("4.75"), orderTwo.getLaborCostSqFt());
        Assert.assertEquals(new BigDecimal("515.00"), orderTwo.getTotalMaterialCost());
        Assert.assertEquals(new BigDecimal("475.00"), orderTwo.getTotalLaborCost());
        Assert.assertEquals(new BigDecimal("61.88"), orderTwo.getTotalTax());
        Assert.assertEquals(new BigDecimal("1051.88"), orderTwo.getOrderTotal());

    }

    /**
     * Test of removeOrder method, of class OrdersFileDao.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testRemoveOrder() throws Exception {
        OrdersFileDao ordersDao = new OrdersFileDao(testFolder);
        Order orderToRemove = ordersDao.displayOrderByNumber(1, twoOrdersDate);
        List<Order> dateToRemoveOrder = ordersDao.displayOrdersByDate(twoOrdersDate);
        Assert.assertNotNull(orderToRemove);
        Assert.assertEquals(2, dateToRemoveOrder.size());
        ordersDao.removeOrder(1, twoOrdersDate);
        List<Order> dateOfRemovedOrder = ordersDao.displayOrdersByDate(twoOrdersDate);
        Assert.assertEquals(1, dateOfRemovedOrder.size());
        Order orderRemoved = ordersDao.displayOrderByNumber(1, twoOrdersDate);
        Assert.assertNull(orderRemoved);
    }

}
