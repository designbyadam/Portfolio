/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vendingmachine.daos;

import java.io.IOException;

/**
 *
 * @author afahrenkamp
 */
public class VendingPersistenceException extends Exception {

    public VendingPersistenceException(String message) {
        super(message);
    }
    
        public VendingPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
