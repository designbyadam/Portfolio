/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Product;
import java.util.List;

/**
 *
 * @author afahrenkamp
 */
public class AlwaysFailProductsDao implements ProductsDao {

    @Override
    public List<Product> getProducts() throws ProductPersistenceException {
        throw new ProductPersistenceException("Could not load product data.");
    }

    @Override
    public Product getProductCosts(String productType) throws ProductPersistenceException {
        throw new ProductPersistenceException("Could not load product data.");
    }

}
