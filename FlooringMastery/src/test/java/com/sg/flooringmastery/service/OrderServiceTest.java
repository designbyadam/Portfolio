/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.InMemoryOrdersAuditDao;
import com.sg.flooringmastery.dao.InMemoryOrdersDao;
import com.sg.flooringmastery.dao.InMemoryProductsDao;
import com.sg.flooringmastery.dao.InMemoryTaxDao;
import com.sg.flooringmastery.dao.OrderPersistenceException;
import com.sg.flooringmastery.dao.ProductPersistenceException;
import com.sg.flooringmastery.dao.TaxPersistenceException;
import com.sg.flooringmastery.dto.Order;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
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
public class OrderServiceTest {

    public OrderServiceTest() {
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
     * Test of addOrder method, of class OrderService.
     *
     * @throws com.sg.flooringmastery.dao.ProductPersistenceException
     * @throws com.sg.flooringmastery.dao.TaxPersistenceException
     */
    @Test
    public void testAddOrderSuccess() throws ProductPersistenceException, TaxPersistenceException {
        InMemoryOrdersDao ordersDao = new InMemoryOrdersDao();
        InMemoryProductsDao productsDao = new InMemoryProductsDao();
        InMemoryTaxDao taxDao = new InMemoryTaxDao();
        InMemoryOrdersAuditDao auditDao = new InMemoryOrdersAuditDao();
        OrderService service = new OrderService(ordersDao, taxDao, productsDao, auditDao);
        LocalDate passDate = LocalDate.of(2020, 3, 1);

        Order passingOrder = new Order();
        passingOrder.setDate(passDate);
        passingOrder.setCustomerName("Fahrenkamp");
        passingOrder.setProductType("Wood");
        passingOrder.setState("OH");
        passingOrder.setArea(new BigDecimal("10"));

        AddOrderResponse response = service.addOrder(passingOrder.getDate(), passingOrder.getCustomerName(),
                passingOrder.getProductType(), passingOrder.getState(), passingOrder.getArea());

        Assert.assertTrue(response.getSuccess());
        Assert.assertNotNull(response.getCurrentOrder());
        Assert.assertEquals(LocalDate.of(2020, 3, 1), response.getCurrentOrder().getDate());
        Assert.assertEquals(1, response.getCurrentOrder().getOrderNumber());
        Assert.assertEquals("Fahrenkamp", response.getCurrentOrder().getCustomerName());
        Assert.assertEquals("Wood", response.getCurrentOrder().getProductType());
        Assert.assertEquals("OH", response.getCurrentOrder().getState());
        Assert.assertEquals(new BigDecimal("10"), response.getCurrentOrder().getArea());
        Assert.assertEquals(new BigDecimal("5.15"), response.getCurrentOrder().getItemCostSqFt());
        Assert.assertEquals(new BigDecimal("4.75"), response.getCurrentOrder().getLaborCostSqFt());
        Assert.assertEquals(new BigDecimal("51.50"), response.getCurrentOrder().getTotalMaterialCost());
        Assert.assertEquals(new BigDecimal("47.50"), response.getCurrentOrder().getTotalLaborCost());
        Assert.assertEquals(new BigDecimal("6.25"), response.getCurrentOrder().getTaxRate());
        Assert.assertEquals(new BigDecimal("6.19"), response.getCurrentOrder().getTotalTax());
        Assert.assertEquals(new BigDecimal("105.19"), response.getCurrentOrder().getOrderTotal());

        Order passingOrderTwo = new Order();
        passingOrderTwo.setDate(passDate);
        passingOrderTwo.setCustomerName("Crighton");
        passingOrderTwo.setProductType("Tile");
        passingOrderTwo.setState("IN");
        passingOrderTwo.setArea(new BigDecimal("5"));

        AddOrderResponse responseTwo = service.addOrder(passingOrderTwo.getDate(), passingOrderTwo.getCustomerName(),
                passingOrderTwo.getProductType(), passingOrderTwo.getState(), passingOrderTwo.getArea());

        Assert.assertTrue(response.getSuccess());
        Assert.assertNotNull(response.getCurrentOrder());
        Assert.assertEquals(LocalDate.of(2020, 3, 1), response.getCurrentOrder().getDate());
        Assert.assertEquals(1, response.getCurrentOrder().getOrderNumber());
        Assert.assertEquals("Fahrenkamp", response.getCurrentOrder().getCustomerName());
        Assert.assertEquals("Wood", response.getCurrentOrder().getProductType());
        Assert.assertEquals("OH", response.getCurrentOrder().getState());
        Assert.assertEquals(new BigDecimal("10"), response.getCurrentOrder().getArea());
        Assert.assertEquals(new BigDecimal("5.15"), response.getCurrentOrder().getItemCostSqFt());
        Assert.assertEquals(new BigDecimal("4.75"), response.getCurrentOrder().getLaborCostSqFt());
        Assert.assertEquals(new BigDecimal("51.50"), response.getCurrentOrder().getTotalMaterialCost());
        Assert.assertEquals(new BigDecimal("47.50"), response.getCurrentOrder().getTotalLaborCost());
        Assert.assertEquals(new BigDecimal("6.25"), response.getCurrentOrder().getTaxRate());
        Assert.assertEquals(new BigDecimal("6.19"), response.getCurrentOrder().getTotalTax());
        Assert.assertEquals(new BigDecimal("105.19"), response.getCurrentOrder().getOrderTotal());

        Assert.assertTrue(responseTwo.getSuccess());
        Assert.assertNotNull(responseTwo.getCurrentOrder());
        Assert.assertEquals(LocalDate.of(2020, 3, 1), responseTwo.getCurrentOrder().getDate());
        Assert.assertEquals(2, responseTwo.getCurrentOrder().getOrderNumber());
        Assert.assertEquals("Crighton", responseTwo.getCurrentOrder().getCustomerName());
        Assert.assertEquals("Tile", responseTwo.getCurrentOrder().getProductType());
        Assert.assertEquals("IN", responseTwo.getCurrentOrder().getState());
        Assert.assertEquals(new BigDecimal("5"), responseTwo.getCurrentOrder().getArea());
        Assert.assertEquals(new BigDecimal("3.50"), responseTwo.getCurrentOrder().getItemCostSqFt());
        Assert.assertEquals(new BigDecimal("4.15"), responseTwo.getCurrentOrder().getLaborCostSqFt());
        Assert.assertEquals(new BigDecimal("17.50"), responseTwo.getCurrentOrder().getTotalMaterialCost());
        Assert.assertEquals(new BigDecimal("20.75"), responseTwo.getCurrentOrder().getTotalLaborCost());
        Assert.assertEquals(new BigDecimal("6.00"), responseTwo.getCurrentOrder().getTaxRate());
        Assert.assertEquals(new BigDecimal("2.30"), responseTwo.getCurrentOrder().getTotalTax());
        Assert.assertEquals(new BigDecimal("40.55"), responseTwo.getCurrentOrder().getOrderTotal());

    }

    @Test
    public void testAddOrderFailInvalidNameBlank() throws ProductPersistenceException, TaxPersistenceException {
        InMemoryOrdersDao ordersDao = new InMemoryOrdersDao();
        InMemoryProductsDao productsDao = new InMemoryProductsDao();
        InMemoryTaxDao taxDao = new InMemoryTaxDao();
        InMemoryOrdersAuditDao auditDao = new InMemoryOrdersAuditDao();
        OrderService service = new OrderService(ordersDao, taxDao, productsDao, auditDao);
        LocalDate passDate = LocalDate.of(2020, 3, 1);
        Order failingOrder = new Order();
        failingOrder.setDate(passDate);
        failingOrder.setCustomerName("");
        failingOrder.setProductType("Wood");
        failingOrder.setState("OH");
        failingOrder.setArea(new BigDecimal("10"));
        AddOrderResponse response = service.addOrder(failingOrder.getDate(), failingOrder.getCustomerName(),
                failingOrder.getProductType(), failingOrder.getState(), failingOrder.getArea());
        Assert.assertFalse(response.getSuccess());
        Assert.assertNull(response.getCurrentOrder());
    }

    @Test
    public void testAddOrderFailInvalidDatePast() throws ProductPersistenceException, TaxPersistenceException {
        InMemoryOrdersDao ordersDao = new InMemoryOrdersDao();
        InMemoryProductsDao productsDao = new InMemoryProductsDao();
        InMemoryTaxDao taxDao = new InMemoryTaxDao();
        InMemoryOrdersAuditDao auditDao = new InMemoryOrdersAuditDao();
        OrderService service = new OrderService(ordersDao, taxDao, productsDao, auditDao);
        Order failingOrder = new Order();
        failingOrder.setDate(LocalDate.of(2019, 1, 15));
        failingOrder.setCustomerName("Crighton");
        failingOrder.setProductType("Wood");
        failingOrder.setState("OH");
        failingOrder.setArea(new BigDecimal("10"));
        AddOrderResponse response = service.addOrder(failingOrder.getDate(), failingOrder.getCustomerName(),
                failingOrder.getProductType(), failingOrder.getState(), failingOrder.getArea());
        Assert.assertFalse(response.getSuccess());
        Assert.assertNull(response.getCurrentOrder());
    }

    @Test
    public void testAddOrderFailInvalidProductTypeBlank() throws ProductPersistenceException, TaxPersistenceException {
        InMemoryOrdersDao ordersDao = new InMemoryOrdersDao();
        InMemoryProductsDao productsDao = new InMemoryProductsDao();
        InMemoryTaxDao taxDao = new InMemoryTaxDao();
        InMemoryOrdersAuditDao auditDao = new InMemoryOrdersAuditDao();
        OrderService service = new OrderService(ordersDao, taxDao, productsDao, auditDao);
        LocalDate passDate = LocalDate.of(2020, 3, 1);
        Order failingOrder = new Order();
        failingOrder.setDate(passDate);
        failingOrder.setCustomerName("Crighton");
        failingOrder.setProductType("");
        failingOrder.setState("OH");
        failingOrder.setArea(new BigDecimal("10"));
        AddOrderResponse response = service.addOrder(failingOrder.getDate(), failingOrder.getCustomerName(),
                failingOrder.getProductType(), failingOrder.getState(), failingOrder.getArea());
        Assert.assertFalse(response.getSuccess());
        Assert.assertNull(response.getCurrentOrder());
    }

    @Test
    public void testAddOrderFailInvalidProductType() throws ProductPersistenceException, TaxPersistenceException {
        InMemoryOrdersDao ordersDao = new InMemoryOrdersDao();
        InMemoryProductsDao productsDao = new InMemoryProductsDao();
        InMemoryTaxDao taxDao = new InMemoryTaxDao();
        InMemoryOrdersAuditDao auditDao = new InMemoryOrdersAuditDao();
        OrderService service = new OrderService(ordersDao, taxDao, productsDao, auditDao);
        LocalDate passDate = LocalDate.of(2020, 3, 1);
        Order failingOrder = new Order();
        failingOrder.setDate(passDate);
        failingOrder.setCustomerName("Crighton");
        failingOrder.setProductType("brick");
        failingOrder.setState("OH");
        failingOrder.setArea(new BigDecimal("10"));
        AddOrderResponse response = service.addOrder(failingOrder.getDate(), failingOrder.getCustomerName(),
                failingOrder.getProductType(), failingOrder.getState(), failingOrder.getArea());
        Assert.assertFalse(response.getSuccess());
        Assert.assertNull(response.getCurrentOrder());
    }

    @Test
    public void testAddOrderFailInvalidStateBlank() throws ProductPersistenceException, TaxPersistenceException {
        InMemoryOrdersDao ordersDao = new InMemoryOrdersDao();
        InMemoryProductsDao productsDao = new InMemoryProductsDao();
        InMemoryTaxDao taxDao = new InMemoryTaxDao();
        InMemoryOrdersAuditDao auditDao = new InMemoryOrdersAuditDao();
        OrderService service = new OrderService(ordersDao, taxDao, productsDao, auditDao);
        LocalDate passDate = LocalDate.of(2020, 3, 1);
        Order failingOrder = new Order();
        failingOrder.setDate(passDate);
        failingOrder.setCustomerName("Crighton");
        failingOrder.setProductType("tile");
        failingOrder.setState("");
        failingOrder.setArea(new BigDecimal("10"));
        AddOrderResponse response = service.addOrder(failingOrder.getDate(), failingOrder.getCustomerName(),
                failingOrder.getProductType(), failingOrder.getState(), failingOrder.getArea());
        Assert.assertFalse(response.getSuccess());
        Assert.assertNull(response.getCurrentOrder());
    }

    @Test
    public void testAddOrderFailInvalidStateWrongLetters() throws ProductPersistenceException, TaxPersistenceException {
        InMemoryOrdersDao ordersDao = new InMemoryOrdersDao();
        InMemoryProductsDao productsDao = new InMemoryProductsDao();
        InMemoryTaxDao taxDao = new InMemoryTaxDao();
        InMemoryOrdersAuditDao auditDao = new InMemoryOrdersAuditDao();
        OrderService service = new OrderService(ordersDao, taxDao, productsDao, auditDao);
        LocalDate passDate = LocalDate.of(2020, 3, 1);
        Order failingOrder = new Order();
        failingOrder.setDate(passDate);
        failingOrder.setCustomerName("Crighton");
        failingOrder.setProductType("tile");
        failingOrder.setState("HI");
        failingOrder.setArea(new BigDecimal("10"));
        AddOrderResponse response = service.addOrder(failingOrder.getDate(), failingOrder.getCustomerName(),
                failingOrder.getProductType(), failingOrder.getState(), failingOrder.getArea());
        Assert.assertFalse(response.getSuccess());;
        Assert.assertNull(response.getCurrentOrder());
    }

    @Test
    public void testAddOrderFailInvalidAreaNegative() throws ProductPersistenceException, TaxPersistenceException {
        InMemoryOrdersDao ordersDao = new InMemoryOrdersDao();
        InMemoryProductsDao productsDao = new InMemoryProductsDao();
        InMemoryTaxDao taxDao = new InMemoryTaxDao();
        InMemoryOrdersAuditDao auditDao = new InMemoryOrdersAuditDao();
        OrderService service = new OrderService(ordersDao, taxDao, productsDao, auditDao);
        LocalDate passDate = LocalDate.of(2020, 3, 1);
        Order failingOrder = new Order();
        failingOrder.setDate(passDate);
        failingOrder.setCustomerName("Crighton");
        failingOrder.setProductType("tile");
        failingOrder.setState("OH");
        failingOrder.setArea(new BigDecimal("-10"));
        AddOrderResponse response = service.addOrder(failingOrder.getDate(), failingOrder.getCustomerName(),
                failingOrder.getProductType(), failingOrder.getState(), failingOrder.getArea());
        Assert.assertFalse(response.getSuccess());
        Assert.assertNull(response.getCurrentOrder());
    }

    @Test
    public void testAddOrderFailInvalidAreaZero() throws ProductPersistenceException, TaxPersistenceException {
        InMemoryOrdersDao ordersDao = new InMemoryOrdersDao();
        InMemoryProductsDao productsDao = new InMemoryProductsDao();
        InMemoryTaxDao taxDao = new InMemoryTaxDao();
        InMemoryOrdersAuditDao auditDao = new InMemoryOrdersAuditDao();
        OrderService service = new OrderService(ordersDao, taxDao, productsDao, auditDao);
        LocalDate passDate = LocalDate.of(2020, 3, 1);
        Order failingOrder = new Order();
        failingOrder.setDate(passDate);
        failingOrder.setCustomerName("Crighton");
        failingOrder.setProductType("tile");
        failingOrder.setState("OH");
        failingOrder.setArea(new BigDecimal("0"));
        AddOrderResponse response = service.addOrder(failingOrder.getDate(), failingOrder.getCustomerName(),
                failingOrder.getProductType(), failingOrder.getState(), failingOrder.getArea());
        Assert.assertFalse(response.getSuccess());
        Assert.assertNull(response.getCurrentOrder());
    }

    /**
     * Test of listOrdersByDate method, of class OrderService.
     *
     * @throws com.sg.flooringmastery.dao.ProductPersistenceException
     * @throws com.sg.flooringmastery.dao.TaxPersistenceException
     */
    @Test
    public void testListOrdersByDateSuccess() throws ProductPersistenceException, TaxPersistenceException {
        LocalDate firstDate = LocalDate.of(2019, 4, 13);
        LocalDate secondDate = LocalDate.of(2018, 6, 8);
        InMemoryOrdersDao ordersDao = new InMemoryOrdersDao();
        InMemoryProductsDao productsDao = new InMemoryProductsDao();
        InMemoryTaxDao taxDao = new InMemoryTaxDao();
        InMemoryOrdersAuditDao auditDao = new InMemoryOrdersAuditDao();
        OrderService service = new OrderService(ordersDao, taxDao, productsDao, auditDao);
        DisplayAllResponse response = service.listOrdersByDate(firstDate);
        Assert.assertTrue(response.getSuccess());
        Assert.assertNotNull(response.getAllOrders());
        Assert.assertEquals(2, response.getAllOrders().size());

        DisplayAllResponse responseTwo = service.listOrdersByDate(secondDate);
        Assert.assertTrue(responseTwo.getSuccess());
        Assert.assertNotNull(responseTwo.getAllOrders());
        Assert.assertEquals(1, responseTwo.getAllOrders().size());

    }

    @Test
    public void testListOrdersByDateFailNoOrder() throws ProductPersistenceException, TaxPersistenceException {
        LocalDate noOrderDate = LocalDate.of(2019, 4, 15);
        InMemoryOrdersDao ordersDao = new InMemoryOrdersDao();
        InMemoryProductsDao productsDao = new InMemoryProductsDao();
        InMemoryTaxDao taxDao = new InMemoryTaxDao();
        InMemoryOrdersAuditDao auditDao = new InMemoryOrdersAuditDao();
        OrderService service = new OrderService(ordersDao, taxDao, productsDao, auditDao);
        DisplayAllResponse response = service.listOrdersByDate(noOrderDate);
        Assert.assertFalse(response.getSuccess());
        Assert.assertNull(response.getAllOrders());
        Assert.assertEquals("No orders on 04/15/2019.", response.getMessage());
    }

    @Test
    public void testListOrdersByDateFailDateBefore1980() throws ProductPersistenceException, TaxPersistenceException {
        LocalDate earlyOrderDate = LocalDate.of(1979, 4, 15);
        InMemoryOrdersDao ordersDao = new InMemoryOrdersDao();
        InMemoryProductsDao productsDao = new InMemoryProductsDao();
        InMemoryTaxDao taxDao = new InMemoryTaxDao();
        InMemoryOrdersAuditDao auditDao = new InMemoryOrdersAuditDao();
        OrderService service = new OrderService(ordersDao, taxDao, productsDao, auditDao);
        DisplayAllResponse response = service.listOrdersByDate(earlyOrderDate);
        Assert.assertFalse(response.getSuccess());
        Assert.assertNull(response.getAllOrders());
    }

    /**
     * Test of listOrderByNumber method, of class OrderService.
     *
     * @throws com.sg.flooringmastery.dao.ProductPersistenceException
     * @throws com.sg.flooringmastery.dao.TaxPersistenceException
     */
    @Test
    public void testListOrderByNumberSuccess() throws ProductPersistenceException, TaxPersistenceException {
        LocalDate firstDate = LocalDate.of(2019, 4, 13);
        LocalDate secondDate = LocalDate.of(2018, 6, 8);

        InMemoryOrdersDao ordersDao = new InMemoryOrdersDao();
        InMemoryProductsDao productsDao = new InMemoryProductsDao();
        InMemoryTaxDao taxDao = new InMemoryTaxDao();
        InMemoryOrdersAuditDao auditDao = new InMemoryOrdersAuditDao();
        OrderService service = new OrderService(ordersDao, taxDao, productsDao, auditDao);
        DisplaySingleResponse response = service.listOrderByNumber(1, firstDate);
        Assert.assertTrue(response.getSuccess());
        Assert.assertNotNull(response.getOrderToDisplay());
        Assert.assertEquals(1, response.getOrderToDisplay().getOrderNumber());
        Assert.assertEquals(LocalDate.of(2019, 4, 13), response.getOrderToDisplay().getDate());
        Assert.assertEquals("crighton", response.getOrderToDisplay().getCustomerName());
        Assert.assertEquals("tile", response.getOrderToDisplay().getProductType());
        Assert.assertEquals("OH", response.getOrderToDisplay().getState());
        Assert.assertEquals(new BigDecimal("10"), response.getOrderToDisplay().getArea());

        DisplaySingleResponse responseTwo = service.listOrderByNumber(3, firstDate);
        Assert.assertTrue(responseTwo.getSuccess());
        Assert.assertNotNull(responseTwo.getOrderToDisplay());
        Assert.assertEquals(3, responseTwo.getOrderToDisplay().getOrderNumber());
        Assert.assertEquals(LocalDate.of(2019, 4, 13), responseTwo.getOrderToDisplay().getDate());
        Assert.assertEquals("jones", responseTwo.getOrderToDisplay().getCustomerName());
        Assert.assertEquals("wood", responseTwo.getOrderToDisplay().getProductType());
        Assert.assertEquals("mi", responseTwo.getOrderToDisplay().getState());
        Assert.assertEquals(new BigDecimal("15"), responseTwo.getOrderToDisplay().getArea());

        DisplaySingleResponse responseThree = service.listOrderByNumber(1, secondDate);
        Assert.assertTrue(responseThree.getSuccess());
        Assert.assertNotNull(responseThree.getOrderToDisplay());
        Assert.assertEquals(1, responseThree.getOrderToDisplay().getOrderNumber());
        Assert.assertEquals(LocalDate.of(2018, 6, 8), responseThree.getOrderToDisplay().getDate());
        Assert.assertEquals("fahrenkamp", responseThree.getOrderToDisplay().getCustomerName());
        Assert.assertEquals("laminate", responseThree.getOrderToDisplay().getProductType());
        Assert.assertEquals("IN", responseThree.getOrderToDisplay().getState());
        Assert.assertEquals(new BigDecimal("1"), responseThree.getOrderToDisplay().getArea());

    }

    @Test
    public void testListOrderByNumberFailNoOrderNumber() throws ProductPersistenceException, TaxPersistenceException {
        LocalDate orderDate = LocalDate.of(2019, 4, 13);
        InMemoryOrdersDao ordersDao = new InMemoryOrdersDao();
        InMemoryProductsDao productsDao = new InMemoryProductsDao();
        InMemoryTaxDao taxDao = new InMemoryTaxDao();
        InMemoryOrdersAuditDao auditDao = new InMemoryOrdersAuditDao();
        OrderService service = new OrderService(ordersDao, taxDao, productsDao, auditDao);
        DisplaySingleResponse response = service.listOrderByNumber(5, orderDate);
        Assert.assertFalse(response.getSuccess());
        Assert.assertNull(response.getOrderToDisplay());
    }

    @Test
    public void testListOrderByNumberFailNoOrderDate() throws ProductPersistenceException, TaxPersistenceException {
        LocalDate orderDate = LocalDate.of(2019, 4, 15);
        InMemoryOrdersDao ordersDao = new InMemoryOrdersDao();
        InMemoryProductsDao productsDao = new InMemoryProductsDao();
        InMemoryTaxDao taxDao = new InMemoryTaxDao();
        InMemoryOrdersAuditDao auditDao = new InMemoryOrdersAuditDao();
        OrderService service = new OrderService(ordersDao, taxDao, productsDao, auditDao);
        DisplaySingleResponse response = service.listOrderByNumber(1, orderDate);
        Assert.assertFalse(response.getSuccess());
        Assert.assertNull(response.getOrderToDisplay());
    }

    @Test
    public void testListOrderByNumberFailDateBefore1980() throws ProductPersistenceException, TaxPersistenceException {
        LocalDate orderDate = LocalDate.of(1979, 4, 15);
        InMemoryOrdersDao ordersDao = new InMemoryOrdersDao();
        InMemoryProductsDao productsDao = new InMemoryProductsDao();
        InMemoryTaxDao taxDao = new InMemoryTaxDao();
        InMemoryOrdersAuditDao auditDao = new InMemoryOrdersAuditDao();
        OrderService service = new OrderService(ordersDao, taxDao, productsDao, auditDao);
        DisplaySingleResponse response = service.listOrderByNumber(1, orderDate);
        Assert.assertFalse(response.getSuccess());
        Assert.assertNull(response.getOrderToDisplay());
    }

    /**
     * Test of getProductList method, of class OrderService.
     *
     * @throws com.sg.flooringmastery.dao.ProductPersistenceException
     * @throws com.sg.flooringmastery.dao.TaxPersistenceException
     */
    @Test
    public void testGetProductListSuccess() throws ProductPersistenceException, TaxPersistenceException {
        InMemoryOrdersDao ordersDao = new InMemoryOrdersDao();
        InMemoryProductsDao productsDao = new InMemoryProductsDao();
        InMemoryTaxDao taxDao = new InMemoryTaxDao();
        InMemoryOrdersAuditDao auditDao = new InMemoryOrdersAuditDao();
        OrderService service = new OrderService(ordersDao, taxDao, productsDao, auditDao);
        List<String> allProducts = service.getProductList();
        Assert.assertNotNull(allProducts);
        Assert.assertEquals(4, allProducts.size());

    }

    /**
     * Test of getStateList method, of class OrderService.
     *
     * @throws com.sg.flooringmastery.dao.ProductPersistenceException
     * @throws com.sg.flooringmastery.dao.TaxPersistenceException
     */
    @Test
    public void testGetStateList() throws ProductPersistenceException, TaxPersistenceException {
        InMemoryOrdersDao ordersDao = new InMemoryOrdersDao();
        InMemoryProductsDao productsDao = new InMemoryProductsDao();
        InMemoryTaxDao taxDao = new InMemoryTaxDao();
        InMemoryOrdersAuditDao auditDao = new InMemoryOrdersAuditDao();
        OrderService service = new OrderService(ordersDao, taxDao, productsDao, auditDao);
        List<String> allStates = service.getStateList();
        Assert.assertNotNull(allStates);
        Assert.assertEquals(4, allStates.size());

    }

    /**
     * Test of editOrder method, of class OrderService.
     *
     * @throws com.sg.flooringmastery.dao.ProductPersistenceException
     * @throws com.sg.flooringmastery.dao.TaxPersistenceException
     * @throws com.sg.flooringmastery.dao.OrderPersistenceException
     */
    @Test
    public void testEditOrderSuccess() throws ProductPersistenceException, TaxPersistenceException, OrderPersistenceException {
        InMemoryOrdersDao ordersDao = new InMemoryOrdersDao();
        InMemoryProductsDao productsDao = new InMemoryProductsDao();
        InMemoryTaxDao taxDao = new InMemoryTaxDao();
        InMemoryOrdersAuditDao auditDao = new InMemoryOrdersAuditDao();
        OrderService service = new OrderService(ordersDao, taxDao, productsDao, auditDao);
        LocalDate orderDate = LocalDate.of(2019, 4, 13);
        Order toEdit = ordersDao.displayOrderByNumber(1, orderDate);
        Order toEditService = ordersDao.displayOrderByNumber(1, orderDate);
        service.editOrder(toEditService);
        EditOrderResponse response = service.editOrder(toEdit);
        Order checkOrder = service.listOrderByNumber(1, orderDate).getOrderToDisplay();
        Assert.assertTrue(response.getSuccess());
        Assert.assertNotNull(checkOrder);
        Assert.assertEquals("crighton", checkOrder.getCustomerName());
        Assert.assertEquals("tile", checkOrder.getProductType());
        Assert.assertEquals(new BigDecimal("6.25"), checkOrder.getTaxRate());
        Assert.assertEquals(new BigDecimal("3.50"), checkOrder.getItemCostSqFt());
        Assert.assertEquals(new BigDecimal("4.15"), checkOrder.getLaborCostSqFt());
        toEditService.setProductType("wood");
        toEditService.setCustomerName("fahrenkamp");
        toEditService.setArea(new BigDecimal("30"));
        toEditService.setState("MI");
        service.editOrder(toEditService);
        Order checkEditedOrder = service.listOrderByNumber(1, orderDate).getOrderToDisplay();
        Assert.assertNotNull(checkEditedOrder);
        Assert.assertEquals("fahrenkamp", checkEditedOrder.getCustomerName());
        Assert.assertEquals("wood", checkEditedOrder.getProductType());
        Assert.assertEquals(new BigDecimal("30"), checkEditedOrder.getArea());
        Assert.assertEquals("MI", checkEditedOrder.getState());
        Assert.assertEquals(new BigDecimal("5.75"), checkEditedOrder.getTaxRate());
        Assert.assertEquals(new BigDecimal("5.15"), checkEditedOrder.getItemCostSqFt());
        Assert.assertEquals(new BigDecimal("4.75"), checkEditedOrder.getLaborCostSqFt());

    }

    @Test
    public void testEditOrderFailInvalidNameBlank() throws ProductPersistenceException, TaxPersistenceException, OrderPersistenceException {
        InMemoryOrdersDao ordersDao = new InMemoryOrdersDao();
        InMemoryProductsDao productsDao = new InMemoryProductsDao();
        InMemoryTaxDao taxDao = new InMemoryTaxDao();
        InMemoryOrdersAuditDao auditDao = new InMemoryOrdersAuditDao();
        OrderService service = new OrderService(ordersDao, taxDao, productsDao, auditDao);
        LocalDate orderDate = LocalDate.of(2019, 4, 13);
        Order toEdit = ordersDao.displayOrderByNumber(1, orderDate);
        toEdit.setDate(orderDate);
        toEdit.setCustomerName("");
        toEdit.setProductType("Wood");
        toEdit.setState("OH");
        toEdit.setArea(new BigDecimal("10"));
        EditOrderResponse response = service.editOrder(toEdit);
        Assert.assertFalse(response.getSuccess());
        Assert.assertNull(response.getEditedOrder());

    }

    @Test
    public void testEditOrderFailInvalidDatePast() throws ProductPersistenceException, TaxPersistenceException, OrderPersistenceException {
        InMemoryOrdersDao ordersDao = new InMemoryOrdersDao();
        InMemoryProductsDao productsDao = new InMemoryProductsDao();
        InMemoryTaxDao taxDao = new InMemoryTaxDao();
        InMemoryOrdersAuditDao auditDao = new InMemoryOrdersAuditDao();
        OrderService service = new OrderService(ordersDao, taxDao, productsDao, auditDao);
        LocalDate orderDate = LocalDate.of(2019, 4, 13);
        LocalDate editedDate = LocalDate.of(2018, 4, 13);
        Order toEdit = ordersDao.displayOrderByNumber(1, orderDate);
        toEdit.setDate(editedDate);
        toEdit.setCustomerName("Smith");
        toEdit.setProductType("Wood");
        toEdit.setState("OH");
        toEdit.setArea(new BigDecimal("10"));
        EditOrderResponse response = service.editOrder(toEdit);
        Assert.assertFalse(response.getSuccess());
        Assert.assertNull(response.getEditedOrder());
    }

    @Test
    public void testEditOrderFailInvalidProductTypeBlank() throws ProductPersistenceException, TaxPersistenceException, OrderPersistenceException {
        InMemoryOrdersDao ordersDao = new InMemoryOrdersDao();
        InMemoryProductsDao productsDao = new InMemoryProductsDao();
        InMemoryTaxDao taxDao = new InMemoryTaxDao();
        InMemoryOrdersAuditDao auditDao = new InMemoryOrdersAuditDao();
        OrderService service = new OrderService(ordersDao, taxDao, productsDao, auditDao);
        LocalDate orderDate = LocalDate.of(2019, 4, 13);
        Order toEdit = ordersDao.displayOrderByNumber(1, orderDate);
        toEdit.setDate(orderDate);
        toEdit.setCustomerName("Crighton");
        toEdit.setProductType("");
        toEdit.setState("OH");
        toEdit.setArea(new BigDecimal("10"));
        EditOrderResponse response = service.editOrder(toEdit);
        Assert.assertFalse(response.getSuccess());
        Assert.assertNull(response.getEditedOrder());
    }

    @Test
    public void testEditOrderFailInvalidProductType() throws ProductPersistenceException, TaxPersistenceException, OrderPersistenceException {
        InMemoryOrdersDao ordersDao = new InMemoryOrdersDao();
        InMemoryProductsDao productsDao = new InMemoryProductsDao();
        InMemoryTaxDao taxDao = new InMemoryTaxDao();
        InMemoryOrdersAuditDao auditDao = new InMemoryOrdersAuditDao();
        OrderService service = new OrderService(ordersDao, taxDao, productsDao, auditDao);
        LocalDate orderDate = LocalDate.of(2019, 4, 13);
        Order toEdit = ordersDao.displayOrderByNumber(1, orderDate);
        toEdit.setDate(orderDate);
        toEdit.setCustomerName("Crighton");
        toEdit.setProductType("brick");
        toEdit.setState("OH");
        toEdit.setArea(new BigDecimal("10"));
        EditOrderResponse response = service.editOrder(toEdit);
        Assert.assertFalse(response.getSuccess());
        Assert.assertNull(response.getEditedOrder());
    }

    @Test
    public void testEditOrderFailInvalidStateBlank() throws ProductPersistenceException, TaxPersistenceException, OrderPersistenceException {
        InMemoryOrdersDao ordersDao = new InMemoryOrdersDao();
        InMemoryProductsDao productsDao = new InMemoryProductsDao();
        InMemoryTaxDao taxDao = new InMemoryTaxDao();
        InMemoryOrdersAuditDao auditDao = new InMemoryOrdersAuditDao();
        OrderService service = new OrderService(ordersDao, taxDao, productsDao, auditDao);
        LocalDate orderDate = LocalDate.of(2019, 4, 13);
        Order toEdit = ordersDao.displayOrderByNumber(1, orderDate);
        toEdit.setDate(orderDate);
        toEdit.setCustomerName("Crighton");
        toEdit.setProductType("tile");
        toEdit.setState("");
        toEdit.setArea(new BigDecimal("10"));
        EditOrderResponse response = service.editOrder(toEdit);
        Assert.assertFalse(response.getSuccess());
        Assert.assertNull(response.getEditedOrder());
    }

    @Test
    public void testEditOrderFailInvalidStateWrongLetters() throws ProductPersistenceException, TaxPersistenceException, OrderPersistenceException {
        InMemoryOrdersDao ordersDao = new InMemoryOrdersDao();
        InMemoryProductsDao productsDao = new InMemoryProductsDao();
        InMemoryTaxDao taxDao = new InMemoryTaxDao();
        InMemoryOrdersAuditDao auditDao = new InMemoryOrdersAuditDao();
        OrderService service = new OrderService(ordersDao, taxDao, productsDao, auditDao);

        LocalDate orderDate = LocalDate.of(2019, 4, 13);
        Order toEdit = ordersDao.displayOrderByNumber(1, orderDate);
        toEdit.setDate(orderDate);
        toEdit.setCustomerName("Crighton");
        toEdit.setProductType("tile");
        toEdit.setState("HI");
        toEdit.setArea(new BigDecimal("10"));
        EditOrderResponse response = service.editOrder(toEdit);
        Assert.assertFalse(response.getSuccess());
        Assert.assertNull(response.getEditedOrder());
    }

    @Test
    public void testEditOrderFailInvalidAreaNegative() throws ProductPersistenceException, TaxPersistenceException, OrderPersistenceException {
        InMemoryOrdersDao ordersDao = new InMemoryOrdersDao();
        InMemoryProductsDao productsDao = new InMemoryProductsDao();
        InMemoryTaxDao taxDao = new InMemoryTaxDao();
        InMemoryOrdersAuditDao auditDao = new InMemoryOrdersAuditDao();
        OrderService service = new OrderService(ordersDao, taxDao, productsDao, auditDao);
        LocalDate orderDate = LocalDate.of(2019, 4, 13);
        Order toEdit = ordersDao.displayOrderByNumber(1, orderDate);
        toEdit.setDate(orderDate);
        toEdit.setCustomerName("Crighton");
        toEdit.setProductType("tile");
        toEdit.setState("OH");
        toEdit.setArea(new BigDecimal("-10"));
        EditOrderResponse response = service.editOrder(toEdit);
        Assert.assertFalse(response.getSuccess());
        Assert.assertNull(response.getEditedOrder());
    }

    @Test
    public void testEditOrderFailInvalidAreaZero() throws ProductPersistenceException, TaxPersistenceException, OrderPersistenceException {
        InMemoryOrdersDao ordersDao = new InMemoryOrdersDao();
        InMemoryProductsDao productsDao = new InMemoryProductsDao();
        InMemoryTaxDao taxDao = new InMemoryTaxDao();
        InMemoryOrdersAuditDao auditDao = new InMemoryOrdersAuditDao();
        OrderService service = new OrderService(ordersDao, taxDao, productsDao, auditDao);
        LocalDate orderDate = LocalDate.of(2019, 4, 13);
        Order toEdit = ordersDao.displayOrderByNumber(1, orderDate);
        toEdit.setDate(orderDate);
        toEdit.setCustomerName("Crighton");
        toEdit.setProductType("tile");
        toEdit.setState("OH");
        toEdit.setArea(new BigDecimal("0"));
        EditOrderResponse response = service.editOrder(toEdit);
        Assert.assertFalse(response.getSuccess());
        Assert.assertNull(response.getEditedOrder());
    }

    /**
     * Test of removeOrder method, of class OrderService.
     *
     * @throws com.sg.flooringmastery.dao.ProductPersistenceException
     * @throws com.sg.flooringmastery.dao.TaxPersistenceException
     * @throws com.sg.flooringmastery.dao.OrderPersistenceException
     */
    @Test
    public void testRemoveOrderSuccess() throws ProductPersistenceException, TaxPersistenceException, OrderPersistenceException {
        InMemoryOrdersDao ordersDao = new InMemoryOrdersDao();
        InMemoryProductsDao productsDao = new InMemoryProductsDao();
        InMemoryTaxDao taxDao = new InMemoryTaxDao();
        InMemoryOrdersAuditDao auditDao = new InMemoryOrdersAuditDao();
        OrderService service = new OrderService(ordersDao, taxDao, productsDao, auditDao);
        LocalDate orderDate = LocalDate.of(2019, 4, 13);
        Order addedOrder = new Order();
        addedOrder.setDate(orderDate);
        addedOrder.setCustomerName("Fahrenkamp");
        addedOrder.setProductType("Wood");
        addedOrder.setState("OH");
        addedOrder.setArea(new BigDecimal("10"));
        addedOrder.setOrderNumber(10);
        service.addOrder(addedOrder.getDate(), addedOrder.getCustomerName(),
                addedOrder.getProductType(), addedOrder.getState(), addedOrder.getArea());
        Order toRemove = ordersDao.displayOrderByNumber(4, orderDate);
        Assert.assertNotNull(toRemove);
        service.removeOrder(4, orderDate);
        Assert.assertNull(ordersDao.displayOrderByNumber(4, orderDate));
    }

    @Test
    public void testRemoveOrderFailNoOrderDate() throws ProductPersistenceException, TaxPersistenceException, OrderPersistenceException {
        InMemoryOrdersDao ordersDao = new InMemoryOrdersDao();
        InMemoryProductsDao productsDao = new InMemoryProductsDao();
        InMemoryTaxDao taxDao = new InMemoryTaxDao();
        InMemoryOrdersAuditDao auditDao = new InMemoryOrdersAuditDao();
        OrderService service = new OrderService(ordersDao, taxDao, productsDao, auditDao);
        LocalDate badDate = LocalDate.of(2020, 3, 1);
        RemoveOrderResponse response = service.removeOrder(1, badDate);
        Assert.assertFalse(response.getSuccess());
    }

    @Test
    public void testRemoveOrderFailNoOrderNumberOnDate() throws ProductPersistenceException, TaxPersistenceException, OrderPersistenceException {
        InMemoryOrdersDao ordersDao = new InMemoryOrdersDao();
        InMemoryProductsDao productsDao = new InMemoryProductsDao();
        InMemoryTaxDao taxDao = new InMemoryTaxDao();
        InMemoryOrdersAuditDao auditDao = new InMemoryOrdersAuditDao();
        OrderService service = new OrderService(ordersDao, taxDao, productsDao, auditDao);
        LocalDate orderDate = LocalDate.of(2019, 4, 13);
        RemoveOrderResponse response = service.removeOrder(10, orderDate);
        Assert.assertFalse(response.getSuccess());
    }

}
