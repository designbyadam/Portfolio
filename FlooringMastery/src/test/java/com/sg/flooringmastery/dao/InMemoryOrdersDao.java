/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author afahrenkamp
 */
public class InMemoryOrdersDao implements OrdersDao {

    Map<LocalDate, List<Order>> allOrders = new HashMap<LocalDate, List<Order>>();

    InMemoryProductsDao productsDao = new InMemoryProductsDao();
    InMemoryTaxDao taxesDao = new InMemoryTaxDao();

    LocalDate firstDate = LocalDate.of(2019, 4, 13);
    LocalDate secondDate = LocalDate.of(2018, 6, 8);

    public InMemoryOrdersDao() throws ProductPersistenceException, TaxPersistenceException {

        allOrders.put(firstDate, new ArrayList<>());
        allOrders.put(secondDate, new ArrayList<>());

        Order future = new Order();
        future.setOrderNumber(1);
        future.setDate(firstDate);
        future.setCustomerName("crighton");
        future.setState("OH");
        future.setArea(new BigDecimal("10"));
        future.setProductType("tile");
        future.setItemCostSqFt(productsDao.getProductCosts("tile").getItemCostSqFt());
        future.setLaborCostSqFt(productsDao.getProductCosts("tile").getLaborCostSqFt());
        future.setTaxRate(taxesDao.getTaxRate("OH").getTaxRate());
        allOrders.get(firstDate).add(future);

        Order futureSecond = new Order();
        futureSecond.setOrderNumber(3);
        futureSecond.setDate(firstDate);
        futureSecond.setCustomerName("jones");
        futureSecond.setState("mi");
        futureSecond.setArea(new BigDecimal("15"));
        futureSecond.setProductType("wood");
        futureSecond.setItemCostSqFt(productsDao.getProductCosts("wood").getItemCostSqFt());
        futureSecond.setLaborCostSqFt(productsDao.getProductCosts("wood").getLaborCostSqFt());
        futureSecond.setTaxRate(taxesDao.getTaxRate("mi").getTaxRate());
        allOrders.get(firstDate).add(futureSecond);

        Order past = new Order();
        past.setOrderNumber(1);
        past.setDate(secondDate);
        past.setCustomerName("fahrenkamp");
        past.setState("IN");
        past.setArea(new BigDecimal("1"));
        past.setProductType("laminate");
        past.setItemCostSqFt(productsDao.getProductCosts("laminate").getItemCostSqFt());
        past.setLaborCostSqFt(productsDao.getProductCosts("laminate").getLaborCostSqFt());
        past.setTaxRate(taxesDao.getTaxRate("IN").getTaxRate());

        allOrders.get(secondDate).add(past);

    }

    @Override
    public Order addOrder(Order toAdd) throws OrderPersistenceException {
        if (!allOrders.containsKey(toAdd.getDate())) {
            allOrders.put(toAdd.getDate(), new ArrayList<>());
        }
        int newOrderNumber = generateNewOrderNumber(allOrders.get(toAdd.getDate()));

        toAdd.setOrderNumber(newOrderNumber);

        allOrders.get(toAdd.getDate()).add(toAdd);

        return toAdd;
    }

    @Override
    public List<Order> displayOrdersByDate(LocalDate orderDate) throws OrderPersistenceException {
        return allOrders.get(orderDate);
    }

    @Override
    public Order displayOrderByNumber(int orderNumber, LocalDate orderDate) throws OrderPersistenceException {
        Order toReturn = null;
        List<Order> allOrders = displayOrdersByDate(orderDate);
        for (Order toCheck : allOrders) {
            if (toCheck.getOrderNumber() == orderNumber) {
                toReturn = toCheck;
                break;
            }
        }
        return toReturn;

    }

    @Override
    public boolean removeOrder(int orderNumber, LocalDate orderDate) throws OrderPersistenceException {
        boolean toReturn = false;
        List<Order> allOrders = displayOrdersByDate(orderDate);
        int index = Integer.MIN_VALUE;

        for (int i = 0; i < allOrders.size(); i++) {
            Order toCheck = allOrders.get(i);
            if (toCheck.getOrderNumber() == orderNumber && toCheck.getDate() == orderDate) {
                index = i;
                toReturn = true;
                break;
            }

        }
        allOrders.remove(index);
        return toReturn;
    }

    @Override
    public Order editOrder(Order toEdit) throws OrderPersistenceException {
        Order toReturn = null;

        List<Order> allOrders = displayOrdersByDate(toEdit.getDate());

        int index = Integer.MIN_VALUE;

        int orderNumber = toEdit.getOrderNumber();

        for (int i = 0; i < allOrders.size(); i++) {
            Order toCheck = allOrders.get(i);
            if (toCheck.getOrderNumber() == orderNumber) {
                index = i;
                break;
            }
        }

        if (index >= 0 && index < allOrders.size()) {
            allOrders.set(index, toEdit);

            toReturn = toEdit;
        }

        return toReturn;
    }

    private int generateNewOrderNumber(List<Order> allOrders) {

        int toReturn = Integer.MIN_VALUE;

        if (allOrders.isEmpty()) {
            toReturn = 1;
        } else {
            for (Order toInspect : allOrders) {
                if (toInspect.getOrderNumber() > toReturn) {
                    toReturn = toInspect.getOrderNumber();
                }
            }

            toReturn++;
        }

        return toReturn;
    }

}
