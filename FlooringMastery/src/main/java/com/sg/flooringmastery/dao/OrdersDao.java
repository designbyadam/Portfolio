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
public interface OrdersDao {

    public Order addOrder(Order toAdd) throws OrderPersistenceException;

    public List<Order> displayOrdersByDate(LocalDate orderDate) throws OrderPersistenceException;

    public Order displayOrderByNumber(int orderNumber, LocalDate orderDate) throws OrderPersistenceException;

    public boolean removeOrder(int orderNumber, LocalDate orderDate) throws OrderPersistenceException;

    public Order editOrder(Order toEdit) throws OrderPersistenceException;

}
