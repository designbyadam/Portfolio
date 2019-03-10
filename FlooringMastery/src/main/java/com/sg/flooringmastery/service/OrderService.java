/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.OrderPersistenceException;
import com.sg.flooringmastery.dao.OrdersAuditDao;
import com.sg.flooringmastery.dao.OrdersDao;
import com.sg.flooringmastery.dao.ProductPersistenceException;
import com.sg.flooringmastery.dao.ProductsDao;
import com.sg.flooringmastery.dao.TaxDao;
import com.sg.flooringmastery.dao.TaxPersistenceException;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author afahrenkamp
 */
public class OrderService {

    OrdersDao ordersDao;
    TaxDao taxesDao;
    ProductsDao productsDao;
    OrdersAuditDao auditDao;

    public OrderService(OrdersDao ordersDao, TaxDao taxesDao, ProductsDao productsDao, OrdersAuditDao auditDao) {
        this.ordersDao = ordersDao;
        this.productsDao = productsDao;
        this.taxesDao = taxesDao;
        this.auditDao = auditDao;
    }

    public AddOrderResponse addOrder(LocalDate orderDate, String name, String productType, String state, BigDecimal area) {
        AddOrderResponse response = new AddOrderResponse();

        try {

            Product currentProduct = productsDao.getProductCosts(productType);
            Tax currentTax = taxesDao.getTaxRate(state);

            Order toAdd = new Order();

            validateProperties(name, state, productType, area);
            validateFutureDate(orderDate);

            toAdd.setCustomerName(name);
            toAdd.setDate(orderDate);
            toAdd.setArea(area);
            toAdd.setItemCostSqFt(currentProduct.getItemCostSqFt());
            toAdd.setLaborCostSqFt(currentProduct.getLaborCostSqFt());
            toAdd.setProductType(productType);
            toAdd.setState(state);
            toAdd.setTaxRate(currentTax.getTaxRate());

            ordersDao.addOrder(toAdd);

            response.setSuccess(true);
            response.setCurrentOrder(toAdd);

        } catch (OrderPersistenceException | NameValidationException | ProductValidationException
                | AreaValidationException | StateValidationException | DateValidationException
                | TaxPersistenceException | ProductPersistenceException ex) {
            try {
                auditDao.writeAuditEntry(ex.getMessage());
            } catch (OrderPersistenceException ex1) {
                response.setMessage(ex1.getMessage());
            }
            response.setSuccess(false);
            response.setMessage(ex.getMessage());
        }
        return response;
    }

    public DisplayAllResponse listOrdersByDate(LocalDate toList) {
        DisplayAllResponse response = new DisplayAllResponse();

        try {
            List<Order> allOrders;

            allOrders = ordersDao.displayOrdersByDate(toList);

            validateDate(toList);
            validateOrderDate(toList, allOrders);
            response.setSuccess(true);
            response.setAllOrders(allOrders);
        } catch (OrderPersistenceException | InvalidOrderDateException | DateValidationException ex) {
            try {
                auditDao.writeAuditEntry(ex.getMessage());
            } catch (OrderPersistenceException ex1) {
                response.setMessage(ex1.getMessage());
            }
            response.setSuccess(false);
            response.setMessage(ex.getMessage());
        }
        return response;
    }

    public DisplaySingleResponse listOrderByNumber(int orderNumber, LocalDate toList) {
        DisplaySingleResponse response = new DisplaySingleResponse();

        try {
            List<Order> orderDateToReturn = ordersDao.displayOrdersByDate(toList);
            validateDate(toList);
            validateOrderDate(toList, orderDateToReturn);
            validateSingleOrderNumber(orderNumber, toList, orderDateToReturn);

            Order matching = ordersDao.displayOrderByNumber(orderNumber, toList);
            response.setOrderToDisplay(matching);
            response.setSuccess(true);
        } catch (OrderPersistenceException | InvalidOrderDateException
                | InvalidOrderNumberException | DateValidationException ex) {
            try {
                auditDao.writeAuditEntry(ex.getMessage());
            } catch (OrderPersistenceException ex1) {
                response.setMessage(ex1.getMessage());
            }
            response.setSuccess(false);
            response.setMessage(ex.getMessage());
        }
        return response;
    }

    public List<String> getProductList() {
        List<String> toReturn = new ArrayList<String>();

        try {
            List<Product> productList = productsDao.getProducts();

            toReturn = new ArrayList<String>(productList.size());

            for (int i = 0; i < productList.size(); i++) {
                toReturn.add(i, productList.get(i).getProductType());
            }
        } catch (ProductPersistenceException ex) {
            try {
                auditDao.writeAuditEntry(ex.getMessage());
            } catch (OrderPersistenceException ex1) {

            }

        }
        return toReturn;
    }

    public List<String> getStateList() {
        List<String> toReturn = new ArrayList<String>();

        try {
            List<Tax> taxList = taxesDao.getTaxes();
            toReturn = new ArrayList<String>(taxList.size());

            for (int i = 0; i < taxList.size(); i++) {
                toReturn.add(i, taxList.get(i).getStatePurchasedIn());
            }
        } catch (TaxPersistenceException ex) {
            try {
                auditDao.writeAuditEntry(ex.getMessage());
            } catch (OrderPersistenceException ex1) {

            }

        }
        return toReturn;
    }

    public EditOrderResponse editOrder(Order toEdit) {
        EditOrderResponse response = new EditOrderResponse();

        try {

            validateFutureDate(toEdit.getDate());
            validateProperties(toEdit.getCustomerName(), toEdit.getState(),
                    toEdit.getProductType(), toEdit.getArea());
            Product currentProduct = productsDao.getProductCosts(toEdit.getProductType());
            Tax currentTax = taxesDao.getTaxRate(toEdit.getState());
            toEdit.setItemCostSqFt(currentProduct.getItemCostSqFt());
            toEdit.setLaborCostSqFt(currentProduct.getLaborCostSqFt());
            toEdit.setTaxRate(currentTax.getTaxRate());

            Order edited = ordersDao.editOrder(toEdit);

            response.setEditedOrder(edited);
            response.setSuccess(true);

        } catch (OrderPersistenceException | ProductPersistenceException | 
                TaxPersistenceException | NameValidationException
                | StateValidationException | ProductValidationException
                | AreaValidationException | DateValidationException ex) {
            try {
                auditDao.writeAuditEntry(ex.getMessage());
            } catch (OrderPersistenceException ex1) {
                response.setMessage(ex1.getMessage());
            }
            response.setMessage(ex.getMessage());
            response.setSuccess(false);
        }

        return response;

    }

    public RemoveOrderResponse removeOrder(int orderNumber, LocalDate orderDate) {
        RemoveOrderResponse response = new RemoveOrderResponse();

        try {

            List<Order> allOrdersOnDate = ordersDao.displayOrdersByDate(orderDate);
            validateOrderDate(orderDate, allOrdersOnDate);
            validateSingleOrderNumber(orderNumber, orderDate, allOrdersOnDate);

            Order matching = ordersDao.displayOrderByNumber(orderNumber, orderDate);

            ordersDao.removeOrder(orderNumber, orderDate);

            response.setSuccess(true);
            response.setName(matching.getCustomerName());
        } catch (OrderPersistenceException | InvalidOrderDateException
                | InvalidOrderNumberException ex) {
            try {
                auditDao.writeAuditEntry(ex.getMessage());
            } catch (OrderPersistenceException ex1) {
                response.setMessage(ex1.getMessage());
            }
            response.setSuccess(false);
            response.setMessage(ex.getMessage());
        }
        return response;

    }

    private boolean validateProperties(String name, String state, String productType, BigDecimal area)
            throws NameValidationException, StateValidationException, ProductValidationException,
            AreaValidationException {
        List<String> stateList = getStateList();
        List<String> productList = getProductList();

        boolean isValid = false;

        if (name.equals("")) {
            throw new NameValidationException("Customer name must be at least one character long.");
        }

        String states = "";

        for (int i = 0; i < stateList.size(); i++) {
            if (i < stateList.size() - 1) {
                states += stateList.get(i) + "/";
            } else {
                states += stateList.get(i);
            }
        }

        for (int i = 0; i < stateList.size(); i++) {
            String currentState = stateList.get(i);
            if (state.equalsIgnoreCase(currentState)) {
                isValid = true;
                break;
            }
        }
        if (!isValid) {
            throw new StateValidationException("You must choose one of the following states: "
                    + "(" + states + ").");
        }

        isValid = false;

        String products = "";

        for (int i = 0; i < productList.size(); i++) {
            if (i < productList.size() - 1) {
                products += productList.get(i) + "/";
            } else {
                products += productList.get(i);
            }
        }

        for (int i = 0; i < productList.size(); i++) {
            String currentProduct = productList.get(i);
            if (productType.equalsIgnoreCase(currentProduct)) {
                isValid = true;
                break;
            }
        }
        if (!isValid) {
            throw new ProductValidationException("Product type must be "
                    + "one of the following: (" + products + ").");
        }

        isValid = false;

        if (area.compareTo(new BigDecimal(0)) <= 0) {
            throw new AreaValidationException("Area must be a positive number.");
        } else {
            isValid = true;
        }
        return isValid;
    }

    private boolean validateFutureDate(LocalDate orderDate) throws DateValidationException {
        boolean isValid = false;
        LocalDate today = LocalDate.now();
        if (orderDate.isBefore(today)) {
            throw new DateValidationException("Date must be after today's date.");
        } else {
            isValid = true;
        }
        return isValid;
    }

    private boolean validateDate(LocalDate orderDate) throws DateValidationException {
        boolean isValid = false;
        LocalDate startOfCompany = LocalDate.ofYearDay(1980, 1);
        if (orderDate.isBefore(startOfCompany)) {
            throw new DateValidationException("Date must be after company's start date of 01/01/1980.");
        } else {
            isValid = true;
        }
        return isValid;
    }

    private boolean validateSingleOrderNumber(int orderNumber, LocalDate orderDate, List<Order> allOrdersOnDate)
            throws InvalidOrderNumberException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String stringOrderDate = orderDate.format(formatter);
        boolean isValid = false;

        for (Order toCheck : allOrdersOnDate) {
            if (orderNumber == toCheck.getOrderNumber()) {
                isValid = true;
                break;
            }
        }
        if (!isValid) {
            throw new InvalidOrderNumberException("There is not an order #" + orderNumber + " on " + stringOrderDate + ".");
        }
        return isValid;
    }

    private boolean validateOrderDate(LocalDate orderDate, List<Order> allOrdersOnDate)
            throws InvalidOrderDateException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String stringOrderDate = orderDate.format(formatter);
        boolean isValid = false;

        if (allOrdersOnDate == null || allOrdersOnDate.isEmpty()) {
            throw new InvalidOrderDateException("No orders on " + stringOrderDate + ".");
        }

        for (Order toCheck : allOrdersOnDate) {
            if (orderDate.equals(toCheck.getDate())) {
                isValid = true;
                break;
            }
        }
        if (!isValid) {
            throw new InvalidOrderDateException("No orders on " + stringOrderDate + ".");
        }
        return isValid;
    }
}
