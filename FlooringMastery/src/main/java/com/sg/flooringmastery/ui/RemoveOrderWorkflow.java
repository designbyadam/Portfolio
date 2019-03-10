/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.ui;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.service.DisplayAllResponse;
import com.sg.flooringmastery.service.OrderService;
import com.sg.flooringmastery.service.RemoveOrderResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author afahrenkamp
 */
public class RemoveOrderWorkflow extends GetOrderWorkflow {

    OrderService service;

    RemoveOrderWorkflow(OrderService service) {
        this.service = service;
    }

    public void run(ConsoleIO ui) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        boolean isFound = false;

        while (!isFound) {

            LocalDate orderDate = ui.readDate("Please enter the date of the order to remove (MM/DD/YYYY): ", LocalDate.now(), LocalDate.MAX);

            DisplayAllResponse getAllResponse = service.listOrdersByDate(orderDate);

            if (!getAllResponse.getSuccess()) {
                ui.print("Error: " + getAllResponse.getMessage() + "\n");
            } else {
                if (getAllResponse.getAllOrders().isEmpty()) {

                } else {

                    List<Order> allOrders = getAllResponse.getAllOrders();
                    Order toRemove = getSingleOrder(ui, allOrders);

                    if (toRemove == null) {

                    } else {
                        isFound = true;
                        RemoveOrderResponse response = service.removeOrder(toRemove.getOrderNumber(), toRemove.getDate());
                        if (response.getSuccess()) {
                            ui.print("Order for " + response.getName() + " was removed successfully.\n");
                        } else {
                            ui.print("Error: " + response.getMessage() + "\n");

                        }
                    }

                }
            }
        }

    }

}
