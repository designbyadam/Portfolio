/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vendingmachine.ui;

import com.vendingmachine.daos.VendingPersistenceException;
import com.vendingmachine.dtos.VendingMachine;
import com.vendingmachine.services.ChangeReturnResponse;
import com.vendingmachine.services.EnterMoneyResponse;
import com.vendingmachine.services.VendingService;
import java.math.BigDecimal;

/**
 *
 * @author afahrenkamp
 */
public class ChangeReturnWorkflow {

    VendingService service;

    public ChangeReturnWorkflow(VendingService service) {
        this.service = service;
    }

    public static void run(ConsoleIO ui, VendingService service) throws VendingPersistenceException {

        ChangeReturnResponse response = service.returnChange();

        ui.print("You have been returned:\n" + response.getChange().getDollars() + " dollars\n"
                + response.getChange().getQuarters() + " quarter(s)\n"
                + response.getChange().getDimes() + " dime(s)\n"
                + response.getChange().getNickels() + " nickel(s)\n"
                + response.getChange().getPennies() + " penny(ies)\n" + 
                "Total money returned: $" + response.getChange().getTotalMoneyReturned() + " returned.\n");

    }

}
