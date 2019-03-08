/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vendingmachine.daos;

import com.vendingmachine.dtos.Item;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author afahrenkamp
 */
public class InMemoryDao implements VendingDao {
    
    List<Item> allItems = new ArrayList();
    
    public InMemoryDao() {
        Item gatorade = new Item();
        gatorade.setId(1);
        gatorade.setName("gatorade");
        gatorade.setPriceInPennies(100);
        gatorade.setQuantity(10);
        allItems.add(gatorade);
        
        Item snickers = new Item();
        snickers.setId(2);
        snickers.setName("snickers");
        snickers.setPriceInPennies(100);
        snickers.setQuantity(0);
        allItems.add(snickers);
    }

    @Override
    public List<Item> getAllItems() {
        return allItems;
    }

    @Override
    public Item getItemById(int id) {
        Item toReturn = null;
        for (Item toCheck : allItems) {
            if (toCheck.getId() == id) {
                toReturn = toCheck;
                break;
            } 
        }
        return toReturn;
    }

    @Override
    public Item vendItem(Item toVend) {
        Item toReturn = getItemById(toVend.getId());
        return toReturn;
    }
    
}
