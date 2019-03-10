/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author afahrenkamp
 */
public class AlwaysFailOrdersDao implements OrdersDao {

    @Override
    public Order addOrder(Order toAdd) throws OrderPersistenceException {
        throw new OrderPersistenceException("Could not load order data.");
    }

    @Override
    public List<Order> displayOrdersByDate(LocalDate orderDate) throws OrderPersistenceException {
        throw new OrderPersistenceException("Could not load order data.");
    }

    @Override
    public Order displayOrderByNumber(int orderNumber, LocalDate orderDate) throws OrderPersistenceException {
        throw new OrderPersistenceException("Could not load order data.");
    }

    @Override
    public boolean removeOrder(int orderNumber, LocalDate orderDate) throws OrderPersistenceException {
        throw new OrderPersistenceException("Could not load order data.");
    }

    @Override
    public Order editOrder(Order toEdit) throws OrderPersistenceException {
        throw new OrderPersistenceException("Could not load order data.");
    }

}
