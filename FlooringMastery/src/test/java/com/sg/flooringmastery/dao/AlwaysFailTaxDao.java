/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Tax;
import java.util.List;

/**
 *
 * @author afahrenkamp
 */
public class AlwaysFailTaxDao implements TaxDao {

    @Override
    public List<Tax> getTaxes() throws TaxPersistenceException {
        throw new TaxPersistenceException("Could not load tax data.");
    }

    @Override
    public Tax getTaxRate(String stateAbbrev) throws TaxPersistenceException {
        throw new TaxPersistenceException("Could not load tax data.");
    }

}
