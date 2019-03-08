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
public interface VendingDao {


    public List<Item> getAllItems() throws VendingPersistenceException;

    public Item getItemById(int id) throws VendingPersistenceException;

    public Item vendItem(Item toVend) throws VendingPersistenceException;

    
}
