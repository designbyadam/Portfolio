/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author afahrenkamp
 */
public class Product {
    private String productType;
    private BigDecimal itemCostSqFt;
    private BigDecimal laborCostSqFt;

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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.productType);
        hash = 97 * hash + Objects.hashCode(this.itemCostSqFt);
        hash = 97 * hash + Objects.hashCode(this.laborCostSqFt);
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
        final Product other = (Product) obj;
        if (!Objects.equals(this.productType, other.productType)) {
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
