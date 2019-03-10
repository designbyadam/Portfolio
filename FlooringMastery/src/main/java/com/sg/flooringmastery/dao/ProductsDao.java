/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Product;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author afahrenkamp
 */
public interface ProductsDao {

    public List<Product> getProducts() throws ProductPersistenceException;

    public Product getProductCosts(String productType) throws ProductPersistenceException;

}
