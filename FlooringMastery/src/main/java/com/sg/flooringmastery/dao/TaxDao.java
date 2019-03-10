/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Tax;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author afahrenkamp
 */
public interface TaxDao {
    
    public List<Tax> getTaxes() throws TaxPersistenceException;
    
    public Tax getTaxRate(String stateAbbrev) throws TaxPersistenceException;
    
}