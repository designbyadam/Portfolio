/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.ui;

import com.sg.flooringmastery.service.OrderService;

/**
 *
 * @author afahrenkamp
 */
public class MainMenu {

    static ConsoleIO ui = new ConsoleIO();

    public static void run(OrderService service) {
        boolean isDone = false;

        while (!isDone) {
            int userChoice = getUserChoice();

            switch (userChoice) {
                case 1:
                    DisplayAllWorkflow displayAllFlow = new DisplayAllWorkflow(service);
                    displayAllFlow.run(ui);
                    break;
                case 2:
                    DisplaySingleWorkflow displaySingleFlow = new DisplaySingleWorkflow(service);
                    displaySingleFlow.run(ui);
                    break;
                case 3:
                    AddOrderWorkflow addFlow = new AddOrderWorkflow(service);
                    addFlow.run(ui);
                    break;
                case 4:
                    EditOrderWorkflow editFlow = new EditOrderWorkflow(service);
                    editFlow.run(ui);
                    break;
                case 5:
                    RemoveOrderWorkflow removeFlow = new RemoveOrderWorkflow(service);
                    removeFlow.run(ui);
                    break;
                case 6:
                    isDone = true;
                    break;

            }
        }
    }

    private static int getUserChoice() {
        ui.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\n");
        ui.print("<<Flooring Program>>");
        ui.print("\n1. Display All Orders From Single Day\n");
        ui.print("2. Display Single Order\n");
        ui.print("3. Add an Order\n");
        ui.print("4. Edit an Order\n");
        ui.print("5. Remove an Order\n");
        ui.print("6. Quit\n");
        ui.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\n");

        int toReturn = ui.readInt("Please select a choice between 1 and 6: ", 1, 6);

        return toReturn;
    }
}
