/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Product;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author afahrenkamp
 */
public class InMemoryProductsDao implements ProductsDao {

    List<Product> allProducts = new ArrayList<>();

    public InMemoryProductsDao() {

        Product carpet = new Product();
        carpet.setProductType("carpet");
        carpet.setItemCostSqFt(new BigDecimal("2.25"));
        carpet.setLaborCostSqFt(new BigDecimal("2.10"));
        allProducts.add(carpet);

        Product laminate = new Product();
        laminate.setProductType("laminate");
        laminate.setItemCostSqFt(new BigDecimal("1.75"));
        laminate.setLaborCostSqFt(new BigDecimal("2.10"));
        allProducts.add(laminate);

        Product tile = new Product();
        tile.setProductType("tile");
        tile.setItemCostSqFt(new BigDecimal("3.50"));
        tile.setLaborCostSqFt(new BigDecimal("4.15"));
        allProducts.add(tile);

        Product wood = new Product();
        wood.setProductType("wood");
        wood.setItemCostSqFt(new BigDecimal("5.15"));
        wood.setLaborCostSqFt(new BigDecimal("4.75"));
        allProducts.add(wood);
        
    }

    @Override
    public List<Product> getProducts() throws ProductPersistenceException {
        return allProducts;
    }

    @Override
    public Product getProductCosts(String productType) throws ProductPersistenceException {
        Product toReturn = new Product();

        List<Product> productList = getProducts();

        for (int i = 0; i < productList.size(); i++) {
            if (productType.equalsIgnoreCase(productList.get(i).getProductType())) {
                toReturn.setProductType(productList.get(i).getProductType());
                toReturn.setItemCostSqFt(productList.get(i).getItemCostSqFt());
                toReturn.setLaborCostSqFt(productList.get(i).getLaborCostSqFt());
            }
        }

        return toReturn;
    }

}
