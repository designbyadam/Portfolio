/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.ui;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.service.DisplayAllResponse;
import com.sg.flooringmastery.service.OrderService;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author afahrenkamp
 */
public class DisplayAllWorkflow extends GetOrderWorkflow {

    OrderService service;

    public DisplayAllWorkflow(OrderService service) {
        this.service = service;
    }

    public void run(ConsoleIO ui) {

        LocalDate orderDate = ui.readDate("Please enter the date for which you would like to view orders (MM/DD/YYYY): ", LocalDate.MIN, LocalDate.MAX);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String stringOrderDate = orderDate.format(formatter);

        DisplayAllResponse response = service.listOrdersByDate(orderDate);

        if (!response.getSuccess()) {
            ui.print("Error: " + response.getMessage() + "\n");
        } else {
            List<Order> allOrders = response.getAllOrders();
            ui.print("\nList of orders from " + stringOrderDate + ":\n\n");
            for (Order currentOrder : allOrders) {
                displayOrder(ui, currentOrder);
            }
        }
    }
}
