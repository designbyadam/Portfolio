/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author afahrenkamp
 */
public class OrdersFileDao implements OrdersDao {

    String path;

    public OrdersFileDao(String path) {
        this.path = path;
    }
    
    public OrdersFileDao() {
        path = "/home/designbyadam/FlooringFiles/Orders/";
    }

    @Override
    public Order addOrder(Order toAdd) throws OrderPersistenceException {

        Order toReturn = toAdd;
        List<Order> allOrdersOnDate = displayOrdersByDate(toAdd.getDate());

        String dateString = orderDateToString(toAdd.getDate());

        File orderFile = new File(path + "orders_" + dateString + ".txt");

        if (!orderFile.exists()) {
            try {
                orderFile.createNewFile();
            } catch (IOException ex) {
                throw new OrderPersistenceException("Could not create file: "
                        + path + "orders_" + dateString + ".txt");
            }
        }

        if (allOrdersOnDate.isEmpty()) {
            toAdd.setOrderNumber(1);
            allOrdersOnDate.add(toAdd);
            boolean success = writeFile(allOrdersOnDate, toAdd.getDate());
            if (!success) {
                toReturn = null;
            }
        } else {

            int newOrderNumber = generateNewOrderNumber(allOrdersOnDate);

            toAdd.setOrderNumber(newOrderNumber);

            allOrdersOnDate.add(toAdd);
            boolean success = writeFile(allOrdersOnDate, toAdd.getDate());
            if (!success) {
       
                toReturn = null;
            }

        }
        return toReturn;
    }

    @Override
    public List<Order> displayOrdersByDate(LocalDate orderDate) throws OrderPersistenceException {
        List<Order> toReturn = new ArrayList<Order>();

        String dateString = orderDateToString(orderDate);

        File orderFile = new File(path + "orders_" + dateString + ".txt");
        if (orderFile.exists()) {
            try {

                Scanner reader = new Scanner(new BufferedReader(new FileReader(orderFile)));
                while (reader.hasNextLine()) {
                    String line = reader.nextLine();
                    String[] cells = line.split(",");
                    Order toAdd = new Order();

                    toAdd.setDate(orderDate);

                    toAdd.setOrderNumber(Integer.parseInt(cells[0]));
                    toAdd.setCustomerName(cells[1]);
                    toAdd.setState(cells[2]);
                    toAdd.setTaxRate(new BigDecimal(cells[3]));
                    toAdd.setProductType(cells[4]);
                    toAdd.setArea(new BigDecimal(cells[5]));
                    toAdd.setItemCostSqFt(new BigDecimal(cells[6]));
                    toAdd.setLaborCostSqFt(new BigDecimal(cells[7]));
                    toReturn.add(toAdd);
                }

            } catch (FileNotFoundException ex) {
                throw new OrderPersistenceException("Could not load order data into memory.", ex);
            }
        }
        return toReturn;
    }

    @Override
    public Order editOrder(Order toEdit) throws OrderPersistenceException {
        Order toReturn = null;

        List<Order> allOrdersForDate = displayOrdersByDate(toEdit.getDate());

        int index = Integer.MIN_VALUE;

        int orderNumber = toEdit.getOrderNumber();

        for (int i = 0; i < allOrdersForDate.size(); i++) {
            Order toCheck = allOrdersForDate.get(i);
            if (toCheck.getOrderNumber() == orderNumber) {
                index = i;
                break;
            }
        }

        if (index >= 0 && index < allOrdersForDate.size()) {
            allOrdersForDate.set(index, toEdit);

            boolean success;
            try {
                success = writeFile(allOrdersForDate, toEdit.getDate());
            } catch (OrderPersistenceException ex) {
                throw new OrderPersistenceException("Could not retrieve order data", ex);
            }
            if (success) {
                toReturn = toEdit;
            }
        }
        return toReturn;
    }

    @Override
    public Order displayOrderByNumber(int orderNumber, LocalDate orderDate) throws OrderPersistenceException {
        Order toReturn = null;
        List<Order> allOrders;
        try {
            allOrders = displayOrdersByDate(orderDate);
        } catch (OrderPersistenceException ex) {
            throw new OrderPersistenceException("Could not load order data into memory.", ex);
        }

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
        List<Order> allOrdersFromDate = displayOrdersByDate(orderDate);
        int index = Integer.MIN_VALUE;

        for (int i = 0; i < allOrdersFromDate.size(); i++) {
            Order toCheck = allOrdersFromDate.get(i);
            if (toCheck.getOrderNumber() == orderNumber && toCheck.getDate() == orderDate) {
                index = i;
                break;
            }
        }

        allOrdersFromDate.remove(index);

        boolean success;
        try {
            success = writeFile(allOrdersFromDate, orderDate);
        } catch (OrderPersistenceException ex) {
            throw new OrderPersistenceException("Could not retrieve order data", ex);
        }

        return success;
    }

    private String orderDateToString(LocalDate orderDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
        String toReturn = orderDate.format(formatter);
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

    private boolean writeFile(List<Order> allOrdersOnDate, LocalDate orderDate) throws OrderPersistenceException {
        boolean success = false;

        String dateString = orderDateToString(orderDate);

        File orderFile = new File(path + "orders_" + dateString + ".txt");

        if (!orderFile.exists()) {
            try {
                orderFile.createNewFile();
            } catch (IOException ex) {
                throw new OrderPersistenceException("Could not create new file.", ex);
            }
        }

        try {
            PrintWriter pw = new PrintWriter(new FileWriter(orderFile));

            for (Order toWrite : allOrdersOnDate) {
                String line = orderToLine(toWrite);
                pw.println(line);
            }

            pw.flush();
            pw.close();

            success = true;
        } catch (IOException ex) {
            throw new OrderPersistenceException("Could not save order date.", ex);
        }
        return success;
    }

    private String orderToLine(Order toWrite) {
        String toReturn = toWrite.getOrderNumber() + ","
                + toWrite.getCustomerName() + ","
                + toWrite.getState() + ","
                + toWrite.getTaxRate() + ","
                + toWrite.getProductType() + ","
                + toWrite.getArea() + ","
                + toWrite.getItemCostSqFt() + ","
                + toWrite.getLaborCostSqFt() + ","
                + toWrite.getTotalMaterialCost() + ","
                + toWrite.getTotalLaborCost() + ","
                + toWrite.getTotalTax() + ","
                + toWrite.getOrderTotal();

        return toReturn;
    }

}
