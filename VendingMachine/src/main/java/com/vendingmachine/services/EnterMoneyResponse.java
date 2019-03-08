/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vendingmachine.services;

import java.math.BigDecimal;

/**
 *
 * @author afahrenkamp
 */
public class EnterMoneyResponse extends Response {

    private BigDecimal totalMoneyEntered;

    /**
     * @return the totalMoneyEntered
     */
    public BigDecimal getTotalMoneyEntered() {
        return totalMoneyEntered;
    }

    /**
     * @param totalMoneyEntered the totalMoneyEntered to set
     */
    public void setTotalMoneyEntered(BigDecimal totalMoneyEntered) {
        this.totalMoneyEntered = totalMoneyEntered;
    }

}
