/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;

/**
 *
 * @author afahrenkamp
 */
public class AddOrderResponse extends Response {

    private Order currentOrder;

    private Product currentProduct;
    
    private Tax currentTax;

    /**
     * @return the currentOrder
     */
    public Order getCurrentOrder() {
        return currentOrder;
    }

    /**
     * @param currentOrder the currentOrder to set
     */
    public void setCurrentOrder(Order currentOrder) {
        this.currentOrder = currentOrder;
    }

    /**
     * @return the currentProduct
     */
    public Product getCurrentProduct() {
        return currentProduct;
    }

    /**
     * @param currentProduct the currentProduct to set
     */
    public void setCurrentProduct(Product currentProduct) {
        this.currentProduct = currentProduct;
    }

    /**
     * @return the currentTax
     */
    public Tax getCurrentTax() {
        return currentTax;
    }

    /**
     * @param currentTax the currentTax to set
     */
    public void setCurrentTax(Tax currentTax) {
        this.currentTax = currentTax;
    }

}
