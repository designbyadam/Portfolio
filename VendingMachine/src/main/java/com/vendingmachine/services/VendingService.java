/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vendingmachine.services;

import com.vendingmachine.daos.VendingAuditDao;
import com.vendingmachine.daos.VendingDao;
import com.vendingmachine.daos.VendingPersistenceException;
import com.vendingmachine.dtos.Change;
import com.vendingmachine.dtos.Item;
import com.vendingmachine.dtos.VendingMachine;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author afahrenkamp
 */
public class VendingService {
    
    VendingMachine machine = new VendingMachine();
    
    VendingDao dao;
    
    VendingAuditDao auditDao;
    
    public VendingService(VendingDao dao, VendingAuditDao auditDao) {
        this.dao = dao;
        this.auditDao = auditDao;
    }
    
    public PrintItemsResponse getAllItems() {
        PrintWriter out;
        PrintItemsResponse response = new PrintItemsResponse();
        List<Item> allItems = null;
        try {
            allItems = dao.getAllItems();
            machine.setToSell(allItems);
            response.setSuccess(true);
            response.setAllItems(allItems);
            
        } catch (VendingPersistenceException ex) {
            try {
                auditDao.writeAuditEntry(ex.getMessage());
            } catch (VendingPersistenceException ex1) {
                
            }
            response.setSuccess(false);
            response.setMessage(ex.getMessage());
        }
        
        return response;
    }
    
    public EnterMoneyResponse enterMoney(BigDecimal moneyEntered) {
        EnterMoneyResponse response = new EnterMoneyResponse();
        machine.enterMoney(moneyEntered);
        response.setTotalMoneyEntered(machine.getTotalMoney());
        response.setSuccess(true);
        
        return response;
    }
    
    public VendItemResponse vendItem(int id) {
        
        VendItemResponse toReturn = new VendItemResponse();
        List<Item> allItems = null;
        try {
            allItems = dao.getAllItems();
        } catch (VendingPersistenceException ex) {
            try {
                auditDao.writeAuditEntry(ex.getMessage());
            } catch (VendingPersistenceException ex1) {
                toReturn.setMessage(ex1.getMessage());
            }
            toReturn.setSuccess(false);
            toReturn.setMessage(ex.getMessage());
        }
        
        Item selectedItem;
        try {
            selectedItem = validateId(id, allItems);
            int newQuantity = selectedItem.getQuantity() - 1;
            selectedItem.setQuantity(newQuantity);
            machine.enterMoney(selectedItem.getPrice().multiply(new BigDecimal(-1)));
            selectedItem = dao.vendItem(selectedItem);
            toReturn.setChange(machine.returnChange());
            toReturn.setSuccess(true);
            toReturn.setVendedItem(selectedItem);
        } catch (VendingPersistenceException | InvalidItemException | InsufficientFundsException | OutOfStockException ex) {
            try {
                auditDao.writeAuditEntry(ex.getMessage());
            } catch (VendingPersistenceException ex1) {
                toReturn.setMessage(ex1.getMessage());
            }
            toReturn.setSuccess(false);
            toReturn.setMessage(ex.getMessage());
        }
        return toReturn;
    }
    
    public ChangeReturnResponse returnChange() {
        ChangeReturnResponse toReturn = new ChangeReturnResponse();
        Change moneyToReturn = new Change(machine.getTotalPenniesEntered());
        BigDecimal changeBack = machine.getTotalMoney();
        machine.enterMoney(machine.getTotalMoney().negate());
        toReturn.setChange(moneyToReturn);
        toReturn.setSuccess(true);
        
        return toReturn;
    }
    
    private Item validateId(int id, List<Item> allItems) throws InvalidItemException, InsufficientFundsException, OutOfStockException {
        Item foundItem = null;
        
        for (Item toCheck : allItems) {
            if (id == toCheck.getId()) {
                foundItem = toCheck;
                break;
            }
        }
        if (foundItem == null) {
            throw new InvalidItemException("Invalid Item Exception: "
                    + "No item exists that is associated with " + id);
        }
        
        if (machine.getTotalMoney().compareTo(foundItem.getPrice()) < 0) {
            throw new InsufficientFundsException("Insufficient Funds Exception: "
                    + "User did not enter enough money to purchase " + foundItem.getName());
        }
        if (foundItem.getQuantity() <= 0) {
            throw new OutOfStockException("Out Of Stock Exception: "
                    + "The machine is out of " + foundItem.getName());
        }
        return foundItem;
    }
    
}
