/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vendingmachine.ui;

import com.vendingmachine.daos.VendingPersistenceException;
import com.vendingmachine.services.EnterMoneyResponse;
import com.vendingmachine.services.VendingService;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author afahrenkamp
 */
public class EnterMoneyWorkflow {

    VendingService service;

    public EnterMoneyWorkflow(VendingService service) {
        this.service = service;
    }

    public static void run(ConsoleIO ui, VendingService service) throws VendingPersistenceException {

        BigDecimal totalMoneyInMachine = new BigDecimal(0);

        BigDecimal moneyAdded;

        boolean noMoreSelection = false;

        while (!noMoreSelection) {
            int customerResponse = ui.readInt("What denomination of currency would "
                    + "you like to enter?\n "
                    + "1: Dollar bill\n "
                    + "2: Quarter\n "
                    + "3: Dime\n "
                    + "4: Nickel\n "
                    + "5: Penny\n "
                    + "6: Finished entering\n", 1, 6);

            EnterMoneyResponse response = null;
            BigDecimal toDivide = new BigDecimal(100);
            switch (customerResponse) {

                case 1:
                    response = service.enterMoney(new BigDecimal("1.00"));
                    break;
                case 2:
                    response = service.enterMoney(new BigDecimal("0.25"));
                    break;
                case 3:
                    response = service.enterMoney(new BigDecimal("0.10"));
                    break;
                case 4:
                    response = service.enterMoney(new BigDecimal("0.05"));
                    break;
                case 5:
                    response = service.enterMoney(new BigDecimal("0.01"));
                    break;
                case 6:
                    noMoreSelection = true;
                    break;

            }
            if (!noMoreSelection) {
                   ui.print("Current amount of money in machine: $" + response.getTotalMoneyEntered() + "\n");
            }
        }
    }
}
