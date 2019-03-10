/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Product;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author afahrenkamp
 */
public class ProductsFileDao implements ProductsDao {

    String path;
 

    public ProductsFileDao(String path) {
        this.path = path;
    }

    public ProductsFileDao() {
        path = "/home/designbyadam/FlooringFiles/Products/Products.txt";
    }

    @Override
    public List<Product> getProducts() throws ProductPersistenceException {
        List<Product> toReturn = new ArrayList<Product>();

        File productFile = new File(path);

        try {
            Scanner reader = new Scanner(new BufferedReader(new FileReader(productFile)));
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] cells = line.split(",");
                Product toAdd = new Product();

                toAdd.setProductType(cells[0]);
                toAdd.setItemCostSqFt(new BigDecimal(cells[1]));
                toAdd.setLaborCostSqFt(new BigDecimal(cells[2]));

                toReturn.add(toAdd);
            }

        } catch (FileNotFoundException ex) {
            throw new ProductPersistenceException("Could not load product data into memory.", ex);
        }
        return toReturn;
    }

    @Override
    public Product getProductCosts(String productType) throws ProductPersistenceException {
        Product toReturn = new Product();

        List<Product> productList;
        try {
            productList = getProducts();

            for (int i = 0; i < productList.size(); i++) {
                if (productType.equalsIgnoreCase(productList.get(i).getProductType())) {
                    toReturn.setProductType(productList.get(i).getProductType());
                    toReturn.setItemCostSqFt(productList.get(i).getItemCostSqFt());
                    toReturn.setLaborCostSqFt(productList.get(i).getLaborCostSqFt());
                }
            }
        } catch (ProductPersistenceException ex) {
            throw new ProductPersistenceException("Could not load product data into memory.", ex);
        }

        return toReturn;
    }

}
