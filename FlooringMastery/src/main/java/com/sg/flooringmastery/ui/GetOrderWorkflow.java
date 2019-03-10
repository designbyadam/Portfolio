/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.ui;

import com.sg.flooringmastery.dto.Order;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author afahrenkamp
 */
public abstract class GetOrderWorkflow {

    protected static Order getSingleOrder(ConsoleIO ui, List<Order> allOrders) {
        Order toDisplay = null;

        boolean found = false;

        while (!found) {
            int orderNumber = ui.readInt("Please enter the order number: ");

            for (Order toCheck : allOrders) {
                found = (toCheck.getOrderNumber() == orderNumber);
                if (found) {
                    toDisplay = toCheck;
                    break;
                }
            }
        }
        return toDisplay;
    }

    protected static void displayOrder(ConsoleIO ui, Order orderToDisplay) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String stringOrderDate = orderToDisplay.getDate().format(formatter);

        ui.print("\n Date: " + stringOrderDate + "  Order #" 
                + orderToDisplay.getOrderNumber()
                + "  Customer: " + orderToDisplay.getCustomerName()
                + "  Product: " + orderToDisplay.getProductType()
                + "  Total Cost: $" + orderToDisplay.getOrderTotal().setScale(2) + "\n\n");
    }

}
