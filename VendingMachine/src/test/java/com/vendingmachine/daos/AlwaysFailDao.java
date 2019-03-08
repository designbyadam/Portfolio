/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vendingmachine.daos;

import com.vendingmachine.dtos.Item;
import java.util.List;

/**
 *
 * @author afahrenkamp
 */
public class AlwaysFailDao implements VendingDao {

    @Override
    public List<Item> getAllItems() throws VendingPersistenceException {
        throw new VendingPersistenceException("Error retrieving items.");
    }

    @Override
    public Item getItemById(int id) throws VendingPersistenceException {
        throw new VendingPersistenceException("Error retrieving items.");
    }

    @Override
    public Item vendItem(Item toVend) throws VendingPersistenceException {
        throw new VendingPersistenceException("Error retrieving items.");
    }
    

}
