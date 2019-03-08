/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vendingmachine.ui;

import com.vendingmachine.daos.VendingPersistenceException;
import com.vendingmachine.services.VendingService;

/**
 *
 * @author afahrenkamp
 */
public class MainMenu {

    static ConsoleIO ui = new ConsoleIO();

    public static void run(VendingService service) throws VendingPersistenceException {

        boolean shouldExit = false;

        while (!shouldExit) {
            PrintItemWorkflow.run(ui, service);
            
            ui.print("-------------------------\n");
            ui.print("1. Enter money.\n");
            ui.print("2. Vend item.\n");
            ui.print("3. Return change.\n");
            ui.print("4. Quit.\n");
            ui.print("-------------------------\n");

            int userChoice = ui.readInt("Please enter selection: ", 1, 4);

            switch (userChoice) {
                case 1:
                    EnterMoneyWorkflow.run(ui, service);
                    break;
                case 2:
                    VendItemWorkflow.run(ui, service);
                    break;
                case 3:
                    ChangeReturnWorkflow.run(ui, service);
                    break;
                case 4:
                    shouldExit = true;
                    break;
            }

        }
    }




}
