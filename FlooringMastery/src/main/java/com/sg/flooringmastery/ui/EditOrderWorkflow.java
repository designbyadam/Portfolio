/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.ui;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.service.DisplayAllResponse;
import com.sg.flooringmastery.service.DisplaySingleResponse;
import com.sg.flooringmastery.service.EditOrderResponse;
import com.sg.flooringmastery.service.OrderService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author afahrenkamp
 */
public class EditOrderWorkflow extends GetOrderWorkflow {

    OrderService service;

    EditOrderWorkflow(OrderService service) {
        this.service = service;
    }

    public void run(ConsoleIO ui) {

        boolean isFound = false;

        while (!isFound) {

            LocalDate orderDate = ui.readDate("Please enter the date for which you would like to edit your order (MM/DD/YYYY): ", LocalDate.now(), LocalDate.MAX);

            DisplayAllResponse getAllResponse = service.listOrdersByDate(orderDate);

            if (!getAllResponse.getSuccess()) {
                ui.print("Error: " + getAllResponse.getMessage() + "\n");
            } else {

                List<Order> allOrders = getAllResponse.getAllOrders();
                Order toEdit = getSingleOrder(ui, allOrders);

                if (toEdit == null) {
                    ui.print("Error: " + getAllResponse.getMessage() + "\n");
                } else {

                    DisplaySingleResponse displaySingleResponse = service.listOrderByNumber(toEdit.getOrderNumber(), orderDate);
                    if (!displaySingleResponse.getSuccess()) {
                        ui.print("Error: " + displaySingleResponse.getMessage() + "\n");
                    } else {

                        String newName = getCustName(ui, toEdit.getCustomerName());
                        if (!newName.isEmpty()) {
                            toEdit.setCustomerName(newName);
                        }

                        String newState = getStateAbbrev(ui, toEdit.getState());
                        if (!newState.isEmpty()) {
                            toEdit.setState(newState);
                        }

                        String newProductType = getProductType(ui, toEdit.getProductType());
                        if (!newProductType.isEmpty()) {
                            toEdit.setProductType(newProductType);
                        }

                        BigDecimal newArea = getArea(ui, toEdit.getArea());
                        if (newArea.compareTo(new BigDecimal(0)) > 0) {
                            toEdit.setArea(newArea);
                        }

                        toEdit.setDate(orderDate);

                        EditOrderResponse response = service.editOrder(toEdit);

                        if (response.getSuccess()) {
                            Order editedOrder = response.getEditedOrder();
                            displayOrderDetails(editedOrder, ui);
                        } else {

                            ui.print("Error: " + response.getMessage() + "\n");
                        }
                    }
                }
                isFound = true;
            }

        }
    }

    private String getCustName(ConsoleIO ui, String oldName) {
        String toReturn = ui.readString("Please enter new customer name (blank to keep: " + oldName + "): ");
        return toReturn;
    }

    private String getStateAbbrev(ConsoleIO ui, String oldState) {
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
            toReturn = ui.readString("Please enter the new state "
                    + "(blank to keep: " + oldState + ")\n" + "(Must be either " + states + "): ");

            if (toReturn.equals(currentState)) {
                isValid = true;
            } else {

                for (int i = 0; i < stateList.size(); i++) {
                    currentState = stateList.get(i);
                    if (toReturn.equalsIgnoreCase(currentState)) {
                        isValid = true;
                    }
                }

            }
        }
        return toReturn;
    }

    private String getProductType(ConsoleIO ui, String oldProductType) {
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
            toReturn = ui.readString("Please enter the new product type "
                    + " (blank to keep: " + oldProductType + ")\n" + "(Must be either " + products + "): ");
            if (toReturn.equals(currentProduct)) {
                isValid = true;
            } else {
                for (int i = 0; i < productList.size(); i++) {
                    currentProduct = productList.get(i);
                    if (toReturn.equalsIgnoreCase(currentProduct)) {
                        isValid = true;
                    }
                }
            }

        }
        return toReturn;
    }

    private BigDecimal getArea(ConsoleIO ui, BigDecimal oldArea) {
        BigDecimal toReturn = oldArea;
        boolean isValid = false;

        while (!isValid) {
            String stringArea = ui.readString("Please enter the new square footage that needs to "
                    + "be floored (blank to keep: " + oldArea + ")\n");

            if (stringArea.isEmpty()) {
                toReturn = oldArea;
                isValid = true;
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

    private void displayOrderDetails(Order editedOrder, ConsoleIO ui) {

        LocalDate orderDate = editedOrder.getDate();

        DisplayAllResponse getAllResponse = service.listOrdersByDate(orderDate);

        if (!getAllResponse.getSuccess()) {
            ui.print("Error: " + getAllResponse.getMessage());
        } else {

            List<Order> allOrders = getAllResponse.getAllOrders();

            DisplaySingleResponse displaySingleResponse = service.listOrderByNumber(editedOrder.getOrderNumber(), orderDate);

            if (!displaySingleResponse.getSuccess()) {
                ui.print("Error: " + displaySingleResponse.getMessage());
            } else {
                Order toDisplay = displaySingleResponse.getOrderToDisplay();

                displayOrder(ui, toDisplay);
            }
        }
    }
}
