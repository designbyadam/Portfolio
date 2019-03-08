/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vendingmachine.ui;

import com.vendingmachine.daos.VendingPersistenceException;
import com.vendingmachine.dtos.Item;
import com.vendingmachine.services.PrintItemsResponse;
import com.vendingmachine.services.VendingService;
import java.util.List;

/**
 *
 * @author afahrenkamp
 */
public class PrintItemWorkflow {

    public static void run(ConsoleIO ui, VendingService service) throws VendingPersistenceException {

        PrintItemsResponse response = service.getAllItems();

        if (response.isSuccess()) {

            ui.print("--------------------------------\n");
            ui.print("Current vending machine items\n");
            ui.print("--------------------------------\n");

            List<Item> allItems = response.getAllItems();

            for (Item i : allItems) {
                ui.print(i.getId() + ": " + i.getName() + " $" + i.getPrice() + " [" + i.getQuantity() + "]\n");
            }
        } else {
            ui.print("Error: " + response.getMessage());
        }
    }

}
