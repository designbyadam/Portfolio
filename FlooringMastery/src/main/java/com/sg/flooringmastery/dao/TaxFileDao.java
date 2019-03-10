/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Tax;
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
public class TaxFileDao implements TaxDao {

    String path;

    public TaxFileDao(String path) {
        this.path = path;
    }

    public TaxFileDao() {
        path = "/home/designbyadam/FlooringFiles/Tax/Tax.txt";
    }

    @Override
    public List<Tax> getTaxes() throws TaxPersistenceException {

        List<Tax> toReturn = new ArrayList<Tax>();

        File taxFile = new File(path);

        try {
            Scanner reader = new Scanner(new BufferedReader(new FileReader(taxFile)));
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] cells = line.split(",");
                Tax toAdd = new Tax();

                toAdd.setStatePurchasedIn(cells[0]);
                toAdd.setTaxRate(new BigDecimal(cells[1]));
                toReturn.add(toAdd);
            }

        } catch (FileNotFoundException ex) {
            throw new TaxPersistenceException("Could not load tax data into memory.");
        }
        return toReturn;
    }

    @Override
    public Tax getTaxRate(String stateAbbrev) throws TaxPersistenceException {
        Tax toReturn = new Tax();

        try {
            List<Tax> taxList = getTaxes();

            for (int i = 0; i < taxList.size(); i++) {
                if (stateAbbrev.equalsIgnoreCase(taxList.get(i).getStatePurchasedIn())) {
                    toReturn.setTaxRate(taxList.get(i).getTaxRate());
                    toReturn.setStatePurchasedIn(taxList.get(i).getStatePurchasedIn());
                    break;
                }

            }
        } catch (TaxPersistenceException ex) {
            throw new TaxPersistenceException("Could not load tax data into memory.", ex);
        }
        return toReturn;
    }

}
