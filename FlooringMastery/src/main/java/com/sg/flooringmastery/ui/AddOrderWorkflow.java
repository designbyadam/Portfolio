/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.ui;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.service.AddOrderResponse;
import com.sg.flooringmastery.service.DisplayAllResponse;
import com.sg.flooringmastery.service.OrderService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author afahrenkamp
 */
public class AddOrderWorkflow extends GetOrderWorkflow {

    OrderService service;

    public AddOrderWorkflow(OrderService service) {
        this.service = service;
    }

    public void run(ConsoleIO ui) {

        LocalDate dateOfOrder = getOrderDate(ui);
        String custName = getCustName(ui);
        String stateAbbrev = getStateAbbrev(ui);
        String productType = getProductType(ui);
        BigDecimal area = getArea(ui);

        AddOrderResponse response = service.addOrder(dateOfOrder, custName, productType, stateAbbrev, area);

        if (!response.getSuccess()) {
            ui.print("Error: " + response.getMessage() + "\n");

        } else {
            Order returnedOrder = response.getCurrentOrder();
            displayOrder(ui, returnedOrder);
        }
    }

    private String getCustName(ConsoleIO ui) {
        String toReturn;

        do {
            toReturn = ui.readString("Please enter Customer's Last Name: ");
        } while (toReturn.equals(""));
        return toReturn;
    }

    private String getStateAbbrev(ConsoleIO ui) {
        String toReturn = "";
        String states = "";

        List<String> stateList = service.getStateList();

        for (int i = 0; i < stateList.size(); i++) {
            if (i < stateList.size() - 1) {
                states += stateList.get(i) + "/";
            } else {
                states += stateList.get(i);
            }
        }

        boolean isValid = false;
        while (!isValid) {

            String currentState = "";
            toReturn = ui.readString("In which state is this being installed"
                    + " (" + states + ")?: ");
            for (int i = 0; i < stateList.size(); i++) {
                currentState = stateList.get(i);
                if (toReturn.equalsIgnoreCase(currentState)) {
                    isValid = true;
                }
            }

        }
        return toReturn;
    }

    private String getProductType(ConsoleIO ui) {
        String toReturn = "";
        String products = "";

        List<String> productList = service.getProductList();

        for (int i = 0; i < productList.size(); i++) {
            if (i < productList.size() - 1) {
                products += productList.get(i) + "/";
            } else {
                products += productList.get(i);
            }
        }

        boolean isValid = false;
        while (!isValid) {

            String currentProduct = "";
            toReturn = ui.readString("Which product would you like to purchase"
                    + " (" + products + ")?: ");
            for (int i = 0; i < productList.size(); i++) {
                currentProduct = productList.get(i);
                if (toReturn.equalsIgnoreCase(currentProduct)) {
                    isValid = true;
                }
            }

        }
        return toReturn;
    }

    private BigDecimal getArea(ConsoleIO ui) {
        BigDecimal toReturn = new BigDecimal("0");

        boolean isValid = false;

        while (!isValid) {
            String stringArea = ui.readString("Please enter the square footage that needs to "
                    + "be floored: ");

            if (stringArea.isEmpty()) {

            } else {
                BigDecimal bdArea = new BigDecimal(stringArea);

                if (bdArea.compareTo(new BigDecimal(0)) > 0) {
                    toReturn = bdArea;
                    isValid = true;
                }
            }

        }
        return toReturn;

    }

    private LocalDate getOrderDate(ConsoleIO ui) {
        LocalDate toReturn;
        toReturn = ui.readDate("Please enter the date you would like to schedule the installation (MM/DD/YYYY): ", LocalDate.now(), LocalDate.MAX);
        return toReturn;
    }
}
