/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.ui;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.service.DisplayAllResponse;
import com.sg.flooringmastery.service.DisplaySingleResponse;
import com.sg.flooringmastery.service.OrderService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author afahrenkamp
 */
public class DisplaySingleWorkflow extends GetOrderWorkflow {

    OrderService service;

    public DisplaySingleWorkflow(OrderService service) {
        this.service = service;
    }

    public void run(ConsoleIO ui) {
        LocalDate orderDate = ui.readDate("Please enter the date for which you would like to view an order (MM/DD/YYYY): ", LocalDate.MIN, LocalDate.MAX);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String stringOrderDate = orderDate.format(formatter);

        DisplayAllResponse getAllResponse = service.listOrdersByDate(orderDate);

        if (!getAllResponse.getSuccess()) {
            ui.print("Error: " + getAllResponse.getMessage() + "\n");
        } else {

            List<Order> allOrders = getAllResponse.getAllOrders();

            Order toGet = getSingleOrder(ui, allOrders);

            DisplaySingleResponse displaySingleResponse = service.listOrderByNumber(toGet.getOrderNumber(), orderDate);

            if (!displaySingleResponse.getSuccess()) {
                ui.print("Error: " + displaySingleResponse.getMessage() + "\n");
            } else {
                Order toDisplay = displaySingleResponse.getOrderToDisplay();
                displayOrder(ui, toDisplay);
            }
        }
    }
}
