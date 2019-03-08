/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vendingmachine.ui;

import com.vendingmachine.daos.VendingPersistenceException;
import com.vendingmachine.dtos.Item;
import com.vendingmachine.services.PrintItemsResponse;
import com.vendingmachine.services.VendItemResponse;
import com.vendingmachine.services.VendingService;
import java.util.List;
import static jdk.nashorn.internal.runtime.Debug.id;

/**
 *
 * @author afahrenkamp
 */
public class VendItemWorkflow {

    VendingService service;

    public VendItemWorkflow(VendingService service) {
        this.service = service;
    }

    public static void run(ConsoleIO ui, VendingService service) throws VendingPersistenceException {

        PrintItemsResponse allItemsResponse = service.getAllItems();

        if (!allItemsResponse.getSuccess()) {
            ui.print("SEVERE ERROR: " + allItemsResponse.getMessage());
        } else {
            List<Item> allItems = allItemsResponse.getAllItems();

            int id = getIdToRemoveByName(ui, allItems);

            VendItemResponse response = service.vendItem(id);

            if (response.getSuccess()) {
                Item itemVended = response.getVendedItem();

                ui.print("You purchased: " + itemVended.getName() + "\n");
                ui.print("You have been returned:\n" + response.getChange().getDollars() + " dollars\n"
                        + response.getChange().getQuarters() + " quarter(s)\n"
                        + response.getChange().getDimes() + " dime(s)\n"
                        + response.getChange().getNickels() + " nickel(s)\n"
                        + response.getChange().getPennies() + " penny(ies)\n"
                        + "Total money returned: $" + response.getChange().getTotalMoneyReturned() + " returned.\n");

            } else {
                ui.print("Error: " + response.getMessage() + "\n");
            }
        }
    }

    private static int getIdToRemoveByName(ConsoleIO ui, List<Item> allItems) {
        int toReturn = Integer.MIN_VALUE;

        boolean found = false;
        while (!found) {

            String name = ui.readString("Please enter the name of the item you want to purchase: ");
            for (Item toCheck : allItems) {
                found = toCheck.getName().equalsIgnoreCase(name);
                if (found) {

                    //get id of correct item
                    toReturn = toCheck.getId();
                    break;
                }
            }
        }
        //return id of item
        return toReturn;
    }

}
