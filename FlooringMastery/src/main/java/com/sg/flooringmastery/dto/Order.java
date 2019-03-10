/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dto;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author afahrenkamp
 */
public class Order {

    private LocalDate date;
    private int orderNumber;
    private String customerName;
    private String state;
    private BigDecimal taxRate;
    private String productType;
    private BigDecimal area;
    private BigDecimal itemCostSqFt;
    private BigDecimal laborCostSqFt;
    

    /**
     * @return the date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * @return the orderNumber
     */
    public int getOrderNumber() {
        return orderNumber;
    }

    /**
     * @param orderNumber the orderNumber to set
     */
    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    /**
     * @return the customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @param customerName the customerName to set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the taxRate
     */
    public BigDecimal getTaxRate() {
        return taxRate;
    }

    /**
     * @param taxRate the taxRate to set
     */
    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    /**
     * @return the productType
     */
    public String getProductType() {
        return productType;
    }

    /**
     * @param productType the productType to set
     */
    public void setProductType(String productType) {
        this.productType = productType;
    }

    /**
     * @return the area
     */
    public BigDecimal getArea() {
        return area;
    }

    /**
     * @param area the area to set
     */
    public void setArea(BigDecimal area) {
        this.area = area;
    }

    /**
     * @return the itemCostSqFt
     */
    public BigDecimal getItemCostSqFt() {
        return itemCostSqFt;
    }

    /**
     * @param itemCostSqFt the itemCostSqFt to set
     */
    public void setItemCostSqFt(BigDecimal itemCostSqFt) {
        this.itemCostSqFt = itemCostSqFt;
    }

    /**
     * @return the laborCostSqFt
     */
    public BigDecimal getLaborCostSqFt() {
        return laborCostSqFt;
    }

    /**
     * @param laborCostSqFt the laborCostSqFt to set
     */
    public void setLaborCostSqFt(BigDecimal laborCostSqFt) {
        this.laborCostSqFt = laborCostSqFt;
    }

    public BigDecimal getTotalMaterialCost() {
        BigDecimal toReturn = area.multiply(itemCostSqFt).setScale(2, RoundingMode.HALF_UP);
        return toReturn;
    }

    public BigDecimal getTotalLaborCost() {
        BigDecimal toReturn = area.multiply(laborCostSqFt).setScale(2, RoundingMode.HALF_UP);
        return toReturn;
    }

    public BigDecimal getTotalTax() {
        BigDecimal totalMaterialCost = getTotalMaterialCost();
        BigDecimal totalLaborCost = getTotalLaborCost();
        BigDecimal decimalTaxRate = taxRate.divide(new BigDecimal(100));
        BigDecimal preTax = totalMaterialCost.add(totalLaborCost);

        BigDecimal toReturn = preTax.multiply(decimalTaxRate).setScale(2, RoundingMode.HALF_UP);
        return toReturn;
    }

    public BigDecimal getOrderTotal() {
        BigDecimal totalMaterialCost = getTotalMaterialCost();
        BigDecimal totalLaborCost = getTotalLaborCost();
        BigDecimal totalTax = getTotalTax();
        BigDecimal toReturn = (totalMaterialCost.add(totalLaborCost).add(totalTax)).setScale(2, RoundingMode.HALF_UP);
        return toReturn;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + Objects.hashCode(this.date);
        hash = 43 * hash + this.orderNumber;
        hash = 43 * hash + Objects.hashCode(this.customerName);
        hash = 43 * hash + Objects.hashCode(this.state);
        hash = 43 * hash + Objects.hashCode(this.taxRate);
        hash = 43 * hash + Objects.hashCode(this.productType);
        hash = 43 * hash + Objects.hashCode(this.area);
        hash = 43 * hash + Objects.hashCode(this.itemCostSqFt);
        hash = 43 * hash + Objects.hashCode(this.laborCostSqFt);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Order other = (Order) obj;
        if (this.orderNumber != other.orderNumber) {
            return false;
        }
        if (!Objects.equals(this.customerName, other.customerName)) {
            return false;
        }
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        if (!Objects.equals(this.productType, other.productType)) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        if (!Objects.equals(this.taxRate, other.taxRate)) {
            return false;
        }
        if (!Objects.equals(this.area, other.area)) {
            return false;
        }
        if (!Objects.equals(this.itemCostSqFt, other.itemCostSqFt)) {
            return false;
        }
        if (!Objects.equals(this.laborCostSqFt, other.laborCostSqFt)) {
            return false;
        }
        return true;
    }
    
    
}
