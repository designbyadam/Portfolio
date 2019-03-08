/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vendingmachine.daos;

/**
 *
 * @author afahrenkamp
 */
public class AuditDaoStub implements VendingAuditDao {

    @Override
    public void writeAuditEntry(String entry) throws VendingPersistenceException {
 //do literally nothing...
    }
    
}
