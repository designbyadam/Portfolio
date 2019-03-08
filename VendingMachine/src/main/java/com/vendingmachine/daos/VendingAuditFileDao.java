/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vendingmachine.daos;

import com.vendingmachine.services.VendingService;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

/**
 *
 * @author afahrenkamp
 */
public class VendingAuditFileDao implements VendingAuditDao {
    
    public static final String AUDIT_FILE = "/home/designbyadam/Downloads/vendingaudit.txt";

    public void writeAuditEntry(String entry) throws VendingPersistenceException {
        PrintWriter out;
        
        try {
            out = new PrintWriter(new FileWriter(AUDIT_FILE, true));
        } catch (IOException e) {
            throw new VendingPersistenceException("Could not persist audit information.", e);
        }
        
        LocalDateTime timestamp = LocalDateTime.now();
        out.println(timestamp.toString() +  " : " + entry);
        out.flush();
    }
    
}
