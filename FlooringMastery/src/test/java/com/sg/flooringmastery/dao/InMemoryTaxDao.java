/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Tax;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author afahrenkamp
 */
public class InMemoryTaxDao implements TaxDao {

    List<Tax> allTaxes = new ArrayList<>();

    public InMemoryTaxDao() {
        Tax ohio = new Tax();
        ohio.setStatePurchasedIn("OH");
        ohio.setTaxRate(new BigDecimal("6.25"));
        allTaxes.add(ohio);

        Tax pennsylvania = new Tax();
        pennsylvania.setStatePurchasedIn("PA");
        pennsylvania.setTaxRate(new BigDecimal("6.75"));
        allTaxes.add(pennsylvania);

        Tax michigan = new Tax();
        michigan.setStatePurchasedIn("MI");
        michigan.setTaxRate(new BigDecimal("5.75"));
        allTaxes.add(michigan);

        Tax indiana = new Tax();
        indiana.setStatePurchasedIn("IN");
        indiana.setTaxRate(new BigDecimal("6.00"));
        allTaxes.add(indiana);
    }

    @Override
    public List<Tax> getTaxes() throws TaxPersistenceException {

        return allTaxes;
    }

    @Override
    public Tax getTaxRate(String stateAbbrev) throws TaxPersistenceException {
        Tax toReturn = new Tax();

        List<Tax> taxList = getTaxes();

        for (int i = 0; i < taxList.size(); i++) {
            if (stateAbbrev.equalsIgnoreCase(taxList.get(i).getStatePurchasedIn())) {
                toReturn.setTaxRate(taxList.get(i).getTaxRate());
                toReturn.setStatePurchasedIn(taxList.get(i).getStatePurchasedIn());
                break;
            }

        }
        return toReturn;
    }

}
